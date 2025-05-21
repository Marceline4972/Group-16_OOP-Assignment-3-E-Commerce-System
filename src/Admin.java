public class Admin extends User {

    public Admin() {
        // Default constructor
        super("u_9999999999", "DefaultAdmin", "AdminPassword", "01-01-2025_00:00:00", "admin");
    }

    public Admin(String userId, String userName, String userPassword,
                 String userRegisterTime, String userRole) {
        super(userId, userName, userPassword, userRegisterTime, userRole);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
