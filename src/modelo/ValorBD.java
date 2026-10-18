package modelo;

/**
 * Contrato de los enums que se guardan en la BD como texto.
 * Cada enum sabe su valor en la BD (getValorBD) y su texto para la pantalla (getDescripcion).
 * El método genérico desde(...) sirve para TODOS los enums: un solo código en vez de uno por enum.
 */
public interface ValorBD {

    /** Texto con el que se guarda en la BD. */
    String getValorBD();

    /** Texto amigable para combos y tablas. */
    String getDescripcion();

    /**
     * Convierte el texto de la BD en la constante del enum indicado.
     * Ejemplo: Rol r = ValorBD.desde(Rol.class, rs.getString("rol"));
     */
    static <E extends Enum<E> & ValorBD> E desde(Class<E> tipo, String valor) {
        if (valor == null) {
            return null;
        }
        for (E constante : tipo.getEnumConstants()) {
            if (constante.getValorBD().equalsIgnoreCase(valor.trim())
                    || constante.name().equalsIgnoreCase(valor.trim())) {
                return constante;
            }
        }
        throw new IllegalArgumentException("Valor '" + valor + "' no válido para " + tipo.getSimpleName());
    }
}
