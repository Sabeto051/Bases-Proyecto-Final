import java.io.IOException;
import java.sql.*;
import java.util.Properties;

import javax.swing.*;

import java.awt.*;



public class Proyecto { 
	final static String esc = "\033[";
	public static void main(String[] args) throws IOException, InterruptedException, SQLException, ClassNotFoundException { 
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
}
