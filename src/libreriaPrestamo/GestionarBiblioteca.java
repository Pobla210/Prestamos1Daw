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
        for (int i=0; i<=numeroUsuarios;i++){
            if (socio.equals(this.usuarios)){
                throw new UsuarioRepetidoException("El usuario se encuentra repetido");
            }
        }

        for (int i=0;i<=numeroUsuarios;i++){
            if (this.usuarios[i]==null){
                this.usuarios[i]=socio;
            }
        }
    }

    public void realizarPrestamo(String codlibro, String titulolibro, LocalDate fechaprestamo, Usuario socio){

    }
}
