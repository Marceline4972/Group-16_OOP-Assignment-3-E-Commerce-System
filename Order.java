import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Order {
    private String orderId;
    private String userId;
    private String proId;
    private String orderTime;

    public Order(String orderId, String userId, String proId, String orderTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.proId = proId;
        this.orderTime = orderTime;
    }

    public Order() {
        this.orderId = "o_00000";
        this.userId = "u_0000000000";
        this.proId = "p_00000";
        this.orderTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss"));
    }

    public Order(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format("{\"order_id\":\"%s\", \"user_id\":\"%s\", \"pro_id\":\"%s\", \"order_time\":\"%s\"}",
                orderId, userId, proId, orderTime);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

