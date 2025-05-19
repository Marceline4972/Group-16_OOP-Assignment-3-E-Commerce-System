public class Main {
    public static void main(String[] args) {
        AdminOperation.getInstance().registerAdmin();
        IOInterface io = IOInterface.getInstance();
        UserOperation userOps = UserOperation.getInstance();
        CustomerOperation customerOperation = CustomerOperation.getInstance();

        while (true) {
            io.mainMenu();
            String[] loginArgs = io.getUserInput("Enter username and password: ", 2);
            User user = userOps.login(loginArgs[0], loginArgs[1]);

            if (user == null) {
                io.printErrorMessage("Login", "Invalid username or password.");
                continue;
            }

            io.printMessage("Login successful! Welcome " + user.userName);

            if (user instanceof Admin) {
                boolean adminLoop = true;
                while (adminLoop) {
                    io.adminMenu();
                    String choice = io.getUserInput("Select option: ", 1)[0];
                    switch (choice) {
                        case "8" -> adminLoop = false;
                        default -> io.printMessage("Feature not implemented yet.");
                    }
                }
            } else if (user instanceof Customer customer) {
                boolean custLoop = true;
                while (custLoop) {
                    io.customerMenu();
                    String choice = io.getUserInput("Select option: ", 1)[0];
                    switch (choice) {
                        case "6" -> custLoop = false;
                        default -> io.printMessage("Feature not implemented yet.");
                    }
                }
            }
        }
    }
}
