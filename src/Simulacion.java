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
	private int pregunta_id=0;
	private int plan_id=0;
	private int escuela_id=0;
	private int carrera_id=0;
	private int curso_id;
	private int prof_id;
	private int foro_id;
	private String prof_name;
	private String prof_lname;
	private int respuesta_id;
	private boolean meGusta=false;
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
		foro_id=0;
		pregunta_id=0;
		plan_id=0;
		escuela_id=0;
		carrera_id=0;
		curso_id=0;
		prof_id=0;
		prof_name="";
		prof_lname="";
		foro_id=0;
		pregunta_id=0;
		respuesta_id=0;

		System.out.println("Bienvenido a "+DataBase.dbConnection.getCatalog());
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
		System.out.println("Usuario Nuevo");
		System.out.println();
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
			String nom=input.leerString("el nombre");
			Object idN[]=new Object[1];
			int idNombre=DataBase.buscarExistente("nombres", "id", "nombre", nom);
			idN[0]=idNombre;
			if(idNombre!=-1) {
				DataBase.AgregarRegistroCONDatos("usuario_id", usuarios_id,"FirstNameU", "usuario_id",idN);
			}
			else {
				idN[0]=nom;
				idNombre=DataBase.AgregarRegistroCONDatos("id","nombres", "nombre", idN);
				idN[0]=idNombre;
				DataBase.AgregarRegistroCONDatos("usuario_id", usuarios_id,"FirstNameU", "usuario_id",idN);
				
			}
			//Agregar Apellido
			String ape=input.leerString("el apellido");
			Object idA[]=new Object[1];
			int idapellido=DataBase.buscarExistente("apellidos", "id", "apellido", ape);
			idA[0]=idapellido;
			if(idapellido!=-1) {
				DataBase.AgregarRegistroCONDatos("usuario_id", usuarios_id,"LastNameU", "usuario_id",idA);
			}
			else {
				idA[0]=ape;
				idapellido=DataBase.AgregarRegistroCONDatos("id","apellidos", "apellido", idA);
				idA[0]=idapellido;
				DataBase.AgregarRegistroCONDatos("usuario_id", usuarios_id,"LastNameU", "usuario_id",idA);
				
			}
			existe=false;
			
		}
		}
		System.out.println();
		mostrarPlanUser();
	}
	public void loginUser() throws SQLException, ParseException {
		// Conecta con la tabla de usuarios
		DataBase.accederATabla("usuarios");
		// pide número y contraseña
		boolean error=true;
		while(error==true) {
			error=false;
		String tel="3008106969";//this.input.leerString("su telefono");
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
			String contra="rank";//this.input.leerString("Contrasena");
			if(contra.equalsIgnoreCase(DataBase.buscarValorDeCampoSegunID("usuarios","id",usuarios_id, "pwd").toString())) {
				errorContra=false;
			}
			else {
			System.out.println("Contrasena errada");
			errorContra=true;
			}
		}
		}
		
		}
		mostrarPlanUser();
	}
	public void mostrarPlanUser() throws SQLException, ParseException {
		// Se conecta la tabla planes
		// Se conecta la tabla Usuarios-planes
		
		cls();
		DataBase.accederATabla("Usuarios");
		int Nid=Integer.parseInt(DataBase.buscarValorDeCampoSegunID("firstnameu","usuario_id", usuarios_id, "nombre_id").toString());
		int Nap=Integer.parseInt(DataBase.buscarValorDeCampoSegunID("lastnameu","usuario_id", usuarios_id, "apellido_id").toString());
		String name=DataBase.buscarValorDeCampoSegunID("nombres", "id", Nid, "nombre").toString();
		String ape=DataBase.buscarValorDeCampoSegunID("apellidos", "id", Nap, "apellido").toString();
		System.out.println(name+" "+ape);
		System.out.println("Su Plan Actual");
		System.out.println();
		plan_id=DataBase.mostrarTablaSegunCriterio("planes", "id", "plan_id", "usuariosplanes", "usuario_id",usuarios_id,"")[0];
		if(plan_id>0) {
		System.out.println();
		System.out.println();
		DataBase.mostrarResultSet(DataBase.select2criteriosLog("usuariosplanes", "usuario_id", usuarios_id, "AND","plan_id", plan_id));
		System.out.println();
		System.out.println();
		Object fecha_fin=DataBase.buscarValorDeCampoSegunID("usuariosplanes","usuario_id", usuarios_id, "fecha_fin");
		String f1=fecha_fin.toString();
		Calendar c = Calendar.getInstance();
		Date fin=c.getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
		String f2 = dateFormat.format(fin);
		
		double diff=DataBase.diferenciaFecha(f1, f2);
		if(diff<0) {
			System.out.println("Plan se vencio");
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
			if(plan_id>0){
			mostrarEscuelas();}
			else{
			System.out.println("El plan esta vencido o no existe ningun plan. ");
			System.out.println("Desea comprar nuevo plan.\n1. Si\n2. No ");
			int acc=input.leerInt("que desea realizar ",1,2);
			if(acc==1){
			crearPlanParaUsuario();
			}
			else{
			mostrarPlanUser();
			}
			
			}
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
			boolean existe=true;
			while(existe==true) {
			existe=false;
			String campoVerificar="telefono";
			int thi=DataBase.buscarExistente("Usuarios", "id", campoVerificar, tel);
			if(thi>0) {
				System.out.println("Telefono ya existe. Ingrese nuevamente el telefono.");
				tel=input.leerString("el telefono nuevo  ");
				existe=true;
			}
			else {
				existe=false;
			}
			}
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
			System.out.println(   "El nombre viejo es       "+nombreViejo);
			String nom=input.leerString("el nombre nuevo ");
			Object idN[]=new Object[1];
			int idNombre=DataBase.buscarExistente("nombres", "id", "nombre", nom);
			idN[0]=idNombre;
			if(idNombre!=-1) {
				DataBase.ModificarRegistroUnico("FirstNameU", "usuario_id", 
						"usuario_id", Integer.toString(usuarios_id), "nombre_id",idN[0]);
			}
			else {
				idN[0]=nom;
				idNombre=DataBase.AgregarRegistroCONDatos("id","nombres", "nombre",idN);
				idN[0]=idNombre;
				DataBase.ModificarRegistroUnico("FirstNameU", "usuario_id", 
						"usuario_id", Integer.toString(usuarios_id), "nombre_id",idN[0]);
				
			}
			modificarInfoUser();
			break;
		case 4:
			System.out.println(   "El apellido viejo es        "+apellidoViejo);
			String ape=input.leerString(" el apellido nuevo ");
			Object idA[]=new Object[1];
			int idapellido=DataBase.buscarExistente("apellidos", "id", "apellido", ape);
			idA[0]=idapellido;
			if(idapellido!=-1) {
				DataBase.ModificarRegistroUnico("LastNameU", "usuario_id", 
						"usuario_id", Integer.toString(usuarios_id), "apellido_id",idA[0]);
			}
			else {
				idA[0]=ape;
				idapellido=DataBase.AgregarRegistroCONDatos("id","apellidos", "apellido",idA);
				idA[0]=idapellido;
				DataBase.ModificarRegistroUnico("LastNameU", "usuario_id", 
						"usuario_id", Integer.toString(usuarios_id), "apellido_id",idA[0]);
				
			}
			modificarInfoUser();
			break;
		case 5:
			mostrarPlanUser();
			break;
		}
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
		System.out.println("planes");
		System.out.println();
		DataBase.mostrarTablaConAtributoDeOtraTabla("id", "planes", "plan_id", "escuela_id", "planesescuela","id","escuelas","nombre");
		// Se muestran planes disponibles
		// Se escoge uno de los planes disponibles
		Object option=this.input.leerInt("el numero del plan a escoger",1,DataBase.maxID("id","planes"));
		DataBase.accederATabla("usuariosplanes");
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
			int date1=input.leerInt("Dia ",1,31);
			int date2=input.leerInt("Mes ",1,12);
			int date3=input.leerInt("Ano ",1800,10000);
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
				System.out.println("La fecha a ingresar debe ser despues de hoy. Ingrese fecha nuevamente");
				System.out.println("Fecha Hoy:                 "+fechaHoy);
				System.out.println("Fecha Ingresada No Valida: "+fecha);
				aceptada=false;
			}
			}
		}
		Object datos[]=new Object[2];
		datos[0]=fecha;
		String fecha2= DataBase.sumarAFecha("planes",fecha,"id", plan_id, "duracion");
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
			int date1=input.leerInt("Dia ",1,31);
			int date2=input.leerInt("Mes ",1,12);
			int date3=input.leerInt("A�o ",1800,10000);
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
				System.out.println("La fecha a ingresar debe ser despues de hoy. Ingrese fecha nuevamente");
				System.out.println("Fecha Hoy:                 "+fechaHoy);
				System.out.println("Fecha Ingresada No Valida: "+fecha);
				aceptada=false;
			}
		}
		}
		Object datos[]=new Object[3];
		datos[0]=fecha;
		String fecha2= DataBase.sumarAFecha("planes",fecha,"id", plan_id, "duracion");
		datos[1]=fecha2;
		datos[2]=plan_id;
		if(DataBase.buscarExistente("usuariosplanes", "usuario_id", "usuario_id",Integer.toString(usuarios_id))>0) {
			DataBase.ModificarRegistroUnico("usuariosplanes","usuario_id", "usuario_id", Integer.toString(usuarios_id), "fecha_inicio",datos[0]);
			DataBase.ModificarRegistroUnico("usuariosplanes","usuario_id", "usuario_id", Integer.toString(usuarios_id), "fecha_fin",datos[1]);
		}else {
			DataBase.AgregarRegistroCONDatos("usuario_id",usuarios_id, "usuariosplanes", "usuario_id", datos);
		}
		}
		}
		mostrarPlanUser();
		}
	public void mostrarEscuelas() throws SQLException, ParseException {
		cls();
		escuela_id=0;
		// Se conecta con la tabla Escuelas
		// Se conecta la tabla planes-Escuela (Dijimos que no todas las escuelas están en todos los planes)
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuelas ");
		System.out.println();
		int opciones[]=DataBase.mostrarTablaSegunCriterio("escuelas", "id", "escuela_id", "planesescuela", "plan_id",plan_id,"numerado");
		System.out.println();
		System.out.println((opciones.length+1)+". Retroceder");
		System.out.println();
		// se muestran las escuelas
		// se escoge una escuela
		int in=input.leerInt("la escuela a acceder o "+(opciones.length+1)+" para retroceder",1,opciones.length+1);
		if(in==opciones.length+1) {
			mostrarPlanUser();
		}
		else {
		escuela_id=opciones[in-1];
		mostrarCarreras();
		
		} // de la escuela
	}
	public void mostrarCarreras() throws SQLException, ParseException {
		// se muestran las carreras de la escuela
		carrera_id=0;
		cls();
		// Se conecta con la tabla Escuelas
		// Se conecta la tabla planes-Escuela (Dijimos que no todas las escuelas están en todos los planes)
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuela: "+DataBase.buscarValorDeCampoSegunID("escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras ");
		System.out.println();
		String ats[]=new String[2];
		ats[0]="nombre";
		ats[1]="descripcion";
		int opciones[]=DataBase.mostrarTablaSegunCriterio2("nombre","carreras",ats,"id","escuela_id",Integer.toString(escuela_id),"numerado","no");
		System.out.println();
		System.out.println((opciones.length+1)+". Retroceder");
		System.out.println();
		
			int in=input.leerInt("la carrera a acceder o "+(opciones.length+1)+" para retroceder",1,opciones.length+1);
			if(in==opciones.length+1) {
				mostrarEscuelas();
			}
			else {
				carrera_id=opciones[in-1];
				mostrarCursos(); 
			}
		}
	
	public void mostrarCursos() throws SQLException, ParseException {
		curso_id=0;
		prof_id=0;
		prof_name="";
		prof_lname="";
		cls();
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuela: "+DataBase.buscarValorDeCampoSegunID("escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras: "+DataBase.buscarValorDeCampoSegunID("carreras","id", carrera_id, "nombre"));
		System.out.println("Cursos ");
		System.out.println();
		// se conecta con tabla cursos
		String ats[]=new String[2];
		ats[0]="nombre";
		ats[1]="descripcion";
		int opciones[]=DataBase.mostrarTablaSegunCriterio2("nombre","cursos",ats,"id","carrera_id",Integer.toString(carrera_id),"numerado","no");
		System.out.println();
		System.out.println((opciones.length+1)+". Retroceder");
		System.out.println();	
			int in=input.leerInt("el curso a acceder o "+(opciones.length+1)+" para retroceder",1,opciones.length+1);
			if(in==opciones.length+1) {
				mostrarCarreras(); 
			}
			else {
				curso_id=opciones[in-1];
				prof_id=Integer.parseInt(DataBase.buscarValorDeCampoSegunID("cursos","id", curso_id, "profesor_id").toString());
				int PName=Integer.parseInt(DataBase.buscarValorDeCampoSegunID("firstnamep", "profesor_id", prof_id, "nombre_id").toString());
				int PLName=Integer.parseInt(DataBase.buscarValorDeCampoSegunID("lastnamep", "profesor_id", prof_id, "apellido_id").toString());
				prof_name=DataBase.buscarValorDeCampoSegunID("nombres","id", PName, "nombre").toString();
				prof_lname=DataBase.buscarValorDeCampoSegunID("apellidos","id", PLName, "apellido").toString();
				mostrarCursoMenu();
			}
		
	}
	public void mostrarCursoMenu() throws SQLException, ParseException {
		cls();
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuela: "+DataBase.buscarValorDeCampoSegunID("escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras: "+DataBase.buscarValorDeCampoSegunID("carreras","id", carrera_id, "nombre"));
		System.out.println("Cursos: "+DataBase.buscarValorDeCampoSegunID("cursos","id", curso_id, "nombre"));
		System.out.println("Profesor "+prof_name +" "+prof_lname);
		System.out.println();
		System.out.println("1. Acceder a los foros del curso\n2. Acceder a los videos\n3. Retroceder");
		int option = this.input.leerInt("opcion deseada", 1, 3);
		switch (option) {
		case 1:
			mostrarForos();
			break;
		case 2:
			mostrarVideos();
			break;
		case 3:
			mostrarCursos();
			break;
		}
	}
	public void mostrarForos() throws SQLException, ParseException {
		foro_id=0;
		cls();
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuela: "+DataBase.buscarValorDeCampoSegunID("escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras: "+DataBase.buscarValorDeCampoSegunID("carreras","id", carrera_id, "nombre"));
		System.out.println("Cursos: "+DataBase.buscarValorDeCampoSegunID("cursos","id", curso_id, "nombre"));
		System.out.println("Profesor: "+prof_name +" "+prof_lname);
		System.out.println("Foros");
		String ats[]=new String[1];
		ats[0]="nombre";
		int opciones[]=DataBase.mostrarTablaSegunCriterio2("nombre","foros",ats,"id","curso_id",Integer.toString(curso_id),"numerado","no");
		System.out.println();
		System.out.println((opciones.length+1)+". Retroceder");
		System.out.println();
			int in=input.leerInt("el foro a acceder o "+(opciones.length+1)+" para retroceder",1,opciones.length+1);
			if(in==opciones.length+1) {
				mostrarCursoMenu(); 
			}
			else {
			foro_id=opciones[in-1];
			mostrarForoMenu();
			}
	}
	public void mostrarForoMenu() throws SQLException, ParseException {
		cls();
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuela: "+DataBase.buscarValorDeCampoSegunID("escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras: "+DataBase.buscarValorDeCampoSegunID("carreras","id", carrera_id, "nombre"));
		System.out.println("Cursos: "+DataBase.buscarValorDeCampoSegunID("cursos","id", curso_id, "nombre"));
		System.out.println("Profesor: "+prof_name +" "+prof_lname);
		System.out.println("Foro "+DataBase.buscarValorDeCampoSegunID("foros","id", foro_id, "nombre"));
		System.out.println();
		System.out.println();
		System.out.println("1. Ver Preguntas del foro\n2. Hacer Pregunta\n3. Retroceder");
		int option = this.input.leerInt("opcion deseada", 1, 2);
		switch (option) {
		case 1:
			mostrarPreguntas();
			break;
		case 2:
			crearPregunta();
			break;
		case 3:
			mostrarForos();
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
	public void mostrarPreguntas() throws SQLException, ParseException {
		cls();
		pregunta_id=0;
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuela: "+DataBase.buscarValorDeCampoSegunID("escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras: "+DataBase.buscarValorDeCampoSegunID("carreras","id", carrera_id, "nombre"));
		System.out.println("Cursos: "+DataBase.buscarValorDeCampoSegunID("cursos","id", curso_id, "nombre"));
		System.out.println("Profesor: "+prof_name +" "+prof_lname);
		System.out.println("Foro: "+DataBase.buscarValorDeCampoSegunID("foros","id", foro_id, "nombre"));
		System.out.println("Preguntas");
		System.out.println();
		System.out.println();
		/*String encabezado = "id          contenido";
		String separador = "-----------------------------------------------------------------------------";
		System.out.println(encabezado + "\n" + separador);*/
		pregunta_id = SimulacionUtilities.mostrarPreguntas(Integer.toString(foro_id));
		if(pregunta_id==-1) {
			pregunta_id=0;
			mostrarForoMenu();
		}
		else {
		mostrarPreguntaMenu();}
	}
	public void mostrarPreguntaMenu() throws SQLException, ParseException {
		cls();
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuela: "+DataBase.buscarValorDeCampoSegunID("escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras: "+DataBase.buscarValorDeCampoSegunID("carreras","id", carrera_id, "nombre"));
		System.out.println("Cursos: "+DataBase.buscarValorDeCampoSegunID("cursos","id", curso_id, "nombre"));
		System.out.println("Profesor: "+prof_name +" "+prof_lname);
		System.out.println("Foro: "+DataBase.buscarValorDeCampoSegunID("foros","id", foro_id, "nombre"));
		System.out.println("Pregunta: "+DataBase.buscarValorDeCampoSegunID("preguntas","id", pregunta_id, "contenido"));
		System.out.println();
		System.out.println();
		System.out.println("1. Ver Respuestas a la pregunta\n2. Responder Pregunta\n3. Retroceder");
		int option = this.input.leerInt("opcion deseada", 1, 3);
		System.out.println("\n\n\n");
		switch (option) {
		case 1:
			mostrarRespuestas();
			break;
		case 2:
			crearRespuesta();
			break;
		case 3:
			mostrarPreguntas();
			break;
		}
	}
	public void mostrarRespuestas() throws SQLException, ParseException {
		cls();
		respuesta_id=0;
		meGusta=false;
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuela: "+DataBase.buscarValorDeCampoSegunID("escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras: "+DataBase.buscarValorDeCampoSegunID("carreras","id", carrera_id, "nombre"));
		System.out.println("Cursos: "+DataBase.buscarValorDeCampoSegunID("cursos","id", curso_id, "nombre"));
		System.out.println("Profesor: "+prof_name +" "+prof_lname);
		System.out.println("Foro: "+DataBase.buscarValorDeCampoSegunID("foros","id", foro_id, "nombre"));
		System.out.println("Pregunta: "+DataBase.buscarValorDeCampoSegunID("preguntas","id", pregunta_id, "contenido"));
		System.out.println("Respuestas ");
		System.out.println();
		System.out.println();
		String encabezado = "";
		String separador = "";
		int espacios = 108;
		for (int i = 0; i < espacios; i++) {
			if (i==0) {encabezado+=("id");}
			if (i>="id".length() && i<espacios/3) {encabezado+=(" ");}
			if (i==espacios/3) {encabezado+=("contenido");}
			if (i>=(espacios/3)+"contenido".length() && i<espacios*2/3) {encabezado+=(" ");}
			if (i==espacios*2/3) {encabezado+=("puntuacion");}
			separador+="-";
		}
		System.out.println(encabezado);
		System.out.println(separador);
		// Se muestran las respuestas de la pregunta
		respuesta_id=SimulacionUtilities.mostrarRespuestas(pregunta_id, espacios);
		System.out.println(respuesta_id);
		if(respuesta_id==-1) {
			respuesta_id=0;
			mostrarPreguntaMenu();
		}
		else {
			mostrarMenuRespuesta();
		}

	}
	public void mostrarMenuRespuesta() throws ParseException, SQLException {
		cls();
		System.out.println("Plan: "+DataBase.buscarValorDeCampoSegunID("planes","id", plan_id, "id"));
		System.out.println("Escuela: "+DataBase.buscarValorDeCampoSegunID("escuelas","id", escuela_id, "nombre"));
		System.out.println("Carreras: "+DataBase.buscarValorDeCampoSegunID("carreras","id", carrera_id, "nombre"));
		System.out.println("Cursos: "+DataBase.buscarValorDeCampoSegunID("cursos","id", curso_id, "nombre"));
		System.out.println("Profesor: "+prof_name +" "+prof_lname);
		System.out.println("Foro: "+DataBase.buscarValorDeCampoSegunID("foros","id", foro_id, "nombre"));
		System.out.println("Pregunta: "+DataBase.buscarValorDeCampoSegunID("preguntas","id", pregunta_id, "contenido"));
		System.out.println("Respuesta "+DataBase.buscarValorDeCampoSegunID("respuestas","id", respuesta_id, "contenido") 
				  + "       Total Me gusta: "+DataBase.buscarValorDeCampoSegunID("respuestas","id", respuesta_id, "puntuacion"));
		System.out.println();
		String idr=DataBase.buscarValorDeCampoSegun2ID("ulike", "usuario_id", Integer.toString(usuarios_id), "respuesta_id",Integer.toString(respuesta_id),"respuesta_id").toString();
		if(idr.equals("") || idr.equals("0")) {
			meGusta=false;
			System.out.println("");
		}
		else {
			meGusta=true;
			System.out.println("Te gusta esta respuesta");
		}
		System.out.println();
		System.out.println("1. Dar me gusta a la Respuesta\n2. Quitar me gusta\n3. Retroceder");
		int option = this.input.leerInt("opcion deseada", 1, 3);
		switch (option) {
		case 1:
			if(meGusta==false) {
			darMegusta();
			break;
			}
			else {
			mostrarMenuRespuesta();
			break;}
		case 2:
			if(meGusta==true) {
				darNoMegusta();
				break;
				}
			else {
				mostrarMenuRespuesta();
				break;}
		case 3:
			mostrarRespuestas();
			break;
		}
	}
	public void darMegusta() throws ParseException {
		try {
				int puntuacionAnterior=Integer.parseInt(DataBase.buscarValorDeCampoSegunID("respuestas","id", respuesta_id, "puntuacion").toString());
				int puntNueva=puntuacionAnterior+1;
				Object dato=puntNueva;
				String respuesta_id_string=Integer.toString(respuesta_id);
				System.out.println(puntuacionAnterior);
				System.out.println(puntNueva);
				DataBase.ModificarRegistroUnico("respuestas", "id", "id", respuesta_id_string, "puntuacion", dato);
				
				Object datos[]=new Object[1];
				datos[0]=respuesta_id;
				DataBase.AgregarRegistroCONDatos("usuario_id", usuarios_id, "Ulike", "usuario_id", datos);
				meGusta=true;
			
			mostrarMenuRespuesta();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void darNoMegusta() throws ParseException {
		try {
			int puntuacionAnterior=Integer.parseInt(DataBase.buscarValorDeCampoSegunID("respuestas","id", respuesta_id, "puntuacion").toString());
			int puntNueva=puntuacionAnterior-1;
			Object dato=puntNueva;
			String respuesta_id_string=Integer.toString(respuesta_id);
			DataBase.ModificarRegistroUnico("respuestas", "id", "id", respuesta_id_string, "puntuacion", dato);
			String uid=Integer.toString(usuarios_id);
			String rid=Integer.toString(respuesta_id);
			System.out.println(usuarios_id);
			System.out.println(rid);
			DataBase.EliminarRegistroBuscando2campos("ulike", "usuario_id",uid,"usuario_id", uid, "respuesta_id", rid);
			meGusta=false;
			mostrarMenuRespuesta();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	public void crearRespuesta() throws SQLException, ParseException {
		// Se conecta con tabla respuestas, preguntas, usuario
		SimulacionUtilities.crearRespuesta(usuarios_id, pregunta_id);
		mostrarPreguntas();
	}
	public void crearPregunta() throws SQLException, ParseException {
		// Se conecta con tabla usuarios, foro, pregunta
		SimulacionUtilities.crearPregunta(usuarios_id, foro_id);
		mostrarForoMenu();
	}
	
	
	

	public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
		Menus menu = new Menus();
		
		//String cred[]=menu.MenuUsuarioContrasenaBD();
		String cred[]=new String[2];
		/*cred[0]="santiago";
		cred[1]="asdf";*/
		cred[0]="root";
		cred[1]="holahola123";
		DataBase.username=cred[0];
		DataBase.password=cred[1];
		
		Simulacion s = new Simulacion();
		DataBase.Connect("Nisand");
		s.ingreso();
	}

}
