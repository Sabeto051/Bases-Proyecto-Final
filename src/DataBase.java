
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.accessibility.AccessibleEditableText;

import com.mysql.cj.conf.ConnectionUrl.Type;
import com.mysql.cj.protocol.Resultset;



public class DataBase {
	
	
	
	
	/* static Connection dbConnection = null; 
	    static String username; //= "root"; // replace with your MySQL client username
	    static String password; //= "holahola123"; // replace with your MySQL client password
	    
	    */
	    static Connection dbConnection = null; 
	    static String username = "santiago"; // replace with your MySQL client username
	    static String password = "asdf"; // replace with your MySQL client password
	    static String tabla="";
	    static String dataBase="";
	    static Statement stmt = null;
        static ResultSet resultset = null;
	    static int espacioEntreCol=35;
	    
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
					System.out.println("Acceso Denegado Por Usuario o Contrase�a Errados");
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
	
	
	///////////////////////////////////////////
	///////////////////////////////////////////
	///////////////////////////////////////////	
	///////////////////////////////////////////
	///////////////////////////////////////////
	//Utilizar estos metodos desde aca crud
	public static void accederATabla(String nombre_tabla) {

		tabla=nombre_tabla;
	}
	public static void mostrarTabla(String ids,String nombre_tabla) throws SQLException {
		accederATabla(nombre_tabla);
		DatabaseMetaData metadata = DataBase.dbConnection.getMetaData();
		ResultSet resultSet = metadata.getColumns(DataBase.dataBase, null, DataBase.tabla, "%");
		int postypes=0;
		int posnames=0;
		String typess[]=new String[0];
		String namess[]=new String[0];
		  while (resultSet.next()) {
		    String tableName = resultSet.getString("COLUMN_NAME");
		    String type = resultSet.getString("TYPE_NAME");
		    
		    		typess=Arrays.copyOf(typess, typess.length+1);
		    		typess[postypes]=type;
				    postypes++;
				    namess=Arrays.copyOf(namess, namess.length+1);
		    		namess[posnames]=tableName;
				    posnames++;
		  }
		  Statement statement = dbConnection.createStatement();
		  ResultSet resultSet2 = statement.executeQuery("select * from "+tabla);
	      int count = 0;
	      while (resultSet2.next()) {
	        count++;
	      }  
	      for(int i=0;i<count+1;i++) {
		      for(int j=0;j<typess.length;j++) {
		    	  if(i==0) {
		    		  int espacios=espacioEntreCol-namess[j].length();
		     		  String spaces="";
		     		  for(int k=0;k<espacios;k++) {
		     			  spaces=spaces+" ";
		     		  }
		    		  System.out.print(namess[j]+spaces);
		    	  }
		    	  else {
		    		  String valorBuscado=buscarValorDeCampoSegunID(nombre_tabla,ids,(i),namess[j]).toString();
		    		  int espacios=espacioEntreCol-valorBuscado.length();
		     		  String spaces="";
		     		  for(int k=0;k<espacios;k++) {
		     			  spaces=spaces+" ";
		     		  }
		    		  System.out.print(valorBuscado+spaces); 
		      }
		      }
		      System.out.println();
		      }
		
		
	}
	public static void mostrarTablaConAtributoDeOtraTabla(String ids,
	String nombre_tabla,String idsIntermedia1,String idsIntermedia2,String tablaIntermedia,String ids2,String tabla2,String atributo) throws SQLException {
		accederATabla(nombre_tabla);
		DatabaseMetaData metadata = DataBase.dbConnection.getMetaData();
		ResultSet resultSet = metadata.getColumns(DataBase.dataBase, null, DataBase.tabla, "%");
		int postypes=0;
		int posnames=0;
		String typess[]=new String[0];
		String namess[]=new String[0];
		  while (resultSet.next()) {
		    String tableName = resultSet.getString("COLUMN_NAME");
		    String type = resultSet.getString("TYPE_NAME");
		    
		    		typess=Arrays.copyOf(typess, typess.length+1);
		    		typess[postypes]=type;
				    postypes++;
				    namess=Arrays.copyOf(namess, namess.length+1);
		    		namess[posnames]=tableName;
				    posnames++;
		  }
		  Statement statement = dbConnection.createStatement();
		  ResultSet resultSet2 = statement.executeQuery("select * from "+tabla);
	      int count = 0;
	      while (resultSet2.next()) {
	        count++;
	      }  
	      for(int i=0;i<count+1;i++) {
		     int id=0;
	    	  for(int j=0;j<typess.length+1;j++) {
		    	 if(j<typess.length) {
		    	  if(i==0) {
		    		  int espacios=espacioEntreCol-namess[j].length();
		     		  String spaces="";
		     		  for(int k=0;k<espacios;k++) {
		     			  spaces=spaces+" ";
		     		  }
		    		  System.out.print(namess[j]+spaces);
		    	  }
		    	  else {
		    		  String valorBuscado=buscarValorDeCampoSegunID(nombre_tabla,ids,(i),namess[j]).toString();
		    		  int espacios=espacioEntreCol-valorBuscado.length();
		     		  String spaces="";
		     		  for(int k=0;k<espacios;k++) {
		     			  spaces=spaces+" ";
		     		  }
		     		  if(j==0) {
		     			  id=Integer.parseInt(valorBuscado);
		     		  }
		     		  
		    		  System.out.print(valorBuscado+spaces); 
		      }
		    	 }
		    	 else {
		    	if(i>0) {
		    	  Statement statement2 = dbConnection.createStatement();
		    	  String que="select "+atributo+" from "+tabla2+" where "+ids2+" in ( select "+
				  idsIntermedia2+" from " +tablaIntermedia + " where " + idsIntermedia1 + " = "+id+")";
		    	  ResultSet resultSet3 = statement2.executeQuery(que);
		   		  String type=resultSet3.getMetaData().getColumnTypeName(1);
		   		  Object campoMostrar=null;
		   		  String spaces="";
		   		  Object fieldNames[]=getRowNames(ids2,tabla2,atributo);
		   		  Object fields[]=new Object[getRowCount(ids2, tabla2)];
		   		  int FieldCont=0;
		   		  while(resultSet3.next()) {
		   			campoMostrar="";
			      		if(type.equalsIgnoreCase("INT")){
			      			campoMostrar = resultSet3.getInt(1);
				    	  }
				    	  if(type.equalsIgnoreCase("VARCHAR")){
				    		  campoMostrar = resultSet3.getString(1);
				    	  }
				    	  if(type.equalsIgnoreCase("FLOAT")){
				    			campoMostrar = resultSet3.getFloat(1);
				    	  }
				    	  if(type.equalsIgnoreCase("TEXT") || type.equalsIgnoreCase("DATETIME") ){
				    		  campoMostrar = resultSet3.getString(1);
				    	  }
				    	  if(!campoMostrar.equals(fieldNames[FieldCont])) {
				    		  boolean perf=false;
					    		while(perf==false) {
					    			if(campoMostrar.equals(fieldNames[FieldCont])) {
							    		 perf=true;
							    	  }
					    			else {
					    				 fields[FieldCont]="--------------";
								    	  FieldCont++;
					    			}
				    	  }
				    	 
				    	  }
				    	  fields[FieldCont]=campoMostrar;
				    	  FieldCont++;
		    	 }
		   		 for(int q=FieldCont;q<fields.length;q++){
		   			fields[FieldCont]="--------------";
			    	  FieldCont++;
		   		 }
		   		System.out.print("|  ");
		   		for(int p=0;p<fields.length;p++) {
		   			spaces="";
	     		  for(int k=0;k<espacioEntreCol;k++) {
	     			  if(k<fields[p].toString().length()) {
	     			  }
	     			  else {
	     				 if(k<espacioEntreCol)
	     					 if(k==espacioEntreCol/2+8) {
	     				     spaces=spaces+"|";	 
	     					 }else {
	     					 spaces=spaces+" "; }
	     			  }
	     		  }
	    		  System.out.print(fields[p].toString()+spaces); 
		   		}
		    	}
		    	
		    	else {
		    		int rowCount=getRowCount(ids2, tabla2);
		    		if(rowCount%2==0) {
		    			int half=rowCount/2;
		    			for(int k=0;k<espacioEntreCol*half-7;k++) {
			     		System.out.print(" ");
			     		  }
		    			System.out.print(tabla2);
		    		}
		    		else {
		    			int half=rowCount/2;
		    			for(int k=0;k<espacioEntreCol*half+4;k++) {
				     		System.out.print(" ");
				     		  }
			    			System.out.print(tabla2);
		    		}
		    	}
		      }
	    	  }
		      System.out.println();
		      }
		
		
	}
	
