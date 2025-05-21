import java.util.List;
import java.util.Scanner;

public class IOInterface {

    private static IOInterface instance;
    private Scanner scanner;

    private IOInterface() {
        this.scanner = new Scanner(System.in);
    }

    public static IOInterface getInstance() {
        if (instance == null) {
            instance = new IOInterface();
        }
        return instance;
    }

    public String[] getUserInput(String message, int numOfArgs) {
        System.out.print(message); // Changed to print (not println)
        String[] inputs = new String[numOfArgs];
        String line = scanner.nextLine();
        String[] tokens = line.split(" ");

        for (int i = 0; i < numOfArgs; i++) {
            if (i < tokens.length) {
                inputs[i] = tokens[i];
            } else {
                inputs[i] = ""; // Fill remaining with empty strings
            }
        }
        return inputs;
    }

    public void mainMenu() {
        System.out.println("======== E-Commerce System ========");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
        System.out.println("==================================");
        System.out.print("Enter your choice: "); // Changed to print
    }

    public void adminMenu() {
        System.out.println("======== Admin Menu ========");
        System.out.println("1. Show products");
        System.out.println("2. Add customers");
        System.out.println("3. Show customers");
        System.out.println("4. Show orders");
        System.out.println("5. Generate test data");
        System.out.println("6. Generate all statistical figures");
        System.out.println("7. Delete all data");
        System.out.println("8. Logout");
        System.out.println("============================");
        System.out.print("Enter your choice: "); // Changed to print
    }

    public void customerMenu() {
        System.out.println("======== Customer Menu ========");
        System.out.println("1. Show profile");
        System.out.println("2. Update profile");
        System.out.println("3. Show products (user input could be '3 keyword' or '3')");
        System.out.println("4. Show history orders");
        System.out.println("5. Generate all consumption figures");
        System.out.println("6. Logout");
        System.out.println("===============================");
        System.out.print("Enter your choice: "); // Changed to print
    }

    public void showList(String userRole, String listType, List<?> objectList, int pageNumber, int totalPages) {
        System.out.println("======== " + listType + " List (Page " + pageNumber + "/" + totalPages + ") ========");
        for (int i = 0; i < objectList.size(); i++) {
            System.out.println((i + 1) + ". " + objectList.get(i).toString());
        }
        System.out.println("==================================================");

        if (listType.equals("Product")) {
            System.out.print("Enter your choice: ");
        } else if (listType.equals("Customer")) {
            System.out.print("Enter 'n' for next page, 'p' for previous page, 'b' to go back, or a customer ID to view details:\n");
        } else if (listType.equals("Order")) {
            System.out.print("Enter 'n' for next page, 'p' for previous page, 'b' to go back:\n");
        }
    }

    public void printErrorMessage(String errorSource, String errorMessage) {
        System.err.println("Error in " + errorSource + ": " + errorMessage);
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printObject(Object targetObject) {
        System.out.println(targetObject.toString());
    }

    public String[] getCustomerRegistrationDetails() {
        String[] details = new String[4];
        details[0] = getUserInput("Enter username: ", 1)[0];
        details[1] = getUserInput("Enter password: ", 1)[0];
        details[2] = getUserInput("Enter email: ", 1)[0];
        details[3] = getUserInput("Enter mobile number: ", 1)[0];
        return details;
    }
}