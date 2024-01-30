import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ShoppingGUI extends JFrame {

    private final WestminsterShoppingManager shoppingManager;
    private final ShoppingCart shoppingCart;
    private final DefaultTableModel tableModel;
    private final JTextArea detailsTextArea;
    private final JTextArea descriptionTextArea;

    private final ShoppingCartGUI shoppingCartGUI;

    public ShoppingGUI(WestminsterShoppingManager shoppingManager) {
        this.shoppingManager = shoppingManager;
        this.shoppingCart = new ShoppingCart();
        this.shoppingCartGUI = new ShoppingCartGUI(shoppingCart);

        setTitle("Westminster Shopping Centre");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel topRightButtonPanel = new JPanel();
        topRightButtonPanel.setLayout(new BoxLayout(topRightButtonPanel, BoxLayout.X_AXIS));

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        categoryPanel.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));

        String[] productTypes = {"All", "Electronics", "Clothes"};
        JComboBox<String> productTypeComboBox = new JComboBox<>(productTypes);
        categoryPanel.add(productTypeComboBox);
        topRightButtonPanel.add(categoryPanel);

        JButton viewCartButton = new JButton("Shopping Cart");
        topRightButtonPanel.add(Box.createHorizontalGlue());
        topRightButtonPanel.add(viewCartButton);
        add(topRightButtonPanel, BorderLayout.NORTH);

        String[] columnNames = {"Product Type", "Product Name", "Product ID", "Price", "Available Items"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        detailsTextArea = new JTextArea();
        add(new JScrollPane(detailsTextArea), BorderLayout.SOUTH);

        descriptionTextArea = new JTextArea();
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

        // Create a panel for description and add it to the west
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);
        add(descriptionPanel, BorderLayout.WEST);

        JButton addToCartButton = new JButton("Add to Shopping Cart");
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomButtonPanel.add(addToCartButton);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        addToCartButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                String productId = (String) tableModel.getValueAt(selectedRow, 2);
                Product selectedProduct = findProductById(productId);
                if (selectedProduct != null) {
                    shoppingCart.addProduct(selectedProduct);
                    updateDetailsTextArea(selectedProduct);
                    JOptionPane.showMessageDialog(ShoppingGUI.this,
                            selectedProduct.getProductName() + " added to Shopping Cart!");
                    shoppingCartGUI.updateShoppingCart();
                }
            }
        });

        viewCartButton.addActionListener(e -> showShoppingCart());

        productTypeComboBox.addActionListener(e -> updateTable((String) productTypeComboBox.getSelectedItem()));

        ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    String productId = (String) tableModel.getValueAt(selectedRow, 2);
                    Product selectedProduct = findProductById(productId);
                    updateDetailsTextArea(selectedProduct);
                    updateDescriptionArea(selectedProduct);
                }
            }
        });
    }

    private void updateTable(String productType) {
        tableModel.setRowCount(0);
        List<Product> productsToShow = filterProductsByType(productType);

        for (Product product : productsToShow) {
            Object[] rowData = {product.getProductType(), product.getProductName(),
                    product.getProductId(), product.getPrice(), product.getAvailableItems()};
            tableModel.addRow(rowData);
        }
    }

    private List<Product> filterProductsByType(String productType) {
        List<Product> filteredProducts = new ArrayList<>();

        for (Product product : shoppingManager.getProductList()) {
            String actualProductType = product.getProductType();

            if (("All".equals(productType) || actualProductType.equals(productType))) {
                filteredProducts.add(product);
            } else if ("Clothes".equals(productType) && actualProductType.equals("Clothing")) {
                filteredProducts.add(product);
            }
        }

        return filteredProducts;
    }

    private Product findProductById(String productId) {
        for (Product product : shoppingManager.getProductList()) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    private void updateDetailsTextArea(Product selectedProduct) {
        detailsTextArea.setText("");

        if (selectedProduct != null) {
            detailsTextArea.append("Product Name: " + selectedProduct.getProductName() + "\n");
            detailsTextArea.append("Product ID: " + selectedProduct.getProductId() + "\n");
            detailsTextArea.append("Product Type: " + selectedProduct.getProductType() + "\n");
            detailsTextArea.append("Price: Rs" + selectedProduct.getPrice() + "\n");
            detailsTextArea.append("Available Items: " + selectedProduct.getAvailableItems() + "\n");

            if (selectedProduct instanceof Electronics electronicsProduct) {
                detailsTextArea.append("Brand: " + electronicsProduct.getBrand() + "\n");
                detailsTextArea.append("Warranty Period: " + electronicsProduct.getWarrantyPeriod() + " months\n");
            } else if (selectedProduct instanceof Clothing clothingProduct) {
                detailsTextArea.append("Size: " + clothingProduct.getSize() + "\n");
                detailsTextArea.append("Color: " + clothingProduct.getColour() + "\n");
            }
        }
    }

    private void showShoppingCart() {
        SwingUtilities.invokeLater(() -> shoppingCartGUI.setVisible(true));
    }

    private void updateDescriptionArea(Product selectedProduct) {
        descriptionTextArea.setText("");

        if (selectedProduct != null) {
            descriptionTextArea.append("Product Description:\n");
            descriptionTextArea.append("Product Name: " + selectedProduct.getProductName() + "\n");
            descriptionTextArea.append("Product ID: " + selectedProduct.getProductId() + "\n");
            descriptionTextArea.append("Product Type: " + selectedProduct.getProductType() + "\n");
            descriptionTextArea.append("Price: Rs" + selectedProduct.getPrice() + "\n");

            if (selectedProduct instanceof Electronics electronicsProduct) {
                descriptionTextArea.append("Brand: " + electronicsProduct.getBrand() + "\n");
                descriptionTextArea.append("Warranty Period: " + electronicsProduct.getWarrantyPeriod() + " months\n");
            } else if (selectedProduct instanceof Clothing clothingProduct) {
                descriptionTextArea.append("Size: " + clothingProduct.getSize() + "\n");
                descriptionTextArea.append("Color: " + clothingProduct.getColour() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
            shoppingManager.initSampleData();

            ShoppingGUI shoppingGUI = new ShoppingGUI(shoppingManager);
            shoppingGUI.setVisible(true);
        });
    }
}
