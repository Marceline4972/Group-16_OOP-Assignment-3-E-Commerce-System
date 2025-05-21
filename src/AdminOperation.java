
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AdminOperation {

    private static AdminOperation instance;
    private static final String USERS_FILE_PATH = "users.txt";

    private AdminOperation() {
        // Private constructor for Singleton
    }

    public static AdminOperation getInstance() {
        if (instance == null) {
            instance = new AdminOperation();
        }
        return instance;
    }

    public void registerAdmin() {
        UserOperation userOperation = UserOperation.getInstance();
        if (userOperation.checkUsernameExist("admin")) {
            return; // Admin already exists
        }

        String adminId = userOperation.generateUniqueUserId();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss");
        String adminRegisterTime = dtf.format(LocalDateTime.now());
        String encryptedPassword = userOperation.encryptPassword("admin123"); // Default password
        Admin admin = new Admin(adminId, "admin", encryptedPassword, adminRegisterTime, "admin");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH, true))) {
            writer.write(admin.toString());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}