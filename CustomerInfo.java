package canteen.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.sql.Connection;

public class CustomerInfo extends JFrame implements ActionListener{
    
    
    JButton b1,b2;
    JTable t1;
    CustomerInfo(){
        
        
    t1 = new JTable();
    t1.setBounds(0, 40, 1000, 500); 
    add(t1);
    
    JLabel l1 = new JLabel("ID"); 
    l1.setBounds(40,10, 50, 20); 
    add(l1);

//    JLabel l2 = new JLabel("Number"); 
//    l2.setBounds(160,10, 70, 20); 
//    add(l2);
    
    JLabel l3 = new JLabel("Name"); 
    l3.setBounds(290,10, 100, 20); 
    add(l3);
    
//    JLabel l4 = new JLabel("Gender"); 
//    l4.setBounds(400,10, 70, 20); 
//    add(l4);
//    
    JLabel l5 = new JLabel("Phone no"); 
    l5.setBounds(540,10, 70, 20); 
    add(l5);
    
//    JLabel l6 = new JLabel("Allocated TN"); 
//    l6.setBounds(640,10, 100, 20); 
//    add(l6);
//    
    JLabel l7 = new JLabel("Password"); 
    l7.setBounds(790,10, 70, 20); 
    add(l7);
    
//    JLabel l8 = new JLabel("Item Amount"); 
//    l8.setBounds(900,10, 70, 20); 
//    add(l8);
//     
     
     b1 = new JButton("Load Data");
     b1.setBackground(Color.BLACK);
     b1.setForeground(Color.WHITE);
     b1.setBounds(350, 560, 120, 30);
    
     b1.addActionListener(this);
     add(b1);

     b2 = new JButton("Back");
     b2.setBackground(Color.BLACK); 
     b2.setForeground(Color.WHITE);
     b2.setBounds(530, 560, 120, 30); 
     b2.addActionListener(this);
     add(b2);
     
     getContentPane().setBackground(Color.WHITE);
     
     setLayout(null);
     setBounds(320, 150, 1000, 650);
     setVisible(true);
     
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1){
        	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen", "root", "#Pink1234")) {
                String str = "SELECT user_id, user_name, phone_no, password FROM user";
                try (Statement stm = con.createStatement()) {
                    ResultSet rs = stm.executeQuery(str);  // Corrected here: using 'stm' instead of 'con.stm'
                    t1.setModel(DbUtils.resultSetToTableModel(rs)); // Populate the table with data
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage());
                e.printStackTrace();
            }
        }else if(ae.getSource() == b2){
            new OrderCounter().setVisible(true);
            this.setVisible(false);
        }
        
    }
    public static void main(String[] args){
     new CustomerInfo().setVisible(true);
    }
    
}  