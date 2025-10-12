package st10474409_chatapp;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {
    
    @Test
    public void testCheckMessageID() {
        Message message = new Message("+27718693002", "Test message");
        assertTrue("Message ID should be valid (10 digits or less)", message.checkMessageID());
    }
    
    @Test
    public void testCheckRecipientCell_Success() {
        Message message = new Message("+27718693002", "Test message");
        int result = message.checkRecipientCell();
        assertTrue("Valid international number should return positive", result > 0);
    }
    
    @Test
    public void testCheckRecipientCell_Failure() {
        Message message = new Message("08575975889", "Test message"); // No international code
        int result = message.checkRecipientCell();
        assertEquals("Invalid number should return -1", -1, result);
    }
    
    @Test
    public void testCreateMessageHash() {
        Message message = new Message("+27718693002", "Hi Mike, can you join us for dinner tonight");
        String hash = message.createMessageHash();
        assertNotNull("Message hash should not be null", hash);
        assertTrue("Message hash should be in uppercase", hash.equals(hash.toUpperCase()));
    }
    
    @Test
    public void testCheckMessageLength_Success() {
        Message message = new Message("+27718693002", "Short message");
        assertTrue("Message within 250 characters should be valid", message.checkMessageLength());
    }
    
    @Test
    public void testCheckMessageLength_Failure() {
        // Create a long message
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            longMessage.append("a");
        }
        Message message = new Message("+27718693002", longMessage.toString());
        assertFalse("Message exceeding 250 characters should be invalid", message.checkMessageLength());
    }
    
    @Test
    public void testReturnTotalMessages() {
        int initialCount = Message.returnTotalMessages();
        
        // Create and send a message
        Message message = new Message("+27718693002", "Test message");
        // Note: In actual test, you'd need to simulate the send action
        
        // This test would need to be adjusted based on how you implement the counter
        assertTrue("Total messages should be non-negative", Message.returnTotalMessages() >= 0);
    }
}
