package libreriaPrestamo;

import java.time.LocalDate;

public class Prestamo {
    private String codigoLibro;
    private static final String codigoLibroCorrecto="^[A-Z]{3}\\d{4}$";
    private String tituloLibro;
    private Usuario socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal=null;

    public Prestamo(String codigoLibro,Usuario socio,String tituloLibro,LocalDate fechaPrestamo)throws PrestamoInvalidoException{
        if (!codigoLibro.matches(codigoLibroCorrecto)){
            throw new PrestamoInvalidoException("Error. El codigo del libro introducido no es correcto, prueba de nuevo");
        }
        else {
            this.codigoLibro=codigoLibro;
        }

        if (socio==null){
            throw new PrestamoInvalidoException("Error. El socio no puede ser un campo nulo");
        }
        else {
            this.socio=socio;
        }

        if (tituloLibro.trim().isEmpty()){
            throw new PrestamoInvalidoException("Error. El titulo del libro se encuentra vacio.");
        }
        else {
            this.tituloLibro=tituloLibro;
        }

        if (fechaPrestamo==null) {
            throw new PrestamoInvalidoException("Error. La fecha introducida es nula.");
        }
        else if (fechaPrestamo.isAfter(LocalDate.now())) {
            throw new PrestamoInvalidoException("Error. La fecha introducida es posterior a la fecha actual.");
        }
        else {
           this.fechaPrestamo=fechaPrestamo;
           this.fechaDevolucionPrevista=fechaPrestamo.plusDays(14);
       }

    }

    public void registrarDevolucion(LocalDate devolucion){
        if (devolucion==null){

        }

    }
}
