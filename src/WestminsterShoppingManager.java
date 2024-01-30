import javax.swing.*;
import java.io.*;
import java.util.*;

public class WestminsterShoppingManager implements ShoppingManager {

    private final ArrayList<Product> productList;
    private final Scanner scanner;

    public WestminsterShoppingManager() {
        this.productList = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        shoppingManager.run();
    }

    public void run() {
        boolean runStatus = true;

        while (runStatus) {
            try {
                System.out.print("""
                        =====Manager Menu=====
                        1. Add a new product.
                        2. Remove a product.
                        3. Print all products.
                        4. Save data to a file.
                        5. Load data from a file.
                        6. Exit from the program.
                        7. Open Shopping center GUI.
                        Enter the option number:\s""");

                int option = scanner.nextInt();

                switch (option) {
                    case 1 -> addAProduct();
                    case 2 -> deleteAProduct();
                    case 3 -> printAllProductsInTheShop();
                    case 4 -> saveToFile();
                    case 5 -> loadFromFile();
                    case 6 -> {
                        System.out.println("Exiting the program...\nGoodbye..");
                        runStatus = false;
                    }
                    case 7 -> openGui();
                    default -> System.out.println("Invalid option...\nTry again..\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid option.");
                scanner.next(); // Consume the invalid input
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }

    private void addAProduct() {
        boolean validInput = false;

        while (!validInput) {
            try {
                System.out.println("Enter 1 to add Electronic product.");
                System.out.println("Enter 2 to add Clothing product.");

                System.out.print("Enter product type :");

                int productType = scanner.nextInt();

                if (productType == 1 || productType == 2) {
                    System.out.print("Enter product ID :");
                    String productId = scanner.next();

                    System.out.print("Enter product name :");
                    String productName = scanner.next();

                    System.out.print("Enter number of items that available :");

                    int numOfItemsAvailable;
                    while (true) {
                        try {
                            numOfItemsAvailable = scanner.nextInt();
                            break; // If successful, exit the loop
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input for number of items available. Enter a valid integer.");
                            scanner.next(); // Consume the invalid input
                        }
                    }

                    double price;
                    while (true) {
                        try {
                            System.out.print("Enter price :");
                            price = scanner.nextDouble();

                            if (price <= 0) {
                                throw new InputMismatchException("Invalid input for price. Enter a valid positive number.");
                            }

                            break; // If successful, exit the loop
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input for price. Enter a valid positive number.");
                            scanner.next(); // Consume the invalid input
                        }
                    }

                    int warrantyPeriod = 0;
                    if (productType == 1) {
                        while (true) {
                            try {
                                System.out.print("Enter the warranty period :");
                                warrantyPeriod = scanner.nextInt();

                                if (warrantyPeriod <= 0) {
                                    throw new InputMismatchException("Invalid input for warranty period. Enter a valid positive number.");
                                }

                                break; // If successful, exit the loop
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input for warranty period. Enter a valid positive number.");
                                scanner.next(); // Consume the invalid input
                            }
                        }
                    }

                    if (productType == 1) {
                        System.out.print("Enter the Brand name :");
                        String brand = scanner.next();

                        addProductToSystem(new Electronics(productId, productName, numOfItemsAvailable, price, brand, warrantyPeriod));
                    } else {
                        System.out.print("Enter the size :");
                        String size = scanner.next();

                        System.out.print("Enter the colour :");
                        String colour = scanner.next();

                        addProductToSystem(new Clothing(productId, productName, numOfItemsAvailable, price, size, colour));
                    }

                    System.out.println(productName + " Added to the system.");
                    validInput = true; // Set flag to exit the loop
                } else {
                    System.out.println("Invalid product type.!\nTry again..");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid option.");
                scanner.next(); // Consume the invalid input
            }
        }
    }

    @Override
    public void addProductToSystem(Product product) {
        if (IsIdUnique(product.getProductId()) && productList.size() <= 50) {
            productList.add(product);
            sortProductsAlphabetically(); // Call the sorting method
        } else if (productList.size() > 50) {
            System.out.println("Product cannot be added. Maximum products that can be stored in the system is 50");
        } else {
            System.out.println("Product cannot be added. Entered product is already available...");
        }
    }

    private void sortProductsAlphabetically() {
        productList.sort(Comparator.comparing(Product::getProductName));
    }


    private void deleteAProduct() {
        System.out.print("Enter Product ID that needs to delete :");
        String removingProductId = scanner.next();
        removeProductFromSystem(removingProductId);
    }

    @Override
    public void removeProductFromSystem(String productId) {
        if (productList.isEmpty()) {
            System.out.println("Shop is empty..");
        } else {
            Iterator<Product> iterator = productList.iterator();
            boolean found = false;

            while (iterator.hasNext()) {
                Product product = iterator.next();

                if (product.getProductId().equals(productId)) {
                    String tempProductName = product.getProductName();
                    iterator.remove();
                    found = true;

                    if (product instanceof Electronics) {
                        System.out.println("Type: Electronics");
                        System.out.println("Product " + tempProductName
                                + " that has " + productId
                                + " Id removed from the shop...");
                        break;
                    } else if (product instanceof Clothing) {
                        System.out.println("Type: Clothing");
                        System.out.println("Product " + tempProductName
                                + " that has " + productId
                                + " Id removed from the shop...");
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("Product with ID " + productId + " not found..");
            }
        }
    }

    @Override
    public void printAllProductsInTheShop() {
        List<Product> productList = new ArrayList<>();

        if (this.productList.isEmpty()) {
            System.out.println("Product list is empty..");
        } else {
            System.out.println("----Product list----");
            for (Product product : this.productList) {
                System.out.println("Product Type: " + product.getProductType());
                System.out.println("Product Name: " + product.getProductName());
                System.out.println("Product ID: " + product.getProductId());
                System.out.println("Price: Rs" + product.getPrice());

                if (product instanceof Electronics) {
                    System.out.println("Brand: " + ((Electronics) product).getBrand());
                    System.out.println("Warranty Period: " + ((Electronics) product).getWarrantyPeriod() + " months");
                } else if (product instanceof Clothing) {
                    System.out.println("Size: " + ((Clothing) product).getSize());
                    System.out.println("Color: " + ((Clothing) product).getColour());
                }

                System.out.println("Number of Available Items: " + product.getAvailableItems() + "\n");

                // Add the product to the list to be returned
                productList.add(product);
            }
        }

    }

    @Override
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("ProductListOfSystem.txt"))) {
            for (Product product : productList) {
                if (product instanceof Electronics) {
                    writer.println("Type: Electronics " +
                            "\nProduct name: " + product.getProductName()
                            + "\nProduct ID: " + product.getProductId()
                            + "\nPrice: Rs" + product.getPrice()
                            + "\nBrand: " + ((Electronics) product).getBrand()
                            + "\nWarranty period: " + ((Electronics) product).getWarrantyPeriod()
                            + "\nNumber of available Items: " + product.getAvailableItems()
                            + "\n---------------");
                } else if (product instanceof Clothing) {
                    writer.println("Type: Clothing " +
                            "\nProduct name: " + product.getProductName()
                            + "\nProduct ID: " + product.getProductId()
                            + "\nPrice: Rs" + product.getPrice()
                            + "\nSize: " + ((Clothing) product).getSize()
                            + "\nColour: " + ((Clothing) product).getColour()
                            + "\nNumber of available Items: " + product.getAvailableItems()
                            + "\n---------------");
                }
            }
            System.out.println("Data saved successfully to ProductListOfSystem.txt");
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    @Override
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("ProductListOfSystem.txt"))) {
            String line;
            Product currentProduct = null;

            while ((line = reader.readLine()) != null) {
                if (line.equals("---------------")) {
                    if (currentProduct != null) {
                        productList.add(currentProduct);
                        currentProduct = null;
                    }
                    continue;
                }

                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String attributeName = parts[0].trim();
                    String attributeValue = parts[1].trim();

                    switch (attributeName) {
                        case "Type" -> {
                            if (attributeValue.equals("Electronics")) {
                                currentProduct = new Electronics(null, null, 0, 0.0, null, 0);
                            } else if (attributeValue.equals("Clothing")) {
                                currentProduct = new Clothing(null, null, 0, 0.0, null, null);
                            }
                        }
                        case "Product ID" -> {
                            if (currentProduct != null) {
                                currentProduct.setProductID(attributeValue);
                            }
                        }
                        case "Product name" -> {
                            if (currentProduct != null) {
                                currentProduct.setProductName(attributeValue);
                            }
                        }
                        case "Number of available Items" -> {
                            if (currentProduct != null) {
                                currentProduct.setAvailableItems(Integer.parseInt(attributeValue));
                            }
                        }
                        case "Price" -> {
                            if (currentProduct != null) {
                                currentProduct.setPrice(Double.parseDouble(attributeValue.substring(2)));
                            }
                        }
                        case "Brand" -> {
                            if (currentProduct instanceof Electronics) {
                                ((Electronics) currentProduct).setBrand(attributeValue);
                            }
                        }
                        case "Warranty period" -> {
                            if (currentProduct instanceof Electronics) {
                                ((Electronics) currentProduct).setWarrantyPeriod(Integer.parseInt(attributeValue));
                            }
                        }
                        case "Size" -> {
                            if (currentProduct instanceof Clothing) {
                                ((Clothing) currentProduct).setSize(attributeValue);
                            }
                        }
                        case "Colour" -> {
                            if (currentProduct instanceof Clothing) {
                                ((Clothing) currentProduct).setColour(attributeValue);
                            }
                        }
                    }
                }
            }

            // Add the last product
            if (currentProduct != null) {
                productList.add(currentProduct);
            }

            System.out.println("Data loaded successfully from ProductListOfSystem.txt");
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }

    private boolean IsIdUnique(String productId) {
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void openGui() {
        SwingUtilities.invokeLater(() -> {
            ShoppingGUI shoppingGUI = new ShoppingGUI(this);
            shoppingGUI.setVisible(true);
        });
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void initSampleData() {
    }
}
