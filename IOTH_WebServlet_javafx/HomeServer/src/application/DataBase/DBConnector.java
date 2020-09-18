package application.DataBase;

import java.sql.*;

public class DBConnector {
	protected Connection con=null;
	protected PreparedStatement pstmt;
	protected boolean Connect=false;
	public boolean tryConnect() {
		try {
			String URL="jdbc:oracle:thin:@localhost:1521:XE";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con=DriverManager.getConnection(URL,"eb","ll");
			Connect=true;
			System.out.println("db¿ÀÇÂ");
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("¿À·ù");
			e.printStackTrace();
		}	
		return false;
	}

}
