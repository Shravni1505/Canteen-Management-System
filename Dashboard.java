package canteen.management.system;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Dashboard extends JFrame implements ActionListener {
    
    JMenuBar mb;
    JMenu m2;  // Only Admin menu now
    JMenuItem i2, i4;  // Add Employee and Add Menu items
    
    Dashboard() {
     
        super("CANTEEN MANAGEMENT SYSTEM: STAFF INTERFACE");
        
        // Create the menu bar
        mb = new JMenuBar();
        add(mb);
        
        // Create the Admin menu
        m2 = new JMenu("ADMIN");
        m2.setForeground(Color.BLUE);
        mb.add(m2);
        
        // Create menu items for Admin options
        i2 = new JMenuItem("ADD EMPLOYEE");
        i2.setBackground(Color.BLACK); 
        i2.setForeground(Color.WHITE);
        i2.addActionListener(this);
        m2.add(i2);
        
        i4 = new JMenuItem("ADD MENU");
        i4.setBackground(Color.BLACK); 
        i4.setForeground(Color.WHITE);
        i4.addActionListener(this);
        m2.add(i4);
        
        // Set the position of the menu bar
        mb.setBounds(0, 0, 1920, 30);
        
        // Set up the background image
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
        getContentPane().setBackground(Color.BLACK);
        
        // Set layout and window size
        setLayout(null);
        setBounds(0, 0, 20500, 15200);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        // Action listeners for the Admin menu items
        if (ae.getActionCommand().equals("ADD EMPLOYEE")) {
            new AddEmployee().setVisible(true); // Navigate to AddEmployee screen
        } else if (ae.getActionCommand().equals("ADD MENU")) {
            new AddMenu().setVisible(true); // Navigate to AddMenu screen
        }
    }
    
    // Main method to run the Dashboard
    public static void main(String[] args) {
        new Dashboard().setVisible(true); // Create and show the Dashboard window
    }
}
