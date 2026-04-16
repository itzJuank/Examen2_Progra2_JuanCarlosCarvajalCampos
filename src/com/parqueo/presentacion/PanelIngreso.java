package com.parqueo.presentacion;

import com.parqueo.entidades.Registro;
import com.parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PanelIngreso extends JPanel {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final ParqueoService service;
    private final PanelActivos panelActivos;
    private final JTextField txtPlaca;
    private final JComboBox<String> cmbTipo;
    private final JLabel lblHoraEntrada;
    private final JLabel lblMensaje;
    private final JLabel lblResumen;

    public PanelIngreso(ParqueoService service, PanelActivos panelActivos) {
        this.service = service;
        this.panelActivos = panelActivos;
        setLayout(new BorderLayout(0, 18));
        setBackground(EstilosUI.COLOR_FONDO);
        setOpaque(false);

        PanelTarjeta formulario = new PanelTarjeta(new GridBagLayout());
        formulario.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtPlaca = new JTextField(20);
        cmbTipo = new JComboBox<String>(new String[]{"CARRO", "MOTO"});
        lblHoraEntrada = EstilosUI.crearValorDestacado(FORMATO.format(LocalDateTime.now()));
        lblMensaje = new JLabel(" ");
        lblResumen = EstilosUI.crearChip("Listo para registrar", EstilosUI.COLOR_ACENTO_SUAVE, EstilosUI.COLOR_PRIMARIO_OSCURO);
        JButton btnRegistrar = new JButton("Registrar ingreso");
        btnRegistrar.addActionListener(e -> registrarIngreso());

        EstilosUI.configurarCampo(txtPlaca);
        EstilosUI.configurarCombo(cmbTipo);
        EstilosUI.configurarBotonPrimario(btnRegistrar);
        EstilosUI.mostrarMensajeInformativo(lblMensaje, "La placa se normaliza a mayúsculas al guardar.");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formulario.add(EstilosUI.crearTitulo("Nuevo ingreso"), gbc);

        gbc.gridy = 1;
        formulario.add(EstilosUI.crearSubtitulo("Registre la entrada del vehículo y actualice el parqueo en tiempo real."), gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formulario.add(EstilosUI.crearEtiqueta("Placa"), gbc);

        gbc.gridx = 1;
        formulario.add(txtPlaca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formulario.add(EstilosUI.crearEtiqueta("Tipo de vehículo"), gbc);

        gbc.gridx = 1;
        formulario.add(cmbTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formulario.add(EstilosUI.crearEtiqueta("Hora actual"), gbc);

        gbc.gridx = 1;
        formulario.add(lblHoraEntrada, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        formulario.add(lblResumen, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formulario.add(btnRegistrar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formulario.add(lblMensaje, gbc);

        PanelTarjeta informacion = new PanelTarjeta(new GridLayout(3, 1, 0, 14), EstilosUI.COLOR_PANEL_SUAVE);
        informacion.setBorder(new EmptyBorder(20, 20, 20, 20));
        informacion.add(crearBloqueInformacion("Validación segura",
                "Las placas deben incluir letras y números, sin espacios y con longitud entre 5 y 8 caracteres."));
        informacion.add(crearBloqueInformacion("Registro inmediato",
                "Al confirmar el ingreso, el vehículo pasa automáticamente a la pestaña de activos."));
        informacion.add(crearBloqueInformacion("Interfaz clara",
                "Los mensajes de éxito y error se muestran en el panel, con colores y prioridad visual."));

        JPanel contenido = new JPanel(new GridLayout(1, 2, 18, 18));
        contenido.setOpaque(false);
        contenido.add(formulario);
        contenido.add(informacion);

        add(contenido, BorderLayout.CENTER);
    }

    private void registrarIngreso() {
        lblHoraEntrada.setText(FORMATO.format(LocalDateTime.now()));
        try {
            String placa = txtPlaca.getText() == null ? "" : txtPlaca.getText().trim().toUpperCase();
            Registro registro = service.registrarIngreso(placa, (String) cmbTipo.getSelectedItem());
            EstilosUI.mostrarMensajeExito(lblMensaje, "Ingreso registrado correctamente");
            lblHoraEntrada.setText(FORMATO.format(registro.getHoraEntrada()));
            lblResumen.setText("Activo: " + registro.getVehiculo().getPlaca());
            txtPlaca.setText("");
            cmbTipo.setSelectedIndex(0);
            panelActivos.refrescarTabla();
        } catch (Exception ex) {
            lblResumen.setText("Revisión requerida");
            EstilosUI.mostrarMensajeError(lblMensaje, ex.getMessage());
        }
    }

    private JPanel crearBloqueInformacion(String titulo, String descripcion) {
        JPanel bloque = new JPanel(new BorderLayout(0, 8));
        bloque.setOpaque(true);
        bloque.setBackground(EstilosUI.COLOR_PANEL);
        bloque.setBorder(new EmptyBorder(16, 16, 16, 16));
        bloque.add(EstilosUI.crearEtiqueta(titulo), BorderLayout.NORTH);
        bloque.add(EstilosUI.crearSubtitulo("<html><body style='width:240px'>" + descripcion + "</body></html>"),
                BorderLayout.CENTER);
        return bloque;
    }
}
