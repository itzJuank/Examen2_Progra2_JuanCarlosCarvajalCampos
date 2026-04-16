package parqueo.presentacion;

import parqueo.entidades.Registro;
import parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class PanelIngreso extends JPanel {
    private final ParqueoService service;
    private final PanelActivos panelActivos;
    private final JTextField txtPlaca;
    private final JComboBox<String> cmbTipo;
    private final JLabel lblHoraEntrada;
    private final JLabel lblMensaje;
    private final JLabel lblResumen;
    private final JLabel lblEstadoVisual;
    private final Timer timerReloj;

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
        lblHoraEntrada = EstilosUI.crearValorDestacado(EstilosUI.formatearFechaHora(LocalDateTime.now()));
        lblMensaje = new JLabel(" ");
        lblResumen = EstilosUI.crearChip("Listo para registrar", EstilosUI.COLOR_ACENTO_SUAVE, EstilosUI.COLOR_PRIMARIO_OSCURO);
        lblEstadoVisual = EstilosUI.crearChip("Zona de ingreso activa", EstilosUI.COLOR_EXITO_SUAVE, EstilosUI.COLOR_EXITO);
        JButton btnRegistrar = new JButton("Registrar ingreso");
        btnRegistrar.addActionListener(e -> registrarIngreso());
        timerReloj = new Timer(1000, e -> actualizarReloj());

        EstilosUI.configurarCampo(txtPlaca);
        EstilosUI.configurarCombo(cmbTipo);
        EstilosUI.configurarBotonPrimario(btnRegistrar);
        EstilosUI.mostrarMensajeInformativo(lblMensaje, "La placa se normaliza a mayúsculas al guardar.");
        actualizarReloj();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formulario.add(EstilosUI.crearTitulo("Nuevo ingreso"), gbc);

        gbc.gridy = 1;
        formulario.add(EstilosUI.crearSubtitulo("Registre la entrada del vehículo y actualice el parqueo en tiempo real."), gbc);

        gbc.gridy = 2;
        formulario.add(lblEstadoVisual, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formulario.add(EstilosUI.crearEtiqueta("Placa"), gbc);

        gbc.gridx = 1;
        formulario.add(txtPlaca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formulario.add(EstilosUI.crearEtiqueta("Tipo de vehículo"), gbc);

        gbc.gridx = 1;
        formulario.add(cmbTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formulario.add(EstilosUI.crearEtiqueta("Hora actual"), gbc);

        gbc.gridx = 1;
        formulario.add(lblHoraEntrada, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        formulario.add(lblResumen, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formulario.add(btnRegistrar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formulario.add(lblMensaje, gbc);

        PanelTarjeta decorativo = new PanelTarjeta(new BorderLayout(0, 16), EstilosUI.COLOR_PANEL_SUAVE);
        decorativo.setBorder(new EmptyBorder(20, 20, 20, 20));
        decorativo.add(new PanelDecorativoIngreso(), BorderLayout.CENTER);

        JPanel metricasLaterales = new JPanel(new GridLayout(2, 1, 0, 14));
        metricasLaterales.setOpaque(false);
        metricasLaterales.add(new TarjetaMetrica("Cobro mínimo", EstilosUI.formatearMonto(ParqueoService.TARIFA_POR_HORA),
                "Se factura una hora como base", EstilosUI.COLOR_ACENTO));
        metricasLaterales.add(new TarjetaMetrica("Formato de placa", "5 - 8", "Letras, números y sin espacios", EstilosUI.COLOR_SECUNDARIO));
        decorativo.add(metricasLaterales, BorderLayout.SOUTH);

        JPanel contenido = new JPanel(new BorderLayout(18, 0));
        contenido.setOpaque(false);
        contenido.add(formulario, BorderLayout.CENTER);
        contenido.add(decorativo, BorderLayout.EAST);

        add(contenido, BorderLayout.CENTER);
    }

    private void registrarIngreso() {
        actualizarReloj();
        try {
            String placa = txtPlaca.getText() == null ? "" : txtPlaca.getText().trim().toUpperCase();
            Registro registro = service.registrarIngreso(placa, (String) cmbTipo.getSelectedItem());
            EstilosUI.mostrarMensajeExito(lblMensaje, "Ingreso registrado correctamente");
            lblHoraEntrada.setText(EstilosUI.formatearFechaHora(registro.getHoraEntrada()));
            lblResumen.setText("Activo: " + registro.getVehiculo().getPlaca());
            lblEstadoVisual.setText("Espacio asignado");
            txtPlaca.setText("");
            cmbTipo.setSelectedIndex(0);
            panelActivos.refrescarTabla();
        } catch (Exception ex) {
            lblResumen.setText("Revisión requerida");
            lblEstadoVisual.setText("Validación pendiente");
            EstilosUI.mostrarMensajeError(lblMensaje, ex.getMessage());
        }
    }

    private void actualizarReloj() {
        lblHoraEntrada.setText(EstilosUI.formatearFechaHora(LocalDateTime.now()));
    }

    @Override
    public void addNotify() {
        super.addNotify();
        timerReloj.start();
    }

    @Override
    public void removeNotify() {
        timerReloj.stop();
        super.removeNotify();
    }
}
