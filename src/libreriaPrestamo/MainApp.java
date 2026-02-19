package libreriaPrestamo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) throws FormatoInvalidoException {
        Scanner in = new Scanner(System.in);
        GestionarBiblioteca gestor = new GestionarBiblioteca();
        int opcion;
        do {
            System.out.println("1.Registrar nuevo usuario" +
                    "\n2.Realizar prestamo libro" +
                    "\n3.Devolver libro" +
                    "\n4.Consultar estado de usuario" +
                    "\n5.Mostrar prestamos activos" +
                    "\n6.Mostrar usuarios sancionados" +
                    "\n7.Actualizar sanciones" +
                    "\n8.Salir");

            opcion = in.nextInt();

            switch (opcion) {
                case 1:
                    try {
                        registrarUsuario(in, gestor);
                    } catch (UsuarioInvalidoExcepcion ue) {
                        System.out.println(ue.getMessage());
                    } catch (FormatoInvalidoException fe) {
                        System.out.println(fe.getMessage());
                    } catch (UsuarioRepetidoException ure) {
                        System.out.println(ure.getMessage());
                    }
                    break;

                case 2:
                    try {
                        registrarPrestamo(in, gestor);
                    } catch (UsuarioInvalidoExcepcion ue) {
                        System.out.println(ue.getMessage());
                    } catch (FormatoInvalidoException fe) {
                        System.out.println(fe.getMessage());
                    } catch (PrestamoInvalidoException pie) {
                        System.out.println(pie.getMessage());
                    } catch (UsuarioSancionadoException use) {
                        System.out.println(use.getMessage());
                    } catch (LibroNoDisponibleException lnde) {
                        System.out.println(lnde.getMessage());
                    }
                    break;
                case 3:
                    try {
                        devolverLibro(in, gestor);
                    } catch (FormatoInvalidoException fe) {
                        System.out.println(fe.getMessage());
                    } catch (PrestamoInvalidoException pie) {
                        System.out.println(pie.getMessage());
                    } catch (UsuarioSancionadoException use) {
                        System.out.println(use.getMessage());
                    }
                    break;
                case 4:
                    try {
                        consultarEstadoUsuario(in, gestor);
                    } catch (UsuarioInvalidoExcepcion uie) {
                        System.out.println(uie.getMessage());
                    }
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    System.out.println("Saliendo...");
                    break;
            }

        } while (opcion != 8);
    }

    public static void registrarUsuario(Scanner in, GestionarBiblioteca gb) throws FormatoInvalidoException, UsuarioRepetidoException, UsuarioInvalidoExcepcion {
        String[] fechas;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Introduce tu nombre: ");
        String nombre = in.nextLine();

        System.out.println("Introduce tu email: ");
        String email = in.nextLine();

        System.out.println("Introduce tu numero de socio (SOC00000)");
        String numeroSocio = in.nextLine();

        System.out.println("Introduce la fecha de registro (dd/MM/yyyy): ");
        String fecha = in.nextLine();
        LocalDate fechaRegistro = null;

        if (fecha.matches("^([1-9]|0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
            fechas = fecha.split("/");
            fechaRegistro = LocalDate.of(Integer.parseInt(fechas[2]), Integer.parseInt(fechas[1]), Integer.parseInt(fechas[0]));
        } else {
            throw new FormatoInvalidoException("La fecha introducida no es valida");
        }

        Usuario usuario = new Usuario(nombre, email, numeroSocio, fechaRegistro);
        System.out.println("Usuario creado correctamente");

        gb.registrarUsuario(usuario);
    }

    public static void registrarPrestamo(Scanner in, GestionarBiblioteca gb) throws PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException, FormatoInvalidoException, UsuarioInvalidoExcepcion {
        String[] fechas;
        Usuario socio;
        System.out.println("Introduce el codigo del libro: ");
        String codigoLibro = in.nextLine();

        System.out.println("Introduce el titulo del libro: ");
        String tituloLibro = in.nextLine();

        System.out.println("Introduce la fecha del prestamo: ");
        String fecha = in.nextLine();
        LocalDate fechaRegistro = null;

        if (fecha.matches("^([1-9]|0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
            fechas = fecha.split("/");
            fechaRegistro = LocalDate.of(Integer.parseInt(fechas[2]), Integer.parseInt(fechas[1]), Integer.parseInt(fechas[0]));
        } else {
            throw new FormatoInvalidoException("La fecha introducida no es valida");
        }

        System.out.println("Introduce tu numero de socio: ");
        String numSocio = in.nextLine();

        if (numSocio == null) {
            throw new UsuarioInvalidoExcepcion("Socio no encontrado");
        } else {
            socio = gb.buscarUsuario(numSocio);
        }

        System.out.println("Prestamo realizado correctamente");

        gb.realizarPrestamo(codigoLibro, tituloLibro, fechaRegistro, socio);
    }

    public static String devolverLibro(Scanner in, GestionarBiblioteca gb) throws PrestamoInvalidoException, UsuarioSancionadoException, FormatoInvalidoException {
        String[] fechas;
        System.out.println("Introduce el codigo del libro que quieres devolver: ");
        String codigoLibro = in.nextLine();

        System.out.println("Dime el dia de la devolucion: ");
        String fecha = in.nextLine();
        LocalDate fechaDevolucion = null;

        if (fecha.matches("^([1-9]|0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$")) {
            fechas = fecha.split("/");
            fechaDevolucion = LocalDate.of(Integer.parseInt(fechas[2]), Integer.parseInt(fechas[1]), Integer.parseInt(fechas[0]));
        } else {
            throw new FormatoInvalidoException("La fecha introducida no es valida");
        }
        return gb.devolverLibro(codigoLibro, fechaDevolucion) ? "Libro devuelto correctamente" : "Codigo del libro invalido";

    }

    public static String consultarEstadoUsuario(Scanner in, GestionarBiblioteca gb) throws UsuarioInvalidoExcepcion {
        System.out.println("Introduce el nombre del socio: ");
        String socio = in.nextLine();

        Usuario sociocomprobar = gb.buscarUsuario(socio);

        if (gb.buscarUsuario(socio) == null) {
            throw new UsuarioInvalidoExcepcion("El usuario buscado no existe.");
        }
        if (sociocomprobar.estaSancionado() == true) {
            return sociocomprobar.toString() + "\nEl socio se encuntra sancionado actualmente";
        } else {
            return sociocomprobar.toString() + "\nEl socio no se encuntra sancionado actualmente";
        }
    }


    public static String mostrarPrestamosActivos(Scanner in, GestionarBiblioteca gb) throws PrestamoInvalidoException {
        System.out.println("Dime el nombre del socio");
        String socio=in.nextLine();

    }
}



