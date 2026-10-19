package dao;

/**
 * Contrato CRUD genérico: insertar, actualizar, eliminar (o desactivar), buscarPorId y listar.
 * Cada DAO concreto lo implementa para su clase: CrudDAO<Autor, Integer>, CrudDAO<Usuario, String>...
 * Las ventanas y servicios dependen de esta interfaz, no de cómo está hecho el SQL.
 */
public interface CrudDAO<T, ID> extends ConsultaDAO<T, ID> {

    /** Inserta y devuelve el mismo objeto con su id asignado por la BD. */
    T insertar(T objeto);

    /** Actualiza el registro según su llave. */
    void actualizar(T objeto);

    /**
     * Elimina el registro. En las tablas con borrado lógico (personal) lo DESACTIVA:
     * cada DAO decide cómo, quien lo usa no necesita saberlo (polimorfismo).
     */
    void eliminar(ID id);
}
