package canteen.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddEmployee extends JFrame implements ActionListener {

    // Declare UI components
    JTextField t1, t2, t3, t4;
    JComboBox<String> c1;
    JButton b1;

    // Constructor to set up the UI
    AddEmployee() {
        super("CONTACTLESS COMPUTERIZED CANTEEN MANAGEMENT SYSTEM");

        // Employee ID label and field
        JLabel name = new JLabel("Employee ID");
        name.setFont(new Font("Tahoma", Font.PLAIN, 17));
        name.setBounds(60, 30, 120, 30);
        add(name);

        t1 = new JTextField();
        t1.setBounds(200, 30, 150, 30);
        add(t1);

        // Employee Name label and field
        JLabel age = new JLabel("Name");
        age.setFont(new Font("Tahoma", Font.PLAIN, 17));
        age.setBounds(60, 80, 120, 30);
        add(age);

        t2 = new JTextField();
        t2.setBounds(200, 80, 150, 30);
        add(t2);

        // Job Post label and ComboBox (drop-down)
        JLabel job = new JLabel("Post");
        job.setFont(new Font("Tahoma", Font.PLAIN, 17));
        job.setBounds(60, 130, 120, 30);
        add(job);

        // Job positions for ComboBox
        String[] jobs = {"Chef(South Indian)", "Chef(Chinese)", "Chef(Sandwiches)", "Chef(Beverages)", "Helper", "Manager"};
        c1 = new JComboBox<>(jobs);
        c1.setBackground(Color.WHITE);
        c1.setBounds(200, 130, 150, 30);
        add(c1);

        // Salary label and field
        JLabel salary = new JLabel("Salary");
        salary.setFont(new Font("Tahoma", Font.PLAIN, 17));
        salary.setBounds(60, 180, 120, 30);
        add(salary);

        t3 = new JTextField();
        t3.setBounds(200, 180, 150, 30);
        add(t3);

        // Phone label and field
        JLabel phone = new JLabel("Phone");
        phone.setFont(new Font("Tahoma", Font.PLAIN, 17));
        phone.setBounds(60, 230, 120, 30);
        add(phone);

        t4 = new JTextField();
        t4.setBounds(200, 230, 150, 30);
        add(t4);

        // Submit button
        b1 = new JButton("Submit");
        b1.setBounds(200, 280, 150, 30);
        b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
        b1.addActionListener(this); // Attach action listener
        add(b1);

        // Adding the background image/logo (optional)
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("logo.jpg"));
        Image i2 = i1.getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(360, 60, 450, 450);
        add(l1);

        // Heading Label
        JLabel l2 = new JLabel("ADD EMPLOYEE DETAILS");
        l2.setForeground(Color.BLUE);
        l2.setBounds(410, 30, 400, 30);
        l2.setFont(new Font("Tahoma", Font.PLAIN, 30));
        add(l2);

        // Set the background color and layout
        getContentPane().setBackground(Color.WHITE);

        setLayout(null);
        setBounds(400, 200, 850, 530); // Set frame size and position
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // Retrieve data from the text fields and combo box
        String ID = t1.getText();
        String name = t2.getText();
        String post = (String) c1.getSelectedItem(); // Get selected job post
        String salary = t3.getText();
        String phone = t4.getText();

        // Check if any of the fields are empty
        if (ID.isEmpty() || name.isEmpty() || salary.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields.");
            return; // Exit if fields are empty
        }

        // Validate Phone number (ensure it's a valid format, can be expanded)
        if (!phone.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(null, "Invalid phone number. Enter a 10-digit number.");
            return;
        }

        // Database interaction
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen", "root", "#Pink1234")) {
            String query = "INSERT INTO staff (staff_id, staff_name, staff_phone_no, post, salary) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setString(1, ID);
                ps.setString(2, name);
                ps.setString(3, phone);
                ps.setString(4, post);
                ps.setString(5, salary);

                // Execute the query
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "New Employee Added Successfully!");
                    clearFields(); // Clear fields after successful insertion
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error adding employee: " + e.getMessage());
        }
    }

    // Method to clear all fields after successful insertion
    private void clearFields() {
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        c1.setSelectedIndex(0); // Reset ComboBox to first item
    }

    // Main method to launch the AddEmployee window
    public static void main(String[] args) {
        new AddEmployee().setVisible(true); // Create and show the AddEmployee window
    }
}
