
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductOperation {

    private static ProductOperation instance;
    private static final String PRODUCTS_FILE_PATH = "products.txt";

    private ProductOperation() {
        // Private constructor for Singleton
    }

    public static ProductOperation getInstance() {
        if (instance == null) {
            instance = new ProductOperation();
        }
        return instance;
    }

    public void extractProductsFromFiles() {
        //  Implementation depends on the format of your input files.
        //  This is a placeholder. You'll need to read the files,
        //  parse the data, create Product objects, and save them to products.txt.
    }

    public ProductListResult getProductList(int pageNumber) {
        List<Product> products = new ArrayList<>();
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return new ProductListResult(products, 0, 0);
        }

        int totalProducts = lines.size();
        int productsPerPage = 10;
        int totalPages = (int) Math.ceil((double) totalProducts / productsPerPage);

        int startIndex = (pageNumber - 1) * productsPerPage;
        int endIndex = Math.min(startIndex + productsPerPage, totalProducts);

        for (int i = startIndex; i < endIndex; i++) {
            String line = lines.get(i);
            //  Parse the JSON line to a Product object (similar to CustomerOperation)
            Product product = parseProductFromJson(line);
            if (product != null) {
                products.add(product);
            }
        }

        return new ProductListResult(products, pageNumber, totalPages);
    }

    private Product parseProductFromJson(String jsonLine) {
        // Implement JSON parsing for Product
        return null; // Placeholder
    }

    public boolean deleteProduct(String productId) {
        List<String> lines;
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            lines = reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        boolean removed = lines.removeIf(line -> line.contains("\"pro_id\":\"" + productId + "\""));

        if (removed) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE_PATH))) {
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

    public List<Product> getProductListByKeyword(String keyword) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //  Parse JSON and check if product name contains keyword (case-insensitive)
                Product product = parseProductFromJson(line);
                if (product != null && product.getProName().toLowerCase().contains(keyword.toLowerCase())) {
                    products.add(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(String productId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse JSON and check if product ID matches
                Product product = parseProductFromJson(line);
                if (product != null && product.getProId().equals(productId)) {
                    return product;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void generateCategoryFigure() {
        //  Implement chart generation using javafx (bar chart)
    }

    public void generateDiscountFigure() {
        //  Implement chart generation using javafx (pie chart)
    }

    public void generateLikesCountFigure() {
        //  Implement chart generation using javafx (bar chart)
    }

    public void generateDiscountLikesCountFigure() {
        //  Implement chart generation using javafx (scatter chart)
    }

    public void deleteAllProducts() {
        try (FileWriter writer = new FileWriter(PRODUCTS_FILE_PATH)) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
