package canteen.management.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.sql.Connection;

public class NewLogin extends JFrame implements ActionListener{
    
    JLabel l1,l2,l3,l4;
    JTextField t1,t3,t4;
    JPasswordField t2;
    JButton b1,b2;
    
        
    NewLogin()
    {
 
        super("CONTACLESS COMPUTERIZED CANTEEN MANAGEMENT SYSTEM");
        
        JLabel l1 = new JLabel("NEW USER"); 
        l1.setBounds(105,20, 200, 30); 
        l1.setForeground(Color.BLUE);
        l1.setFont(new Font ("Tahoma",Font.BOLD,20));
        add(l1);
    
        l1 = new JLabel("UserId");
        l1.setBounds(40 , 80 , 100 , 30 );
        add(l1);
        
        l2 = new JLabel("Password");
        l2.setBounds(40 , 140 , 100 , 30 );
        add(l2);
        
        
        
        t1 = new JTextField();
        t1.setBounds(150 , 80 , 150 , 30);
        add(t1);
        
        t2 = new JPasswordField();
        t2.setBounds(150 , 140 , 150 , 30);
        add(t2);
        
        l3 = new JLabel("Username");
        l3.setBounds(40 , 200 , 100 , 30 );
        add(l3);
        
        t3 = new JTextField();
        t3.setBounds(150 , 200 , 150 , 30);
        add(t3);
        
        l4 = new JLabel("Phone no");
        l4.setBounds(40 , 260 , 100 , 30 );
        add(l4);
        
        t4 = new JTextField();
        t4.setBounds(150 , 260 , 150 , 30);
        add(t4);
        
        b1 = new JButton("Signup");
        b1.setBackground(Color.RED);
        b1.setForeground(Color.WHITE);
        b1.setBounds(40 , 320 , 120 , 30 );
        b1.addActionListener(this);
        add(b1);
        
        b2 = new JButton("Cancel");
        b2.setBounds(180 , 320 , 120 , 30 );
        b2.setBackground(Color.RED);
        b2.setForeground(Color.WHITE);
        b2.addActionListener(this);
        add(b2);
        
//        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("canteen/management/system/icons/second.jpg "));
//        Image i2 = i1.getImage().getScaledInstance(200 , 200 , Image.SCALE_DEFAULT);
//        ImageIcon i3 = new ImageIcon(i2);
//        
        
//        JLabel l3 = new JLabel(i3);
//        l3.setBounds(350 , 10 , 200 , 200 );
//        add(l3);
//        
        
        getContentPane().setBackground(Color.WHITE);
        
        setLayout(null);
        setBounds(500, 400, 800, 370);
        setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==b1){
        String userid = t1.getText();
        String password = new String(t2.getPassword());
        String username=t3.getText();
        String phoneno=t4.getText();
        
        // Validate inputs
        if (userid.isEmpty() || password.isEmpty()|| username.isEmpty()|| phoneno.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }
        //database connection
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/canteen", "root", "#Pink1234")) {
                    String str = "insert into user (user_id,user_name,phone_no,password) values (?,?,?,?)";
                    try (PreparedStatement ps = con.prepareStatement(str)) {
                        ps.setString(1, userid);
                        ps.setString(2, username);
                        ps.setString(3, phoneno);
                        ps.setString(4, password);
                       

                        int rowsAffected = ps.executeUpdate();
                        if (rowsAffected > 0) {
                        	 JOptionPane.showMessageDialog(null,"New user Added");
                            this.setVisible(false);  // Close the window
                            this.dispose();  // Dispose of the frame
                        }
                    }
                }catch(Exception e){
                      System.out.println(e);
                    }
		
        
    }else if(ae.getSource() == b2){
        new Login().setVisible(true);
            this.setVisible(false);
    }
    }
    
     public static void main(String[] args){
         new NewLogin();
         
     }
}