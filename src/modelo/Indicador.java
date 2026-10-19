package modelo;

/**
 * Números que muestran las tarjetas de los menús. Cada uno se cuenta en la BD (dao.IndicadorDAO).
 * Está en el paquete modelo para que dao y servicio lo usen sin depender uno del otro.
 */
public enum Indicador {
    ESTUDIANTES_REGISTRADOS,
    BIBLIOTECARIOS_ACTIVOS,
    PRESTAMOS_ACTIVOS,
    PRESTAMOS_ATRASADOS,
    DEVOLUCIONES_HOY,
    EJEMPLARES_DISPONIBLES,
    HUELLAS_FALLIDAS_HOY
}
