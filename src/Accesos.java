
public class Accesos {
private Entradas inputs=new Entradas();
private Menus menus=new Menus();
	
public int Ingreso() {
menus.MenuPrincipal();
int min=1;
int max=2;
int opcion=inputs.leerInt("la opcion deseada",min,max);
if(opcion==max) {
	opcion=-1;
}
return opcion;
}
}
