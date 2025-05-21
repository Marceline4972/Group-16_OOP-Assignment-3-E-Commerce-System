public class Customer extends User {

    private String userEmail;
    private String userMobile;

    public Customer() {
        // Default constructor
        super(); // Calls the default constructor of User
        this.userEmail = "default@example.com";
        this.userMobile = "0000000000";
    }

    public Customer(String userId, String userName, String userPassword,
                    String userRegisterTime, String userRole, String userEmail, String userMobile) {
        super(userId, userName, userPassword, userRegisterTime, userRole);
        this.userEmail = userEmail;
        this.userMobile = userMobile;
    }

    // Getters and setters
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserMobile() { return userMobile; }
    public void setUserMobile(String userMobile) { this.userMobile = userMobile; }


    @Override
    public String toString() {
        return super.toString().replace("}", ", \"user_email\":\"" + userEmail + "\", \"user_mobile\": \"" + userMobile + "\"}");
    }
}