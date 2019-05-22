import java.sql.SQLException;

public class SimulacionUtilities {
	static Entradas inputSim = new Entradas();
	
	
	public static void crearPregunta(int usuario_id, int foro_id) {
		String pregunta = inputSim.leerString("Pregunta");
		DataBase.accederATabla("Preguntas");
		Object[] datos = new Object[] {foro_id,usuario_id,pregunta};
		String[] namess = new String[] {"foro_id","usuario_id","contenido"};
		String[] typess =  new String[] {"INT", "INT", "TEXT"};
		try {
			DataBase.AgregarRegistro(datos, namess, typess);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void crearRespuesta(int usuario_id, int pregunta_id) {
		String respuesta = inputSim.leerString("Respuesta ");
		DataBase.accederATabla("Respuestas");
		Object[] datos = new Object[] {pregunta_id,usuario_id,respuesta, 0};
		String[] namess = new String[] {"pregunta_id","usuario_id","contenido", "puntuacion"};
		String[] typess =  new String[] {"INT", "INT", "TEXT", "INT"};
		try {
			DataBase.AgregarRegistro(datos, namess, typess);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void mostrarRespuestas(int pregunta_id) {
		try {
			DataBase.buscarExistente("pregunta_id", Integer.toString(pregunta_id));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
