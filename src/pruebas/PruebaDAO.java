package pruebas;

import dao.*;
import excepciones.BibliotecaException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import modelo.*;

/**
 * Prueba de la Fase 2 contra la BD real (XAMPP encendido, script biblioteca_huella.sql cargado).
 * En NetBeans: clic derecho → Run File (Shift+F6).
 * Al final borra lo que creó, así la BD queda como estaba.
 */
public class PruebaDAO {

    private static final List<Integer> prestamosCreados = new ArrayList<>();
    private static Integer registroCreado;

    public static void main(String[] args) {
        try {
            usuarios();
            catalogo();
            estudiantesYHuellas();
            prestamosYMultas();
            bitacoraYParametros();
        } finally {
            limpiar();
        }
    }

    private static void usuarios() {
        titulo("UsuarioDAO: la fábrica crea la subclase según la columna rol");
        UsuarioDAO dao = new UsuarioDAO();
        for (Usuario u : dao.listar()) {
            System.out.printf("  %-10s %-26s → %-14s catálogo=%s parámetros=%s%n", u.getUsuario(),
                    u.getNombreCompleto(), u.getClass().getSimpleName(), u.puedeGestionarCatalogo(),
                    u.puedeConfigurarParametros());
        }
        System.out.println("  buscarPorUsuario(\"admin\") → " + dao.buscarPorUsuario("admin").map(Usuario::getTipo).orElse("no existe"));
    }

    private static void catalogo() {
        titulo("AutorDAO (CRUD completo) y errores traducidos");
        AutorDAO autores = new AutorDAO();
        Autor a = autores.insertar(new Autor("Prueba", "Temporal"));
        System.out.println("  insertado con id " + a.getId());
        a.setNacionalidad("Ecuatoriana");
        autores.actualizar(a);
        System.out.println("  releído: " + autores.buscarPorId(a.getId()).map(x -> x + " (" + x.getNacionalidad() + ")").orElse("?"));
        autores.eliminar(a.getId());
        System.out.println("  eliminado; existe todavía: " + autores.buscarPorId(a.getId()).isPresent());
        probar("Eliminar un autor que tiene libros", () -> autores.eliminar(1));
        probar("Categoría repetida", () -> new CategoriaDAO().insertar(new Categoria("Literatura")));

        titulo("CategoriaDAO.listarConTotales y LibroDAO (usa la vista v_catalogo)");
        for (Categoria c : new CategoriaDAO().listarConTotales("")) {
            System.out.println("  " + c + ": " + c.getCantidadLibros() + " libros, " + c.getCantidadEjemplares() + " ejemplares");
        }
        for (Libro l : new LibroDAO().buscar("")) {
            System.out.println("  " + l.getTitulo() + " | " + l.getAutor() + " | " + l.getCategoria() + " | disponibles " + l.getResumenDisponibles());
        }
        System.out.println("  Ejemplares de Clean Code: " + new EjemplarDAO().listarPorLibro(1));
    }

    private static void estudiantesYHuellas() {
        titulo("EstudianteDAO + CarreraDAO (solo consulta) + HuellaDAO");
        System.out.println("  Carreras: " + new CarreraDAO().listar());
        for (Estudiante e : new EstudianteDAO().listar()) {
            System.out.println("  " + e.getCedula() + " " + e.getNombreCompleto() + " | " + e.getCarrera() + " | " + e.getEstado());
        }
        Estudiante carlos = new EstudianteDAO().buscarPorCedula("1800000001").orElseThrow();
        HuellaDAO huellas = new HuellaDAO();
        Huella h = new Huella(Dedo.INDICE_DERECHO, new byte[]{10, 20, 30});
        carlos.agregarHuella(h);
        huellas.insertar(h);
        System.out.println("  Huella guardada; Carlos tiene " + huellas.contarPorEstudiante(carlos.getIdEstudiante()) + " huella(s)");
        probar("Mismo dedo otra vez (UNIQUE en la BD)", () -> {
            Huella repetida = new Huella(Dedo.INDICE_DERECHO, new byte[]{1});
            repetida.setIdEstudiante(carlos.getIdEstudiante());
            huellas.insertar(repetida);
        });
        huellas.eliminar(h.getId());
    }

