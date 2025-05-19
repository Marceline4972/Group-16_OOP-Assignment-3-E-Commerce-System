import java.util.Scanner;

class IOInterface {
    private static IOInterface instance;
    private final Scanner scanner = new Scanner(System.in);

    private IOInterface() {}

    public static IOInterface getInstance() {
        if (instance == null) {
            instance = new IOInterface();
        }
        return instance;
    }

    public String[] getUserInput(String message, int numOfArgs) {
        System.out.print(message);
        System.out.flush();
        String input = scanner.nextLine();
        String[] tokens = input.trim().split("\\s+");

        String[] args = new String[numOfArgs];
        for (int i = 0; i < numOfArgs; i++) {
            args[i] = i < tokens.length ? tokens[i] : "";
        }
        return args;
    }

    public void mainMenu() {
        System.out.println("==== Main Menu ====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
    }

    public void printMessage(String msg) {
        System.out.println(msg);
    }

    public void printErrorMessage(String source, String msg) {
        System.out.println("ERROR in " + source + ": " + msg);
    }

    public void adminMenu() {
        System.out.println("== Admin Menu ==");
        System.out.println("1. Show products");
        System.out.println("2. Add customers");
        System.out.println("3. Show customers");
        System.out.println("4. Show orders");
        System.out.println("5. Generate test data");
        System.out.println("6. Generate all statistical figures");
        System.out.println("7. Delete all data");
        System.out.println("8. Logout");
    }

    public void customerMenu() {
        System.out.println("== Customer Menu ==");
        System.out.println("1. Show profile");
        System.out.println("2. Update profile");
        System.out.println("3. Show products");
        System.out.println("4. Show history orders");
        System.out.println("5. Generate all consumption figures");
        System.out.println("6. Logout");
    }
}
