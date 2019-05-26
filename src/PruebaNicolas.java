import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Properties;

public class PruebaNicolas {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		String url = "jdbc:mysql://localhost/"+"Nisand"+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
		Properties info = new Properties();
		info.put("user","root"); 
		info.put("password", "Nikolito0!"); 
		Class.forName("com.mysql.cj.jdbc.Driver"); 
		Connection dbConnection = DriverManager.getConnection(url, info); 
		if (dbConnection != null) { 
		System.out.println("conecto");
			} 
		else {
			System.out.println("mierda");
		}
	}
}
