package canteen.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class AddItemPayment extends JFrame implements ActionListener {

    private JTextField t1, t3;
    private JComboBox<String> c1;
    private JButton b1, b2, b3;
    private JTextArea cartArea;

    private ArrayList<String> cartItems;
    private ArrayList<Integer> cartQuantities;
    private ArrayList<Double> cartPrices;

    double totalPrice;

    private java.sql.Connection conn;

    public AddItemPayment() {
        super("Place Order - Canteen Management System");

        cartItems = new ArrayList<>();
        cartQuantities = new ArrayList<>();
        cartPrices = new ArrayList<>();

        setupUI();
        initializeDatabaseConnection();
        loadItemsIntoComboBox();
    }

    private void setupUI() {
        JLabel l1 = new JLabel("Place Order");
        l1.setBounds(180, 20, 150, 30);
        l1.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(l1);

        JLabel customerLabel = new JLabel("Customer ID");
        customerLabel.setBounds(60, 80, 120, 30);
        customerLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(customerLabel);

        t1 = new JTextField();
        t1.setBounds(200, 80, 150, 30);
        add(t1);

        JLabel itemLabel = new JLabel("Item Name");
        itemLabel.setBounds(60, 130, 120, 30);
        itemLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(itemLabel);

        c1 = new JComboBox<>();
        c1.setBounds(200, 130, 150, 30);
        add(c1);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(60, 180, 120, 30);
        quantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(quantityLabel);

        t3 = new JTextField();
        t3.setBounds(200, 180, 150, 30);
        add(t3);

        b1 = new JButton("Add Item");
        b1.setBounds(60, 230, 130, 30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Cancel");
        b2.setBounds(220, 230, 130, 30);
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        add(b2);

        b3 = new JButton("View Cart");
        b3.setBounds(320, 20, 120, 30);
        b3.setBackground(Color.GREEN);
        b3.setForeground(Color.WHITE);
        b3.addActionListener(this);
        add(b3);

        cartArea = new JTextArea();
        cartArea.setBounds(400, 80, 250, 300);
        cartArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cartArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(cartArea);
        scrollPane.setBounds(400, 80, 250, 300);
        add(scrollPane);

        getContentPane().setBackground(Color.WHITE);
        setBounds(570, 200, 700, 500);
        setLayout(null);
        setVisible(true);
    }

    private void initializeDatabaseConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen", "root", "#Pink1234");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
            System.exit(1);
        }
    }

    private void loadItemsIntoComboBox() {
        try {
            String query = "SELECT item_name FROM item";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                c1.addItem(rs.getString("item_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading items: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            String customerId = t1.getText();
            String itemName = (String) c1.getSelectedItem();
            String quantityStr = t3.getText();

            if (customerId.isEmpty() || quantityStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Customer ID and Quantity.");
                return;
            }

            if (itemName == null || itemName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select an item.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(quantityStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
                return;
            }

            double price = getItemPrice(itemName);
            cartItems.add(itemName);
            cartQuantities.add(quantity);
            cartPrices.add(price * quantity);

            JOptionPane.showMessageDialog(this, itemName + " added to the order!");
            t3.setText("");
            updateCartDisplay();

            String orderId = insertOrderIntoDatabase(customerId, itemName, quantity);
            if (orderId != null) {
                addPaymentForOrder(orderId);
            }

        } else if (ae.getSource() == b2) {
            this.setVisible(false);
        } else if (ae.getSource() == b3) {
            updateCartDisplay();
        }
    }

    private void updateCartDisplay() {
        StringBuilder cartContent = new StringBuilder();
        totalPrice = 0.0;

        for (int i = 0; i < cartItems.size(); i++) {
            cartContent.append(cartItems.get(i))
                        .append(" - Quantity: ").append(cartQuantities.get(i))
                        .append(" - Price: ₹").append(String.format("%.2f", cartPrices.get(i)))
                        .append("\n");
            totalPrice += cartPrices.get(i);
        }

        cartContent.append("\nTotal: ₹").append(String.format("%.2f", totalPrice));
        cartArea.setText(cartContent.toString());
    }

    private double getItemPrice(String itemName) {
        double price = 0.0;
        try {
            String query = "SELECT item_price FROM item WHERE item_name = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, itemName);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                price = rs.getDouble("item_price");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving item price: " + e.getMessage());
        }
        return price;
    }

    private String insertOrderIntoDatabase(String customerId, String itemName, int quantity) {
        String orderId = null;
        try {
            String query = "INSERT INTO orders (user_id, item_name, quantity) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, customerId);
            pst.setString(2, itemName);
            pst.setInt(3, quantity);
            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getString(1);
            }

            double price = getItemPrice(itemName);
            double orderAmt = price * quantity;
            String updateQuery = "UPDATE orders SET order_amt = ? WHERE order_id = ?";
            PreparedStatement pstUpdate = conn.prepareStatement(updateQuery);
            pstUpdate.setDouble(1, orderAmt);
            pstUpdate.setString(2, orderId);
            pstUpdate.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error placing order: " + e.getMessage());
        }
        return orderId;
    }

    private void addPaymentForOrder(String orderId) {
        try {
            String query = "SELECT order_amt FROM orders WHERE order_id = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, orderId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double amt = rs.getDouble("order_amt");

                String paymentQuery = "INSERT INTO payment (order_id, payment_mode, amt) VALUES (?, ?, ?)";
                PreparedStatement pstPayment = conn.prepareStatement(paymentQuery);
                pstPayment.setString(1, orderId);
                pstPayment.setString(2, "Cash");  // Assuming cash payment for simplicity
                pstPayment.setDouble(3, amt);
                pstPayment.executeUpdate();
                JOptionPane.showMessageDialog(this, "Payment for Order ID " + orderId + " has been processed.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error processing payment: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddItemPayment();
    }
}
