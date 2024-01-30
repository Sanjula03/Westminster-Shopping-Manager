public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productID, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productID, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String getProductType() {
        return "Electronics";
    }

    @Override
    public String toString() {
        String result = super.toString() + String.format("\nBrand: %s\nWarranty Period: %d months", getBrand(), getWarrantyPeriod());

        // Add a null check before invoking equals
        String productId = getProductId();
        if (productId != null) {
            result += "\nProduct ID: " + productId;
        }

        return result;
    }

    @Override
    public void remove(Product product) {

    }

    // Handle null values in getProductId()
    public String getProductId() {
        return super.getProductID() != null ? super.getProductID() : "";
    }

    public void setProductId(String attributeValue) {
        // Do nothing or handle as needed
    }

    @Override
    public void setNumOfItemsAvailable(int i) {
        // Do nothing or handle as needed
    }
}
