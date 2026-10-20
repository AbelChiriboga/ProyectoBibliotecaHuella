package vista;

/**
 * Registro de estudiantes con su huella (solo el administrador).
 * Hereda de VentanaBase: encabezado, pie, tamaño y botón Regresar ya vienen listos.
 *
 * @author User
 */
public class CRUDEstudiantes extends componentes.VentanaBase {

    private final servicio.EstudianteService servicio = new servicio.EstudianteService();
    private final servicio.BiometriaService biometria = new servicio.BiometriaService();
    private final componentes.TablaObjetos<modelo.Estudiante> tabla;
    private final componentes.ControlCrud control;
    /** Estudiante que se está viendo o editando (uno nuevo al presionar Nuevo). */
    private modelo.Estudiante actual;
    /** Huellas capturadas en el formulario que todavía no se guardan (dedo → plantilla). */
    private final java.util.Map<modelo.Dedo, byte[]> huellasCapturadas = new java.util.EnumMap<>(modelo.Dedo.class);
    /**
     * Número de la captura vigente. Si el usuario cancela, cambia de estudiante o cierra la ventana
     * mientras el lector espera el dedo, el número cambia y el resultado de esa captura vieja se ignora
     * (no aparece ningún mensaje ni se guarda una huella en el estudiante equivocado).
     */
    private int capturaVigente;

