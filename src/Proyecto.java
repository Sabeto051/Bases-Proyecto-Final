import java.io.IOException;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane; 


public class Proyecto { 
	final static String esc = "\033[";
	public static void main(String[] args) throws IOException, InterruptedException { 
	Accesos Acces=new Accesos();
	JFrame frame = new JFrame("Exiting");
	int opcion=Acces.Ingreso();
	if(opcion==-1) {
		JOptionPane.showMessageDialog(frame,
		        "Adios",
		        "   Exiting",
		        JOptionPane.INFORMATION_MESSAGE);
	}

	
		
	}
	
	public static void cls()
	{
		for(int i=0;i<10;i++) {
			System.out.println();
		}
	}
}
