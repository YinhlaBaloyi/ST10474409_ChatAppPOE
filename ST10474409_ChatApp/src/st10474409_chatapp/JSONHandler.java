package st10474409_chatapp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONHandler {
    private static final String FILE_NAME = "messages.json";

    public static void storeMessage(Message message) {
        try {
            // Read existing messages
            List<JSONObject> messages = readAllMessages();
            
            // Create JSON object for new message
            JSONObject messageJson = new JSONObject();
            messageJson.put("messageID", message.getMessageID());
            messageJson.put("messageCount", message.getMessageCount());
            messageJson.put("recipient", message.getRecipient());
            messageJson.put("message", message.getMessage());
            messageJson.put("messageHash", message.getMessageHash());
            messageJson.put("status", message.getStatus());
            
            // Add new message to list
            messages.add(messageJson);
            
            // Write back to file
            writeMessagesToFile(messages);
            
        } catch (IOException e) {
            System.err.println("Error storing message: " + e.getMessage());
        }
    }

    private static List<JSONObject> readAllMessages() throws IOException {
        List<JSONObject> messages = new ArrayList<>();
        
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return messages;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            
            if (content.length() > 0) {
                JSONArray jsonArray = new JSONArray(content.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    messages.add(jsonArray.getJSONObject(i));
                }
            }
        }
        
        return messages;
    }

    private static void writeMessagesToFile(List<JSONObject> messages) throws IOException {
        try (FileWriter file = new FileWriter(FILE_NAME)) {
            JSONArray jsonArray = new JSONArray(messages);
            file.write(jsonArray.toString(4)); // 4 spaces for indentation
            file.flush();
        }
    }

    public static String getAllMessages() {
        try {
            List<JSONObject> messages = readAllMessages();
            if (messages.isEmpty()) {
                return "No messages stored.";
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("Stored Messages:\n");
            for (JSONObject msg : messages) {
                sb.append("MessageID: ").append(msg.getString("messageID"))
                  .append(", Recipient: ").append(msg.getString("recipient"))
                  .append(", Status: ").append(msg.getString("status"))
                  .append("\n");
            }
            return sb.toString();
            
        } catch (IOException e) {
            return "Error reading messages: " + e.getMessage();
        }
    }
}