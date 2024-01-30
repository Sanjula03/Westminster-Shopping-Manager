public class Clothing extends Product {
    private String size;
    private String color;

    public Clothing(String productID, String productName, int availableItems, double price, String size, String color) {
        super(productID, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String getProductType() {
        return "Clothing";
    }

    @Override
    public String toString() {
        String result = super.toString() + String.format("\nSize: %s\nColor: %s", getSize(), getColor());

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

    @Override
    public void setProductId(String attributeValue) {

    }

    @Override
    public void setNumOfItemsAvailable(int i) {

    }

    // Handle null values in getColour() and setColour()
    public String getColour() {
        return color != null ? color : "";
    }

    public void setColour(String attributeValue) {
        this.color = attributeValue != null ? attributeValue : "";
    }
}
