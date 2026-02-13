package libreriaPrestamo;

import java.time.LocalDate;

public class GestionarBiblioteca {

    private static final int MAX_USUARIOS=50;
    private static final int MAX_PRESTAMOS=200;
    private Usuario[] usuarios;
    private Prestamo[] prestamos;
    private int numeroUsuarios=0;
    private int numeroPrestamos=0;

    public GestionarBiblioteca(){
        this.usuarios=new Usuario[MAX_USUARIOS];
        this.prestamos=new Prestamo[MAX_PRESTAMOS];
    }

    public void registrarUsuario(Usuario socio)throws UsuarioRepetidoException{
        if (numeroUsuarios<MAX_USUARIOS){
        for (int i=0; i<=numeroUsuarios;i++){
            if (socio.equals(this.usuarios[i])){
                throw new UsuarioRepetidoException("El usuario se encuentra repetido");
            }
        }

        for (int i=0;i<=numeroUsuarios;i++){
            if (this.usuarios[i]==null){
                this.usuarios[i]=socio;
            }
        }
    }
        else {
            throw new UsuarioRepetidoException("Usuarios maximos alcanzados");
        }
    }

    public void realizarPrestamo(String codlibro, String titulolibro, LocalDate fechaprestamo, Usuario socio) throws PrestamoInvalidoException,UsuarioSancionadoException,LibroNoDisponibleException{
        if (socio.estaSancionado()){
            throw new UsuarioSancionadoException("El usuario esta sancionado.");
        }
        for (int i = 0; i < prestamos.length; i++) {
            if (prestamos[i] != null) {
                if (prestamos[i].getCodigoLibro().equalsIgnoreCase(codlibro) && prestamos[i].getFechaDevolucionReal()==null) {
                    throw new LibroNoDisponibleException("El libro ya está prestado.");
                }
            }
        }

        for (int i=0;i<prestamos.length;i++){
            if (prestamos[i]==null){
                prestamos[i]=new Prestamo(codlibro,socio,titulolibro,fechaprestamo);
            }
        }

        throw new PrestamoInvalidoException("No se pueden prestar mas libros.");
    }

    public boolean devolverLibro(String codlibro,LocalDate devolucion)throws PrestamoInvalidoException,UsuarioSancionadoException{
        boolean devuelto=false;
        for (int i=0;i<prestamos.length;i++){
            if (prestamos[i] != null && prestamos[i].getCodigoLibro().equalsIgnoreCase(codlibro)) {
                if (devolucion.isBefore(prestamos[i].getFechaPrestamo())) {
                    throw new PrestamoInvalidoException("Error. La fecha de devolucion no puede ser anterior a la fecha del prestamo.");
                }
                prestamos[i].registrarDevolucion(devolucion);
                if (prestamos[i].estaRetrasado()) {
                    prestamos[i].getSocio()
                            .sancionar(prestamos[i].calcularDiasRetraso(), devolucion);
                }
                devuelto = true;
                break;
            }
            }
            return devuelto;
    }

    public Usuario buscarUsuario(String codigoSocio){
        for (int i=0;i<usuarios.length;i++){
            if (usuarios[i]!=null && usuarios[i].getNumeroSocio().equalsIgnoreCase(codigoSocio)){
                return usuarios[i];
            }
        }
        return null;
    }

    public Prestamo[] getPrestamos() {
        return prestamos;
    }

    public Usuario[] getUsuarios() {
        return usuarios;
    }

    public String toString(){
        String resultado=" ";
        resultado+="--Usuarios--";
        for (int i=0;i<usuarios.length;i++){
            if (usuarios[i]!=null){
                resultado+=usuarios[i].toString()+"\n";
            }
        }

        resultado+="--Prestamos--";
        for (int i=0;i<prestamos.length;i++){
            if (prestamos[i]!=null){
                resultado+=prestamos[i].toString()+"\n";
            }
        }

        return resultado;
    }
}
