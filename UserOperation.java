import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.*;

import ModelClasses.Customer;

public class UserOperation {
    private static UserOperation instance;
    private final String userFilePath = "data/users.txt";

    private UserOperation() {}

    public static UserOperation getInstance() {
        if (instance == null) {
            instance = new UserOperation();
        }
        return instance;
    }

    public String generateUniqueUserId() {
        return "u_" + String.format("%010d", new Random().nextInt(1_000_000_000));
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

    @SuppressWarnings({"empty-statement", "CallToPrintStackTrace"})
    public boolean checkUsernameExist(String userName) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(userFilePath));
            for (String line : lines) {
                JSONObject user = new JSONObject(line);
                if (user.getString("user_name").equals(userName)) return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        return false;
    }

    public boolean validateUsername(String userName) {
        return userName.matches("[a-zA-Z_]{5,}");
    }

    public boolean validatePassword(String userPassword) {
        return userPassword.matches("(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{5,}");
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public User login(String userName, String userPassword) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(userFilePath));
            for (String line : lines) {
                JSONObject userJson = new JSONObject(line);
                String storedName = userJson.getString("user_name");
                String storedPassword = decryptPassword(userJson.getString("user_password"));
                if (storedName.equals(userName) && storedPassword.equals(userPassword)) {
                    if (userJson.getString("user_role").equals("admin")) {
                        return new Admin(
                            userJson.getString("user_id"),
                            storedName,
                            userJson.getString("user_password"),
                            userJson.getString("user_register_time"),
                            "admin"
                        );
                    } else {
                        return new Customer(
                            userJson.getString("user_id"),
                            storedName,
                            userJson.getString("user_password"),
                            userJson.getString("user_register_time"),
                            "customer",
                            userJson.getString("user_email"),
                            userJson.getString("user_mobile")
                        );
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}