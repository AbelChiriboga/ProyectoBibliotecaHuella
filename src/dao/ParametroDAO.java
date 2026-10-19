package dao;

import excepciones.DatosException;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import modelo.Parametro;

/**
 * Parámetros del sistema (clave / valor). Las claves vienen precargadas: desde la app
 * solo se leen y se cambian sus valores (no se crean ni se borran).
 */
public class ParametroDAO extends BaseDAO<Parametro, String> {

    @Override
    protected String sqlSelect() {
        return "SELECT p.clave, p.valor, p.descripcion FROM parametros p";
    }

    @Override
    protected String columnaId() {
        return "p.clave";
    }

    @Override
    protected String orden() {
        return "p.clave";
    }

    @Override
    protected Parametro mapear(ResultSet rs) throws SQLException {
        Parametro p = new Parametro(rs.getString("clave"), rs.getString("valor"));
        p.setDescripcion(rs.getString("descripcion"));
        return p;
    }

    /** Busca un parámetro que DEBE existir (si falta, la BD está incompleta). */
    public Parametro obtener(String clave) {
        return buscarPorId(clave).orElseThrow(
                () -> new DatosException("Falta el parámetro " + clave + " en la tabla parametros."));
    }

    public int obtenerEntero(String clave) {
        return obtener(clave).getValorEntero();
    }

    public BigDecimal obtenerDecimal(String clave) {
        return obtener(clave).getValorDecimal();
    }

    public void actualizar(Parametro p) {
        ejecutar("UPDATE parametros SET valor = ? WHERE clave = ?", p.getValor(), p.getClave());
    }

    /** Guarda todos los parámetros juntos: o se cambian todos o ninguno. */
    public void actualizarVarios(List<Parametro> parametros) {
        enTransaccion(cn -> {
            for (Parametro p : parametros) {
                ejecutar(cn, "UPDATE parametros SET valor = ? WHERE clave = ?", p.getValor(), p.getClave());
            }
        });
    }
}
