package libreriaPrestamo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Prestamo {
    private String codigoLibro;
    private static final String codigoLibroCorrecto="^[A-Z]{3}\\d{4}$";
    private String tituloLibro;
    private Usuario socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionPrevista;
    private LocalDate fechaDevolucionReal=null;

    public String getCodigoLibro() {
        return codigoLibro;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public Usuario getSocio() {
        return socio;
    }

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

    public void registrarDevolucion(LocalDate devolucion)throws PrestamoInvalidoException{
        if (devolucion==null){
            throw new PrestamoInvalidoException("Error. La fecha de devolucion no puede ser nula");
        }
        else if (devolucion.isBefore(fechaPrestamo)) {
            throw new PrestamoInvalidoException("Error. La fecha no puede ser anterior a la fecha de prestamo.");
        }
        else {
            this.fechaDevolucionReal=devolucion;
        }
    }

    public int calcularDiasRetraso()throws PrestamoInvalidoException{
        int diasRetraso;
        if (fechaDevolucionReal==null){
            return (int)ChronoUnit.DAYS.between(fechaPrestamo,LocalDate.now());
        }
        diasRetraso=(int)ChronoUnit.DAYS.between(fechaDevolucionPrevista,fechaDevolucionReal);
        if (diasRetraso<=0){
            return 0;
        }
        else {
            return diasRetraso;
        }
    }

    public boolean estaRetrasado(){
        if (fechaDevolucionPrevista.isBefore(LocalDate.now())){
            return false;
        }
        else if (fechaDevolucionPrevista.isEqual(LocalDate.now())){
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter patron = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Codigo Libro: "+this.codigoLibro+"\nTitulo libro: "+this.tituloLibro+"\nNombre socio: "+this.socio.getNombre()+
                "\nFecha del prestamo: "+fechaPrestamo.format(patron)+"\nFecha de devolucion prevista: "+fechaDevolucionPrevista.format(patron)+
                "\nFecha de devolucion real: "+fechaDevolucionReal.format(patron);
    }
}
