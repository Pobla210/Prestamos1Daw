package libreriaPrestamo;

public class PrestamoInvalidoException extends Exception{
    public PrestamoInvalidoException(String mensaje){
        super(mensaje);
    }
}
