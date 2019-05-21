
public class Simulacion {
	private Entradas input;
	private int telefono;
	private int plan;
	public Simulacion() {
		this.input = new Entradas();
	}

	public void ingreso() {
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

	public void loginAdmin() {
		// Conecta tabla de usuarios
		// Hagamos que el user sea el usuario con ID 1 - Lo creamos a mano, no por la app

	}
	public void registerUser() {
		// Conecta con la tabla de usuarios
		// Crea un nuevo usuario con Plan (porque es obligatiorio)
		this.telefono = this.input.leerInt("Telefono", 0, 1000000000);
		String pwd = this.input.leerString("Contrasena");
		// Se muestran los planes
		this.input.leerString("Numero del plan");
		mostrarPlanUser();
	}
	public void loginUser() {
		// Conecta con la tabla de usuarios
		// pide número y contraseña
		this.telefono = this.input.leerInt("Telefono", 0, 1000000000);
		String pwd = this.input.leerString("Contrasena");
		mostrarPlanUser();
	}

	public void mostrarPlanUser() {
		// Se conecta la tabla Planes
		// Se conecta la tabla Usuarios-Planes
		System.out.println("Hola INSERTE NOMBRE AQUI");
		System.out.println("Su plan acaba el INSERTE FECHA FINAL AQUI");
		System.out.println("1. Comprar nuevo Plan\n2. Acceder a Escuelas\n3. Ver Cursos completados");
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
		}

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
		this.input.leerString("Cualquier tecla para ver Video\n\n\n");
		verVideo();
	}
	public void verVideo() {
		// se conecta la tabla videos y Estudiante-videos
		// se agrega el video a videos vistos por el estudiante
		System.out.println("Video visto");
		this.input.leerString("Cualquier tecla para retroceder Curso\n\n\n");
		mostrarVideos();
	}
	public void mostrarForos() {
		// se conecta con la tabla foros
		// se muestran los foros del curso
		this.input.leerString("Cualquier tecla para escoger Foro\n\n\n");
		
	}

	public static void main(String[] args) {
		Simulacion s = new Simulacion();

		s.ingreso();
	}

}
