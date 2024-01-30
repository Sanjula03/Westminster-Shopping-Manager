import java.util.List;

public interface ShoppingManager {
    void addProductToSystem(Product product);
    void removeProductFromSystem(String productId);
    void printAllProductsInTheShop();
    void saveToFile();
    void loadFromFile();
    void openGui();

    // Retrieve the product list
    List<Product> getProductList();
}
