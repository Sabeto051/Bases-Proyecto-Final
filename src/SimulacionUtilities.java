import java.sql.SQLException;
import java.util.Arrays;



public class SimulacionUtilities {
	static Entradas inputSim = new Entradas();
	
	
	public static void crearPregunta(int usuario_id, int foro_id) throws SQLException {
		String pregunta = inputSim.leerString("Pregunta");
		DataBase.accederATabla("Preguntas");
		Object[] datos = new Object[] {foro_id,usuario_id,pregunta};
		String[] namess = new String[] {"foro_id","usuario_id","contenido"};
		String[] typess =  new String[] {"INT", "INT", "TEXT"};
		try {
			DataBase.AgregarRegistro("Preguntas","");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void crearRespuesta(int usuario_id, int pregunta_id) throws SQLException {
		String respuesta = inputSim.leerString("Respuesta ");
		DataBase.accederATabla("Respuestas");
		Object[] datos = new Object[] {pregunta_id,usuario_id,respuesta, 0};
		String[] namess = new String[] {"pregunta_id","usuario_id","contenido", "puntuacion"};
		String[] typess =  new String[] {"INT", "INT", "TEXT", "INT"};
		try {
			DataBase.AgregarRegistro("Respuestas","");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int  mostrarRespuestas(int pregunta_id, int espacioss) {
		int respuesta = -1;
		DataBase.accederATabla("respuestas");
		try {
			int[] ids = DataBase.buscarExistenteArray("respuestas","id", "pregunta_id", Integer.toString(pregunta_id));

			String[] contenido = new String[ids.length];
			String[] puntuacion = new String[ids.length];
			for (int i=0; i<ids.length;i++) {
				contenido[i] = DataBase.buscarValorDeCampoSegunID("respuestas", "id", ids[i], "contenido").toString();
				puntuacion[i] = DataBase.buscarValorDeCampoSegunID("respuestas", "id", ids[i], "puntuacion").toString();

				String id = Integer.toString(ids[i]);
				int espacios = espacioss;
				for (int j = 0; j < espacios; j++) {
					if (j<espacios/3) {
						if (j==0) {
							System.out.print(id);
						}
						if (j>id.length()-1) {
							System.out.print(" ");
						}
					}
					if (j>=espacios/3 && j<espacios*2/3) {
						if (j==espacios/3) {
							System.out.print(contenido[i]);
						}
						if (j>contenido[i].length()-1 + espacios/3) {
							System.out.print(" ");
						}
					}

					if (j==espacios*2/3) {
						System.out.print(puntuacion[i]);
					}

				}
				System.out.println("");
			}

			respuesta = inputSim.leerIntConArray("Respuesta", ids);
			if(respuesta==ids.length+1) {
				respuesta=-1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return respuesta;
	}
	
	public static int mostrarPreguntas(int foro_id) {
		int respuesta = -1;
		DataBase.accederATabla("preguntas");
		try {
			int[] ids = DataBase.buscarExistenteArray("preguntas","id", "foro_id", Integer.toString(foro_id));
//			DataBase.mostrarIDSconCampo("preguntas", "id", ids, "contenido");
			String[] contenido = new String[ids.length];
			for (int i=0; i<ids.length;i++) {
				contenido[i] = DataBase.buscarValorDeCampoSegunID("respuestas", "id", ids[i], "contenido").toString();
				String id = Integer.toString(ids[i]);
				
				for (int j =0;j<12;j++) {
					if (j==0) {
						System.out.print(id);
					}
					if (j>=0+id.length()) {
						System.out.print(" ");
					}
				}
				System.out.println(contenido[i]+"\n");
			}

			respuesta = inputSim.leerIntConArray("Pregunta", ids);
			if(respuesta==ids.length+1) {
				respuesta=-1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return respuesta;
	}
	
	
}
