package st10474409_chatapp;

import java.util.Scanner;
import java.util.regex.Pattern;

public class ST10474409_ChatApp {
    private String username;
    private String password;
    private String cellNumber;
    private String firstName;
    private String lastName;

    // === Constructors ===

    // Default constructor
    public ST10474409_ChatApp() {
        this.username = "";
        this.password = "";
        this.cellNumber = "";
        this.firstName = "";
        this.lastName = "";
    }

    // Parameterized constructor
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
        }

        scanner.close();
    }

    // === Validation Methods ===

    public boolean checkUsername(String username) {
        return username.length() <= 5 && username.contains("_");
    }

    public boolean checkPasswordComplexity(String password) {
        if (password.length() < 8) return false;
        if (!Pattern.compile("[A-Z]").matcher(password).find()) return false;
        if (!Pattern.compile("[0-9]").matcher(password).find()) return false;
        return Pattern.compile("[^A-Za-z0-9]").matcher(password).find(); // special char
    }

    public boolean checkCellPhoneNumber(String cellNumber) {
        String pattern = "^\\+\\d{1,3}\\d{7,10}$"; // E.g., +27831234567
        return Pattern.matches(pattern, cellNumber);
    }

    // === Registration ===

    public String registerUser(Scanner scanner) {
        System.out.print("Enter your first name: ");
        String firstNameInput = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastNameInput = scanner.nextLine();

        // Username validation
        System.out.print("Enter username (must contain _ and be <5 characters): ");
        String usernameInput = scanner.nextLine();
        if (!checkUsername(usernameInput)) {
            return "Username is not correctly formatted. Please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        // Password validation
        System.out.print("Enter password (<8 chars, with capital letter, number, and special character): ");
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
