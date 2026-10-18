package componentes;

import javax.swing.JButton;

/**
 * Botón con el estilo del sistema (blanco, verde suave al pasar el mouse, verde al hacer clic).
 * Con primario = true queda verde desde el inicio (para Guardar, Confirmar, etc.).
 * Se puede arrastrar desde la paleta y configurar el ícono en Properties.
 */
public class BotonSistema extends JButton {

    private String rutaIcono = "";
    private int tamanoIcono = 20;
    private boolean textoAbajo = false;
    private boolean primario = false;

    public BotonSistema() {
        this("Botón");
    }

    public BotonSistema(String texto) {
        super(texto);
        util.Estilos.boton(this);
    }

    /** Nombre del archivo dentro de /imagenes (por ejemplo "Regresar.png"). */
    public String getRutaIcono() { return rutaIcono; }

    public void setRutaIcono(String rutaIcono) {
        this.rutaIcono = rutaIcono == null ? "" : rutaIcono;
        aplicarIcono();
    }

    public int getTamanoIcono() { return tamanoIcono; }

    public void setTamanoIcono(int tamanoIcono) {
        this.tamanoIcono = tamanoIcono;
        aplicarIcono();
    }

    /** true = texto debajo del ícono (botones de menú); false = texto a la derecha. */
    public boolean isTextoAbajo() { return textoAbajo; }

    public void setTextoAbajo(boolean textoAbajo) {
        this.textoAbajo = textoAbajo;
        aplicarIcono();
    }

    /** true = botón de la acción principal (verde con texto blanco), p. ej. Guardar o Confirmar. */
    public boolean isPrimario() { return primario; }

    public void setPrimario(boolean primario) {
        this.primario = primario;
        putClientProperty("primario", primario);
        util.Estilos.pintarBoton(this);
    }

    private void aplicarIcono() {
        if (!rutaIcono.isEmpty()) {
            util.Estilos.icono(this, rutaIcono, tamanoIcono, textoAbajo);
        }
    }
}
