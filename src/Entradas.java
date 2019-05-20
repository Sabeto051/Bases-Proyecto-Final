import java.io.Console;
import java.io.IOException;
import java.util.Scanner;

public class Entradas {
	static Scanner reader=new Scanner(System.in);
public int leerInt(String valor, int min, int max) {
	reader=new Scanner(System.in);
	boolean error=false;
	System.out.print("Ingrese "+valor+" ");
	int a=reader.nextInt();
	System.out.println();
	if((a<min || a>max)) {
	error=true;
	System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max);
	}
	while(error==true) {
		error=false;
		System.out.print("Ingrese "+valor+" ");
		a=reader.nextInt();
		System.out.println();
		if(a<min || a>max) {
		error=true;
		System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max);
		}
	}
	return a;
}
public double leerDouble(String valor, double min, double max) {
	reader=new Scanner(System.in);
	boolean error=false;
	System.out.print("Ingrese "+valor+" ");
	double a=reader.nextDouble();
	System.out.println();
	if((a<min || a>max)) {
	error=true;
	System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max);
	}
	while(error==true) {
		error=false;
		System.out.print("Ingrese "+valor+" ");
		a=reader.nextDouble();
		System.out.println();
		if(a<min || a>max) {
		error=true;
		System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max);
		}
	}
	return a;
}
public String leerString(String valor) {
	reader=new Scanner(System.in);
	System.out.print("Ingrese "+valor+" ");
	String a="";
	a= reader.nextLine();
	return a;
}



	

}
