
public abstract class User {

    private String userId;
    private String userName;
    private String userPassword;
    private String userRegisterTime;
    private String userRole;

    public User() {
        // Default constructor
        this("u_0000000000", "defaultUser", "defaultPassword", "01-01-2025_00:00:00", "customer");

    }

    public User(String userId, String userName, String userPassword,
                String userRegisterTime, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRegisterTime = userRegisterTime;
        this.userRole = userRole;
    }

    // Getters and setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public String getUserRegisterTime() { return userRegisterTime; }
    public void setUserRegisterTime(String userRegisterTime) { this.userRegisterTime = userRegisterTime; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }


    @Override
    public String toString() {
        return "{\"user_id\": \"" + userId + "\", \"user_name\": \"" + userName + "\", " +
               "\"user_password\": \"" + userPassword + "\", \"user_register_time\": \"" + userRegisterTime + "\", " +
               "\"user_role\": \"" + userRole + "\"}";
    }
}