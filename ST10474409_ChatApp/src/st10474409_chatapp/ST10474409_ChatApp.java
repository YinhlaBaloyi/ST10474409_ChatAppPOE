package st10474409_chatapp;

import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class ST10474409_ChatApp {
    private String username;
    private String password;
    private String cellNumber;
    private String firstName;
    private String lastName;
    private boolean isLoggedIn = false;

    // Constructors
    public ST10474409_ChatApp() {
        this.username = "";
        this.password = "";
        this.cellNumber = "";
        this.firstName = "";
        this.lastName = "";
    }

    public ST10474409_ChatApp(String username, String password, String cellNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellNumber = cellNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // === Main method ===
    public static void main(String[] args) {
        ST10474409_ChatApp loginSystem = new ST10474409_ChatApp();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Registration ===");
        String registrationResult = loginSystem.registerUser(scanner);
        System.out.println("\n" + registrationResult);

        if (registrationResult.toLowerCase().contains("successful")) {
            System.out.println("\n=== Login ===");
            System.out.print("Enter username: ");
            String enteredUsername = scanner.nextLine();

            System.out.print("Enter password: ");
            String enteredPassword = scanner.nextLine();

            boolean loginSuccess = loginSystem.loginUser(enteredUsername, enteredPassword);
            System.out.println("\n" + loginSystem.returnLoginStatus(loginSuccess));
            
            if (loginSuccess) {
                loginSystem.isLoggedIn = true;
                loginSystem.runMessagingSystem(scanner);
            }
        }

        scanner.close();
    }

    // Messaging System
    private void runMessagingSystem(Scanner scanner) {
        System.out.println("\n=== QuickChat Messaging System ===");
        System.out.println("Welcome to QuickChat.");
        
        boolean running = true;
        
        while (running) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) View Stored Messages");
            System.out.println("4) Quit");
            System.out.print("Enter your choice: ");
            
            int choice = getIntInput(scanner);
            
            switch (choice) {
                case 1:
                    sendMessages(scanner);
                    break;
                case 2:
                    System.out.println("Coming Soon.");
                    break;
                case 3:
                    viewStoredMessages();
                    break;
                case 4:
                    System.out.println("Thank you for using QuickChat. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void sendMessages(Scanner scanner) {
        System.out.print("How many messages do you wish to send? ");
        int numMessages = getIntInput(scanner);
        
        for (int i = 0; i < numMessages; i++) {
            System.out.println("\n--- Message " + (i + 1) + " ---");
            
            Message message = new Message();
            
            // Get recipient number
            String recipient;
            do {
                System.out.print("Enter recipient cell number (with international code, e.g., +27718693002): ");
                recipient = scanner.nextLine();
                if (message.checkRecipientCell(recipient) == 0) {
                    System.out.println("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.");
                }
            } while (message.checkRecipientCell(recipient) == 0);
            message.setRecipient(recipient);
            
            // Get message content
            String messageText;
            do {
                System.out.print("Enter your message (max 250 characters): ");
                messageText = scanner.nextLine();
                if (messageText.length() > 250) {
                    int excess = messageText.length() - 250;
                    System.out.println("Message exceeds 250 characters by " + excess + ", please reduce size.");
                } else if (messageText.isEmpty()) {
                    System.out.println("Message cannot be empty.");
                }
            } while (messageText.length() > 250 || messageText.isEmpty());
            message.setMessage(messageText);
            
            // Display message details
            System.out.println("\nMessage Details:");
            System.out.println("Message ID: " + message.getMessageID());
            System.out.println("Message Hash: " + message.getMessageHash());
            System.out.println("Recipient: " + message.getRecipient());
            System.out.println("Message: " + message.getMessage());
            
            // Display message options
            System.out.println("\nChoose an option for this message:");
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Enter your choice: ");
            int sendChoice = getIntInput(scanner);
            
            String result = message.sentMessage(sendChoice);
            System.out.println(result);
            
            if (sendChoice == 1) {
                // Display message details in JOptionPane
                JOptionPane.showMessageDialog(null, 
                    "Message Details:\n" + message.printMessages(),
                    "Message Sent", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
        // Display total messages sent
        System.out.println("\nTotal messages sent in this session: " + Message.returnTotalMessages());
    }

    private void viewStoredMessages() {
        String storedMessages = JSONHandler.getAllMessages();
        System.out.println("\n" + storedMessages);
    }

    private int getIntInput(Scanner scanner) {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return input;
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Validation Methods
    public boolean checkUsername(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;
        if (!Pattern.compile("[A-Z]").matcher(password).find()) return false;
        if (!Pattern.compile("[0-9]").matcher(password).find()) return false;
        return Pattern.compile("[^A-Za-z0-9]").matcher(password).find();
    }

    public boolean checkCellPhoneNumber(String cellNumber) {
        String pattern = "^\\+\\d{1,3}\\d{7,10}$";
        return Pattern.matches(pattern, cellNumber);
    }

    // Registration Process
    public String registerUser(Scanner scanner) {
        System.out.print("Enter your first name: ");
        String firstNameInput = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastNameInput = scanner.nextLine();

        // Username validation
        System.out.print("Enter username (must contain _ and be ≤5 characters): ");
        String usernameInput = scanner.nextLine();
        if (!checkUsername(usernameInput)) {
            return "Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        // Password validation
        System.out.print("Enter password (≥8 chars, with capital letter, number, and special character): ");
        String passwordInput = scanner.nextLine();
        if (!checkPasswordComplexity(passwordInput)) {
            return "Password is not correctly formatted. Please ensure the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        // Cell number validation
        System.out.print("Enter cell phone number (with international code, e.g., +27831234567): ");
        String cellNumberInput = scanner.nextLine();
        if (!checkCellPhoneNumber(cellNumberInput)) {
            return "Cell phone number is incorrectly formatted or does not contain a valid international code.";
        }

        // Save validated data
        this.firstName = firstNameInput;
        this.lastName = lastNameInput;
        this.username = usernameInput;
        this.password = passwordInput;
        this.cellNumber = cellNumberInput;

        return "Registration successful!";
    }

    // === Login ===
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(this.username) && enteredPassword.equals(this.password);
    }

    public String returnLoginStatus(boolean isSuccessful) {
        if (isSuccessful) {
            return "Welcome " + firstName + " " + lastName + ", it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}
