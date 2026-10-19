package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * DAO de una tabla de ENTIDADES (autores, libros, estudiantes...). Hereda de AccesoBD
 * la conexión, los parámetros y el manejo de errores.
 *
 * Patrón "método plantilla": buscarPorId() y listar() ya están escritos aquí y funcionan
 * para cualquier tabla, porque cada hija solo completa las partes abstractas:
 * sqlSelect(), columnaId(), orden() y mapear().
 */
public abstract class BaseDAO<T, ID> extends AccesoBD implements ConsultaDAO<T, ID> {

    // ---------- Partes que completa cada hija ----------

    /** SELECT ... FROM ... JOIN ... sin WHERE ni ORDER BY. */
    protected abstract String sqlSelect();

    /** Columna de la llave primaria, con su alias de tabla (por ejemplo "a.id_autor"). */
    protected abstract String columnaId();

    /** Orden para listar (por ejemplo "a.apellidos, a.nombres"). */
    protected abstract String orden();

    /** Convierte la fila actual del ResultSet en un objeto del modelo. */
    protected abstract T mapear(ResultSet rs) throws SQLException;

    // ---------- Consultas que ya funcionan para todas las hijas ----------

    @Override
    public Optional<T> buscarPorId(ID id) {
        return consultarUno(sqlSelect() + " WHERE " + columnaId() + " = ?", id);
    }

    @Override
    public List<T> listar() {
        return consultarLista(sqlSelect() + " ORDER BY " + orden());
    }

    /** Lista de objetos de ESTE DAO. */
    protected List<T> consultarLista(String sql, Object... parametros) {
        return consultar(sql, this::mapear, parametros);
    }

    /** Un solo objeto de ESTE DAO (el primero), o vacío. */
    protected Optional<T> consultarUno(String sql, Object... parametros) {
        List<T> lista = consultarLista(sql, parametros);
        return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    }
}
