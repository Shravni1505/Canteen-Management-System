package canteen.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddMenu extends JFrame implements ActionListener {

    JTextField t1, t2, t3, t4, t5, t6;
    JButton b1;

    AddMenu() {
        super("CONTACTLESS COMPUTERIZED CANTEEN MANAGEMENT SYSTEM");

        // Initialize UI components
        JLabel name = new JLabel("Item Id");
        name.setFont(new Font("Tahoma", Font.PLAIN, 17));
        name.setBounds(60, 30, 120, 30);
        add(name);

        t1 = new JTextField();
        t1.setBounds(200, 30, 150, 30);
        add(t1);

        JLabel cost = new JLabel("Item Name");
        cost.setFont(new Font("Tahoma", Font.PLAIN, 17));
        cost.setBounds(60, 80, 120, 30);
        add(cost);

        t2 = new JTextField();
        t2.setBounds(200, 80, 150, 30);
        add(t2);
        
        JLabel type = new JLabel("Item type");
        type.setFont(new Font("Tahoma", Font.PLAIN, 17));
        type.setBounds(60, 130, 150, 30);
        add(type);

        t3 = new JTextField();
        t3.setBounds(200, 130, 150, 30);
        add(t3);
        
        JLabel price = new JLabel("Price");
        price.setFont(new Font("Tahoma", Font.PLAIN, 17));
        price.setBounds(60, 180, 120, 30);
        add(price);

        t4 = new JTextField();
        t4.setBounds(200, 180, 150, 30);
        add(t4);

        JLabel quantity = new JLabel("Quantity");
        quantity.setFont(new Font("Tahoma", Font.PLAIN, 17));
        quantity.setBounds(60, 230, 120, 30);
        add(quantity);

        t5 = new JTextField();
        t5.setBounds(200, 230, 150, 30);
        add(t5);

        b1 = new JButton("Submit");
        b1.setBounds(200, 420, 150, 30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this);
        add(b1);

        // Load logo image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Logo.jpg"));
        if (i1 == null) {
            System.out.println("Image not found!");
        } else {
            Image i2 = i1.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
            ImageIcon i3 = new ImageIcon(i2);
            JLabel l1 = new JLabel(i3);
            l1.setBounds(360, 60, 450, 450);
            add(l1);
        }

        JLabel l2 = new JLabel("ADD NEW MENU DETAILS");
        l2.setForeground(Color.BLUE);
        l2.setBounds(410, 30, 400, 30);
        l2.setFont(new Font("Tahoma", Font.BOLD, 30));
        add(l2);

        getContentPane().setBackground(Color.WHITE);

        setLayout(null);
        setBounds(400, 200, 850, 530);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
    	String itemId=t1.getText();
        String itemName = t2.getText();
        String itemType = t3.getText();
        String itemPrice = t4.getText();
        String itemQuantity = t5.getText();

        // Validate inputs
        if (itemName.isEmpty() || itemId.isEmpty()|| itemType.isEmpty()|| itemPrice.isEmpty()|| itemQuantity.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        // Database insertion
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen", "root", "#Pink1234")) {
            String query = "INSERT INTO item (item_id, item_name, item_type, item_price, item_quantity) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, itemId);
                ps.setString(2, itemName);
                ps.setString(3, itemType);
                ps.setString(4, itemPrice);
                ps.setString(5, itemQuantity);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "New Menu Added");
                    this.setVisible(false);  // Close the window
                    this.dispose();  // Dispose of the frame
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new AddMenu().setVisible(true);
    }
}
