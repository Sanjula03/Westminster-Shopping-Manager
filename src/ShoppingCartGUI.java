import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ShoppingCartGUI extends JFrame {

    private final ShoppingCart shoppingCart;
    private final DefaultTableModel tableModel;
    private final JLabel totalPriceLabel;

    public ShoppingCartGUI(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;

        setTitle("Shopping Cart");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setLayout(new BorderLayout());

        String[] columnNames = {"Product Name", "Product ID", "Price", "Available Items"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null); // Disable editing
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        totalPriceLabel = new JLabel("Total Price: Rs 0.0");
        bottomPanel.add(totalPriceLabel, BorderLayout.NORTH);

        JButton closeButton = new JButton("Close");
        bottomPanel.add(closeButton, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(e -> dispose());
    }

    public void updateShoppingCart() {
        List<Product> cartProducts = shoppingCart.getProducts();
        tableModel.setRowCount(0);

        if (cartProducts != null) {
            double totalPrice = 0.0;

            for (Product product : cartProducts) {
                Object[] rowData = {
                        product.getProductName(),
                        product.getProductId(),
                        product.getPrice(),  // Removed the applyDiscount method here
                        product.getAvailableItems()
                };
                tableModel.addRow(rowData);

                totalPrice += product.getPrice();  // Removed the applyDiscount method here
            }

            totalPriceLabel.setText("Total Price: Rs " + (totalPrice));
        }
    }
}
