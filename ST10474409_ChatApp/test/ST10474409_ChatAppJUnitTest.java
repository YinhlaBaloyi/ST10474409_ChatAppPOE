import org.junit.Test;
import static org.junit.Assert.*;
import st10474409_chatapp.ST10474409_ChatApp;

public class ST10474409_ChatAppTest {
    private ST10474409_ChatApp loginSystem = new ST10474409_ChatApp();

    @Test
    public void testCheckUsername() {
        // Correct format
        assertTrue(loginSystem.checkUsername("kyl_1"));
        assertTrue(loginSystem.checkUsername("a_b"));

        // Incorrect format
        assertFalse(loginSystem.checkUsername("kyle!!!!!!!"));
        assertFalse(loginSystem.checkUsername("kyle"));
        assertFalse(loginSystem.checkUsername("ky le"));
    }

    @Test
    public void testCheckPasswordComplexity() {
        // Correct passwords
        assertTrue(loginSystem.checkPasswordComplexity("Ch&&sec@ke99!"));
        assertTrue(loginSystem.checkPasswordComplexity("A1@bcdefg"));

        // Incorrect passwords
        assertFalse(loginSystem.checkPasswordComplexity("password"));        // No caps, numbers, special chars
        assertFalse(loginSystem.checkPasswordComplexity("Password"));        // No numbers or special chars
        assertFalse(loginSystem.checkPasswordComplexity("P@ssw0r"));         // Too short
        assertFalse(loginSystem.checkPasswordComplexity("password123"));     // No caps or special chars
    }

    @Test
    public void testCheckCellPhoneNumber() {
        // Correct formats
        assertTrue(loginSystem.checkCellPhoneNumber("+27831234567"));
        assertTrue(loginSystem.checkCellPhoneNumber("+1234567890"));

        // Incorrect formats
        assertFalse(loginSystem.checkCellPhoneNumber("08966553"));       // No international code
        assertFalse(loginSystem.checkCellPhoneNumber("+123456"));        // Too short
        assertFalse(loginSystem.checkCellPhoneNumber("27831234567"));    // Missing +
        assertFalse(loginSystem.checkCellPhoneNumber("+abc1234567"));    // Contains letters
    }

    @Test
    public void testLoginUser() {
        // Use constructor or reflection-friendly access
        loginSystem = new ST10474409_ChatApp("test_1", "P@ssw0rd123", "+27831234567", "Test", "User");

        // Successful login
        assertTrue(loginSystem.loginUser("test_1", "P@ssw0rd123"));

        // Failed logins
        assertFalse(loginSystem.loginUser("wrong", "P@ssw0rd123"));
        assertFalse(loginSystem.loginUser("test_1", "wrong"));
        assertFalse(loginSystem.loginUser("wrong", "wrong"));
    }

    @Test
    public void testReturnLoginStatus() {
        // Initialize with known names
        loginSystem = new ST10474409_ChatApp("test_1", "P@ssw0rd123", "+27831234567", "John", "Doe");

        // Successful login message
        assertEquals("Welcome John Doe, it is great to see you again.",
                     loginSystem.returnLoginStatus(true));

        // Failed login message
        assertEquals("Username or password incorrect, please try again.",
                     loginSystem.returnLoginStatus(false));
    }
}
