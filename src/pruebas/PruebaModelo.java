package pruebas;

import excepciones.BibliotecaException;
import java.math.BigDecimal;
import java.util.List;
import modelo.*;

/** Prueba de la Fase 1 (sin BD ni ventanas). */
public class PruebaModelo {

    public static void main(String[] args) {
        titulo("1. Fábrica sin if: cada Rol crea su subclase");
        Usuario a = Rol.ADMINISTRADOR.crearUsuario();
        Usuario b = ValorBD.desde(Rol.class, "BIBLIOTECARIO").crearUsuario();
        a.setCedula("1804567897"); a.setNombres("Ana"); a.setApellidos("Torres"); a.setUsuario("admin");
        b.setCedula("1712345675"); b.setNombres("Luis"); b.setApellidos("Mena"); b.setUsuario("biblio");
        System.out.println(a.getClass().getSimpleName() + " / " + b.getClass().getSimpleName());

        titulo("2. Polimorfismo: la misma pregunta, cada objeto responde distinto");
        List<Usuario> personal = List.of(a, b);
        for (Usuario u : personal) {
            System.out.printf("%-14s registrarEst=%-5s catalogo=%-5s prestamos=%-5s parametros=%-5s bitacora=%s%n",
                    u.getTipo(), u.puedeRegistrarEstudiantes(), u.puedeGestionarCatalogo(),
                    u.puedeGestionarPrestamos(), u.puedeConfigurarParametros(), u.puedeVerBitacora());
        }

        titulo("3. Abstracción: Persona agrupa personal y estudiantes");
        Carrera software = new Carrera(1, "Software");
        Estudiante e = new Estudiante("1804567897", "Diego", "Paredes", software, 6);
        e.setCorreo("dparedes@uta.edu.ec");
        List<Persona> personas = List.of(a, b, e);
        for (Persona p : personas) {
            System.out.println(p.getNombreCompleto() + " → " + p.getTipo()
                    + " | inicia sesión: " + p.puedeIniciarSesion());
        }

        titulo("4. Encapsulamiento: los setters rechazan datos inválidos");
        probar("Cédula con letras", () -> e.setCedula("18ABC"));
        probar("Semestre 15", () -> e.setSemestre(15));
        probar("Correo mal escrito", () -> e.setCorreo("diego@"));
        probar("Libro sin autor", () -> new Libro("9780132350884", "Clean Code", null, new Categoria("Software")));
        probar("Cédula ecuatoriana falsa", () -> util.Validaciones.cedulaEcuatoriana("1804567890"));
        e.setCorreo("dparedes@uta.edu.ec");
        System.out.println("Correo válido guardado: " + e.getCorreo());

        titulo("5. Lista protegida: las huellas solo entran por agregarHuella()");
        e.agregarHuella(new Huella(Dedo.INDICE_DERECHO, new byte[]{1, 2, 3}));
        probar("Mismo dedo dos veces", () -> e.agregarHuella(new Huella(Dedo.INDICE_DERECHO, new byte[]{4})));
        probar("Modificar la lista desde afuera", () -> e.getHuellas().clear());
        System.out.println("Huellas registradas: " + e.getHuellas().size());

        titulo("6. Enums y reglas simples");
        System.out.println("Estados de ejemplar: " + List.of(EstadoEjemplar.values()));
        Prestamo p = new Prestamo(new Ejemplar(new Libro(null, "Clean Code", new Autor("Robert", "Martin"),
                new Categoria("Software")), "lib-0001", "Estante A1"), e, b);
        p.setFechaLimite(java.time.LocalDateTime.now().minusDays(4));
        System.out.println("Ejemplar " + p.getEjemplar().getCodigoInventario() + " atrasado " + p.diasAtraso()
                + " días → multa: $" + Multa.calcularMonto(p.diasAtraso(), new BigDecimal("0.50")));
    }

    private static void probar(String caso, Runnable accion) {
        try {
            accion.run();
            System.out.println("  " + caso + ": (aceptado)");
        } catch (BibliotecaException ex) {
            System.out.println("  " + caso + ": " + ex.getTitulo() + " → " + ex.getMessage());
        } catch (UnsupportedOperationException ex) {
            System.out.println("  " + caso + ": bloqueado (lista de solo lectura)");
        }
    }

    private static void titulo(String t) {
        System.out.println("\n=== " + t + " ===");
    }
}
