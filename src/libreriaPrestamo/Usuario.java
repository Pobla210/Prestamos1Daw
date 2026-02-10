package libreriaPrestamo;

import java.time.LocalDate;

public class Usuario {
    private String nombre;
    private String email;
    private static final String emailvalido="^[A-Za-z0-9]+@[A-Za-z0-9]+\\.(com|es)$";
    private String numeroSocio;
    private static final String numeroSociovalido= "^SOC\\d{5}$";
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
        if(sancionado){
            throw new UsuarioSancionadoException("Error. El usuario ya se encuentra sancionado");
        }
        this.fechaFinSancion=sancioninicio.plusDays(diasancion);
    }

    public void levantarSancion()throws UsuarioSancionadoException{
        if (sancionado==true){
            fechaFinSancion=null;
            sancionado=false;
        }
        else {
            throw new UsuarioSancionadoException("Error.El usuario no se encuentra sancionado actualmente.");
        }
    }

    public boolean estaSancionado(){
        return sancionado;
    }

    public String ToString(){
        return "Nombre: "+nombre+"\nemail: "+email+"\nNumero Socio: "+numeroSocio+"\nFecha Registro: "+fechaRegistro+"\nFecha Fin Sancion: "+fechaFinSancion;
    }
}
