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

public class PanelSalida extends JPanel {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final ParqueoService service;
    private final PanelActivos panelActivos;
    private final PanelHistorial panelHistorial;
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final JLabel lblMonto;
    private final JLabel lblMensaje;

    public PanelSalida(ParqueoService service, PanelActivos panelActivos, PanelHistorial panelHistorial) {
        this.service = service;
        this.panelActivos = panelActivos;
        this.panelHistorial = panelHistorial;
        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(new Object[]{"ID", "Placa", "Tipo", "Hora de Entrada"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        lblMonto = new JLabel("Monto a pagar: ₡0.0");
        lblMensaje = new JLabel(" ");

        JButton btnRegistrarSalida = new JButton("Registrar salida");
        btnRegistrarSalida.addActionListener(e -> registrarSalida());

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Seleccione un vehículo activo para registrar su salida"));

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.add(btnRegistrarSalida);
        panelInferior.add(lblMonto);
        panelInferior.add(lblMensaje);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        refrescarTabla();
    }

    private void registrarSalida() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText("Seleccione un registro activo para procesar la salida");
            return;
        }
        String id = String.valueOf(tabla.getValueAt(fila, 0));
        try {
            Registro registro = service.registrarSalida(id);
            lblMonto.setText("Monto a pagar: ₡" + registro.getMonto());
            lblMensaje.setForeground(new Color(0, 128, 0));
            lblMensaje.setText("Salida registrada correctamente");
            refrescarTabla();
            panelActivos.refrescarTabla();
            panelHistorial.refrescarTabla();
        } catch (Exception ex) {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText(ex.getMessage());
        }
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        List<Registro> activos = service.obtenerActivos();
        for (Registro registro : activos) {
            modelo.addRow(new Object[]{
                registro.getId(),
                registro.getVehiculo().getPlaca(),
                registro.getVehiculo().getTipo(),
                registro.getHoraEntrada().format(FORMATO)
            });
        }
    }
}
