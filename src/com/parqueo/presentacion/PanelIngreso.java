package com.parqueo.presentacion;

import com.parqueo.entidades.Registro;
import com.parqueo.negocio.ParqueoService;
import java.awt.Color;
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

public class PanelIngreso extends JPanel {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final ParqueoService service;
    private final PanelActivos panelActivos;
    private final JTextField txtPlaca;
    private final JComboBox<String> cmbTipo;
    private final JLabel lblHoraEntrada;
    private final JLabel lblMensaje;

    public PanelIngreso(ParqueoService service, PanelActivos panelActivos) {
        this.service = service;
        this.panelActivos = panelActivos;
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtPlaca = new JTextField(20);
        cmbTipo = new JComboBox<String>(new String[]{"CARRO", "MOTO"});
        lblHoraEntrada = new JLabel(FORMATO.format(LocalDateTime.now()));
        lblMensaje = new JLabel(" ");
        JButton btnRegistrar = new JButton("Registrar ingreso");
        btnRegistrar.addActionListener(e -> registrarIngreso());

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Placa:"), gbc);

        gbc.gridx = 1;
        add(txtPlaca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Tipo:"), gbc);

        gbc.gridx = 1;
        add(cmbTipo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Hora actual:"), gbc);

        gbc.gridx = 1;
        add(lblHoraEntrada, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnRegistrar, gbc);

        gbc.gridy = 4;
        add(lblMensaje, gbc);
    }

    private void registrarIngreso() {
        lblHoraEntrada.setText(FORMATO.format(LocalDateTime.now()));
        try {
            Registro registro = service.registrarIngreso(txtPlaca.getText(), (String) cmbTipo.getSelectedItem());
            lblMensaje.setForeground(new Color(0, 128, 0));
            lblMensaje.setText("Ingreso registrado correctamente");
            lblHoraEntrada.setText(FORMATO.format(registro.getHoraEntrada()));
            txtPlaca.setText("");
            cmbTipo.setSelectedIndex(0);
            panelActivos.refrescarTabla();
        } catch (Exception ex) {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText(ex.getMessage());
        }
    }
}
