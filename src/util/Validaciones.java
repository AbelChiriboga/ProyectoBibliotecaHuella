package util;

import excepciones.ValidacionException;
import java.math.BigDecimal;

/**
 * Validaciones reutilizables. Todas lanzan ValidacionException con un mensaje claro,
 * así el modelo (setters) y los servicios no repiten código.
 */
public final class Validaciones {

    private Validaciones() {
    }

    /** Campo obligatorio: no nulo ni vacío. Devuelve el texto sin espacios a los lados. */
    public static String obligatorio(String valor, String campo) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new ValidacionException("El campo " + campo + " es obligatorio.");
        }
        return valor.trim();
    }

    /** Opcional: devuelve null si viene vacío, o el texto sin espacios. */
    public static String opcional(String valor) {
        return (valor == null || valor.trim().isEmpty()) ? null : valor.trim();
    }

    /** Longitud máxima (las columnas VARCHAR tienen límite). */
    public static String maximo(String valor, int max, String campo) {
        if (valor != null && valor.length() > max) {
            throw new ValidacionException("El campo " + campo + " admite máximo " + max + " caracteres.");
        }
        return valor;
    }

    /** Formato de cédula: exactamente 10 dígitos. */
    public static String formatoCedula(String cedula) {
        String c = obligatorio(cedula, "cédula");
        if (!c.matches("\\d{10}")) {
            throw new ValidacionException("La cédula debe tener 10 dígitos.");
        }
        return c;
    }

    /**
     * Cédula ecuatoriana completa: formato, provincia (01-24 o 30), tercer dígito menor a 6
     * y dígito verificador (algoritmo módulo 10).
     */
    public static String cedulaEcuatoriana(String cedula) {
        String c = formatoCedula(cedula);
        int provincia = Integer.parseInt(c.substring(0, 2));
        if ((provincia < 1 || provincia > 24) && provincia != 30) {
            throw new ValidacionException("La cédula tiene un código de provincia no válido.");
        }
        if (c.charAt(2) - '0' >= 6) {
            throw new ValidacionException("La cédula no corresponde a una persona natural.");
        }
        int suma = 0;
        for (int i = 0; i < 9; i++) {
            int d = (c.charAt(i) - '0') * (i % 2 == 0 ? 2 : 1);
            suma += d > 9 ? d - 9 : d;
        }
        int verificador = (10 - suma % 10) % 10;
        if (verificador != c.charAt(9) - '0') {
            throw new ValidacionException("La cédula ingresada no es válida.");
        }
        return c;
    }

    /** Correo electrónico con forma usuario@dominio.ext (opcional si viene vacío). */
    public static String correo(String correo) {
        String c = opcional(correo);
        if (c != null && !c.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new ValidacionException("El correo electrónico no es válido.");
        }
        return c;
    }

    /** Teléfono: 7 a 10 dígitos (opcional si viene vacío). */
    public static String telefono(String telefono) {
        String t = opcional(telefono);
        if (t != null && !t.matches("\\d{7,10}")) {
            throw new ValidacionException("El teléfono debe tener entre 7 y 10 dígitos.");
        }
        return t;
    }

    /** Convierte texto a entero y verifica el rango. */
    public static int entero(String valor, String campo, int min, int max) {
        String v = obligatorio(valor, campo);
        try {
            return rango(Integer.parseInt(v), campo, min, max);
        } catch (NumberFormatException e) {
            throw new ValidacionException("El campo " + campo + " debe ser un número entero.");
        }
    }

    /** Verifica que un entero esté dentro del rango. */
    public static int rango(int valor, String campo, int min, int max) {
        if (valor < min || valor > max) {
            throw new ValidacionException("El campo " + campo + " debe estar entre " + min + " y " + max + ".");
        }
        return valor;
    }

    /** Convierte texto a decimal no negativo (montos de dinero). */
    public static BigDecimal decimal(String valor, String campo) {
        String v = obligatorio(valor, campo).replace(',', '.');
        try {
            return noNegativo(new BigDecimal(v), campo);
        } catch (NumberFormatException e) {
            throw new ValidacionException("El campo " + campo + " debe ser un número.");
        }
    }

    /** Un decimal no puede ser nulo ni negativo. */
    public static BigDecimal noNegativo(BigDecimal valor, String campo) {
        if (valor == null || valor.signum() < 0) {
            throw new ValidacionException("El campo " + campo + " no puede ser negativo.");
        }
        return valor;
    }

    /** Un objeto relacionado (carrera, autor, libro...) es obligatorio. */
    public static <T> T requerido(T valor, String campo) {
        if (valor == null) {
            throw new ValidacionException("Debe seleccionar " + campo + ".");
        }
        return valor;
    }
}
