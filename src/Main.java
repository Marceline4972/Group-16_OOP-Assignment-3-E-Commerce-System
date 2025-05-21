public class Main {

    public static void main(String[] args) {
        IOInterface io = IOInterface.getInstance();
        UserOperation userOperation = UserOperation.getInstance();
        AdminOperation adminOperation = AdminOperation.getInstance();
        CustomerOperation customerOperation = CustomerOperation.getInstance();
        ProductOperation productOperation = ProductOperation.getInstance();

        adminOperation.registerAdmin(); // Ensure admin is registered at startup

        boolean running = true;
        User currentUser = null;

        while (running) {
            io.mainMenu();
            String[] input = io.getUserInput("", 1); // Get main menu choice
            int choice;

            try {
                choice = Integer.parseInt(input[0]);
            } catch (NumberFormatException e) {
                io.printErrorMessage("Main Menu", "Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: // Login
                    input = io.getUserInput("Enter username and password: ", 2);
                    String username = input[0];
                    String password = input[1];
                    currentUser = userOperation.login(username, password);
                    if (currentUser != null) {
                        io.printMessage("Login successful. Welcome, " + currentUser.getUserName() + "!");
                        if (currentUser.getUserRole().equals("admin")) {
                            adminControlLoop(io);
                        } else if (currentUser.getUserRole().equals("customer")) {
                            customerControlLoop(io);
                        }
                    } else {
                        io.printErrorMessage("Login", "Invalid username or password.");
                    }
                    break;
                case 2: // Register
                    String[] regDetails = io.getCustomerRegistrationDetails();
                    if (customerOperation.registerCustomer(regDetails[0], regDetails[1], regDetails[2], regDetails[3])) {
                        io.printMessage("Registration successful!");
                    } else {
                        io.printErrorMessage("Registration", "Registration failed. Please check your input.");
                    }
                    break;
                case 3: // Quit
                    running = false;
                    io.printMessage("Exiting the E-Commerce System.");
                    break;
                default:
                    io.printErrorMessage("Main Menu", "Invalid choice. Please try again.");
            }
        }
    }

    private static void adminControlLoop(IOInterface io) {
        boolean adminMenuRunning = true;
        while (adminMenuRunning) {
            io.adminMenu();
            String[] input = io.getUserInput("", 1);
            int choice;

            try {
                choice = Integer.parseInt(input[0]);
            } catch (NumberFormatException e) {
                io.printErrorMessage("Admin Menu", "Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1: // Show products
                    // Implement show products logic using ProductOperation and IOInterface
                    break;
                case 2: // Add customers
                    // Implement add customers logic
                    break;
                case 3: // Show customers
                    // Implement show customers logic
                    break;
                case 4: // Show orders
                    // Implement show orders logic
                    break;
                case 5: // Generate test data
                    // Implement generate test data
                    break;
                case 6: // Generate all statistical figures
                    // Implement generate figures
                    break;
                case 7: // Delete all data
                    // Implement delete all data
                    break;
                case 8: // Logout
                    adminMenuRunning = false;
                    io.printMessage("Logged out.");
                    break;
                default:
                    io.printErrorMessage("Admin Menu", "Invalid choice. Please try again.");
            }
        }
    }

    private static void customerControlLoop(IOInterface io) {
        // Implement customer menu loop similar to adminControlLoop
    }
}
