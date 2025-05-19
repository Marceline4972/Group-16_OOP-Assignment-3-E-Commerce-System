package Model_class;

public class Order {
    private String orderId; // format: "o_5 digits"
    private String userId;
    private String proId;
    private String orderTime; // format: "DD-MM-YYYY_HH:MM:SS"

    /**
     * Constructs an order object.
     * @param orderId Must be a unique string, format is o_5 digits such as o_12345
     * @param userId ID of the user who placed the order
     * @param proId ID of the product ordered
     * @param orderTime Format: "DD-MM-YYYY_HH:MM:SS"
     */
    public Order(String orderId, String userId, String proId, String orderTime) {
        if (!orderId.matches("o_\\d{5}")) {
            throw new IllegalArgumentException("Order ID must be in the format o_5 digits");
        }

        this.orderId = orderId;
        this.userId = userId;
        this.proId = proId;
        this.orderTime = orderTime;
    }

    /**
     * Default constructor
     */
    public Order() {
        this.orderId = "o_00000";
        this.userId = "u_0000000000";
        this.proId = "unknown";
        this.orderTime = "01-01-2025_00:00:00";
    }

    /**
     * Returns the order information as a formatted string.
     * @return String in JSON-like format
     */
    @Override
    public String toString() {
        return String.format("{\"orderId\":\"%s\", \"userId\":\"%s\", \"proId\":\"%s\", \"orderTime\":\"%s\"}", 
                orderId, userId, proId, orderTime);
    }
}
