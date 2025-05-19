package Model_class;
public class Product {
    private String proId; 
    private String proModel;
    private String proCategory;
    private String proName;
    private double proCurrentPrice;
    private double proRawPrice;
    private double proDiscount;
    private int proLikesCount;

    /**
     * Constructs a product object.
     * @param proId Product ID (must be unique)
     * @param proModel Product model
     * @param proCategory Product category
     * @param proName Product name
     * @param proCurrentPrice Current price of the product
     * @param proRawPrice Original price of the product
     * @param proDiscount Discount percentage
     * @param proLikesCount Number of likes
     */
    public Product(String proId, String proModel, String proCategory, String proName, double proCurrentPrice, double proRawPrice, double proDiscount, int proLikesCount) {
        this.proId = proId;
        this.proModel = proModel;
        this.proCategory = proCategory;
        this.proName = proName;
        this.proCurrentPrice = proCurrentPrice;
        this.proRawPrice = proRawPrice;
        this.proDiscount = proDiscount;
        this.proLikesCount = proLikesCount;
    }

    /**
     * Default constructor
     */
    public Product() {
        this.proId = "unknown";
        this.proModel = "unknown";
        this.proCategory = "unknown";
        this.proName = "unknown"; 
        this.proCurrentPrice = 0.0;
        this.proRawPrice = 0.0;
        this.proDiscount = 0.0;
        this.proLikesCount = 0;
    }

    /**
     * Returns the product information as a formatted string.
     * @return String in JSON-like format
     */
    @Override
    public String toString() {
        return String.format("{\"proId\":\"%s\", \"proModel\":\"%s\", \"proCategory\":\"%s\", \"proName\":\"%s\", \"proCurrentPrice\":%.2f, \"proRawPrice\":%.2f, \"proDiscount\":%.2f, \"proLikesCount\":%d}", 
                proId, proModel, proCategory, proName, proCurrentPrice, proRawPrice, proDiscount, proLikesCount);
    }
}
