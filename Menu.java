
package canteen.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import java.sql.Connection;

public class Menu extends JFrame implements ActionListener{
    
    
    JButton b1,b2;
    JTable t1;
    Menu(){
        super("CONTACLESS COMPUTERIZED CANTEEN MANAGEMENT SYSTEM");
    
    JLabel l1 = new JLabel("Item Name"); 
    l1.setBounds(180,10, 100, 20); 
    add(l1);

    JLabel l2 = new JLabel("Cost"); 
    l2.setBounds(370,10, 100, 20); 
    add(l2);
        
     t1 = new JTable();
     t1.setBounds(0, 50, 700, 350); 
     add(t1);
     
     b1 = new JButton("Load Data");
     b1.setBackground(Color.BLACK);
     b1.setForeground(Color.WHITE);
     b1.setBounds(180, 400, 120, 30);
    
     b1.addActionListener(this);
     add(b1);

     b2 = new JButton("Back");
     b2.setBackground(Color.BLACK); 
     b2.setForeground(Color.WHITE);
     b2.setBounds(380, 400, 120, 30); 
     b2.addActionListener(this);
     add(b2);
     
     getContentPane().setBackground(Color.WHITE);
     
     setLayout(null);
     setBounds(480, 200, 700, 480);
     setVisible(true);
     
    } 
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == b1){
        	try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen", "root", "#Pink1234")) {
                String str = "SELECT item_name, item_price FROM item";
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
        } 
        else if(ae.getSource() == b2){
            new OrderCounter().setVisible(true);
            this.setVisible(false);
        }
        
    }
    public static void main(String[] args){
     new Menu().setVisible(true);
    }
    
}