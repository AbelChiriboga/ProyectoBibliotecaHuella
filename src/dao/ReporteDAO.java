package dao;

import excepciones.DatosException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/** Llena un reporte Jasper con la conexión del sistema (la consulta SQL está en el .jrxml). */
public class ReporteDAO extends AccesoBD {

    public JasperPrint llenar(JasperReport reporte, Map<String, Object> parametros) {
        try (Connection cn = obtenerConexion()) {
            return JasperFillManager.fillReport(reporte, parametros, cn);
        } catch (SQLException e) {
            throw traducir(e);
        } catch (JRException e) {
            throw new DatosException("No se pudo generar el reporte: " + e.getMessage(), e);
        }
    }
}
