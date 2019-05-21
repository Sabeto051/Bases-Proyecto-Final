
import java.sql.*;
import java.util.Arrays;
import java.util.Properties;

import com.mysql.cj.conf.ConnectionUrl.Type;
import com.mysql.cj.protocol.Resultset;


public class DataBase {
	
	
	
	
	/* static Connection dbConnection = null; 
	    static String username; //= "root"; // replace with your MySQL client username
	    static String password; //= "holahola123"; // replace with your MySQL client password
	    
	    */
	    static Connection dbConnection = null; 
	    static String username = "root"; // replace with your MySQL client username
	    static String password = "holahola123"; // replace with your MySQL client password
	    static String tabla="";
	    static String dataBase="";
	    static Statement stmt = null;
        static ResultSet resultset = null;
	    
	    
	 public static boolean Connect(String database_name) {
			
			try { 
			String url = "jdbc:mysql://localhost/"+database_name+"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"; 
			Properties info = new Properties();
			info.put("user", username); 
			info.put("password", password); 
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			dbConnection = DriverManager.getConnection(url, info); 
			if (dbConnection != null) { 
				dataBase=database_name;
				return true;
				} 
			else {
				return false;
			}
			} 
			
			catch (SQLException | ClassNotFoundException ex) { 
				if(ex.toString().split(":")[1].substring(0, 17).equals(" Unknown database")) {
					System.out.println("Acceso Denegado Base de Datos inexistente");
				}
				else {
					System.out.println("Acceso Denegado Por Usuario o Contraseņa Errados");
				}
				return false;}
	 }
	public static String[] executeMySQLQuery(){
	        @SuppressWarnings("rawtypes")
	        String BD[]=new String[0];
			try {
	            stmt = dbConnection.createStatement();
	            resultset = stmt.executeQuery("SHOW DATABASES;");
	 
	            if (stmt.execute("SHOW DATABASES;")) {
	                resultset = stmt.getResultSet();
	            }
	            int cont=1;
	            while (resultset.next()) {
	            	BD=Arrays.copyOf(BD, BD.length+1);
	                System.out.println(cont+":  "+resultset.getString("Database"));
	                BD[cont-1]=resultset.getString("Database");
	                cont++;
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
			return BD;
	    }
	public static void AgregarBaseDeDatos(String nombre) throws SQLException {
		try {
			stmt=dbConnection.createStatement();
			stmt.execute("CREATE DATABASE "+nombre+";");
			dbConnection.commit(); // now the database physically exists
			DataBase.dataBase=nombre;
			} catch (SQLException exception) {
				DataBase.dataBase=nombre;
			}
	}
	public static void EliminarBaseDeDatos(String nombre) throws SQLException {
		try {
			stmt=dbConnection.createStatement();
		    String sql = "DROP DATABASE "+nombre;
		    stmt.executeUpdate(sql);
		    dataBase=null;
		}
		catch(SQLException se) {
		      se.printStackTrace();
		   }
	} 
	public static String[] showTables() throws SQLException, ClassNotFoundException {
		 String[] BD = new String[0];
		 try {
			
			  DatabaseMetaData metadata = dbConnection.getMetaData();
			 
			 
			  // Specify the type of object; in this case we want tables
			 
			  String[] types = {"TABLE"};
			  ResultSet resultSet = metadata.getTables(dataBase, null, "%", types);
			
			 int cont=1;
			  while (resultSet.next()) {
			 
			    String tableName = resultSet.getString(3);
			 
			    String tableCatalog = resultSet.getString(1);
			 
			    String tableSchema = resultSet.getString(2);
			 
			    BD=Arrays.copyOf(BD, BD.length+1);
			    System.out.println((cont)+":  "+tableName);
			    BD[cont-1]=tableName;
			    cont++;
			 
			  }
			  return BD;
			    } catch (SQLException e) {
			 
			  System.out.println("Could not get database metadata " + e.getMessage());
			  return BD;
			    
	        }
	}
	public static void AgregarTabla(String nombreTabla,String caracteristicas) throws SQLException {
		try {
		String table="CREATE TABLE IF NOT EXISTS "+nombreTabla+"(" 
	            + caracteristicas+");";
		caracteristicas=nombreTabla+caracteristicas;
		stmt = dbConnection.createStatement();
        //This line has the issue
        stmt.executeUpdate(table);
		}
		catch(SQLException e) {
		System.out.println("Caracteristicas no validas");
		}
	}
	public static void getColumnNames(ResultSet rs) throws SQLException {
	    if (rs == null) {
	      return;
	    }
	    ResultSetMetaData rsMetaData = rs.getMetaData();
	    int numberOfColumns = rsMetaData.getColumnCount();

	    // get the column names; column indexes start from 1
	    for (int i = 1; i < numberOfColumns + 1; i++) {
	      String columnName = rsMetaData.getColumnName(i);
	      // Get the name of the column's table name
	      String tableName = rsMetaData.getTableName(i);
	      System.out.println("column name=" + columnName + " table=" + tableName + "");
	    }
	  }
	public static void EliminarTabla() throws SQLException {
		String table="DROP TABLE "+DataBase.tabla+";";
		stmt = dbConnection.createStatement();
        //This line has the issue
        stmt.executeUpdate(table);
        DataBase.tabla=null;
	}
	public static void AgregarRegistro(Object[] datos,String namess[], String typess[]) throws SQLException {
		
			 String querys="";
		     for(int i=0;i<namess.length;i++) {
		    	 if(i!=0) {
		    	 querys=querys+", "+namess[i];
		    	 }
		    	 else {
		         querys=namess[i]; 
		    	 }
		     }
		     String preguntas="?";
		     for(int i=0;i<namess.length-1;i++) {
		    	 preguntas=preguntas+", ?";
		     }
		     
		    String query = " insert into "+ DataBase.tabla + " ("+querys+")"
				        + " values ("+preguntas+")";
		     PreparedStatement preparedStmt = DataBase.dbConnection.prepareStatement(query);
		 
		      for(int i=0;i<typess.length;i++) {
		    	  System.out.println(typess[i]);
		    	  if(typess[i].equals("INT")){
		    		  preparedStmt.setInt((i+1),(int)(datos[i]));
		    	  }
		    	  if(typess[i].equals("VARCHAR")){
		    		  preparedStmt.setString((i+1),(String)(datos[i]));
		    	  }
		    	  if(typess[i].equals("FLOAT")){
		    		  preparedStmt.setFloat((i+1),(float)(datos[i]));
		    	  }
		    	  if(typess[i].equals("TEXT")){
		    		  preparedStmt.setString((i+1),(String)(datos[i]));
		    	  }
		    	 
		      }
	    	  preparedStmt.execute();
	    	 
		      
	}
	
	public static void EliminarRegistro(String campo,  String dato) throws SQLException {
		// our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
	      String querys = "SELECT * FROM "+tabla+" WHERE "+campo+"='"+dato+"';";
	      System.out.println(querys);

	      // create the java statement
	      Statement st = dbConnection.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(querys);
	      
	      // iterate through the java resultset
	      	  int id=0;
	      	  while(rs.next()) {
	    	  id = rs.getInt("id");
	    	  }
	
	        
	        // print the result
		
		String query = "delete from "+tabla+" where id = "+id;
	      PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
	      preparedStmt.execute();
	      }

	public static void ModificarRegistro(String campo,  Object dato) throws SQLException {
		// our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
	      String query = " insert into "+ DataBase.tabla + " ("+campo+")"
			        + " values (?) "+" WHERE "+campo+"='"+dato.toString()+"';";
	      PreparedStatement preparedStmt = DataBase.dbConnection.prepareStatement(query);
	      		  if(dato instanceof Integer) {
	      		  preparedStmt.setInt(1, (Integer)dato);    
	      		  }
			      if(dato instanceof Float) {
			      preparedStmt.setFloat(1, (Float)dato);  
			      		  }
			      		
				  if(dato instanceof String) {
				  preparedStmt.setString (1, (String)dato);  
				      		  }
	      	 preparedStmt.execute();
	      }
}





