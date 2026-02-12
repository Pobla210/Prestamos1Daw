package libreriaPrestamo;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        int opcion;
        do {
            System.out.println("1.Registrar nuevo usuario"+
                    "\n2.Realizar prestamo libro"+
                    "\n3.Devolver libro"+
                    "\n4.Consultar estado de usuario"+
                    "\n5.Mostrar prestamos activos"+
                    "\n6.Mostrar usuarios sancionados"+
                    "\n7.Actualizar sanciones"+
                    "\n8.Salir");

            opcion=in.nextInt();

            switch (opcion){
                case 1:

                    break;
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    System.out.println("Saliendo...");
                    break;
            }

        }while (opcion!=8);
    }
}
