
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class OrderOperation {

    private static OrderOperation instance;
    private static final String ORDERS_FILE_PATH = "orders.txt";

    private OrderOperation() {
        // Private constructor for Singleton
    }

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
        if (createTime == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss");
            createTime = dtf.format(LocalDateTime.now());
        }

        String orderId = generateUniqueOrderId();
        Order order = new Order(orderId, customerId, productId, createTime);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE_PATH, true))) {
            writer.write(order.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrder(String orderId) {
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE_PATH))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        boolean removed = lines.removeIf(line -> line.contains("\"order_id\":\"" + orderId + "\""));

        if (removed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDERS_FILE_PATH))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    public OrderListResult getOrderList(String customerId, int pageNumber) {
        List<Order> orders = new ArrayList<>();
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDERS_FILE_PATH))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new OrderListResult(orders, 0, 0);
        }

        List<String> customerOrders = lines.stream()
                .filter(line -> line.contains("\"user_id\":\"" + customerId + "\""))
                .collect(Collectors.toList());

        int totalOrders = customerOrders.size();
        int ordersPerPage = 10;
        int totalPages = (int) Math.ceil((double) totalOrders / ordersPerPage);

        int startIndex = (pageNumber - 1) * ordersPerPage;
        int endIndex = Math.min(startIndex + ordersPerPage, totalOrders);

        for (int i = startIndex; i < endIndex; i++) {
            String line = customerOrders.get(i);
            // Parse JSON to Order object
            Order order = parseOrderFromJson(line);
            if (order != null) {
                orders.add(order);
            }
        }

        return new OrderListResult(orders, pageNumber, totalPages);
    }

    private Order parseOrderFromJson(String jsonLine) {
        // Implement JSON parsing for Order
        return null; // Placeholder
    }

    public void generateTestOrderData() {
        //  Implement test data generation
    }

    public void generateSingleCustomerConsumptionFigure(String customerId) {
        //  Implement chart generation (javafx)
    }

    public void generateAllCustomersConsumptionFigure() {
        //  Implement chart generation (javafx)
    }

    public void generateAllTop10BestSellersFigure() {
        //  Implement chart generation (javafx)
    }

    public void deleteAllOrders() {
        try (FileWriter writer = new FileWriter(ORDERS_FILE_PATH)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}