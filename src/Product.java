

public class Product {

    private String proId;
    private String proModel;
    private String proCategory;
    private String proName;
    private double proCurrentPrice;
    private double proRawPrice;
    private double proDiscount;
    private int proLikesCount;

    public Product() {
        // Default constructor
        this.proId = "unknown";
        this.proModel = "unknown";
        this.proCategory = "unknown";
        this.proName = "unknown"; 
        this.proCurrentPrice = 0.0;
        this.proRawPrice = 0.0;
        this.proDiscount = 0.0;
        this.proLikesCount = 0;
    }

    public Product(String proId, String proModel, String proCategory,
                   String proName, double proCurrentPrice, double proRawPrice,
                   double proDiscount, int proLikesCount) {
        this.proId = proId;
        this.proModel = proModel;
        this.proCategory = proCategory;
        this.proName = proName;
        this.proCurrentPrice = proCurrentPrice;
        this.proRawPrice = proRawPrice;
        this.proDiscount = proDiscount;
        this.proLikesCount = proLikesCount;
    }

    // Getters and setters
    public String getProId() { return proId; }
    public void setProId(String proId) { this.proId = proId; }

    public String getProModel() { return proModel; }
    public void setProModel(String proModel) { this.proModel = proModel; }

    public String getProCategory() { return proCategory; }
    public void setProCategory(String proCategory) { this.proCategory = proCategory; }

    public String getProName() { return proName; }
    public void setProName(String proName) { this.proName = proName; }

    public double getProCurrentPrice() { return proCurrentPrice; }
    public void setProCurrentPrice(double proCurrentPrice) { this.proCurrentPrice = proCurrentPrice; }

    public double getProRawPrice() { return proRawPrice; }
    public void setProRawPrice(double proRawPrice) { this.proRawPrice = proRawPrice; }

    public double getProDiscount() { return proDiscount; }
    public void setProDiscount(double proDiscount) { this.proDiscount = proDiscount; }

    public int getProLikesCount() { return proLikesCount; }
    public void setProLikesCount(int proLikesCount) { this.proLikesCount = proLikesCount; }


    @Override
    public String toString() {
        return "{\"pro_id\":\"" + proId + "\", \"pro_model\": \"" + proModel + "\", " +
               "\"pro_category\":\"" + proCategory + "\", \"pro_name\": \"" + proName + "\", " +
               "\"pro_current_price\":\"" + proCurrentPrice + "\", \"pro_raw_price\": \"" + proRawPrice + "\", " +
               "\"pro_discount\": \"" + proDiscount + "\", \"pro_likes_count\": \"" + proLikesCount + "\"}";
    }
}