class ProductOperation {
    private static ProductOperation instance;
    private final String productFilePath = "data/products.txt";

    private ProductOperation() {}

    public static ProductOperation getInstance() {
        if (instance == null) {
            instance = new ProductOperation();
        }
        return instance;
    }

    public void extractProductsFromFiles() {
        // Placeholder: implement if required
    }

    public boolean deleteProduct(String productId) {
        File inputFile = new File(productFilePath);
        File tempFile = new File("data/temp_products.txt");
        boolean deleted = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("\"pro_id\":\"" + productId + "\"")) {
                    deleted = true;
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (deleted && inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
        return deleted;
    }

    public void deleteAllProducts() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(productFilePath))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(String productId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(productFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("\"pro_id\":\"" + productId + "\"")) {
                    // Ideally parse JSON, here just return dummy object
                    return new Product(); // Replace with actual parsed product
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> getProductListByKeyword(String keyword) {
        List<Product> result = new ArrayList<>();
        keyword = keyword.toLowerCase();
        try (BufferedReader reader = new BufferedReader(new FileReader(productFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(keyword)) {
                    result.add(new Product()); // Replace with actual parsed product
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
    // Placeholder for getProductList() and chart generation methods
}