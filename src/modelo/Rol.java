package modelo;

/**
 * Rol del personal. Además de su valor en la BD, cada constante sabe CREAR su subclase de Usuario.
 * Es la "fábrica" que usa el DAO: no hay if/else ni switch para decidir qué objeto crear,
 * cada constante redefine crearUsuario() (polimorfismo dentro del enum).
 */
public enum Rol implements ValorBD {

    ADMINISTRADOR("ADMIN", "Administrador") {
        @Override
        public Usuario crearUsuario() {
            return new Administrador();
        }
    },
    BIBLIOTECARIO("BIBLIOTECARIO", "Bibliotecario") {
        @Override
        public Usuario crearUsuario() {
            return new Bibliotecario();
        }
    };

    private final String valorBD;
    private final String descripcion;

    Rol(String valorBD, String descripcion) {
        this.valorBD = valorBD;
        this.descripcion = descripcion;
    }

    /** Crea un usuario vacío de la subclase que corresponde a este rol. */
    public abstract Usuario crearUsuario();

    @Override
    public String getValorBD() {
        return valorBD;
    }

    @Override
    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
