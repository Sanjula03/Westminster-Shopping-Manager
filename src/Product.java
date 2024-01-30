public abstract class Product {
    private String productID;
    private String productName;
    private int availableItems;
    private double price;

    public Product(String productID, String productName, int availableItems, double price) {
        this.productID = productID;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    // Getters and setters
    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getProductType();

    @Override
    public String toString() {
        return String.format("Product ID: %s\nProduct Name: %s\nAvailable Items: %d\nPrice: %.2f",
                getProductID(), getProductName(), getAvailableItems(), getPrice());
    }

    public abstract void remove(Product product);

    // Updated getProductId() method with null check
    public String getProductId() {
        return productID != null ? productID : "";
    }

    public abstract void setProductId(String attributeValue);

    public abstract void setNumOfItemsAvailable(int i);

}