	//mostrar tabla
	
	public static int AgregarRegistro(String nombre_tabla, String campoVerificar) throws SQLException {
		accederATabla(nombre_tabla);
		 if(campoVerificar.equals("")) {
			  campoVerificar="id";
		  }
		DatabaseMetaData metadata = DataBase.dbConnection.getMetaData();
		 
		 
		  // Specify the type of object; in this case we want tables
		
		String BD[]=new String[0];
		ResultSet resultSet = metadata.getColumns(DataBase.dataBase, null, DataBase.tabla, "%");
		
	      // get the column names from the ResultSet
		
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
		 
		 
		  }
		      
		     
		     Object[] datos=new Object[typess.length];
		     Entradas inputs=new Entradas();
		      for(int i=0;i<typess.length;i++) {
		    	  if(typess[i].equals("INT")){
		    		 datos[i]=inputs.leerInt(namess[i],0,1000000000);
		    	  }
		    	  if(typess[i].equals("VARCHAR")){
		    		  datos[i]=inputs.leerString(namess[i]);
		    	  }
		    	  if(typess[i].equals("FLOAT")){
		    		  datos[i]=(float) inputs.leerfloat(namess[i],0,10000000);
		    	  }
		    	  if(typess[i].equals("TEXT") || typess[i].equals("DATETIME")){
		    		  datos[i]=inputs.leerString(namess[i]);
		    	  }
		    	 
		    	  
		    	  
		      }
		
		
		
		
			querys="";
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
		    boolean entra=false;
		     PreparedStatement preparedStmt = DataBase.dbConnection.prepareStatement(query);
		     Object verificar = null;
		      for(int i=0;i<typess.length;i++) {
		    	  if(typess[i].equals("INT")){
		    		  preparedStmt.setInt((i+1),(int)(datos[i]));
		    	  }
		    	  if(typess[i].equals("VARCHAR")){
		    		  preparedStmt.setString((i+1),(String)(datos[i]));
		    	  }
		    	  if(typess[i].equals("FLOAT")){
		    		  preparedStmt.setFloat((i+1),(float)(datos[i]));
		    	  }
		    	  if(typess[i].equals("TEXT") || typess[i].equals("DATETIME") ){
		    		  preparedStmt.setString((i+1),(String)(datos[i]));
		    	  }
		    	 if(!campoVerificar.equals("id") && namess[i].equalsIgnoreCase(campoVerificar)) {
		    		 verificar=datos[i];
		    	 }
		    	 else {
		    		 if(campoVerificar.equals("id")) {
				    	 entra=true;
		    		 }
		    	 }
		      }
		      if(entra) {
		    	  preparedStmt.execute();
		    	  Statement statement2 = dbConnection.createStatement();
				  ResultSet resultSet3 = statement2.executeQuery("select MAX(id) from "+tabla);
				  int id=-1;
				  while (resultSet3.next()) {
			    	  id=resultSet3.getInt(1);
			      }  
		    	  return id;
		      }
			  String queryss = "SELECT * FROM "+tabla+" WHERE "+campoVerificar+"='"+verificar.toString()+"';";
			 
