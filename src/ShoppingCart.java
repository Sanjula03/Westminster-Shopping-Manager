import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final ArrayList<Product> products;

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        // Check if the product with the same ID is already in the cart
        for (Product existingProduct : products) {
            if (existingProduct.getProductId().equals(product.getProductId())) {
                // Update the quantity and price of the existing product
                existingProduct.setNumOfItemsAvailable(existingProduct.getAvailableItems() + 1);
                existingProduct.setPrice(existingProduct.getPrice() + product.getPrice());
                return;
            }
        }

        // If the product is not already in the cart, add it
        products.add(product);
    }

    public List<Product> getProducts() {
        return products;
    }

}
