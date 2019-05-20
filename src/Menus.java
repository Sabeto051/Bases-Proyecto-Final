
public class Menus {
	private Entradas inputs=new Entradas();
	public void MenuPrincipal() {
		System.out.println("Menu Principal");
		System.out.println("Seleccione la opcion deseada");
		System.out.println("1: Acceder a la base de datos");
		System.out.println("2: Salir");

	}
	public String[] MenuUsuarioContrasenaBD() {
		System.out.println("Ingrese Usuario (en blanco se considerar� root)");
		String usuario=inputs.leerString(" el Usuario");
		if(usuario.equals("")) {
			usuario="root";
		}
		System.out.println("Ingrese Contrase�a");
		String contrasena=inputs.leerString(" la contrasena");
		String cred[]=new String[2];

		cred[0]=usuario;
		cred[1]=contrasena;

		return cred;
	}
	public void MenuBD() {
		System.out.println("Bases de Datos");
	}
	public void TablasEnBD() {
		System.out.println("Tablas en Base de Datos: "+DataBase.dataBase);
	}
	public void MenuAccesoTabla() {
		System.out.println("\n"+"Accion a Realizar"+"\n"
				+"1. Acceder a una tabla" + "\n" 
				+"2. Crear una tabla" + "\n" 
				+"3. Eliminar una tabla" + "\n" 
				+"4. Retroceder "+ "\n" 
				+"5. Salir");
	}
	public void ModificarTabla() {
		System.out.println("\n"+"Accion a Realizar"+"\n"
				+"1. Agregar registro" + "\n" 
				+"2. Eliminar registro" + "\n" 
				+"3. Modificar registro" + "\n" 
				+"4. Retroceder "+ "\n" 
				+"5. Salir");
	}

	//Alter table?
}
