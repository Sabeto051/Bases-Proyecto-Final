import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.sun.org.apache.xerces.internal.impl.dv.xs.DateTimeDV;

public class Simulacion {
	private Entradas input;
	private int usuarios_id=0;
	private int foros_id=0;
	private int preguntas_id=0;
	private int plan_id=0;
	
	public Simulacion() {
		this.input = new Entradas();
	}
	public static void cls()
	{
		for(int i=0;i<50;i++) {
			System.out.println();
		}
	}
	public void ingreso() throws ClassNotFoundException, SQLException {
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
	public void registerUser() throws SQLException {
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
			existe=false;
		}
		}
		System.out.println();
		mostrarPlanUser();
	}
	public void loginUser() throws SQLException {
		// Conecta con la tabla de usuarios
		DataBase.accederATabla("Usuarios");
		// pide número y contraseña
		boolean error=true;
		while(error==true) {
			error=false;
		String tel="2";//this.input.leerString("su telefono");
		int id=DataBase.buscarExistente("id","telefono", tel);
		if(id==-1) {
			System.out.println("Telefono no existe");
			error=true;
		}
		else {
			usuarios_id=id;
			boolean errorContra=true;
			while(errorContra==true) {
			errorContra=false;
			String contra="1";//this.input.leerString("Contrasena");
			if(contra.equalsIgnoreCase(DataBase.buscarValorDeCampoSegunID("id",usuarios_id, "pwd").toString())) {
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
	public void mostrarPlanUser() throws SQLException {
		// Se conecta la tabla Planes
		// Se conecta la tabla Usuarios-Planes
		
		cls();
		DataBase.accederATabla("Usuarios");
		System.out.println("Hola "+DataBase.buscarValorDeCampoSegunID("id",usuarios_id, "telefono"));
		System.out.println("Su Plan Actual");
		System.out.println();
		plan_id=DataBase.mostrarTablaSegunCriterio("Planes", "id", "plan_id", "Usuariosplanes", "usuario_id",usuarios_id)[0];
		System.out.println();
		System.out.println();
		DataBase.mostrarResultSet(DataBase.select2criteriosLog("usuariosplanes", "usuario_id", usuarios_id, "AND","plan_id", plan_id));
		System.out.println();
		System.out.println();
		System.out.println("1. Comprar nuevo Plan\n2. Acceder a Escuelas\n3. Ver Cursos completados\n"
				+ "4. Ver preguntas hechas");
		int option = this.input.leerInt("opcion deseada", 1, 4);
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
	public void mostrarCursosCompletados() throws SQLException {
		// Se conecta con tabla Cursos , Usuarios y  Estudaintes-Cursos
		// Se muestran los cursos completados
		this.input.leerString("Cualquier tecla para retroceder\n\n\n");
		mostrarPlanUser();
	}
	public void crearPlanParaUsuario() throws SQLException {
		boolean cambiar=true;
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
		DataBase.mostrarTabla("id","Planes");
		
		// Se muestran planes disponibles
		// Se escoge uno de los planes disponibles
		Object option=this.input.leerInt("el numero del plan a escoger",1,DataBase.maxID("id","Planes"));
		DataBase.accederATabla("Usuariosplanes");
		if(plan_id>0) {
		DataBase.ModificarRegistroUnico("usuario_id","usuario_id",Integer.toString(usuarios_id),"plan_id",option);
		plan_id=Integer.parseInt(option.toString());
		
		Object datos[]=new Object[3];
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
	
		String dateSS=dateS1+"/"+dateS2+"/"+dateS3;
		String fecha=dateS3+"-"+dateS2+"-"+dateS1+ " 12:00:00";
		datos[0]=dateS3+"-"+dateS2+"-"+dateS1+ " 12:00:00";
		String fecha2= DataBase.sumarAFecha(dateSS,"id", plan_id, "duracion");
		datos[1]=fecha2;
		DataBase.ModificarRegistroUnico("usuario_id", "usuario_id", Integer.toString(usuarios_id), "fecha_inicio",datos[0]);
		DataBase.ModificarRegistroUnico("usuario_id", "usuario_id", Integer.toString(usuarios_id), "fecha_fin",datos[1]);
		
		
		}
		else {
		plan_id=Integer.parseInt(option.toString());
		Object datos[]=new Object[3];
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
	
		String dateSS=dateS1+"/"+dateS2+"/"+dateS3;
		datos[0]=dateS3+"-"+dateS2+"-"+dateS1+ " 12:00:00";
		String fecha2= DataBase.sumarAFecha(dateSS,"id", plan_id, "duracion");
		datos[1]=fecha2;
		datos[2]=plan_id;
		DataBase.AgregarRegistroCONDatos("usuario_id",usuarios_id, "Usuariosplanes", "usuario_id", datos);
		}
		}
		mostrarPlanUser();
		}
	
	
	public void mostrarEscuelas() throws SQLException {
		// Se conecta con la tabla Escuelas
		// Se conecta la tabla Planes-Escuela (Dijimos que no todas las escuelas están en todos los planes)

		// se muestran las escuelas
		// se escoge una escuela
		this.input.leerString("Cualquier tecla para escoger escuela\n\n\n");
		mostrarCarreras(); // de la escuela
	}
	public void mostrarCarreras() throws SQLException {
		// se muestran las carreras de la escuela
		System.out.println("Escuela INSERTE NOMBRE AQUI");
		// se escoge una carrera
		this.input.leerString("Cualquier tecla para escoger Carrera\n\n\n");
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
		this.input.leerString("Cualquier tecla para escoger Pregunta\n\n\n");
		mostrarPreguntaMenu();
	}
	public void mostrarPreguntaMenu() throws SQLException {
		System.out.println("1. Ver Respuestas a la pregunta\n2. Retroceder");
		int option = this.input.leerInt("opcion deseada", 1, 2);
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
		this.input.leerString("Cualquier tecla para Responder\n\n\n");
		SimulacionUtilities.crearRespuesta(usuarios_id, preguntas_id);
		mostrarPreguntas();
	}
	public void crearPregunta() throws SQLException {
		// Se conecta con tabla usuarios, foro, pregunta
		SimulacionUtilities.crearPregunta(usuarios_id, foros_id);
		mostrarForoMenu();
	}
	
	
	
	//Metodos relacionados con Admin
		public void crearPlan() throws SQLException {
			int accion=-1;
			while(accion==-1) {
			accion=DataBase.AgregarRegistroLog("Planes", "Duracion","AND","Valor");
			if(accion==-1) {
				System.out.println("Plan ya existe. Intente nuevamente.");
			}
			}
			mostrarPlanUser();
		}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Simulacion s = new Simulacion();
		DataBase.Connect("nisand");
		s.ingreso();
	}

}
