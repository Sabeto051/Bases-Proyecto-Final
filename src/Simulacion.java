import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Simulacion {
	private Entradas input;
	private int usuarios_id=0;
	private int foros_id=0;
	private int preguntas_id=0;
	private int plan_id=0;
	private int escuela_id=0;
	
	public Simulacion() {
		this.input = new Entradas();
	}
	public static void cls()
	{
		for(int i=0;i<50;i++) {
			System.out.println();
		}
	}
	public void ingreso() throws ClassNotFoundException, SQLException, ParseException {
		usuarios_id=0;
		foros_id=0;
		preguntas_id=0;
		plan_id=0;
		System.out.println("Bienvenido a Nisand");
		System.out.println("1. Register\n2. Login\n3. Login Admin");
		int option = 2;//this.input.leerInt("opcion deseada", 1, 3);
		cls();
		switch (option) {
		case 1:
			registerUser();
			break;
		case 2:
			loginUser();
			break;
		case 3:
			loginAdmin();
			break;
		}
	}

	public void loginAdmin() throws ClassNotFoundException, SQLException {
		Accesos Acces=new Accesos();
		JFrame frame = new JFrame("Exiting");
		boolean salio=false;
		while(salio==false) {
			int accion=Acces.Ingreso(false);
			if(accion!=-1) {
			}
			else {
				salio=true;
				JOptionPane.showMessageDialog(frame,
						"Adios",
						"   Exiting",
						JOptionPane.INFORMATION_MESSAGE);
			}
			System.out.println();
		}
		frame.dispose();
		Entradas.reader.close();
	}
	
	
	
	//Metodos relacionados con interfaz usuario
	public void registerUser() throws SQLException, ParseException {
		//crea nuevo usuario en tabla usuarios
		boolean existe=true;
		while(existe==true) {
		existe=false;
		String campoVerificar="telefono";
		int id=DataBase.AgregarRegistro("Usuarios",campoVerificar);
		if(id==-1) {
			System.out.println("Telefono ya existe. Ingrese nuevamente el telefono.");
			existe=true;
		}
		else {
			usuarios_id=id;
			//Agregar Nombre
			System.out.println();
			int idNombre=DataBase.AgregarRegistro("Nombres","nombre");
			Object idN[]=new Object[1];
			idN[0]=idNombre;
			DataBase.AgregarRegistroCONDatos("usuario_id",usuarios_id, "FirstNameU", "usuario_id",idN);
			//Agregar Apellido
			int idApe=DataBase.AgregarRegistro("Apellidos","apellido");
			Object idN2[]=new Object[1];
			idN2[0]=idApe;
			DataBase.AgregarRegistroCONDatos("usuario_id",usuarios_id, "LastNameU", "usuario_id",idN2);
			System.out.println();
			existe=false;
		}
		}
		System.out.println();
		mostrarPlanUser();
	}
	public void loginUser() throws SQLException, ParseException {
		// Conecta con la tabla de usuarios
		DataBase.accederATabla("Usuarios");
		// pide número y contraseña
		boolean error=true;
		while(error==true) {
			error=false;
		String tel="3137938896";//this.input.leerString("su telefono");
		int id=DataBase.buscarExistente("usuarios","id","telefono", tel);
		if(id==-1) {
			System.out.println("Telefono no existe");
			error=true;
		}
		else {
			usuarios_id=id;
			boolean errorContra=true;
			while(errorContra==true) {
			errorContra=false;
			String contra="2";//this.input.leerString("Contrasena");
			if(contra.equalsIgnoreCase(DataBase.buscarValorDeCampoSegunID("Usuarios","id",usuarios_id, "pwd").toString())) {
				errorContra=false;
			}
			else {
			System.out.println("Contrase�a errada");
			errorContra=true;
			}
		}
		}
		
		}
		mostrarPlanUser();
	}
	public void mostrarPlanUser() throws SQLException, ParseException {
		// Se conecta la tabla Planes
		// Se conecta la tabla Usuarios-Planes
		
		cls();
		DataBase.accederATabla("Usuarios");
		System.out.println("Hola "+DataBase.buscarValorDeCampoSegunID("Usuarios","id",usuarios_id, "telefono"));
		System.out.println("Su Plan Actual");
		System.out.println();
		plan_id=DataBase.mostrarTablaSegunCriterio("Planes", "id", "plan_id", "Usuariosplanes", "usuario_id",usuarios_id,"")[0];
		if(plan_id>0) {
		System.out.println();
		System.out.println();
		DataBase.mostrarResultSet(DataBase.select2criteriosLog("usuariosplanes", "usuario_id", usuarios_id, "AND","plan_id", plan_id));
		System.out.println();
		System.out.println();
		Object fecha_fin=DataBase.buscarValorDeCampoSegunID("Usuariosplanes","usuario_id", usuarios_id, "fecha_fin");
		String f1=fecha_fin.toString();
		Calendar c = Calendar.getInstance();
		Date fin=c.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String f2 = dateFormat.format(fin);
		
		double diff=DataBase.diferenciaFecha(f1, f2);
		if(diff<0) {
			System.out.println("Plan se venci�");
			plan_id=0;
		}
		
		}
		else {
			System.out.println("   NA                                NA");
		}
		System.out.println();
		System.out.println("1. Comprar nuevo Plan\n2. Acceder a Escuelas\n3. Ver Cursos completados\n"
				+ "4. Ver preguntas hechas"+ "\n5. Modificar Datos de la Cuenta");
		int option = this.input.leerInt("opcion deseada", 1, 5);
		switch (option) {
		case 1:
			crearPlanParaUsuario();
			break;
		case 2:
			mostrarEscuelas();
			break;
		case 3:
			mostrarCursosCompletados();
			break;
		case 4:
			mostrarPreguntasUser();
			break;
		case 5:
			modificarInfoUser();
			break;
		}

	}

	public void mostrarPreguntasUser() {
		// Se muestran las preguntas hechas por el usuario
		this.input.leerString("Cualquier tecla para escoger Pregunta\n\n\n");
		mostrarPreguntasUserRespuesta();
	}



	public void mostrarPreguntasUserRespuesta() {
		// Creo que este metodo se puede combinar con otro que está más abajo 
	}
	public void modificarInfoUser() throws SQLException, ParseException {
		cls();
		System.out.println("Menu Modificacion de Cuenta");
		System.out.println();
		String telViejo=DataBase.buscarValorDeCampoSegunID("Usuarios", "id",usuarios_id, "telefono").toString();
		String contraViejo=DataBase.buscarValorDeCampoSegunID("Usuarios", "id",usuarios_id, "pwd").toString();
		int Nombrei=Integer.parseInt(DataBase.buscarValorDeCampoSegunID("FirstNameU", "usuario_id",usuarios_id,"nombre_id").toString());
		String nombreViejo=DataBase.buscarValorDeCampoSegunID("Nombres", "id", Nombrei, "nombre").toString();
		int apellidoi=Integer.parseInt(DataBase.buscarValorDeCampoSegunID("LastNameU", "usuario_id",usuarios_id,"apellido_id").toString());
		String apellidoViejo=DataBase.buscarValorDeCampoSegunID("apellidos", "id", apellidoi, "apellido").toString();
		System.out.println("Telefono       "+ telViejo);
		System.out.println("Contrase�a     "+ contraViejo);
		System.out.println("Nombre         "+ nombreViejo);
		System.out.println("Apellido       "+ apellidoViejo);
		System.out.println();
		System.out.println("1. Modificar Telefono\n2. Cambiar contrase�a\n3. Cambiar Nombre\n"
				+ "4. Cambiar Apellido\n" +"5. Volver a Menu Principal");
		int option = input.leerInt("la accion que desea realizar ", 1, 5);
		switch (option) {
		case 1:
			System.out.println("El telefono viejo es        "+telViejo);
			String tel=input.leerString("el telefono nuevo  ");
			DataBase.ModificarRegistroUnico("Usuarios", "id", "telefono", 
			telViejo, "telefono", tel);
			modificarInfoUser();
			break;
		case 2:
			System.out.println(   "La Contrasena vieja es        "+contraViejo);
			String contra=input.leerString("la contrase�a nueva  ");
			DataBase.ModificarRegistroUnico("Usuarios", "id", "pwd", 
			contraViejo, "pwd", contra);
			modificarInfoUser();
			break;
		case 3:
			System.out.println(   "El nombre nuevo es       "+nombreViejo);
			String Nombre=input.leerString("el nombre nuevo");
			int nombreN=DataBase.buscarExistente("Nombres", "id", "nombre",Nombre);
			if(nombreN==-1) {
				int idNombre=DataBase.AgregarRegistro("Nombres","nombre");
				Object idN[]=new Object[1];
				idN[0]=nombreN;
			//	DataBase.AgregarRegistroCONDatos("usuario_id",usuarios_id, "FirstNameU", "usuario_id",);
			}else {
				
			}
			/*DataBase.ModificarRegistroUnico("FirstNameU", "id", "usuarios_id", 
			contraViejo, "", contra);*/
			modificarInfoUser();
			mostrarCursosCompletados();
			break;
		case 4:
			mostrarPreguntasUser();
			break;
		case 5:
			mostrarPlanUser();
			break;
		}
		mostrarPlanUser();
	}
	public void mostrarCursosCompletados() throws SQLException, ParseException {
		// Se conecta con tabla Cursos , Usuarios y  Estudaintes-Cursos
		// Se muestran los cursos completados
		this.input.leerString("Cualquier tecla para retroceder\n\n\n");
		mostrarPlanUser();
	}
	public void crearPlanParaUsuario() throws SQLException, ParseException {
		boolean cambiar=true;
		Calendar c = Calendar.getInstance();
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		Date date22=c.getTime();
		String fechaHoy=dateFormat1.format(date22);
		DateFormat dateFormat2 = new SimpleDateFormat("kk:mm:ss");
		String horaActual=dateFormat2.format(date22);
		
		if(plan_id>0) {
			System.out.println("Usuario ya tiene plan existente. Desea cambiarlo?\n1. Si\n2. No");
			int cambio=input.leerInt("la accion a tomar",1,2);
			if(cambio==1) {
				cambiar=true;
				cls();
			}
			else {
				cambiar=false;
			}
		}
		if(cambiar==true) {
		System.out.println("Planes");
		System.out.println();
		DataBase.mostrarTablaConAtributoDeOtraTabla("id", "planes", "plan_id", "escuela_id", "PlanesEscuela","id","Escuelas","nombre");
		// Se muestran planes disponibles
		// Se escoge uno de los planes disponibles
		Object option=this.input.leerInt("el numero del plan a escoger",1,DataBase.maxID("id","Planes"));
		DataBase.accederATabla("Usuariosplanes");
		if(plan_id>0) {
		DataBase.ModificarRegistroUnico("usuariosplanes","usuario_id","usuario_id",Integer.toString(usuarios_id),"plan_id",option);
		plan_id=Integer.parseInt(option.toString());
		String fecha="";
		int opcion_fecha=input.leerInt("Accion a realizar\n1.Fecha Actual\n2.Fecha Manualmente", 1, 2);
		if(opcion_fecha==1) {
			fecha=fechaHoy;
		}
		else {
			boolean aceptada=false;
			while(aceptada==false) {
			aceptada=true;
			int date1=input.leerInt("Dia de hoy",1,31);
			int date2=input.leerInt("Mes de hoy",1,12);
			int date3=input.leerInt("A�o de hoy",1800,10000);
			String dateS1=Integer.toString(date1);
			String dateS2=Integer.toString(date2);
			String dateS3=Integer.toString(date3);
			if(date1<10 ) {
				dateS1="0"+date1;
			}
			if(date2<10) {
				dateS2="0"+date2;
			}
		
			fecha=dateS3+"-"+dateS2+"-"+dateS1+ " "+horaActual;
			double comp=DataBase.diferenciaFecha(fechaHoy, fecha);
			if(comp<0) {
				//System.out.println("despues de hoy");
			}
			else {
				System.out.println("La fecha a ingresar debe ser despu�s de hoy. Ingrese fehca nuevamente");
				System.out.println("Fecha Hoy:                 "+fechaHoy);
				System.out.println("Fecha Ingresada No Valida: "+fecha);
				aceptada=false;
			}
			}
		}
		Object datos[]=new Object[2];
		datos[0]=fecha;
		String fecha2= DataBase.sumarAFecha("Planes",fecha,"id", plan_id, "duracion");
		datos[1]=fecha2;
		DataBase.ModificarRegistroUnico("usuariosplanes","usuario_id", "usuario_id", Integer.toString(usuarios_id), "fecha_inicio",datos[0]);
		DataBase.ModificarRegistroUnico("usuariosplanes","usuario_id", "usuario_id", Integer.toString(usuarios_id), "fecha_fin",datos[1]);
		
		
		}
		else {
		plan_id=Integer.parseInt(option.toString());
		String fecha="";
		int opcion_fecha=input.leerInt("Accion a realizar\n1.Fecha Actual\n2.Fecha Manualmente", 1, 2);
		if(opcion_fecha==1) {
			fecha=fechaHoy;
		}
		else {
			boolean aceptada=false;
			while(aceptada==false) {
			aceptada=true;
			int date1=input.leerInt("Dia de hoy",1,31);
			int date2=input.leerInt("Mes de hoy",1,12);
			int date3=input.leerInt("A�o de hoy",1800,10000);
			String dateS1=Integer.toString(date1);
			String dateS2=Integer.toString(date2);
			String dateS3=Integer.toString(date3);
			if(date1<10 ) {
				dateS1="0"+date1;
			}
			if(date2<10) {
				dateS2="0"+date2;
			}
		
			fecha=dateS3+"-"+dateS2+"-"+dateS1+ " "+horaActual;
			double comp=DataBase.diferenciaFecha(fechaHoy, fecha);
			if(comp<0) {
				//System.out.println("despues de hoy");
			}
			else {
				System.out.println("La fecha a ingresar debe ser despu�s de hoy. Ingrese fehca nuevamente");
				System.out.println("Fecha Hoy:                 "+fechaHoy);
				System.out.println("Fecha Ingresada No Valida: "+fecha);
				aceptada=false;
			}
		}
		}
		Object datos[]=new Object[3];
		datos[0]=fecha;
		String fecha2= DataBase.sumarAFecha("Planes",fecha,"id", plan_id, "duracion");
		datos[1]=fecha2;
		datos[2]=plan_id;
		DataBase.AgregarRegistroCONDatos("usuario_id",usuarios_id, "Usuariosplanes", "usuario_id", datos);
		}
		}
		mostrarPlanUser();
		}
	public void mostrarEscuelas() throws SQLException {
		cls();
		// Se conecta con la tabla Escuelas
		// Se conecta la tabla Planes-Escuela (Dijimos que no todas las escuelas están en todos los planes)
		System.out.println("Plan "+DataBase.buscarValorDeCampoSegunID("Planes","id", plan_id, "id"));
		System.out.println("Escuelas ");
		System.out.println();
		int opciones[]=DataBase.mostrarTablaSegunCriterio("Escuelas", "id", "escuela_id", "PlanesEscuela", "plan_id",plan_id,"numerado");
		System.out.println();
		// se muestran las escuelas
		// se escoge una escuela
		if(opciones.length==1) {
			escuela_id=opciones[0];
		}else 
		{
		escuela_id=input.leerInt("la escuela a acceder ",1,opciones.length);
		}
		mostrarCarreras(); // de la escuela
	}
	public void mostrarCarreras() throws SQLException {
		// se muestran las carreras de la escuela
		cls();
		// Se conecta con la tabla Escuelas
		// Se conecta la tabla Planes-Escuela (Dijimos que no todas las escuelas están en todos los planes)
		System.out.println("Plan "+DataBase.buscarValorDeCampoSegunID("Planes","id", plan_id, "id"));
		System.out.println("Escuela "+DataBase.buscarValorDeCampoSegunID("Escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras ");
		System.out.println();
		int opciones[]=DataBase.mostrarTablaSegunCriterio("Carreras", "id", "carrera_id", "CarrerasEscuela", "plan_id",plan_id,"numerado");
		System.out.println();
		// se muestran las escuelas
		// se escoge una escuela
		if(opciones.length==1) {
			escuela_id=opciones[0];
		}else 
		{
		escuela_id=input.leerInt("la escuela a acceder ",1,opciones.length);
		}
		mostrarCursos(); // de la carrera
	}
	public void mostrarCursos() throws SQLException {
		// se conecta con tabla cursos
		System.out.println("Carrera INSERTE NOMBRE AQUI");
		// se muestran los cursos y se escoge uno
		this.input.leerString("Cualquier tecla para escoger Curso\n\n\n");
		mostrarCursoMenu(); // del seleccionado
	}
	public void mostrarCursoMenu() throws SQLException {
		System.out.println("Curso INSERTE NOMBRE AQUI");
		System.out.println("Profesor INSERTE NOMBRE AQUI");
		System.out.println("1. Acceder a los foros del curso\n2. Acceder a los videos");
		int option = this.input.leerInt("opcion deseada", 1, 2);
		switch (option) {
		case 1:
			mostrarForos();
			break;
		case 2:
			mostrarVideos();
			break;
		}
	}
	public void mostrarVideos() {
		System.out.println("Curso INSERTE NOMBRE AQUI");
		// Se muestra la lista de videos del curso
		// Si se puede, mostrar en una columna al lado de los videos cuales ha visto
		this.input.leerString("Cualquier tecla para escoder Video\n\n\n");
		verVideo();
	}
	public void verVideo() {
		System.out.println("Video INSERTE TITULO AQUI");
		// se conecta la tabla videos y Estudiante-videos
		// se agrega el video a videos vistos por el estudiante
		System.out.println("Video visto");
		this.input.leerString("Cualquier tecla para retroceder Curso\n\n\n");
		mostrarVideos();
	}
	public void mostrarForos() throws SQLException {
		System.out.println("Curso INSERTE NOMBRE AQUI");
		// se conecta con la tabla foros
		// se muestran los foros del curso
		this.input.leerString("Cualquier tecla para escoger Foro\n\n\n");
		mostrarForoMenu();
	}
	public void mostrarForoMenu() throws SQLException {
		System.out.println("Foro INSERTE NOMBRE AQUI");
		System.out.println("1. Ver Preguntas del foro\n2. Hacer Pregunta");
		int option = this.input.leerInt("opcion deseada", 1, 2);
		switch (option) {
		case 1:
			mostrarPreguntas();
			break;
		case 2:
			crearPregunta();
			break;
		}
	}
	public void mostrarPreguntas() throws SQLException {
		// Se conecta con tablas preguntas 
		// Se muestran las preguntas del j
		this.preguntas_id = SimulacionUtilities.mostrarPreguntas(foros_id);
		System.out.println("\n\n\n");
		mostrarPreguntaMenu();
	}
	public void mostrarPreguntaMenu() throws SQLException {
		System.out.println("1. Ver Respuestas a la pregunta\n2. Retroceder");
		int option = this.input.leerInt("opcion deseada", 1, 2);
		System.out.println("\n\n\n");
		switch (option) {
		case 1:
			mostrarRespuestas();
			break;
		case 2:
			mostrarPreguntas();
			break;
		}
	}
	public void mostrarRespuestas() throws SQLException {
		System.out.println("Respesutas de la pregunta CONTENIDO PREGUNTA");
		// Se muestran las respuestas de la pregunta
		SimulacionUtilities.mostrarRespuestas(preguntas_id);
		System.out.println("1. Responder Pregunta\n2. retroceder");
		int option = this.input.leerInt("opcion deseada", 1, 2);
		System.out.println("\n\n\n");
		switch (option) {
		case 1:
			crearRespuesta();
			break;
		case 2:
			mostrarPreguntas();
			break;
		}

	}
	public void crearRespuesta() throws SQLException {
		// Se conecta con tabla respuestas, preguntas, usuario
		SimulacionUtilities.crearRespuesta(usuarios_id, preguntas_id);
		System.out.println("\n\n\n");
		mostrarPreguntas();
	}
	public void crearPregunta() throws SQLException {
		// Se conecta con tabla usuarios, foro, pregunta
		SimulacionUtilities.crearPregunta(usuarios_id, foros_id);
		System.out.println("\n\n\n");
		mostrarForoMenu();
	}
	
	
	
	//Metodos relacionados con Admin
		public void crearPlan() throws SQLException, ParseException {
			int accion=-1;
			while(accion==-1) {
			accion=DataBase.AgregarRegistroLog("Planes", "Duracion","AND","Valor");
			if(accion==-1) {
				System.out.println("Plan ya existe. Intente nuevamente.");
			}
			}
			mostrarPlanUser();
		}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		Menus menu = new Menus();
		
		//String cred[]=menu.MenuUsuarioContrasenaBD();
		String cred[]=new String[2];
		cred[0]="root";
		cred[1]="holahola123";
		DataBase.username=cred[0];
		DataBase.password=cred[1];
		
		Simulacion s = new Simulacion();
		DataBase.Connect("Nisand");
		s.ingreso();
	}

}
