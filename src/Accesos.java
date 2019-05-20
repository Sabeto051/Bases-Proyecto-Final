import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.corba.se.impl.orb.ParserTable.TestAcceptor1;

public class Accesos {
private Entradas inputs=new Entradas();
private Menus menus=new Menus();
private static int opcion;
public int Ingreso(Boolean forzado) throws SQLException, ClassNotFoundException {
if(!forzado) {
menus.MenuPrincipal();
int min=1;
int max=2;
opcion=inputs.leerInt("la opcion deseada",min,max);
if(opcion==max) {
	opcion=-1;
}else {
Credenciales();
}
return opcion;
}
else {
	return -1;
}
}

public void Credenciales() throws SQLException, ClassNotFoundException {
	String cred[]=menus.MenuUsuarioContrase˝aBD();
	DataBase.username=cred[0];
	DataBase.password=cred[1];
	if(DataBase.Connect("mysql")) {
		cls();
		AccederABD();
	}
	}

public void AccederABD() throws SQLException, ClassNotFoundException {
	DataBase.tabla=null;
	menus.MenuBD();
	String BD[]=DataBase.executeMySQLQuery();
	System.out.println();
	System.out.println("Accion a realizar");
	int baseD=inputs.leerInt("el numero asociado a la base de datos que desee conectar" + "\n" 
			+ (BD.length+1)+" para Crear una Nueva Base de Datos"+ "\n" 
			+ (BD.length+2)+" para Eliminar Base de Datos"+ "\n" 
			+ (BD.length+3)+" para retroceder"+ "\n" 
			+ (BD.length+4) +" para Salir"
			, 1, BD.length+4);
	if(baseD>BD.length) {
		if(BD.length+1==baseD) {
			cls();
			System.out.println();
			DataBase.Connect("mysql");
			String nombre_BD=inputs.leerString("el nombre de la Base de Datos a agregar");
			DataBase.AgregarBaseDeDatos(nombre_BD);
			AccederATablaBD();
			
		}
		else {
			if(BD.length+2==baseD) {
			cls();
			menus.MenuBD();
			System.out.println();
			for(int i=0;i<BD.length;i++) {
				System.out.println((i+1)+": "+BD[i]);
			}
			System.out.println((BD.length+1)+": Ninguno");
			int numBD=inputs.leerInt("el numero de la base de datos a Eliminar", 1, BD.length+1);
			if(numBD-1<BD.length) {
			DataBase.Connect(BD[(numBD-1)]);
			DataBase.EliminarBaseDeDatos(BD[(numBD-1)]);
			}
			cls();
			}
			else {
			if(BD.length+3==baseD) {
				cls();
				opcion=Ingreso(false);
			}
			else {
				cls();
				opcion=Ingreso(true);
			}
		}
		}
	}
	else {
		if(DataBase.Connect(BD[baseD-1])) {
			cls();
			DataBase.dataBase=BD[baseD-1];
			AccederATablaBD();
		}
		else {
			cls();
			AccederABD();
		}
		
	}
}
public void AccederATablaBD() throws SQLException, ClassNotFoundException {
	menus.TablasEnBD();
	String BD[]=DataBase.showTables();
	menus.MenuAccesoTabla();
	int baseD=inputs.leerInt("la accion que deseee ejecutar",1,5);
	
	if(baseD>1) {
			if(2==baseD) {
				//Agregar
				cls();
				System.out.println("si no desea agregar nada llene los campos requeridos con 'X'");
				String nombre_tabla=inputs.leerString("el nombre de la tabla a agregar");
				String caracteristicas_tabla=inputs.leerString("las caracteristicas de la tabla a agregar");
				DataBase.AgregarTabla(nombre_tabla,caracteristicas_tabla);
				cls();
				AccederATablaBD();
			}
			else {
				if(3==baseD) {
					cls();
					menus.TablasEnBD();
					for(int i=0;i<BD.length;i++) {
						System.out.println((i+1)+": "+BD[i]);
					}
					System.out.println((BD.length+1)+": Ninguno");
					if(BD.length>0) {
					int num_tabla=inputs.leerInt("el numero de la tabla a eliminar",1,BD.length+1);
					if(BD.length+1==num_tabla) {
					}
					
					else {
					DataBase.tabla=BD[num_tabla-1];
					DataBase.EliminarTabla();
					}
					cls();
					AccederATablaBD();
					}
					
					else {
					cls();
					System.out.println("No puede Eliminar a ninguna tabla ya que no hay ninguna tabla existente.");	
					System.out.println();
					System.out.println();
					AccederATablaBD();
					}
				}
				else {
				if(4==baseD) {
				cls();
				AccederABD();
				}
				else {
				cls();
				opcion=Ingreso(true);
				}
			}
			}
	}
	else {
		cls();
		menus.TablasEnBD();
		for(int i=0;i<BD.length;i++) {
			System.out.println((i+1)+": "+BD[i]);
		}
		if(BD.length>0) {
			int num_tabla=inputs.leerInt("el numero de la tabla a acceder",1,BD.length);
			DataBase.tabla=BD[num_tabla-1];
			ModificarTabla();
		}
		
		
		
		
		
		else {
		System.out.println("No puede Acceder a ninguna tabla ya que no hay ninguna tabla existente.");	
		System.out.println();
		System.out.println();
		AccederATablaBD();
		}
		
		
		
		
		
		
	}
}
public void ModificarTabla() throws ClassNotFoundException, SQLException {
	cls();
	System.out.println("Tabla: "+DataBase.tabla);
	menus.ModificarTabla();
	int baseD=inputs.leerInt("la accion que deseee ejecutar",1,5);
	if(baseD>3) {
		if(baseD==4) {
			cls();
			AccederATablaBD();
		}else {
			cls();
			opcion=Ingreso(true);
		}
	}
	else {
		
		if(baseD==1) {
			DataBase.AgregarRegistro();
		}else {
			if(baseD==2) {
			//Eliminar Registro
			}
			else {
			//Modificar Registro
			}
		}
		
		
		
		
		
		
		
		
	}
}


public static void cls()
{
	for(int i=0;i<50;i++) {
		System.out.println();
	}
}
}
