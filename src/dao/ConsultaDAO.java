package dao;

import java.util.List;
import java.util.Optional;

/**
 * Operaciones de SOLO LECTURA, comunes a todos los DAO.
 * Genérica: T = clase del modelo, ID = tipo de su llave (Integer, String...).
 *
 * Se separa de CrudDAO porque hay tablas que el sistema solo consulta
 * (carreras fijas, préstamos que no se borran, bitácora): no se les obliga
 * a "implementar" un eliminar que no deben tener (segregación de interfaces).
 */
public interface ConsultaDAO<T, ID> {

    /** Busca por la llave primaria. Optional.empty() si no existe. */
    Optional<T> buscarPorId(ID id);

    /** Todos los registros, en el orden natural de la tabla. */
    List<T> listar();
}