			  Statement st = dbConnection.createStatement();
		      
		      // execute the query, and get a java resultset
		      ResultSet rs1 = st.executeQuery(queryss);
			
			int cont =-1;
		      while (rs1.next()) {
		    	  cont++;
		    	  
		      }  
		    if(cont==-1) {
		    	  preparedStmt.execute();
		    	  Statement statement2 = dbConnection.createStatement();
				  ResultSet resultSet3 = statement2.executeQuery("select MAX(id) from "+tabla);
				  int id=-1;
				  while (resultSet3.next()) {
			    	  id=resultSet3.getInt(1);
			      }  
		    	  return id;
		    }
		    else {
		    	return -1;	    	
	    	  }
	    	  
		      
	}
	public static int AgregarRegistroCONDatos(String ids,int campoid,String nombre_tabla, String campoVerificar,Object [] datos) throws SQLException {
		accederATabla(nombre_tabla);
		 if(campoVerificar.equals("")) {
			  campoVerificar=ids;
		  }
		DatabaseMetaData metadata = DataBase.dbConnection.getMetaData();
		 
		 
		  // Specify the type of object; in this case we want tables
		
		String BD[]=new String[0];
		ResultSet resultSet = metadata.getColumns(DataBase.dataBase, null, DataBase.tabla, "%");
		
	      // get the column names from the ResultSet
		
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
		 
		 
		  }
		 
		
			querys="";
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
		   
		    String query = "insert into "+ DataBase.tabla + " ("+ids+", "+querys+")"
				        + " values (?,"+preguntas+")";
		    boolean entra=false;
		     PreparedStatement preparedStmt = DataBase.dbConnection.prepareStatement(query);
		     Object verificar = null;
		     preparedStmt.setInt(1,campoid);
		      for(int i=1;i<typess.length+1;i++) {
		    	  if(typess[i-1].equals("INT")){
		    		  preparedStmt.setInt((i+1),Integer.parseInt(datos[i-1].toString()));
		    	  }
		    	  if(typess[i-1].equals("VARCHAR")){
		    		  preparedStmt.setString((i+1),(String)(datos[i-1]));
		    	  }
		    	  if(typess[i-1].equals("FLOAT")){
		    		  preparedStmt.setFloat((i+1),(float)(datos[i-1]));
		    	  }
		    	  if(typess[i-1].equals("TEXT") || typess[i-1].equals("DATETIME")){
		    		  preparedStmt.setString((i+1),(String)(datos[i-1]));
		    	  }
		    	 if(!campoVerificar.equals(ids) && namess[i-1].equalsIgnoreCase(campoVerificar)) {
		    		 verificar=datos[i-1];
		    	 }
		    	 else {
		    		 if(campoVerificar.equals(ids)) {
				    	 entra=true;
		    		 }
		    	 }
		      }
		      if(entra) {
		    	  preparedStmt.execute();
		    	  Statement statement2 = dbConnection.createStatement();
				  ResultSet resultSet3 = statement2.executeQuery("select MAX("+ids+") from "+tabla);
				  int id=-1;
				  while (resultSet3.next()) {
			    	  id=resultSet3.getInt(1);
			      }  
		    	  return id;
		      }
			  String queryss = "SELECT * FROM "+tabla+" WHERE "+campoVerificar+"='"+verificar.toString()+"';";
			 
			  Statement st = dbConnection.createStatement();
		      
		      // execute the query, and get a java resultset
		      ResultSet rs1 = st.executeQuery(queryss);
			
			int cont =-1;
		      while (rs1.next()) {
		    	  cont++;
		    	  
		      }  
		    if(cont==-1) {
		    	System.out.println(preparedStmt.toString());
		    	  preparedStmt.execute();
		    	  Statement statement3 = dbConnection.createStatement();
				  ResultSet resultSet4 = statement3.executeQuery("Select MAX("+ids+") from "+tabla);
				  int id2=-1;
				  while (resultSet4.next()) {
			    	  id2=resultSet4.getInt(1);
			      }  
		    	  return id2;
		    }
		    else {
		    return -1;	    	
	    	 }
	    	  
		      
	}
	public static int AgregarRegistroCONDatos(String ids,String nombre_tabla, String campoVerificar,Object [] datos) throws SQLException {
		accederATabla(nombre_tabla);
		 if(campoVerificar.equals("")) {
			  campoVerificar=ids;
		  }
		DatabaseMetaData metadata = DataBase.dbConnection.getMetaData();
		 
		 
		  // Specify the type of object; in this case we want tables
		
		String BD[]=new String[0];
		ResultSet resultSet = metadata.getColumns(DataBase.dataBase, null, DataBase.tabla, "%");
		
	      // get the column names from the ResultSet
		
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
		 
		 
		  }
		 
		
			querys="";
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
		    String query = "insert into "+ DataBase.tabla + " ("+querys+")"
				        + " values ("+preguntas+")";
		    boolean entra=false;
		     PreparedStatement preparedStmt = DataBase.dbConnection.prepareStatement(query);
		     Object verificar = null;
		      for(int i=1;i<typess.length+1;i++) {
		    	  if(typess[i-1].equals("INT")){
		    		  preparedStmt.setInt((i),Integer.parseInt(datos[i-1].toString()));
		    	  }
		    	  if(typess[i-1].equals("VARCHAR")){
		    		  preparedStmt.setString((i),(String)(datos[i-1]));
		    	  }
		    	  if(typess[i-1].equals("FLOAT")){
		    		  preparedStmt.setFloat((i),(float)(datos[i-1]));
		    	  }
		    	  if(typess[i-1].equals("TEXT") || typess[i-1].equals("DATETIME")){
		    		  preparedStmt.setString((i),(String)(datos[i-1]));
		    	  }
		    	 if(!campoVerificar.equals(ids) && namess[i-1].equalsIgnoreCase(campoVerificar)) {
		    		 verificar=datos[i-1];
		    	 }
		    	 else {
		    		 if(campoVerificar.equals(ids)) {
				    	 entra=true;
		    		 }
		    	 }
		      }
		      if(entra) {
		    	  preparedStmt.execute();
		    	  Statement statement2 = dbConnection.createStatement();
				  ResultSet resultSet3 = statement2.executeQuery("select MAX("+ids+") from "+tabla);
				  int id=-1;
				  while (resultSet3.next()) {
			    	  id=resultSet3.getInt(1);
			      }  
		    	  return id;
		      }
			  String queryss = "SELECT * FROM "+tabla+" WHERE "+campoVerificar+"='"+verificar.toString()+"';";
			 
			  Statement st = dbConnection.createStatement();
		      
		      // execute the query, and get a java resultset
		      ResultSet rs1 = st.executeQuery(queryss);
			
			int cont =-1;
		      while (rs1.next()) {
		    	  cont++;
		    	  
		      }  
		    if(cont==-1) {
		    	  preparedStmt.execute();
		    	  Statement statement3 = dbConnection.createStatement();
				  ResultSet resultSet4 = statement3.executeQuery("Select MAX("+ids+") from "+tabla);
				  int id2=-1;
				  while (resultSet4.next()) {
			    	  id2=resultSet4.getInt(1);
			      }  
		    	  return id2;
		    }
		    else {
		    return -1;	    	
	    	 }
	    	  
		      
	}
	
	public static int AgregarRegistroLog(String nombre_tabla, String campoVerificar, String log, String campoVerificar2) throws SQLException {
		accederATabla(nombre_tabla);
		 if(campoVerificar.equals("")) {
			  campoVerificar="id";
		  }
		 if(campoVerificar2.equals("")) {
			  campoVerificar2="id";
		  }
		DatabaseMetaData metadata = DataBase.dbConnection.getMetaData();
		 
		 
		  // Specify the type of object; in this case we want tables
		
		String BD[]=new String[0];
		ResultSet resultSet = metadata.getColumns(DataBase.dataBase, null, DataBase.tabla, "%");
		
	      // get the column names from the ResultSet
		
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
		 
		 
		  }
		      
		     
		     Object[] datos=new Object[typess.length];
		     Entradas inputs=new Entradas();
		      for(int i=0;i<typess.length;i++) {
		    	  if(typess[i].equals("INT")){
		    		 datos[i]=inputs.leerInt(namess[i],0,1000000000);
		    	  }
		    	  if(typess[i].equals("VARCHAR")){
		    		  datos[i]=inputs.leerString(namess[i]);
		    	  }
		    	  if(typess[i].equals("FLOAT")){
		    		  datos[i]=(float) inputs.leerfloat(namess[i],0,10000000);
		    	  }
		    	  if(typess[i].equals("TEXT")){
		    		  datos[i]=inputs.leerString(namess[i]);
		    	  }
		    	 
		    	  
		    	  
		      }
		
		
		
		
			querys="";
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
		    boolean entra=false;
		     PreparedStatement preparedStmt = DataBase.dbConnection.prepareStatement(query);
		     Object verificar = null;
		     Object verificar2 = null;
		      for(int i=0;i<typess.length;i++) {
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
		    	 if(!campoVerificar.equals("id") && namess[i].equalsIgnoreCase(campoVerificar)) {
		    		 verificar=datos[i];
		    	 }
		    	 else {
		    		 if(campoVerificar.equals("id")) {
		    			 verificar="id";
		    			 entra=true;
		    		 }
		    	 }
		    	 if(!campoVerificar2.equals("id") && namess[i].equalsIgnoreCase(campoVerificar2)) {
		    		 verificar2=datos[i];
		    	 }
		    	 else {
		    		 if(campoVerificar2.equals("id")) {
				    	 verificar2="id";
		    			 entra=true;
		    		 }
		    	 }
		      }
		      
			  String queryss = "SELECT * FROM "+tabla+" WHERE "+campoVerificar+"='"+verificar.toString()
			  +"' "+ log +" "+campoVerificar2+"='"+verificar2.toString()+"';";
			  
		
			 
			  Statement st = dbConnection.createStatement();
		      
		      // execute the query, and get a java resultset
		      ResultSet rs1 = st.executeQuery(queryss);
			
			int cont =-1;
		      while (rs1.next()) {
		    	  cont++;
		    	  
		      }  
		    if(cont==-1) {
		    	  preparedStmt.execute();
		    	  Statement statement2 = dbConnection.createStatement();
				  ResultSet resultSet3 = statement2.executeQuery("select MAX(id) from "+tabla);
				  int id=-1;
				  while (resultSet3.next()) {
			    	  id=resultSet3.getInt(1);
			      }  
		    	  return id;
		    }
		    else {
		    	return -1;	    	
	    	  }
	    	  
		      
	}
	public static void EliminarRegistro(String tabla1,String ids,String campo,  String dato) throws SQLException {
		accederATabla(tabla1);
		int id=buscarExistente(tabla1,ids,campo, dato);
		if(id>0) {
		String query = "delete from "+tabla+" where id = "+id;
	      PreparedStatement preparedStmt = dbConnection.prepareStatement(query);
	      preparedStmt.execute();
	      }
		else {
			System.out.println("No se encontro registro a eliminar");
		}
	}
	public static void ModificarRegistroUnico(String tabla1,String ids,String campoAnt, String datoAnt,String campo, Object dato) throws SQLException {
		// our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
		
		accederATabla(tabla1);
		int id=buscarExistente(tabla1,ids,campoAnt, datoAnt);
		
		if(id>0) {
	      String query = "update "+tabla+" set "+campo+" = ? where "+ids+" = "+id;
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
		}else {
			
		}
	      }
	public static void ModificarRegistro(String nombre_tabla,String ids, int id,String campo, Object dato) throws SQLException {
		// our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
		String datoAnt="";
		if(id>0) {
		datoAnt=buscarValorDeCampoSegunID(nombre_tabla,ids,id, campo).toString();
	      String query = "update "+tabla+" set "+campo+" = ? where "+ids+" = "+id;
	      
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
		}else {
			System.out.println("No se encontro registro a eliminar");
		}
		int idArray[] =buscarExistenteArray("id",campo, datoAnt);
		mostrarIDSconCampo(nombre_tabla,ids,idArray, "contenido");
	      }
	public static void ModificarRegistroCascada(String ids,String campo, String datoAnt, Object dato) throws SQLException {
		// our SQL SELECT query. 
	      // if you only need a few columns, specify them by name instead of using "*"
		int id[]=buscarExistenteArray(ids,campo, datoAnt);
		
		for(int i=0;i<id.length;i++){if(id[i]>0) {
	      String query = "update "+tabla+" set "+campo+" = ? where "+ids+" = "+id;
	      
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
		}else {
			System.out.println("No se encontro registro a eliminar");
		}
	}
	      }

	public static int buscarExistente(String tabla1,String ids,String campo,  String dato) throws SQLException {
		accederATabla(tabla1); 
		String querys = "SELECT * FROM "+tabla1+" WHERE "+campo+"='"+dato+"';";

	      // create the java statement
	      Statement st = dbConnection.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(querys);
	      
	      // iterate through the java resultset
	      	  int id=-1;
	      	  while(rs.next()) {
	    	  id = rs.getInt(ids);
	    	  }
	      	  return id;
	}
	public static Object buscarValorDeCampoSegunID(String tabla1,String ids, int id,String campo) throws SQLException {
		DataBase.accederATabla(tabla1);
		DatabaseMetaData metadata = dbConnection.getMetaData();
	      ResultSet resultSet = metadata.getColumns(DataBase.dataBase, null, DataBase.tabla, campo);
	      String tipo_campo ="";
	      while (resultSet.next()) {
	    	 tipo_campo = resultSet.getString("TYPE_NAME");
	      }
		String querys = "SELECT * FROM "+tabla1+" where "+ids+" = "+id;
	      // create the java statement
	      Statement st = dbConnection.createStatement();
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(querys);
	      // iterate through the java resultset
	      	  Object campoMostrar="";
	      	  while(rs.next()) {
	      		if(tipo_campo.equalsIgnoreCase("INT")){
	      			campoMostrar =rs.getInt(campo);
	      			return campoMostrar;
		    	  }
		    	  if(tipo_campo.equalsIgnoreCase("VARCHAR")){
		    		  campoMostrar = rs.getString(campo);
		    		  return campoMostrar;
		    	  }
		    	  if(tipo_campo.equalsIgnoreCase("FLOAT")){
		    			campoMostrar = rs.getFloat(campo);
		    			return campoMostrar;
		    	  }
		    	  if(tipo_campo.equalsIgnoreCase("TEXT") || tipo_campo.equalsIgnoreCase("DATETIME") ){
		    		  campoMostrar = rs.getString(campo);
		    		  return campoMostrar;
		    	  }
	      	  }
	      	  return "";
	}
	public static int[] buscarExistenteArray(String ids,String campoParaBuscar,  String datoABuscar) throws SQLException {
		 String querys = "SELECT * FROM "+tabla+" WHERE "+campoParaBuscar+"='"+datoABuscar+"';";
	      // create the java statement
	      Statement st = dbConnection.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(querys);
	      
	      // iterate through the java resultset
	      	  int id[]= new int[0];
	      	  int cont=0;
	      	  while(rs.next()) {
	      	  id=Arrays.copyOf(id, id.length+1);
	    	  id[cont] = rs.getInt(ids);
	    	  cont++;
	    	  }
	      	  return id;
	}
	
	public static int maxID(String ids,String tabla1) throws SQLException {
		accederATabla(tabla1); 
		String querys = "SELECT MAX("+ids+") FROM "+tabla;
	      // create the java statement
	      Statement st = dbConnection.createStatement();
	      
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(querys);
	      
	      // iterate through the java resultset
	     int id=0;
	      	  while(rs.next()) {
	      	  id=rs.getInt(1);
	    	  }
	      	  return id;
	}
	public static void mostrarIDSconCampo(String nombre_tabla,String ids,int id[],String campo) throws SQLException {
		System.out.println(ids+"    "+campo);
		for(int i=0;i<id.length;i++) {
			String campoMostrar=buscarValorDeCampoSegunID(nombre_tabla,ids,id[i], campo).toString();
	      	 System.out.println(id[i]+"    "+campoMostrar);
		}
	}
	public static String[] getRowNames(String ids,String tabla, String atributo) throws SQLException {
		 Statement st = dbConnection.createStatement();
		 String querys = "SELECT "+ atributo +" FROM "+tabla;
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(querys);
	      String type=rs.getMetaData().getColumnTypeName(1);
	      // iterate through the java resultset
	    String names[]=new String[1];
	    Object campoMostrar;
	    int cont=0;  	  
	    while(rs.next()) {
	    	campoMostrar="";
	    	if(type.equalsIgnoreCase("INT")){
      			campoMostrar = rs.getInt(1);
	    	  }
	    	  if(type.equalsIgnoreCase("VARCHAR")){
	    		  campoMostrar = rs.getString(1);
	    	  }
	    	  if(type.equalsIgnoreCase("FLOAT")){
	    			campoMostrar = rs.getFloat(1);
	    	  }
	    	  if(type.equalsIgnoreCase("TEXT") || type.equalsIgnoreCase("DATETIME") ){
	    		  campoMostrar = rs.getString(1);
	    	  }
	      	 	names[cont]=campoMostrar.toString();
	      	 	names=Arrays.copyOf(names, names.length+1);
	      	 	cont++;
	    	  }
	      	  return names;
		
	}
	public static int getRowCount(String ids,String tabla) throws SQLException {
		 Statement st = dbConnection.createStatement();
		 String querys = "SELECT "+ "*" +" FROM "+tabla;
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(querys);
	    int cont=0;  	  
	    while(rs.next()) {
	      	 	cont++;
	    	  }
	      	  return cont;
		
	}
	public static int [] mostrarTablaSegunCriterio(String tablaASegmentar,String campoParaSegmentar, String campoASeleccionar,
		String tablaIntermedia, String campoParaFiltrar, int dato, String numerado) throws SQLException {
		accederATabla(tablaIntermedia);
		 
		String querys = "SELECT * FROM "+tablaASegmentar+" WHERE "+campoParaSegmentar+" in ("+
					    "SELECT "+campoASeleccionar+" FROM " + tabla + " WHERE "+campoParaFiltrar+"="+dato+")";
	      // create the java statement
			int [] ids=new int[0];
	      Statement st = dbConnection.createStatement();
	      // execute the query, and get a java resultset
	      ResultSet rs = st.executeQuery(querys);
	     if(numerado.equalsIgnoreCase("numerado")) {
	    	    mostrarResultSetConCont(rs);
	     }else {
	    	    mostrarResultSet(rs);
	     }
	      Statement st2 = dbConnection.createStatement();
	      // execute the query, and get a java resultset
	      ResultSet rs2 = st2.executeQuery(querys);
	      int cont=0;
	      while(rs2.next()) {
	    	  ids=Arrays.copyOf(ids, ids.length+1);
	    	  ids[cont]=rs2.getInt(1);
	    	  cont++;
	      }
	      if(ids.length==0) {
	    	  ids=Arrays.copyOf(ids, ids.length+1);
	    	  ids[cont]=0;
	    	  cont++;
	      }
	      
	      return ids;
	      	  
	}
	
	public static int [] mostrarTablaSegunCriterio(String mostrar,String tablaASegmentar,String[] at1,String idss,
			String campoParaSegmentar, String campoASeleccionar,String numerado, String noNumericID) throws SQLException {
			accederATabla(tablaASegmentar);
			String ats="";
			for(int i=0;i<at1.length;i++) {
				if(i<at1.length-1) {
				ats=ats+at1[i]+",";}
				else {
					ats=ats+at1[i];
				}
			}
			String querys = "SELECT "+ats+" FROM "+tablaASegmentar+" WHERE "+campoParaSegmentar+" = " +campoASeleccionar;
		      // create the java sta tement
				int [] ids=new int[0];
		      Statement st = dbConnection.createStatement();
		      // execute the query, and get a java resultset
		      ResultSet rs = st.executeQuery(querys);
		     if(numerado.equalsIgnoreCase("numerado")) {
		    	    if(noNumericID.equals("si")) {
		    	    	mostrarResultSetConCont(rs);}
		    	    else {
		    	    	mostrarResultSetConContSinID(rs);
		    	    }
		     }else {
		    	 if(noNumericID.equals("si")) {
		    	    	mostrarResultSet(rs);}
		    	    else {
		    	    	mostrarResultSetSinID(rs);
		    	    }
		     }
		      Statement st2 = dbConnection.createStatement();
		      // execute the query, and get a java resultset
		      ResultSet rs2 = st2.executeQuery(querys);
		      int cont=0;
		      while(rs2.next()) {
		    	  ids=Arrays.copyOf(ids, ids.length+1);
		    	  ids[cont]=buscarExistente(tablaASegmentar, idss, campoParaSegmentar, rs2.getString(mostrar));
		    	  cont++;
		      }
		      if(ids.length==0) {
		    	  ids=Arrays.copyOf(ids, ids.length+1);
		    	  ids[cont]=0;
		    	  cont++;
		      }
		      
		      
		      return ids;
		      	  
		}
	
	public static ResultSet select2criteriosLog(String tablas, String campo1, int dato1, String log, String campo2, int dato2) throws SQLException {
		accederATabla(tablas);
		String querys = "SELECT * FROM "+tabla+" WHERE "+ campo1+ "="+dato1 + " "+ log+ " "+ campo2+ "="+dato2;
	      // create the java statement
	      Statement st = dbConnection.createStatement();
	      // execute the query, and get a java resultset
	      ResultSet resultset = st.executeQuery(querys);
		return resultset;
		
	}
	
	public static void mostrarResultSet(ResultSet rs) throws SQLException {
	     int tamano= rs.getMetaData().getColumnCount();	  
	    
	     for(int i=2;i<tamano+1;i++) {
	    	 int espacios=espacioEntreCol-rs.getMetaData().getColumnName(i).length();
     		  String spaces="";
     		  for(int k=0;k<espacios;k++) {
     			  spaces=spaces+" ";
     		  }
      			System.out.print(rs.getMetaData().getColumnName(i)+spaces);
      		 }
	     System.out.println();
	     while(rs.next()) {
	      	  for(int i=2;i<tamano+1;i++) {
	      		  String type=rs.getMetaData().getColumnTypeName(i);
	      		  int espacios=0;
	      		if(type.equalsIgnoreCase("INT")){
	      			espacios=espacioEntreCol-Integer.toString(rs.getInt(i)).length();
	      			String spaces="";
		      		  for(int k=0;k<espacios;k++) {
		      			  spaces=spaces+" ";
		      		  }
	      			System.out.print(rs.getInt(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("VARCHAR")){
		    		  espacios=espacioEntreCol-rs.getString(i).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getString(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("FLOAT")){
		    		  espacios=espacioEntreCol-Float.toString(rs.getFloat(i)).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getFloat(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("TEXT") || type.equalsIgnoreCase("DATETIME")){
		    		  espacios=espacioEntreCol-rs.getString(i).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getString(i)+spaces);
		    		  }
	      	  }
	    	  System.out.println();
	    	  }
	}
	public static void mostrarResultSetSinID(ResultSet rs) throws SQLException {
	     int tamano= rs.getMetaData().getColumnCount();	  
	    
	     for(int i=2;i<tamano+1;i++) {
	    	 int espacios=espacioEntreCol-rs.getMetaData().getColumnName(i).length();
    		  String spaces="";
    		  for(int k=0;k<espacios;k++) {
    			  spaces=spaces+" ";
    		  }
     			System.out.print(rs.getMetaData().getColumnName(i)+spaces);
     		 }
	     System.out.println();
	     while(rs.next()) {
	      	  for(int i=1;i<tamano+1;i++) {
	      		  String type=rs.getMetaData().getColumnTypeName(i);
	      		  int espacios=0;
	      		if(type.equalsIgnoreCase("INT")){
	      			espacios=espacioEntreCol-Integer.toString(rs.getInt(i)).length();
	      			String spaces="";
		      		  for(int k=0;k<espacios;k++) {
		      			  spaces=spaces+" ";
		      		  }
	      			System.out.print(rs.getInt(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("VARCHAR")){
		    		  espacios=espacioEntreCol-rs.getString(i).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getString(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("FLOAT")){
		    		  espacios=espacioEntreCol-Float.toString(rs.getFloat(i)).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getFloat(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("TEXT") || type.equalsIgnoreCase("DATETIME")){
		    		  espacios=espacioEntreCol-rs.getString(i).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getString(i)+spaces);
		    		  }
	      	  }
	    	  System.out.println();
	    	  }
	}
	public static void mostrarResultSetConCont(ResultSet rs) throws SQLException {
	     int tamano= rs.getMetaData().getColumnCount();	  
	     System.out.print("   ");
	     for(int i=2;i<tamano+1;i++) {
	    	 int espacios=espacioEntreCol-rs.getMetaData().getColumnName(i).length();
    		  String spaces="";
    		  for(int k=0;k<espacios;k++) {
    			  spaces=spaces+" ";
    		  }
     			System.out.print(rs.getMetaData().getColumnName(i)+spaces);
     		 }
	     System.out.println();
	     int cont=1;
	     while(rs.next()) {
	    	 System.out.print(cont+". ");
	    	 cont++;
	      	  for(int i=2;i<tamano+1;i++) {
	      		  String type=rs.getMetaData().getColumnTypeName(i);
	      		  int espacios=0;
	      		if(type.equalsIgnoreCase("INT")){
	      			espacios=espacioEntreCol-Integer.toString(rs.getInt(i)).length();
	      			String spaces="";
		      		  for(int k=0;k<espacios;k++) {
		      			  spaces=spaces+" ";
		      		  }
	      			System.out.print(rs.getInt(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("VARCHAR")){
		    		  espacios=espacioEntreCol-rs.getString(i).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getString(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("FLOAT")){
		    		  espacios=espacioEntreCol-Float.toString(rs.getFloat(i)).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getFloat(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("TEXT") || type.equalsIgnoreCase("DATETIME")){
		    		  espacios=espacioEntreCol-rs.getString(i).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getString(i)+spaces);
		    		  }
	      	  }
	    	  System.out.println();
	    	  }
	}
	public static void mostrarResultSetConContSinID(ResultSet rs) throws SQLException {
	     int tamano= rs.getMetaData().getColumnCount();	  
	     for(int i=1;i<tamano+1;i++) {
	    	 int espacios=espacioEntreCol-rs.getMetaData().getColumnName(i).length();
   		  String spaces="";
   		  for(int k=0;k<espacios;k++) {
   			  spaces=spaces+" ";
   		  }
    			System.out.print("   "+rs.getMetaData().getColumnName(i)+spaces);
    		 }
	     System.out.println();
	     int cont=1;
	     while(rs.next()) {
	    	 System.out.print(cont+". ");
	    	 cont++;
	      	  for(int i=1;i<tamano+1;i++) {
	      		  String type=rs.getMetaData().getColumnTypeName(i);
	      		  int espacios=0; 
	      		if(type.equalsIgnoreCase("INT")){
	      			espacios=espacioEntreCol-Integer.toString(rs.getInt(i)).length();
	      			String spaces="";
		      		  for(int k=0;k<espacios;k++) {
		      			  spaces=spaces+" ";
		      		  }
	      			System.out.print(rs.getInt(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("VARCHAR")){
		    		  espacios=espacioEntreCol-rs.getString(i).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getString(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("FLOAT")){
		    		  espacios=espacioEntreCol-Float.toString(rs.getFloat(i)).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getFloat(i)+spaces);
		    	  }
		    	  if(type.equalsIgnoreCase("TEXT") || type.equalsIgnoreCase("DATETIME")){
		    		  espacios=espacioEntreCol-rs.getString(i).length();
		      			String spaces="";
			      		  for(int k=0;k<espacios;k++) {
			      			  spaces=spaces+" ";
			      		  }
		    		  System.out.print(rs.getString(i)+spaces);
		    		  }
	      	  }
	    	  System.out.println();
	    	  }
	}
	public static String separarString(String str) {
		char chars[]=str.toCharArray();
		String a="";
		for(int i=chars.length-1;i>=0;i--) {
			if(Character.isDigit(chars[i]) || chars[i]==' ') {
				i=0;
			}
			else {
			a=chars[i]+a;
		}
			}
		return a;
		
	}
	public static int separarStringInt(String str) {
		char chars[]=str.toCharArray();
		String a="";
		for(int i=0;i<chars.length;i++) {
			if(Character.isDigit(chars[i])) {
				a=a+chars[i];
			}
			else {
		}
			}
		return Integer.parseInt(a);
		
	}
	public static String sumarAFecha(String nombre_tabla,String dateSS,String ids, int campoid, String tiempo) throws SQLException, ParseException {
	Calendar c = Calendar.getInstance();
	DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	Date date22=dateFormat1.parse(dateSS);
	c.setTime(date22); // Now use today date.
	accederATabla("planes");
	String plans=buscarValorDeCampoSegunID(nombre_tabla,ids,campoid,tiempo).toString();

	String plas2=DataBase.separarString(plans);
	int times=DataBase.separarStringInt(plans);

	if(plas2.equals("dia") || plas2.equals("dias")) {
	c.add(Calendar.DATE,times);
	}
	if(plas2.equals("mes") || plas2.equals("meses")) {
	c.add(Calendar.MONTH,times);
	}
	if(plas2.equals("a�o") || plas2.equals("a�os")) {
	c.add(Calendar.YEAR,times);
	}
	
	date22 = c.getTime();
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	String strDate = dateFormat.format(date22);
	return strDate;
}
	
	public static double diferenciaFecha(String d1,String d2) throws SQLException, ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Date d11=format.parse(d1);
		Date d12=format.parse(d2);
		long diffInMillies = d11.getTime() - d12.getTime();
		return diffInMillies;
	}	
}




