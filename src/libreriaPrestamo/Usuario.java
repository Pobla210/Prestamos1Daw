package libreriaPrestamo;

import java.time.LocalDate;

public class Usuario {
    private String nombre;
    private String email;
    private String emailvalido="^[A-Za-z0-9]+@[A-Za-z0-9]+\\.(com|es)$";
    private String numeroSocio;
    private String numeroSociovalido= "^SOC\\d{5}$";
    private LocalDate fechaRegistro;
    private boolean sancionado;
    private LocalDate fechaFinSancion=null;

    public Usuario(String nombre,String email,String numeroSocio,LocalDate fechaRegistro) throws UsuarioInvalidoExcepcion{
        this.nombre=nombre;
        if (email.matches(emailvalido)){
            this.email=email;
        }
        else {
            throw new UsuarioInvalidoExcepcion("Usuario no valido. Prueba de nuevo");
        }
        if (numeroSocio.matches(numeroSociovalido)){
            this.numeroSocio=numeroSocio;
        }
        else {
            throw new UsuarioInvalidoExcepcion("Usuario no valido. Prueba de nuevo");
        }
        this.fechaRegistro=fechaRegistro;
    }

    public void sancionar(int diasancion,LocalDate sancioninicio) throws UsuarioSancionadoException{
        this.fechaFinSancion=sancioninicio.plusDays(diasancion);
    }

    public void levantarSancion(){
        if (sancionado==true){
            fechaFinSancion=LocalDate.now();
        }
        else if (sancionado==false){
            fechaFinSancion=null;
        }
    }
}
