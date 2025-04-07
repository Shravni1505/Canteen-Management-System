package canteen.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class OrderCounter extends JFrame implements ActionListener {

    // Declare the buttons
    JButton b1, b2, b3, b4, b5;    

    OrderCounter() {
        super("CONTACTLESS COMPUTERIZED CANTEEN MANAGEMENT SYSTEM");

        // Initialize the buttons
        b1 = new JButton("Login");
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.setBounds(10, 30, 200, 30);
        b1.addActionListener(this);
        add(b1);

        b2 = new JButton("Signup");
        b2.setBackground(Color.BLACK);
        b2.setForeground(Color.WHITE);
        b2.setBounds(10, 70, 200, 30);
        b2.addActionListener(this);
        add(b2);

        b3 = new JButton("Menu");
        b3.setBackground(Color.BLACK);
        b3.setForeground(Color.WHITE);
        b3.setBounds(10, 110, 200, 30);
        b3.addActionListener(this);
        add(b3);

        b4 = new JButton("Order Food Here");
        b4.setBackground(Color.BLACK);
        b4.setForeground(Color.WHITE);
        b4.setBounds(10, 150, 200, 30);
        b4.addActionListener(this);
        add(b4);

        b5 = new JButton("Logout");
        b5.setBackground(Color.BLACK);
        b5.setForeground(Color.WHITE);
        b5.setBounds(10, 190, 200, 30);
        b5.addActionListener(this);
        add(b5);

        // Adding image/logo to the right side of the window (optional)
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("logo.jpg"));
        JLabel l1 = new JLabel(i1);
        l1.setBounds(250, 0, 500, 450);
        add(l1);

        // Setting the background color and layout of the frame
        getContentPane().setBackground(Color.white);
        setLayout(null);
        setBounds(420, 200, 800, 470);
        setVisible(true);
    }

    // Action listeners for all buttons
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            // Navigate to Login page
            new Login().setVisible(true); // You need to implement the Login class
            this.setVisible(false);
        } else if (ae.getSource() == b2) {
            // Navigate to Signup page
            new NewLogin().setVisible(true); // You need to implement the Signup class
            this.setVisible(false);
        } else if (ae.getSource() == b3) {
            // Navigate to Menu page
            new Menu().setVisible(true); // You need to implement the Menu class
            this.setVisible(false);
        } else if (ae.getSource() == b4) {
            // Navigate to Order Food page
            new AddItems().setVisible(true); // You need to implement the AddItems class
            this.setVisible(false);
        } else if (ae.getSource() == b5) {
            // Logout functionality
            JOptionPane.showMessageDialog(this, "You have logged out.");
            System.exit(0); // Closes the application
        }
    }

    public static void main(String[] args) {
        new OrderCounter().setVisible(true); // Create and show the OrderCounter window
    }
}
