package UAT;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	public static Connection connect = null;
	public static Statement st = null;
	public static String Arch_DB;
	public static int Doc_ID_DB;
	public static int Arch_ID_DB;
	
	
	public MySQL() {
		/*try {
			SQL_Connection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	}
	public void SQL_Connection() {
		try{
			if (connect == null){
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Try to connect to MySQL.");
				connect = DriverManager.getConnection("jdbc:mysql://" + LoginPage.server  + ":3307/EDT_S1", "root", "Stark123!");
				System.out.println("Connection: " + connect);
			}
			Statement statement = connect.createStatement();
			ResultSet rs_doc = statement.executeQuery("SELECT * FROM eddocumt;");
			rs_doc.last();
			System.out.println("There are " + rs_doc.getRow() + " documents from database.");
			System.out.println("The Document ID is " + rs_doc.getInt(1));
			Doc_ID_DB = rs_doc.getInt(1);
			System.out.println(rs_doc.getString(12));
			Arch_DB = rs_doc.getString(12);
			rs_doc.isClosed();
			
			ResultSet rs_arch = statement.executeQuery("SELECT * FROM edarchiv;");
			rs_arch.last();
			System.out.println("The Task ID is " + rs_arch.getInt(1));
			Arch_ID_DB = rs_arch.getInt(1);
			rs_arch.isClosed();
			
		} catch(SQLException err){
			System.out.println(err.getMessage());
		} finally {
			try {
				connect.isClosed();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}

}
