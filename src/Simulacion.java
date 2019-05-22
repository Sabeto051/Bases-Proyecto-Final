import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Simulacion {
	private Entradas input;
	private int usuarios_id;
	private int foros_id;
	private int preguntas_id;
	public Simulacion() {
		this.input = new Entradas();
	}

	public void ingreso() throws ClassNotFoundException, SQLException {
		System.out.println("Bienvenido a Nisand");
		System.out.println("1. Register\n2. Login\n3. Login Admin");
		int option = this.input.leerInt("opcion deseada", 1, 3);
		System.out.println(option);
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
	public void registerUser() {
		// Conecta con la tabla de usuarios
		DataBase.accederATabla("Usuario");
		// Crea un nuevo usuario con Plan (porque es obligatiorio)
		
		telefono = this.input.leerInt("Telefono", 0, 1000000000);
		this.input.leerString("Contrasena");
		// Se muestran los planes
		this.input.leerString("Numero del plan");
		mostrarPlanUser();
	}
	public void loginUser() {
		// Conecta con la tabla de usuarios
		// pide número y contraseña
		this.telefono = this.input.leerInt("Telefono", 0, 1000000000);
		this.input.leerString("Contrasena");
		mostrarPlanUser();
	}

	public void mostrarPlanUser() {
		// Se conecta la tabla Planes
		// Se conecta la tabla Usuarios-Planes
		System.out.println("Hola INSERTE NOMBRE AQUI");
		System.out.println("Su plan acaba el INSERTE FECHA FINAL AQUI");
		System.out.println("1. Comprar nuevo Plan\n2. Acceder a Escuelas\n3. Ver Cursos completados\n"
				+ "4. Ver preguntas hechas");
		int option = this.input.leerInt("opcion deseada", 1, 3);
		switch (option) {
		case 1:
			crearPlan();
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
	
	public void mostrarCursosCompletados() {
		// Se conecta con tabla Cursos , Usuarios y  Estudaintes-Cursos
		// Se muestran los cursos completados
		this.input.leerString("Cualquier tecla para retroceder\n\n\n");
		mostrarPlanUser();
	}
	public void crearPlan() {
		// Se conecta la tabla Planes
		// Se conecta la tabla Planes-Escuela

		// Se muestran planes disponibles
		// Se escoge uno de los planes disponibles
		this.input.leerString("Cualquier tecla para escoger plan\n\n\n");
		mostrarPlanUser();
	}
	public void mostrarEscuelas() {
		// Se conecta con la tabla Escuelas
		// Se conecta la tabla Planes-Escuela (Dijimos que no todas las escuelas están en todos los planes)

		// se muestran las escuelas
		// se escoge una escuela
		this.input.leerString("Cualquier tecla para escoger escuela\n\n\n");
		mostrarCarreras(); // de la escuela
	}

	public void mostrarCarreras() {
		// se muestran las carreras de la escuela
		System.out.println("Escuela INSERTE NOMBRE AQUI");
		// se escoge una carrera
		this.input.leerString("Cualquier tecla para escoger Carrera\n\n\n");
		mostrarCursos(); // de la carrera
	}
	public void mostrarCursos() {
		// se conecta con tabla cursos
		System.out.println("Carrera INSERTE NOMBRE AQUI");
		// se muestran los cursos y se escoge uno
		this.input.leerString("Cualquier tecla para escoger Curso\n\n\n");
		mostrarCursoMenu(); // del seleccionado
	}



	public void mostrarCursoMenu() {
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
	public void mostrarForos() {
		System.out.println("Curso INSERTE NOMBRE AQUI");
		// se conecta con la tabla foros
		// se muestran los foros del curso
		this.input.leerString("Cualquier tecla para escoger Foro\n\n\n");
		mostrarForoMenu();
	}

	public void mostrarForoMenu() {
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

	public void mostrarPreguntas() {
		// Se conecta con tablas preguntas 
		// Se muestran las preguntas del j
		this.input.leerString("Cualquier tecla para escoger Pregunta\n\n\n");
		mostrarPreguntaMenu();
	}
	public void mostrarPreguntaMenu() {
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
	public void mostrarRespuestas() {
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

	public void crearRespuesta() {
		// Se conecta con tabla respuestas, preguntas, usuario
		this.input.leerString("Cualquier tecla para Responder\n\n\n");
		SimulacionUtilities.crearRespuesta(usuarios_id, preguntas_id);
		mostrarPreguntas();
	}
	public void crearPregunta() {
		// Se conecta con tabla usuarios, foro, pregunta
		SimulacionUtilities.crearPregunta(usuarios_id, foros_id);
		mostrarForoMenu();
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Simulacion s = new Simulacion();
		DataBase.Connect("nisand");
		s.ingreso();
	}

}
