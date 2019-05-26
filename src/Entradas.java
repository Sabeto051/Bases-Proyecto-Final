import java.io.Console;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Entradas {
	static Scanner reader=new Scanner(System.in);
	public int leerInt(String valor, int min, int max) {
		boolean error=true;
		int a=-1;
		while(error==true) {
		try {
			error=false;
			reader=new Scanner(System.in);
			System.out.print("Ingrese "+valor+" ");
			a=Integer.parseInt(reader.nextLine());
			System.out.println();
			if((a<min || a>max)) {
				error=true;
				System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max);
			}
			while(error==true) {
				error=false;
				System.out.print("Ingrese "+valor+" ");
				a=Integer.parseInt(reader.nextLine());
				System.out.println();
				if(a<min || a>max) {
					error=true;
					System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max+" y debe ser de tipo entero");
					System.out.println();
				}
			}
		}
		catch(InputMismatchException | NumberFormatException e) {
			System.out.println("Hubo un error. Valor Ingresado debe ser entero");
			error=true;
			System.out.println();
		}
		}
		return a;
	}
	public double leerDouble(String valor, double min, double max) {
		boolean error=true;
		double a=-1;
		while(error==true) {
		try {
			error=false;
			reader=new Scanner(System.in);
			System.out.print("Ingrese "+valor+" ");
			a=Double.parseDouble(reader.nextLine());
			//float a=(float)reader.nextfloat();
			System.out.println();
			if((a<min || a>max)) {
				error=true;
				System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max);
			}
			while(error==true) {
				error=false;
				System.out.print("Ingrese "+valor+" ");
				a=Double.parseDouble(reader.nextLine());
				System.out.println();
				if(a<min || a>max) {
					error=true;
					System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max+" y debe ser de tipo float");
					System.out.println();
				}
			}
		}
		catch(InputMismatchException | NumberFormatException e) {
			System.out.println("Hubo un error. Valor Ingresado debe ser float");
			error=true;
			System.out.println();
		}
		}
		return a;

	}
	public float leerfloat(String valor, float min, float max) {
		boolean error=true;
		float a=-1;
		while(error==true) {
		try {
			error=false;
			reader=new Scanner(System.in);
			System.out.print("Ingrese "+valor+" ");
			a=Float.parseFloat(reader.nextLine());
			//float a=(float)reader.nextfloat();
			System.out.println();
			if((a<min || a>max)) {
				error=true;
				System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max);
			}
			while(error==true) {
				error=false;
				System.out.print("Ingrese "+valor+" ");
				a=Float.parseFloat(reader.nextLine());
				System.out.println();
				if(a<min || a>max) {
					error=true;
					System.out.println("Hubo un error. Opcion debe estar entre "+min+" y "+max+" y debe ser de tipo float");
					System.out.println();
				}
			}
		}
		catch(InputMismatchException | NumberFormatException e) {
			System.out.println("Hubo un error. Valor Ingresado debe ser float");
			error=true;
			System.out.println();
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
	




