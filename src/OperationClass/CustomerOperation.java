import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.*;

public class CustomerOperation {
    private static CustomerOperation instance;
    private final String userFilePath = "data/users.txt";

    private CustomerOperation() {}

    public static CustomerOperation getInstance() {
        if (instance == null) {
            instance = new CustomerOperation();
        }
        return instance;
    }

    public boolean validateEmail(String userEmail) {
        return userEmail.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    }

    public boolean validateMobile(String userMobile) {
        return userMobile.matches("^(03|04)\\d{8}$");
    }

    public boolean registerCustomer(String userName, String userPassword, String userEmail, String userMobile) {
        try {
            UserOperation uo = UserOperation.getInstance();
            if (uo.checkUsernameExist(userName)) return false;

            if (!uo.validateUsername(userName) || !uo.validatePassword(userPassword) ||
                !validateEmail(userEmail) || !validateMobile(userMobile)) return false;

            String userId = uo.generateUniqueUserId();
            String encryptedPassword = uo.encryptPassword(userPassword);
            String registerTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss"));

            Customer newCustomer = new Customer(userId, userName, encryptedPassword, registerTime, "customer", userEmail, userMobile);
            Files.write(Paths.get(userFilePath), Collections.singletonList(newCustomer.toString()), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateProfile(String attributeName, String value, Customer customerObject) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(userFilePath));
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                JSONObject userJson = new JSONObject(line);
                if (userJson.getString("user_id").equals(customerObject.getUserId())) {
                    switch (attributeName) {
                        case "user_name":
                            if (!UserOperation.getInstance().validateUsername(value)) return false;
                            userJson.put("user_name", value);
                            break;
                        case "user_password":
                            if (!UserOperation.getInstance().validatePassword(value)) return false;
                            userJson.put("user_password", UserOperation.getInstance().encryptPassword(value));
                            break;
                        case "user_email":
                            if (!validateEmail(value)) return false;
                            userJson.put("user_email", value);
                            break;
                        case "user_mobile":
                            if (!validateMobile(value)) return false;
                            userJson.put("user_mobile", value);
                            break;
                        default:
                            return false;
                    }
                    updatedLines.add(userJson.toString());
                } else {
                    updatedLines.add(line);
                }
            }
            Files.write(Paths.get(userFilePath), updatedLines);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCustomer(String customerId) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(userFilePath));
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                JSONObject userJson = new JSONObject(line);
                if (!userJson.getString("user_id").equals(customerId)) {
                    updatedLines.add(line);
                }
            }
            Files.write(Paths.get(userFilePath), updatedLines);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public CustomerListResult getCustomerList(int pageNumber) {
        List<Customer> customers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(userFilePath));
            for (String line : lines) {
                JSONObject userJson = new JSONObject(line);
                if (userJson.getString("user_role").equals("customer")) {
                    customers.add(new Customer(
                        userJson.getString("user_id"),
                        userJson.getString("user_name"),
                        userJson.getString("user_password"),
                        userJson.getString("user_register_time"),
                        "customer",
                        userJson.getString("user_email"),
                        userJson.getString("user_mobile")
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int totalPages = (int) Math.ceil(customers.size() / 10.0);
        int fromIndex = Math.min((pageNumber - 1) * 10, customers.size());
        int toIndex = Math.min(pageNumber * 10, customers.size());
        return new CustomerListResult(customers.subList(fromIndex, toIndex), pageNumber, totalPages);
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void deleteAllCustomers() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(userFilePath));
            List<String> admins = new ArrayList<>();
            for (String line : lines) {
                JSONObject userJson = new JSONObject(line);
                if (userJson.getString("user_role").equals("admin")) {
                    admins.add(line);
                }
            }
            Files.write(Paths.get(userFilePath), admins);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class CustomerListResult {
    public List<Customer> customers;
    public int currentPage;
    public int totalPages;

    public CustomerListResult(List<Customer> customers, int currentPage, int totalPages) {
        this.customers = customers;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}