    private static void prestamosYMultas() {
        titulo("PrestamoDAO: los triggers aplican las reglas");
        Estudiante carlos = new EstudianteDAO().buscarPorCedula("1800000001").orElseThrow();
        Usuario biblio = new UsuarioDAO().buscarPorUsuario("biblio").orElseThrow();
        EjemplarDAO ejemplares = new EjemplarDAO();
        PrestamoDAO dao = new PrestamoDAO();

        Prestamo sinHuella = new Prestamo(ejemplares.buscarPorId(1).orElseThrow(), carlos, biblio);
        probar("Préstamo SIN huella verificada", () -> dao.insertar(sinHuella));

        Prestamo p = new Prestamo(ejemplares.buscarPorId(1).orElseThrow(), carlos, biblio);
        p.setHuellaPrestamo(true);
        dao.insertar(p);
        prestamosCreados.add(p.getId());
        Prestamo leido = dao.buscarPorId(p.getId()).orElseThrow();
        System.out.println("  Préstamo " + leido.getId() + ": " + leido.getEjemplar() + " | límite (trigger) "
                + leido.getFechaLimite().toLocalDate() + " | ejemplar ahora " + ejemplares.buscarPorId(1).orElseThrow().getEstado());
        probar("Prestar el mismo ejemplar otra vez", () -> {
            Prestamo otra = new Prestamo(ejemplares.buscarPorId(1).orElseThrow(), carlos, biblio);
            otra.setHuellaPrestamo(true);
            dao.insertar(otra);
        });

        // 3 más: superaría el máximo (1 + 3 = 4) → el trigger rechaza el último y se deshace TODO
        List<Prestamo> varios = new ArrayList<>();
        for (int id : new int[]{3, 4, 6}) {
            Prestamo x = new Prestamo(ejemplares.buscarPorId(id).orElseThrow(), carlos, biblio);
            x.setHuellaPrestamo(true);
            varios.add(x);
        }
        probar("Tres préstamos más en una transacción", () -> dao.insertarVarios(varios));
        System.out.println("  Préstamos activos de Carlos tras el rollback: " + dao.contarActivos(carlos.getIdEstudiante()));

        // Simulamos que venció hace 4 días (solo para la prueba) y devolvemos
        Pruebas.sql("UPDATE prestamos SET fecha_limite = DATE_SUB(NOW(), INTERVAL 4 DAY) WHERE id_prestamo = " + p.getId());
        System.out.println("  Atrasados ahora: " + dao.listarAtrasados("").size());
        dao.registrarDevoluciones(List.of(p.getId()));
        Prestamo devuelto = dao.buscarPorId(p.getId()).orElseThrow();
        System.out.println("  Devuelto: estado " + devuelto.getEstado() + " | ejemplar " + ejemplares.buscarPorId(1).orElseThrow().getEstado()
                + " | multa (trigger) " + (devuelto.tieneMulta() ? devuelto.getMulta().getDiasAtraso() + " días = " + devuelto.getMulta().getMontoTexto() : "ninguna"));

        titulo("MultaDAO");
        MultaDAO multas = new MultaDAO();
        System.out.println("  Pendiente de Carlos: $" + multas.totalPendiente(carlos.getIdEstudiante()));
        Multa m = multas.listarPendientesPorEstudiante(carlos.getIdEstudiante()).get(0);
        System.out.println("  Multa del libro: " + m.getPrestamo().getEjemplar().getLibro().getTitulo());
        multas.marcarPagada(m.getId());
        System.out.println("  Tras pagar: $" + multas.totalPendiente(carlos.getIdEstudiante()));
    }

    private static void bitacoraYParametros() {
        titulo("RegistroAutenticacionDAO y ParametroDAO");
        RegistroAutenticacionDAO bitacora = new RegistroAutenticacionDAO();
        RegistroAutenticacion r = bitacora.insertar(new RegistroAutenticacion(null, OperacionAutenticacion.PRESTAMO,
                ResultadoAutenticacion.FALLO, 21, "Huella no reconocida (prueba)"));
        registroCreado = r.getId();
        System.out.println("  Fallos de préstamo hoy: " + bitacora.listar(LocalDate.now(), LocalDate.now(),
                OperacionAutenticacion.PRESTAMO, ResultadoAutenticacion.FALLO).size());
        ParametroDAO parametros = new ParametroDAO();
        System.out.println("  MAX_PRESTAMOS = " + parametros.obtenerEntero(Parametro.MAX_PRESTAMOS)
                + ", MULTA_POR_DIA = " + parametros.obtenerDecimal(Parametro.MULTA_POR_DIA));
    }

    /** Deja la BD como estaba. */
    private static void limpiar() {
        for (int id : prestamosCreados) {
            Pruebas.sql("DELETE FROM prestamos WHERE id_prestamo = " + id);   // la multa se borra en cascada
        }
        Pruebas.sql("UPDATE ejemplares SET estado = 'DISPONIBLE'");
        if (registroCreado != null) {
            Pruebas.sql("DELETE FROM registro_autenticacion WHERE id_registro = " + registroCreado);
        }
        System.out.println("\n(Datos de prueba eliminados)");
    }

    private static void probar(String caso, Runnable accion) {
        try {
            accion.run();
            System.out.println("  " + caso + ": (aceptado)");
        } catch (BibliotecaException ex) {
            System.out.println("  " + caso + ": [" + ex.getClass().getSimpleName() + "] " + ex.getMessage());
        }
    }

    private static void titulo(String t) {
        System.out.println("\n=== " + t + " ===");
    }
}
