package com.parqueo.presentacion;

import com.parqueo.entidades.Registro;
import com.parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelActivos extends JPanel {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final ParqueoService service;
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final JButton btnRefrescar;

    public PanelActivos(ParqueoService service) {
        this.service = service;
        setLayout(new BorderLayout(10, 10));

        modelo = new DefaultTableModel(new Object[]{"Placa", "Tipo", "Hora de Entrada"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> refrescarTabla());

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Vehículos actualmente en parqueo"));
        panelSuperior.add(btnRefrescar);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        refrescarTabla();
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        List<Registro> activos = service.obtenerActivos();
        for (Registro registro : activos) {
            modelo.addRow(new Object[]{
                registro.getVehiculo().getPlaca(),
                registro.getVehiculo().getTipo(),
                registro.getHoraEntrada().format(FORMATO)
            });
        }
    }
}
