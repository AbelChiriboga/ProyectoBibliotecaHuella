package excepciones;

/**
 * Problema técnico con la base de datos (XAMPP apagado, error de SQL inesperado...).
 * La crea BaseDAO a partir de una SQLException para que las ventanas no tengan que conocer JDBC.
 */
public class DatosException extends BibliotecaException {

    public DatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public DatosException(String mensaje) {
        super(mensaje);
    }

    @Override
    public String getTitulo() {
        return "Error de base de datos";
    }

    @Override
    public boolean esErrorTecnico() {
        return true;
    }
}
