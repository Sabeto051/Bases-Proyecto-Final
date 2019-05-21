
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
		System.out.println("1. Comprar nuevo Plan\n2. Acceder a Escuelas\n Ver Cursos completados");
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
		mostrarPlanUser();
	}
	public void crearPlan() {
		// Se conecta la tabla Planes
		// Se conecta la tabla Planes-Escuela

		// Se muestran planes disponibles
		// Se escoge uno de los planes disponibles
		mostrarPlanUser();
	}
	public void mostrarEscuelas() {
		// Se conecta con la tabla Escuelas
		// Se conecta la tabla Planes-Escuela (Dijimos que no todas las escuelas están en todos los planes)
		
		// se muestran las escuelas
		// se escoge una escuela
		mostrarCarreras(); // de la escuela
	}

	public void mostrarCarreras() {
		// se muestran las carreras de la escuela
		System.out.println("Escuela INSERTE NOMBRE AQUI");
		// se escoge una carrera
		mostrarCursos(); // de la carrera
	}
	public void mostrarCursos() {
		// se conecta con tabla cursos
		System.out.println("Carrera INSERTE NOMBRE AQUI");
		// se muestran los cursos y se escoge uno
		mostrarCursoMenu(); // del seleccionado
	}
	
	public void mostrarCursoMenu() {
		System.out.println("Curso INSERTE NOMBRE AQUI");
		System.out.println("Profesor INSERTE NOMBRE AQUI");
		System.out.println("1. Acceder a los foros del curso\n2. Acceder a los xvideos");
		int option = this.input.leerInt("opcion deseada", 1, 2);
		switch (option) {
		case 1:
			mostrarVideos();
			break;
		case 2:
			mostrarForos();
			break;
		}
	}
	
	public void mostrarVideos() {
	
	}
	public void mostrarForos() {
		
	}

	public static void main(String[] args) {
		Simulacion s = new Simulacion();

		s.ingreso();
	}

}
