import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.json.*;

public class OrderOperation {
    private static OrderOperation instance;
    private final String orderFilePath = "data/orders.txt";

    private OrderOperation() {}

    public static OrderOperation getInstance() {
        if (instance == null) {
            instance = new OrderOperation();
        }
        return instance;
    }

    public String generateUniqueOrderId() {
        return "o_" + String.format("%05d", new Random().nextInt(100000));
    }

    public boolean createAnOrder(String customerId, String productId, String createTime) {
        try {
            if (createTime == null) {
                createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss"));
            }
            String orderId = generateUniqueOrderId();
            Order order = new Order(orderId, customerId, productId, createTime);
            Files.write(Paths.get(orderFilePath), Collections.singletonList(order.toString()), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(String orderId) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(orderFilePath));
            List<String> updated = new ArrayList<>();
            for (String line : lines) {
                JSONObject obj = new JSONObject(line);
                if (!obj.getString("order_id").equals(orderId)) {
                    updated.add(line);
                }
            }
            Files.write(Paths.get(orderFilePath), updated);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public OrderListResult getOrderList(String customerId, int pageNumber) {
        List<Order> orders = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(orderFilePath));
            for (String line : lines) {
                JSONObject obj = new JSONObject(line);
                if (obj.getString("user_id").equals(customerId)) {
                    orders.add(new Order(
                        obj.getString("order_id"),
                        obj.getString("user_id"),
                        obj.getString("pro_id"),
                        obj.getString("order_time")
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int totalPages = (int) Math.ceil(orders.size() / 10.0);
        int fromIndex = Math.min((pageNumber - 1) * 10, orders.size());
        int toIndex = Math.min(pageNumber * 10, orders.size());
        return new OrderListResult(orders.subList(fromIndex, toIndex), pageNumber, totalPages);
    }

    public void deleteAllOrders() {
        try {
            Files.write(Paths.get(orderFilePath), new ArrayList<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class OrderListResult {
    public List<Order> orders;
    public int currentPage;
    public int totalPages;

    public OrderListResult(List<Order> orders, int currentPage, int totalPages) {
        this.orders = orders;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}