    public CRUDEstudiantes() {
        initComponents();
        montar("GESTIÓN DE ESTUDIANTES", jpnlContenido);
        componentes.Tablas.soloLectura(jtblEstudiantes, jScrollPane1,
                "Cédula", "Nombres", "Apellidos", "Carrera", "Semestre", "Huellas", "Estado");
        componentes.Tablas.anchos(jtblEstudiantes, 95, 0, 0, 180, 65, 60, 70);
        tabla = new componentes.TablaObjetos<>(jtblEstudiantes, e -> new Object[]{e.getCedula(), e.getNombres(),
            e.getApellidos(), e.getCarrera(), e.getSemestre(), e.getHuellas().size(), e.getEstado()});
        // La huella es parte del formulario: se captura en Nuevo o Editar y se guarda con Guardar
        control = new componentes.ControlCrud(new javax.swing.JComponent[]{jtxtCedula, jtxtNombres, jtxtApellidos,
            jtxtTelefono, jtxtCorreo, jcbxCarrera, jcbxSemestre, jpnlHuella.getCbxDedo(), jpnlHuella.getBotonCapturar()},
                jbtnNuevo, jbtnGuardar, jbtnEditar, jbtnEliminar, jbtnCancelar);
        control.bloquearAlEditar(jtblEstudiantes);   // mientras se escribe no se puede elegir otra fila
        jpnlHuella.getBotonCapturar().addActionListener(e -> capturarHuella());
        jpnlHuella.getBotonCapturar().setFocusable(false);   // solo con clic: Tab/Espacio no inician una captura
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                detenerCaptura();
            }
        });
        alCambiarLector(jpnlHuella::mostrarLector);   // el recuadro avisa si el lector se desconecta
        tabla.alSeleccionar(this::mostrar);
        tabla.usarBarra(barraBuscar);   // búsqueda al escribir + filtro por columnas
        cargarCarreras();
        cancelar();
    }

    // ---------- Acciones de los botones ----------

    private void nuevo() {
        detenerCaptura();
        actual = new modelo.Estudiante();
        limpiar();
        tabla.limpiarSeleccion();
        control.nuevo();
        jpnlHuella.setInfo(textoHuellas());
        jtxtCedula.requestFocus();
    }

    private void editar() {
        ejecutar(() -> {
            actual = tabla.exigirSeleccionado("un estudiante");
            detenerCaptura();
            huellasCapturadas.clear();
            control.edicion();
            jpnlHuella.setInfo(textoHuellas());
            jtxtNombres.requestFocus();
        });
    }

    /** Guarda el estudiante con sus huellas. Sin al menos una huella, el servicio no lo deja guardar. */
    private void guardar() {
        ejecutar(() -> {
            actual.setCedula(jtxtCedula.getText());
            actual.setNombres(jtxtNombres.getText());
            actual.setApellidos(jtxtApellidos.getText());
            actual.setTelefono(jtxtTelefono.getText());
            actual.setCorreo(jtxtCorreo.getText());
            actual.setCarrera(componentes.Combos.seleccionado(jcbxCarrera));
            actual.setSemestre(Integer.parseInt(util.Validaciones.requerido((String) jcbxSemestre.getSelectedItem(), "el semestre")));
            servicio.guardar(actual, huellasCapturadas);
            util.Mensajes.info(this, "Estudiante guardado.");
            recargarYSeleccionar(actual);
        });
    }

    private void eliminar() {
        ejecutar(() -> {
            modelo.Estudiante e = tabla.exigirSeleccionado("un estudiante");
            if (util.Mensajes.confirmar(this, "¿Eliminar a " + e.getNombreCompleto() + "?")) {
                servicio.eliminar(e);
                cancelar();
            }
        });
    }

    /** Vuelve al estado inicial y recarga la tabla (descarta cambios y huellas sin guardar). */
    private void cancelar() {
        detenerCaptura();
        actual = null;
        huellasCapturadas.clear();
        limpiar();
        cargar();
        control.consulta(false);
    }

    /**
     * Captura la huella del dedo elegido (3 muestras). Queda pendiente hasta presionar Guardar.
     * La captura espera el dedo, por eso corre en segundo plano y la ventana no se congela.
     */
    private void capturarHuella() {
        ejecutar(() -> {
            modelo.Dedo dedo = componentes.Combos.seleccionado(jpnlHuella.getCbxDedo());
            if (confirmarReemplazo(dedo)) {
                modelo.Estudiante estudiante = actual;
                int esta = ++capturaVigente;
                jpnlHuella.iniciarCaptura();
                util.Tareas.enSegundoPlano(this,
                        () -> biometria.capturarHuella(estudiante, jpnlHuella),   // el panel muestra avance e imagen
                        plantilla -> siSigueVigente(esta, () -> huellaCapturada(dedo, plantilla)),
                        error -> siSigueVigente(esta, () -> capturaFallida(error)),
                        () -> siSigueVigente(esta, jpnlHuella::terminarCaptura));
            }
        });
    }

    // ---------- Métodos de apoyo ----------

    private void cargar() {
        ejecutar(() -> tabla.mostrar(servicio.listarConHuellas()));
    }

    private void cargarCarreras() {
        ejecutar(() -> {
            componentes.Combos.llenar(jcbxCarrera, servicio.listarCarreras());
            componentes.Combos.ajustarAncho(jcbxCarrera);   // ancho justo para el nombre de carrera más largo
        });
    }

    private void recargarYSeleccionar(modelo.Estudiante e) {
        detenerCaptura();
        huellasCapturadas.clear();
        cargar();
        control.consulta(false);
        tabla.seleccionar(e);
    }

    /** Si el dedo ya tiene huella (guardada o recién capturada), pregunta si se reemplaza. */
    private boolean confirmarReemplazo(modelo.Dedo dedo) {
        boolean yaExiste = actual.buscarHuella(dedo).isPresent() || huellasCapturadas.containsKey(dedo);
        return !yaExiste
                || util.Mensajes.confirmar(this, "El " + dedo + " ya tiene huella. ¿Desea reemplazarla?");
    }

    /** Deja de esperar el dedo y descarta el resultado de la captura que estaba en curso (si había una). */
    private void detenerCaptura() {
        capturaVigente++;
        biometria.cancelarCaptura();
    }

    /** Ejecuta la acción solo si la captura que terminó es la vigente (no una ya cancelada). */
    private void siSigueVigente(int captura, Runnable accion) {
        if (captura == capturaVigente) {
            accion.run();
        }
    }

    /**
     * Los problemas al capturar se avisan en el área de texto del panel de huella, sin ventana emergente:
     * si la huella ya es de otro estudiante, en rojo. Los demás errores sí se muestran en un mensaje.
     */
    private void capturaFallida(excepciones.BibliotecaException error) {
        if (error.getReaccion() == excepciones.Reaccion.MOSTRAR_ERROR) {
            util.Mensajes.error(this, error);
            jpnlHuella.reiniciar();
        } else {
            jpnlHuella.mostrarAviso(error.getMessage(), error.getReaccion() == excepciones.Reaccion.ALERTA);
        }
    }

    /** El lector avisa desde segundo plano; la pantalla se actualiza en el hilo de Swing. */

    private void huellaCapturada(modelo.Dedo dedo, byte[] plantilla) {
        huellasCapturadas.put(dedo, plantilla);
        jpnlHuella.mostrarResultado("Huella capturada", true);
        jpnlHuella.setInfo(textoHuellas());
    }

    private void mostrar(modelo.Estudiante e) {
        detenerCaptura();
        actual = e;
        huellasCapturadas.clear();
        jtxtCedula.setText(e.getCedula());
        jtxtNombres.setText(e.getNombres());
        jtxtApellidos.setText(e.getApellidos());
        jtxtTelefono.setText(e.getTelefono());
        jtxtCorreo.setText(e.getCorreo());
        componentes.Combos.seleccionar(jcbxCarrera, e.getCarrera());
        jcbxSemestre.setSelectedItem(String.valueOf(e.getSemestre()));
        jpnlHuella.reiniciar();
        jpnlHuella.setInfo(textoHuellas());
        control.consulta(true);
    }

    /**
     * Resumen corto de las huellas del estudiante. Ejemplos:
     *   "Sin huellas. Capture al menos una."
     *   "Por guardar: Índice derecho."
     *   "Registradas: Índice derecho.  Por guardar: Pulgar derecho."
     */
    private String textoHuellas() {
        String guardadas = actual == null ? "" : actual.getHuellas().stream().map(h -> h.getDedo().toString())
                .collect(java.util.stream.Collectors.joining(", "));
        String pendientes = huellasCapturadas.keySet().stream().map(Object::toString)
                .collect(java.util.stream.Collectors.joining(", "));
        if (guardadas.isEmpty() && pendientes.isEmpty()) {
            return "Sin huellas. Capture al menos una.";
        }
        return (guardadas.isEmpty() ? "" : "Registradas: " + guardadas + ".")
                + (guardadas.isEmpty() || pendientes.isEmpty() ? "" : "\n")
                + (pendientes.isEmpty() ? "" : "Por guardar: " + pendientes + ".");
    }

    private void limpiar() {
        for (javax.swing.JTextField t : new javax.swing.JTextField[]{jtxtCedula, jtxtNombres, jtxtApellidos,
            jtxtTelefono, jtxtCorreo}) {
            t.setText("");
        }
        jcbxCarrera.setSelectedIndex(-1);
        jcbxSemestre.setSelectedIndex(-1);          // sin semestre hasta que se elija
        jpnlHuella.reiniciar();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        java.awt.GridBagConstraints gridBagConstraints;

        jpnlContenido = new javax.swing.JPanel();
        jpnlIzquierda = new javax.swing.JPanel();
        jpnlDatosEst = new componentes.PanelGrupo();
        jlblCedula = new javax.swing.JLabel();
        jtxtCedula = new javax.swing.JTextField();
        jlblNombres = new javax.swing.JLabel();
        jtxtNombres = new javax.swing.JTextField();
        jlblApellidos = new javax.swing.JLabel();
        jtxtApellidos = new javax.swing.JTextField();
        jlblTelefono = new javax.swing.JLabel();
        jtxtTelefono = new javax.swing.JTextField();
        jpnlFila2 = new javax.swing.JPanel();
        jlblCorreo = new javax.swing.JLabel();
        jtxtCorreo = new javax.swing.JTextField();
        jlblCarrera = new javax.swing.JLabel();
        jcbxCarrera = new javax.swing.JComboBox<>();
        jlblSemestre = new javax.swing.JLabel();
        jcbxSemestre = new javax.swing.JComboBox<>();
        jpnlBotones = new javax.swing.JPanel();
        jbtnNuevo = new componentes.BotonSistema();
        jbtnGuardar = new componentes.BotonSistema();
        jbtnEditar = new componentes.BotonSistema();
        jbtnEliminar = new componentes.BotonSistema();
        jbtnCancelar = new componentes.BotonSistema();
        jpnlEstReg = new componentes.PanelGrupo();
        barraBuscar = new componentes.BarraBusqueda();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtblEstudiantes = new javax.swing.JTable();
        jpnlHuella = new componentes.PanelHuella();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jpnlContenido.setBackground(new java.awt.Color(255, 255, 255));
        jpnlContenido.setPreferredSize(new java.awt.Dimension(1098, 520));
        jpnlContenido.setLayout(new java.awt.BorderLayout(16, 0));

        jpnlIzquierda.setOpaque(false);
        jpnlIzquierda.setLayout(new java.awt.BorderLayout(0, 16));

        jpnlDatosEst.setTitulo("Datos del estudiante");
        jpnlDatosEst.setLayout(new java.awt.GridBagLayout());

        jlblCedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCedula.setText("Cédula");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 4, 6);
        jpnlDatosEst.add(jlblCedula, gridBagConstraints);

        jtxtCedula.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtxtCedula.setPreferredSize(new java.awt.Dimension(120, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 10, 6);
        jpnlDatosEst.add(jtxtCedula, gridBagConstraints);

        jlblNombres.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblNombres.setText("Nombres");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 4, 6);
        jpnlDatosEst.add(jlblNombres, gridBagConstraints);

        jtxtNombres.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtxtNombres.setPreferredSize(new java.awt.Dimension(120, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 10, 6);
        jpnlDatosEst.add(jtxtNombres, gridBagConstraints);

        jlblApellidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblApellidos.setText("Apellidos");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 4, 6);
        jpnlDatosEst.add(jlblApellidos, gridBagConstraints);

        jtxtApellidos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtxtApellidos.setPreferredSize(new java.awt.Dimension(120, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 10, 6);
        jpnlDatosEst.add(jtxtApellidos, gridBagConstraints);

        jlblTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblTelefono.setText("Teléfono");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 4, 6);
        jpnlDatosEst.add(jlblTelefono, gridBagConstraints);

        jtxtTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtxtTelefono.setPreferredSize(new java.awt.Dimension(120, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 10, 6);
        jpnlDatosEst.add(jtxtTelefono, gridBagConstraints);

        jpnlFila2.setOpaque(false);
        jpnlFila2.setLayout(new java.awt.GridBagLayout());

        jlblCorreo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCorreo.setText("Correo electrónico");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 4, 6);
        jpnlFila2.add(jlblCorreo, gridBagConstraints);

        jtxtCorreo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtxtCorreo.setPreferredSize(new java.awt.Dimension(120, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 10, 6);
        jpnlFila2.add(jtxtCorreo, gridBagConstraints);

        jlblCarrera.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCarrera.setText("Carrera");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 4, 6);
        jpnlFila2.add(jlblCarrera, gridBagConstraints);

        jcbxCarrera.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcbxCarrera.setPreferredSize(new java.awt.Dimension(270, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 10, 6);
        jpnlFila2.add(jcbxCarrera, gridBagConstraints);

        jlblSemestre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblSemestre.setText("Semestre");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 6, 4, 6);
        jpnlFila2.add(jlblSemestre, gridBagConstraints);

        jcbxSemestre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcbxSemestre.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        jcbxSemestre.setPreferredSize(new java.awt.Dimension(90, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 10, 6);
        jpnlFila2.add(jcbxSemestre, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jpnlDatosEst.add(jpnlFila2, gridBagConstraints);

        jpnlBotones.setOpaque(false);
        jpnlBotones.setLayout(new java.awt.GridLayout(1, 5, 10, 0));

        jbtnNuevo.setText("Nuevo");
        jbtnNuevo.setPreferredSize(new java.awt.Dimension(100, 36));
        jbtnNuevo.addActionListener(this::jbtnNuevoActionPerformed);
        jpnlBotones.add(jbtnNuevo);

        jbtnGuardar.setText("Guardar");
        jbtnGuardar.setPrimario(true);
        jbtnGuardar.setPreferredSize(new java.awt.Dimension(100, 36));
        jbtnGuardar.addActionListener(this::jbtnGuardarActionPerformed);
        jpnlBotones.add(jbtnGuardar);

        jbtnEditar.setText("Editar");
        jbtnEditar.setPreferredSize(new java.awt.Dimension(100, 36));
        jbtnEditar.addActionListener(this::jbtnEditarActionPerformed);
        jpnlBotones.add(jbtnEditar);

        jbtnEliminar.setText("Eliminar");
        jbtnEliminar.setPreferredSize(new java.awt.Dimension(100, 36));
        jbtnEliminar.addActionListener(this::jbtnEliminarActionPerformed);
        jpnlBotones.add(jbtnEliminar);

        jbtnCancelar.setText("Cancelar");
        jbtnCancelar.setPreferredSize(new java.awt.Dimension(100, 36));
        jbtnCancelar.addActionListener(this::jbtnCancelarActionPerformed);
        jpnlBotones.add(jbtnCancelar);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 6, 2, 6);
        jpnlDatosEst.add(jpnlBotones, gridBagConstraints);

        jpnlIzquierda.add(jpnlDatosEst, java.awt.BorderLayout.NORTH);

        jpnlEstReg.setTitulo("Estudiantes registrados");
        jpnlEstReg.setLayout(new java.awt.BorderLayout(0, 10));

        barraBuscar.setAyuda("Buscar por cédula o nombre");
        jpnlEstReg.add(barraBuscar, java.awt.BorderLayout.NORTH);


        jScrollPane1.setViewportView(jtblEstudiantes);

        jpnlEstReg.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jpnlIzquierda.add(jpnlEstReg, java.awt.BorderLayout.CENTER);

        jpnlContenido.add(jpnlIzquierda, java.awt.BorderLayout.CENTER);

        jpnlHuella.setTitulo("Huella");
        jpnlHuella.setModoRegistro(true);
        jpnlHuella.setPreferredSize(new java.awt.Dimension(280, 400));
        jpnlContenido.add(jpnlHuella, java.awt.BorderLayout.EAST);

        getContentPane().add(jpnlContenido, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jbtnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNuevoActionPerformed
        nuevo();
    }//GEN-LAST:event_jbtnNuevoActionPerformed

    private void jbtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnGuardarActionPerformed
        guardar();
    }//GEN-LAST:event_jbtnGuardarActionPerformed

    private void jbtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditarActionPerformed
        editar();
    }//GEN-LAST:event_jbtnEditarActionPerformed

    private void jbtnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEliminarActionPerformed
        eliminar();
    }//GEN-LAST:event_jbtnEliminarActionPerformed

    private void jbtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelarActionPerformed
        cancelar();
    }//GEN-LAST:event_jbtnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private componentes.BarraBusqueda barraBuscar;
    private javax.swing.JScrollPane jScrollPane1;
    private componentes.BotonSistema jbtnCancelar;
    private componentes.BotonSistema jbtnEditar;
    private componentes.BotonSistema jbtnEliminar;
    private componentes.BotonSistema jbtnGuardar;
    private componentes.BotonSistema jbtnNuevo;
    private javax.swing.JComboBox<modelo.Carrera> jcbxCarrera;
    private javax.swing.JComboBox<String> jcbxSemestre;
    private javax.swing.JLabel jlblApellidos;
    private javax.swing.JLabel jlblCarrera;
    private javax.swing.JLabel jlblCedula;
    private javax.swing.JLabel jlblCorreo;
    private javax.swing.JLabel jlblNombres;
    private javax.swing.JLabel jlblSemestre;
    private javax.swing.JLabel jlblTelefono;
    private javax.swing.JPanel jpnlBotones;
    private javax.swing.JPanel jpnlFila2;
    private javax.swing.JPanel jpnlContenido;
    private componentes.PanelGrupo jpnlDatosEst;
    private componentes.PanelGrupo jpnlEstReg;
    private componentes.PanelHuella jpnlHuella;
    private javax.swing.JPanel jpnlIzquierda;
    private javax.swing.JTable jtblEstudiantes;
    private javax.swing.JTextField jtxtApellidos;
    private javax.swing.JTextField jtxtCedula;
    private javax.swing.JTextField jtxtCorreo;
    private javax.swing.JTextField jtxtNombres;
    private javax.swing.JTextField jtxtTelefono;
    // End of variables declaration//GEN-END:variables
}
