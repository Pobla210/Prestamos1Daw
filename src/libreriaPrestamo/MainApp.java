package libreriaPrestamo;

import java.time.DateTimeException;
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
            in.nextLine();

            switch (opcion) {
                case 1:
                    try {
                        registrarUsuario(in, gestor);
                    }
                    catch (UsuarioInvalidoExcepcion ue) {
                        System.out.println(ue.getMessage());
                    }
                    catch (FormatoInvalidoException fe) {
                        System.out.println(fe.getMessage());
                    }
                    catch (UsuarioRepetidoException ure) {
                        System.out.println(ure.getMessage());
                    }
                    catch (DateTimeException dte){
                        System.out.println("La fecha introducida no es valida");
                    }
                    break;

                case 2:
                    try {
                        registrarPrestamo(in, gestor);
                    }
                    catch (UsuarioInvalidoExcepcion ue) {
                        System.out.println(ue.getMessage());
                    }
                    catch (FormatoInvalidoException fe) {
                        System.out.println(fe.getMessage());
                    }
                    catch (DateTimeException dte){
                        System.out.println("La fecha introducida no es valida");
                    }
                    catch (PrestamoInvalidoException pie) {
                        System.out.println(pie.getMessage());
                    }
                    catch (UsuarioSancionadoException use) {
                        System.out.println(use.getMessage());
                    }
                    catch (LibroNoDisponibleException lnde) {
                        System.out.println(lnde.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println(devolverLibro(in, gestor));
                    }
                    catch (FormatoInvalidoException fe) {
                        System.out.println(fe.getMessage());
                    }
                    catch (PrestamoInvalidoException pie) {
                        System.out.println(pie.getMessage());
                    }
                    catch (UsuarioSancionadoException use) {
                        System.out.println(use.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.println(consultarEstadoUsuario(in, gestor));
                    }
                    catch (UsuarioInvalidoExcepcion uie) {
                        System.out.println(uie.getMessage());
                    }
                    break;
                case 5:
                    try {
                        System.out.println(mostrarPrestamosActivos(gestor));
                    }
                    catch (PrestamoInvalidoException pie) {
                        System.out.println(pie.getMessage());
                    }
                    break;
                case 6:
                    try {
                        System.out.println(mostrarUsuariosSancionados(gestor));
                    }
                    catch (UsuarioSancionadoException use) {
                        System.out.println(use.getMessage());
                    }
                    break;
                case 7:
                    try{
                        actualizarSancion(gestor);
                    }
                    catch (UsuarioSancionadoException use){
                        System.out.println(use.getMessage());
                    }
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
            }

        } while (opcion != 8);
    }

    public static void registrarUsuario(Scanner in, GestionarBiblioteca gb) throws FormatoInvalidoException, UsuarioRepetidoException, UsuarioInvalidoExcepcion, DateTimeException {
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

        if (fecha.matches("^([1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}$")) {
            fechas = fecha.split("/");
            fechaRegistro = LocalDate.of(Integer.parseInt(fechas[2]), Integer.parseInt(fechas[1]), Integer.parseInt(fechas[0]));

        } else {
            throw new FormatoInvalidoException("La fecha introducida no es valida");
        }

        Usuario usuario = new Usuario(nombre, email, numeroSocio, fechaRegistro);
        System.out.println("Usuario creado correctamente");

        gb.registrarUsuario(usuario);
    }

    public static void registrarPrestamo(Scanner in, GestionarBiblioteca gb) throws PrestamoInvalidoException, UsuarioSancionadoException, LibroNoDisponibleException, FormatoInvalidoException, UsuarioInvalidoExcepcion,DateTimeException {
        String[] fechas;
        Usuario socio;
        System.out.println("Introduce el codigo del libro (Ejemplo:ABC1234): ");
        String codigoLibro = in.nextLine();

        System.out.println("Introduce el titulo del libro: ");
        String tituloLibro = in.nextLine();

        System.out.println("Introduce la fecha del prestamo: ");
        String fecha = in.nextLine();
        LocalDate fechaRegistro = null;

        if (fecha.matches("^([1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}$")) {
            fechas = fecha.split("/");
            fechaRegistro = LocalDate.of(Integer.parseInt(fechas[2]), Integer.parseInt(fechas[1]), Integer.parseInt(fechas[0]));
        } else {
            throw new FormatoInvalidoException("La fecha introducida no es valida");
        }

        System.out.println("Introduce tu numero de socio: ");
        String numSocio = in.nextLine();

        if (gb.buscarUsuario(numSocio) == null) {
            throw new UsuarioInvalidoExcepcion("Socio no encontrado");
        }
        else {
            socio = gb.buscarUsuario(numSocio);
        }


        gb.realizarPrestamo(codigoLibro,tituloLibro,fechaRegistro,socio);
        System.out.println("Prestamo realizado correctamente");
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
        System.out.println("Introduce el numero del socio: ");
        String socio = in.nextLine().trim();

        Usuario sociocomprobar = gb.buscarUsuario(socio);

        if (gb.buscarUsuario(socio) == null) {
            throw new UsuarioInvalidoExcepcion("El usuario buscado no existe. Introduce su codigo.");
        }
        if (sociocomprobar.estaSancionado() == true) {
            return sociocomprobar.toString() + "\nEl socio se encuntra sancionado actualmente";
        } else {
            return sociocomprobar.toString() + "\nEl socio no se encuntra sancionado actualmente";
        }
    }

    public static String mostrarPrestamosActivos(GestionarBiblioteca gb) throws PrestamoInvalidoException {
        Prestamo[] todos = gb.getPrestamos();
        String todosPrestamos = "";
        boolean prestamosvalidos = false;
        for (int i = 0; i < gb.getPrestamos().length; i++) {
            Prestamo p = todos[i];
            if (p != null && p.getFechaDevolucionReal() == null) {
                todosPrestamos += p.toString() + "\n";
                prestamosvalidos = true;
            }
        }
        if (!prestamosvalidos) {
            throw new PrestamoInvalidoException("No hay prestamos realizados actualmente");
        }
        return todosPrestamos;
    }

    public static String mostrarUsuariosSancionados(GestionarBiblioteca gb) throws UsuarioSancionadoException {
        Usuario[] todos = gb.getUsuarios();
        String sancionados = "";
        boolean usuariossancionados = false;
        for (int i = 0; i < gb.getUsuarios().length; i++) {
            Usuario u = todos[i];
            if (u != null && u.estaSancionado()) {
                sancionados += u.toString() + "\n";
                usuariossancionados = true;
            }
        }
        if (!usuariossancionados) {
            throw new UsuarioSancionadoException("No hay usuarios sancionados actualmente");
        }
        return sancionados;
    }

    public static void actualizarSancion(GestionarBiblioteca gb) throws UsuarioSancionadoException {
        Usuario[] todos = gb.getUsuarios();
        int usuariossancionados=0;
        for (int i = 0; i < gb.getUsuarios().length; i++) {
            Usuario u = todos[i];
            if (u != null && u.getFechaFinSancion()!=null && u.getFechaFinSancion().isBefore(LocalDate.now())) {
                u.levantarSancion();
                usuariossancionados++;
            }
        }
        if (usuariossancionados==0){
            throw new UsuarioSancionadoException("No hay usuarios sancionados");
        }
    }
}




