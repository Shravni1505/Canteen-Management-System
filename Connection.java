package canteen.management.system;
import java.sql.*;
import java.sql.DriverManager;

public class Connection{
	java.sql.Connection conn;
	Statement stm;
	public Connection(){
		//driver load
		try {
			String url="jdbc:mysql://localhost:3306/";
			String username="root";
			String password="#Pink1234";
			//conn establish
		    conn=DriverManager.getConnection(url,username,password);
			stm=conn.createStatement();
			System.out.println("Connected successfully");
			//closing connection
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 

	}
}

