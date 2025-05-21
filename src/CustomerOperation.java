
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CustomerOperation {

    private static CustomerOperation instance;
    private static final String USERS_FILE_PATH = "users.txt"; // Define file path

    private CustomerOperation() {
        // Private constructor for Singleton
    }

    public static CustomerOperation getInstance() {
        if (instance == null) {
            instance = new CustomerOperation();
        }
        return instance;
    }

    public boolean validateEmail(String userEmail) {
        // Basic email validation (username@domain.extension)
        return userEmail.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

    public boolean validateMobile(String userMobile) {
        // Mobile number validation (10 digits, starts with 04 or 03)
        return userMobile.matches("^(04|03)\\d{8}$");
    }

    public boolean registerCustomer(String userName, String userPassword,
                                    String userEmail, String userMobile) {
        UserOperation userOperation = UserOperation.getInstance();
        if (userOperation.checkUsernameExist(userName)) {
            return false; // Username already exists
        }

        if (!validateEmail(userEmail) || !validateMobile(userMobile) ||
                !userOperation.validateUsername(userName) || !userOperation.validatePassword(userPassword)) {
            return false; // Validation failed
        }

        String userId = userOperation.generateUniqueUserId();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss");
        String userRegisterTime = dtf.format(LocalDateTime.now());
        String encryptedPassword = userOperation.encryptPassword(userPassword);

        Customer newCustomer = new Customer(userId, userName, encryptedPassword,
                userRegisterTime, "customer", userEmail, userMobile);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH, true))) {
            writer.write(newCustomer.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProfile(String attributeName, String value, Customer customerObject) {
        try {
            switch (attributeName.toLowerCase()) {
                case "username":
                    if (UserOperation.getInstance().validateUsername(value)) {
                        customerObject.setUserName(value);
                    } else {
                        return false;
                    }
                    break;
                case "password":
                    if (UserOperation.getInstance().validatePassword(value)) {
                        customerObject.setUserPassword(UserOperation.getInstance().encryptPassword(value));
                    } else {
                        return false;
                    }
                    break;
                case "email":
                    if (validateEmail(value)) {
                        customerObject.setUserEmail(value);
                    } else {
                        return false;
                    }
                    break;
                case "mobile":
                    if (validateMobile(value)) {
                        customerObject.setUserMobile(value);
                    } else {
                        return false;
                    }
                    break;
                default:
                    return false; // Invalid attribute
            }
            //saveUsersToFile(); // Save changes to the file
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomer(String customerId) {
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        boolean removed = lines.removeIf(line -> line.contains("\"user_id\":\"" + customerId + "\""));

        if (removed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public CustomerListResult getCustomerList(int pageNumber) {
        List<Customer> customers = new ArrayList<>();
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new CustomerListResult(customers, 0, 0); // Return empty result
        }

        int totalCustomers = lines.size();
        int customersPerPage = 10;
        int totalPages = (int) Math.ceil((double) totalCustomers / customersPerPage);

        int startIndex = (pageNumber - 1) * customersPerPage;
        int endIndex = Math.min(startIndex + customersPerPage, totalCustomers);

        for (int i = startIndex; i < endIndex; i++) {
            String line = lines.get(i);
            // Assuming each line is a valid JSON, you'll need to parse it
            Customer customer = parseCustomerFromJson(line);
            if (customer != null && "customer".equals(customer.getUserRole())) {
                customers.add(customer);
            }

        }

        return new CustomerListResult(customers, pageNumber, totalPages);
    }

    private Customer parseCustomerFromJson(String jsonLine) {
        //  Implement JSON parsing logic here to convert a JSON string to a Customer object.
        //  Use a library like org.json or similar.  For simplicity, I'll leave this as a placeholder.
        //  You'll need to extract the fields from the JSON string.
        return null; // Placeholder
    }

    public void deleteAllCustomers() {
        try (FileWriter writer = new FileWriter(USERS_FILE_PATH)) {
            writer.write(""); // Clear the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUsersToFile(List<Customer> customers) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH))) {
            for (Customer customer : customers) {
                writer.write(customer.toString());
                writer.newLine();
            }
        }
    }
}
