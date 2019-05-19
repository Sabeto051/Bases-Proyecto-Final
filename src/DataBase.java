
import java.sql.*;
import java.util.Properties;


public class DataBase {
	
	
	
	
	 static Connection dbConnection = null; 
	    static String username = "root"; // replace with your MySQL client username
	    static String password = "holahola123"; // replace with your MySQL client password
	    
	    
	 public void Connect(String database_name) {
			
			try { 
			String database="test";
			String url = "jdbc:mysql://localhost/"+database+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
			Properties info = new Properties();
			info.put("user", username); 
			info.put("password", password); 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			dbConnection = DriverManager.getConnection(url, info); 
			if (dbConnection != null) { 
				System.out.println("Successfully connected to MySQL database test"); 
				} 
			} 
			
			catch (SQLException | ClassNotFoundException ex) { 
				System.out.println("An error occurred while connecting MySQL databse"); ex.printStackTrace(); } 
	 }
	 public static void executeMySQLQuery(){
	        @SuppressWarnings("rawtypes")
			Statement stmt = null;
	        ResultSet resultset = null;
			try {
	            stmt = dbConnection.createStatement();
	            resultset = stmt.executeQuery("SHOW DATABASES;");
	 
	            if (stmt.execute("SHOW DATABASES;")) {
	                resultset = stmt.getResultSet();
	            }
	 
	            while (resultset.next()) {
	                System.out.println(resultset.getString("Database"));
	            }
	        }
	        catch (SQLException ex){
	            // handle any errors
	            ex.printStackTrace();
	        }
	        finally {
	            // release resources
	            if (resultset != null) {
	                try {
	                    resultset.close();
	                } catch (SQLException sqlEx) { }
	                resultset = null;
	            }
	 
	            if (stmt != null) {
	                try {
	                    stmt.close();
	                } catch (SQLException sqlEx) { }
	                stmt = null;
	            }
	 
	            if (dbConnection!= null) {
	                try {
	                	dbConnection.close();
	                } catch (SQLException sqlEx) { }
	                dbConnection = null;
	            }
	        }
	    }
}
