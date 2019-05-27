import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;



public class SimulacionUtilities {
	static Entradas inputSim = new Entradas();
	
	
	public static int crearPregunta(int usuario_id, int foro_id) throws SQLException {
		String pregunta = inputSim.leerString("Pregunta");
		DataBase.accederATabla("Preguntas");
		int id=0;
		try {
			Object datos[]=new Object[4];
			datos[0]=foro_id;
			datos[1]=usuario_id;
			datos[2]=pregunta;
			Calendar c = Calendar.getInstance();
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			Date date22=c.getTime();
			String fechaHoy=dateFormat1.format(date22);
			datos[3]=fechaHoy;
			id=DataBase.AgregarRegistroCONDatos("id", "preguntas", "id", datos);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
		
	}
	
	public static int crearRespuesta(int usuario_id, int pregunta_id) throws SQLException {
		String respuesta = inputSim.leerString("Respuesta");
		DataBase.accederATabla("Respuestas");
		int id=0;
		try {
			Object datos[]=new Object[5];
			datos[0]=pregunta_id;
			datos[1]=usuario_id;
			datos[2]=respuesta;
			Calendar c = Calendar.getInstance();
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
			Date date22=c.getTime();
			String fechaHoy=dateFormat1.format(date22);
			datos[3]=fechaHoy;
			datos[4]=0;
			id=DataBase.AgregarRegistroCONDatos("id", "respuestas", "id", datos);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	//public static int  mostrarRespuestas(int pregunta_id, int espacioss) {
	public static int  mostrarRespuestas(int pregunta_id) {
		int respuesta = -1;
		DataBase.accederATabla("respuestas");
		try {
			/*int[] ids = DataBase.buscarExistenteArray("respuestas","id", "pregunta_id", Integer.toString(pregunta_id));

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
*/
			String atributos[]=new String[1];
			atributos[0]="contenido";
			int pregIDs[]=DataBase.mostrarTablaSegunCriterio2("contenido","respuestas",atributos,"id","pregunta_id",Integer.toString(pregunta_id),"numerado","no");
			if(pregIDs[0]==-1 || pregIDs[0]==0) {
				return -1;
			}
			respuesta=inputSim.leerInt("la respuesta a escoger o "+(pregIDs.length+1) +" para retroceder", 1, pregIDs.length+1);
			if(respuesta==pregIDs.length+1) {
				respuesta=-1;
				return respuesta;
			}
			else {
			return pregIDs[respuesta-1];
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return respuesta;
	}
	
	public static int mostrarPreguntas(int foro_id) {
		DataBase.accederATabla("preguntas");
		int pregunta=0;
		try {
			/*int[] ids = DataBase.buscarExistenteArray("preguntas","id", "foro_id", Integer.toString(foro_id));
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
			}*/
			String atributos[]=new String[1];
			atributos[0]="contenido";
			int pregIDs[]=DataBase.mostrarTablaSegunCriterio2("contenido","preguntas",atributos,"id","foro_id", Integer.toString(foro_id),"numerado","no");
			if(pregIDs[0]==-1 || pregIDs[0]==0) {
				return -1;
			}
			pregunta=inputSim.leerInt("la pregunta a escoger o "+(pregIDs.length+1) +" para retroceder", 1, pregIDs.length+1);
			if(pregunta==pregIDs.length+1) {
				pregunta=-1;
				return pregunta;
			}
			else {
			return pregIDs[pregunta-1];
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pregunta;
	}
	
	
}
