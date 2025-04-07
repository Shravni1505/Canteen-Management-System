package canteen.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CanteenManagementSystem extends JFrame implements ActionListener{
    
    JMenuBar mb;
    JMenu m1;  // Only Canteen Management menu now
    JMenuItem i1;  // Order Counter item
    
    CanteenManagementSystem() {
     
        super("CANTEEN MANAGEMENT SYSTEM: USER INTERFACE");
        
        // Create the menu bar
        mb = new JMenuBar();
        add(mb);
        
        // Create the Canteen Management menu
        m1 = new JMenu("CANTEEN MANAGEMENT");
        m1.setForeground(Color.RED);
        mb.add(m1);
        
        // Create menu items for Canteen Management options
        i1 = new JMenuItem("ORDER COUNTER");
        i1.setBackground(Color.BLACK); 
        i1.setForeground(Color.WHITE);
        i1.addActionListener(this);
        m1.add(i1);
        
        // Set the position of the menu bar
        mb.setBounds(0, 0, 1920, 30);
        
        // Set up the background image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Suswaad.jpg"));
        Image i2 = i1.getImage().getScaledInstance(900, 1100, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l1 = new JLabel(i3);
        l1.setBounds(0, 0, 600, 637);
        add(l1);
        
     // Create a welcome label
        JLabel l2 = new JLabel("SUSWAAD CANTEEN");
        l2.setBounds(120, 60, 900, 70);
        l2.setForeground(Color.white);
        l2.setFont(new Font("serif", Font.PLAIN, 40));
        l1.add(l2);
        
        JLabel l3 = new JLabel("WELCOMES YOU ");
        l3.setBounds(120, 100, 900, 70);
        l3.setForeground(Color.white);
        l3.setFont(new Font("serif", Font.PLAIN, 40));
        l1.add(l3);
        
        // Set the background color of the content pane
        getContentPane().setBackground(Color.WHITE);
        
        // Set layout and window size
        setLayout(null);
        setBounds(0, 0, 1950, 1020);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        // Action listeners for the Canteen Management menu items
        if (ae.getActionCommand().equals("ORDER COUNTER")) {
            new OrderCounter().setVisible(true); // Navigate to OrderCounter screen
        }
    }
    
    // Main method to run the Dashboard
    public static void main(String[] args) {
        new CanteenManagementSystem().setVisible(true); // Create and show the Dashboard window
    }
}