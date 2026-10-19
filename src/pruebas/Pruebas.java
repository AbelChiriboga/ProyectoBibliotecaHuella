package pruebas;

import java.sql.Connection;
import java.sql.Statement;

/** Ayudante SOLO para las clases de prueba: ejecuta SQL directo (fuera de los DAO). */
final class Pruebas {

    private Pruebas() {
    }

    static void sql(String sentencia) {
        try (Connection cn = new conexion.Conexion().conectar();
             Statement st = cn.createStatement()) {
            st.executeUpdate(sentencia);
        } catch (Exception e) {
            System.out.println("  (error de limpieza: " + e.getMessage() + ")");
        }
    }
}
