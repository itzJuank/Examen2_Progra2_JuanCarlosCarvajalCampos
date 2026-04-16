package com.parqueo.presentacion;

import com.parqueo.entidades.Registro;
import com.parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelHistorial extends JPanel {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final ParqueoService service;
    private final DefaultTableModel modelo;
    private final JLabel lblMensaje;

    public PanelHistorial(ParqueoService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(new Object[]{"ID", "Placa", "Tipo", "Entrada", "Salida", "Monto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable tabla = new JTable(modelo);
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> refrescarTabla());
        lblMensaje = new JLabel("El historial estará disponible en v1.1");
        lblMensaje.setForeground(Color.RED);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.add(btnRefrescar);
        panelInferior.add(lblMensaje);

        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        refrescarTabla();
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        List<Registro> historial = service.obtenerHistorial();
        for (Registro registro : historial) {
            modelo.addRow(new Object[]{
                registro.getId(),
                registro.getVehiculo().getPlaca(),
                registro.getVehiculo().getTipo(),
                registro.getHoraEntrada().format(FORMATO),
                registro.getHoraSalida() == null ? "" : registro.getHoraSalida().format(FORMATO),
                registro.getMonto()
            });
        }
    }
}
