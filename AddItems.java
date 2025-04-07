package canteen.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class AddItems extends JFrame implements ActionListener {

    // Declare UI components
    private JTextField t1, t3;
    private JComboBox<String> c1; // ComboBox for item selection
    private JComboBox<String> paymentModeCombo; // ComboBox for payment mode
    private JButton b1, b2, b3;
    private JTextArea cartArea;

    // Cart to hold the items added
    private ArrayList<String> cartItems;
    private ArrayList<Integer> cartQuantities;
    private ArrayList<Double> cartPrices;

    double totalPrice;

    // Database connection variable
    private java.sql.Connection conn;

    AddItems() {
        super("Place Order - Canteen Management System");

        // Initialize the cart arrays
        cartItems = new ArrayList<>();
        cartQuantities = new ArrayList<>();
        cartPrices = new ArrayList<>();

        // Set up the UI components
        setupUI();

        // Initialize the database connection
        initializeDatabaseConnection();

        // Populate the item ComboBox
        loadItemsIntoComboBox();
    }

    private void setupUI() {
        // Title
        JLabel l1 = new JLabel("Place Order");
        l1.setBounds(180, 20, 150, 30);
        l1.setFont(new Font("Tahoma", Font.BOLD, 18));
        add(l1);

        // Customer ID input field
        JLabel customerLabel = new JLabel("Customer ID");
        customerLabel.setBounds(60, 80, 120, 30);
        customerLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(customerLabel);

        t1 = new JTextField();
        t1.setBounds(200, 80, 150, 30);
        add(t1);

        // Item Name dropdown (ComboBox)
        JLabel itemLabel = new JLabel("Item Name");
        itemLabel.setBounds(60, 130, 120, 30);
        itemLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(itemLabel);

        c1 = new JComboBox<>();
        c1.setBounds(200, 130, 150, 30);
        add(c1);

        // Quantity input field
        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(60, 180, 120, 30);
        quantityLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(quantityLabel);

        t3 = new JTextField();
        t3.setBounds(200, 180, 150, 30);
        add(t3);

        // Cart Button (Displays cart items)
        b3 = new JButton("View Cart");
        b3.setBounds(320, 20, 120, 30);
        b3.setBackground(Color.GREEN);
        b3.setForeground(Color.WHITE);
        b3.addActionListener(this);
        add(b3);

        // Cart display area (right side of window)
        cartArea = new JTextArea();
        cartArea.setBounds(400, 80, 250, 300);
        cartArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        cartArea.setEditable(false);

        // Add a scroll pane for the cart area
        JScrollPane scrollPane = new JScrollPane(cartArea);
        scrollPane.setBounds(400, 80, 250, 300);
        add(scrollPane);

        // Payment Mode dropdown
        JLabel paymentModeLabel = new JLabel("Payment Mode");
        paymentModeLabel.setBounds(60, 230, 120, 30);
        paymentModeLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(paymentModeLabel);

        paymentModeCombo = new JComboBox<>(new String[]{"Online", "Cash"});
        paymentModeCombo.setBounds(200, 230, 150, 30);
        add(paymentModeCombo);

        // Add Item Button (now moved below the Payment Mode dropdown)
        b1 = new JButton("Add Item");
        b1.setBounds(60, 280, 130, 30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        add(b1);

        // Cancel Button
        b2 = new JButton("Cancel");
        b2.setBounds(220, 280, 130, 30);
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        add(b2);

        // Frame properties
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
        // Add Item functionality
        if (ae.getSource() == b1) {
            String customerId = t1.getText();
            String itemName = (String) c1.getSelectedItem();
            String quantityStr = t3.getText();

            // Input validation
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

            // Fetch the price of the selected item
            double price = getItemPrice(itemName);

            // Add item to cart
            cartItems.add(itemName);
            cartQuantities.add(quantity);
            cartPrices.add(price * quantity);

            // Show confirmation message
            JOptionPane.showMessageDialog(this, itemName + " added to the order!");

            // Clear the quantity field
            t3.setText("");

            // Update cart display immediately
            updateCartDisplay();

            // Insert into database
            insertOrderIntoDatabase(customerId, itemName, quantity);
        }

        // Cancel functionality (clears all fields and closes the window)
        else if (ae.getSource() == b2) {
            this.setVisible(false);
        }

        // View Cart functionality
        else if (ae.getSource() == b3) {
            updateCartDisplay();
        }
    }

    void updateCartDisplay() {
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

        // Display the cart details
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
            } else {
                JOptionPane.showMessageDialog(this, "Item price not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving item price: " + e.getMessage());
        }
        return price;
    }

    private void insertOrderIntoDatabase(String customerId, String itemName, int quantity) {
        try {
            double orderAmount = getItemPrice(itemName) * quantity;
            
            // Insert into orders table
            String orderQuery = "INSERT INTO orders (user_id, item_name, quantity, order_amt) VALUES (?, ?, ?, ?)";
            PreparedStatement orderPs = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderPs.setString(1, customerId);
            orderPs.setString(2, itemName);
            orderPs.setInt(3, quantity);
            orderPs.setDouble(4, orderAmount);

            int rowsAffected = orderPs.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Order placed successfully.");
                
                // Get the generated order_id
                ResultSet generatedKeys = orderPs.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int orderId = generatedKeys.getInt(1);
                    
                    // Insert payment details
                    String paymentMode = (String) paymentModeCombo.getSelectedItem();
                    String paymentQuery = "INSERT INTO payment (payment_mode, order_id, amt) VALUES (?, ?, ?)";
                    PreparedStatement paymentPs = conn.prepareStatement(paymentQuery);
                    paymentPs.setString(1, paymentMode);
                    paymentPs.setInt(2, orderId);
                    paymentPs.setDouble(3, orderAmount);
                    
                    paymentPs.executeUpdate();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error placing the order.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddItems().setVisible(true); // Create and show the PlaceOrder window
    }
}
