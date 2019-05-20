
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
					System.out.println("Acceso Denegado Por Usuario o Contraseña Errados");
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
	public static void AgregarRegistro() throws SQLException {
		DatabaseMetaData metadata = dbConnection.getMetaData();
		 
		 
		  // Specify the type of object; in this case we want tables
		 
		String BD[]=new String[0];
		ResultSet resultSet = metadata.getColumns(dataBase, null, tabla, "%");
		
	      // get the column names from the ResultSet
		int cont=0;
		String querys="";
		int primero=0;
		int postypes=0;
		int posnames=0;
		String typess[]=new String[0];
		String namess[]=new String[0];
		  while (resultSet.next()) {
		 
		    String tableName = resultSet.getString("COLUMN_NAME");
		    String type = resultSet.getString("TYPE_NAME");
		    
		 
		    if(primero==0) {
		    	 primero=1;
		    }
		    else {
		    	if(primero==1) {
		    		querys=querys+tableName;
		    		typess=Arrays.copyOf(typess, typess.length+1);
		    		typess[postypes]=type;
				    postypes++;
				    namess=Arrays.copyOf(namess, namess.length+1);
		    		namess[posnames]=tableName;
				    posnames++;
			    	 primero=2;
			    }
		    	else {
		    	 querys=querys+", "+tableName;
		    	 namess=Arrays.copyOf(namess, namess.length+1);
		    		namess[posnames]=tableName;
				    posnames++;
		    	 typess=Arrays.copyOf(typess, typess.length+1);
				    typess[postypes]=type;
				    postypes++;
		    	}
		    }
		    cont++;
		 
		  }
		String preguntas="?";
		for(int i=0;i<cont-2;i++) {
			preguntas=preguntas+", ?";
		}
		String query = " insert into "+ tabla + " ("+querys+")"
		        + " values ("+preguntas+")";

		      // create the mysql insert preparedstatement
		      PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
		      
		     
		      
		     Entradas inputs=new Entradas();
		      for(int i=0;i<typess.length;i++) {
		    	  System.out.println(typess[i]);
		    	  if(typess[i].equals("INT")){
		    		  int num_ingresado=inputs.leerInt(namess[i],0,1000000000);
		    		  preparedStmt.setInt    ((i+1), num_ingresado);
		    	  }
		    	  if(typess[i].equals("VARCHAR")){
		    		  String num_ingresado=inputs.leerString(namess[i]);
		    		  preparedStmt.setString((i+1), num_ingresado);
		    	  }
		    	  if(typess[i].equals("FLOAT")){
		    		  float num_ingresado=(float) inputs.leerfloat(namess[i],0,10000000);
		    		  preparedStmt.setFloat((i+1), num_ingresado);
		    	  }
		    	  if(typess[i].equals("TEXT")){
		    		  String num_ingresado=inputs.leerString(namess[i]);
		    		  preparedStmt.setString((i+1), num_ingresado);
		    	  }
		    	 
		    	  
		    	  
		      }
		      
		      preparedStmt.execute();
		      
		      
	}

}
