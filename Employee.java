
package canteen.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.sql.Connection;

public class Employee extends JFrame implements ActionListener{
    
    
    JButton b1,b2;
    JTable t1;
    Employee(){
        
        
    t1 = new JTable();
    t1.setBounds(0, 40, 1050, 500); 
    add(t1);
    
    JLabel l1 = new JLabel("ID"); 
    l1.setBounds(60,10, 50, 20); 
    add(l1);

//    JLabel l2 = new JLabel("Age"); 
//    l2.setBounds(160,10, 70, 20); 
//    add(l2);
    
    JLabel l3 = new JLabel("Name"); 
    l3.setBounds(300,10, 100, 20); 
    add(l3);
    
//    JLabel l4 = new JLabel("Job"); 
//    l4.setBounds(400,10, 70, 20); 
//    add(l4);
    
    JLabel l5 = new JLabel("Phone_No"); 
    l5.setBounds(490,10, 70, 20); 
    add(l5);
    
//    JLabel l6 = new JLabel("Phone"); 
//    l6.setBounds(640,10, 100, 20); 
//    add(l6);
//    
    JLabel l7 = new JLabel("Post"); 
    l7.setBounds(700,10, 70, 20); 
    add(l7);
    
    JLabel l8 = new JLabel("Salary"); 
    l8.setBounds(900,10, 70, 20); 
    add(l8);
     
     
     b1 = new JButton("Load Data");
     b1.setBackground(Color.BLACK);
     b1.setForeground(Color.WHITE);
     b1.setBounds(365, 560, 120, 30);
    
     b1.addActionListener(this);
     add(b1);

     b2 = new JButton("Back");
     b2.setBackground(Color.BLACK); 
     b2.setForeground(Color.WHITE);
     b2.setBounds(565, 560, 120, 30); 
     b2.addActionListener(this);
     add(b2);
     
     getContentPane().setBackground(Color.WHITE);
     
     setLayout(null);
     setBounds(300, 150, 1050, 650);
     setVisible(true);
     
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1){
        	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen", "root", "#Pink1234")) {
                String str = "SELECT * from staff";
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
     new Employee().setVisible(true);
    }
    
}