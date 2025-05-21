import java.util.Random;

public class UserOperation {

    private static UserOperation instance;

    private UserOperation() {
        // Private constructor to enforce Singleton pattern
    }

    public static UserOperation getInstance() {
        if (instance == null) {
            instance = new UserOperation();
        }
        return instance;
    }

    public String generateUniqueUserId() {
        String prefix = "u_";
        String digits = String.format("%010d", new Random().nextInt(1000000000));
        return prefix + digits;
    }

    public String encryptPassword(String userPassword) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder randomString = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < userPassword.length() * 2; i++) {
            randomString.append(characters.charAt(rand.nextInt(characters.length())));
        }

        StringBuilder encrypted = new StringBuilder("^^");
        for (int i = 0; i < userPassword.length(); i++) {
            encrypted.append(randomString.substring(i * 2, i * 2 + 2)).append(userPassword.charAt(i));
        }
        encrypted.append("$$");
        return encrypted.toString();
    }

    public String decryptPassword(String encryptedPassword) {
        String body = encryptedPassword.substring(2, encryptedPassword.length() - 2);
        StringBuilder decrypted = new StringBuilder();
        for (int i = 2; i < body.length(); i += 3) {
            decrypted.append(body.charAt(i));
        }
        return decrypted.toString();
    }

    public boolean checkUsernameExist(String userName) {
        // Implement logic to check if username exists in data/users.txt
        return false; 
    }

    public boolean validateUsername(String userName) {
        return userName.matches("[a-zA-Z_]{5,}");
    }

    public boolean validatePassword(String userPassword) {
        return userPassword.matches("(?=.*[A-Za-z])(?=.\\d)[A-Za-z\\d]{5,}");
    }

    public User login(String userName, String userPassword) {
        // Implement login logic
        return null; 
    }
}