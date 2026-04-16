package com.parqueo.presentacion;

import com.parqueo.entidades.Registro;
import com.parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class PanelHistorial extends JPanel {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final ParqueoService service;
    private final DefaultTableModel modelo;
    private final JTable tabla;
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

        tabla = new JTable(modelo);
        tabla.setRowHeight(24);
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> refrescarTabla());
        lblMensaje = new JLabel(" ");

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Historial de registros finalizados"));

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInferior.add(btnEliminar);
        panelInferior.add(btnRefrescar);
        panelInferior.add(lblMensaje);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
        refrescarTabla();
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText("Seleccione un registro del historial para eliminar");
            return;
        }
        String id = String.valueOf(tabla.getValueAt(fila, 0));
        ConfirmacionDialog dialogo = new ConfirmacionDialog((Frame) SwingUtilities.getWindowAncestor(this));
        dialogo.setVisible(true);
        if (!dialogo.isConfirmado()) {
            lblMensaje.setForeground(Color.RED);
            lblMensaje.setText("La eliminación fue cancelada");
            return;
        }
        service.eliminarHistorial(id);
        lblMensaje.setForeground(new Color(0, 128, 0));
        lblMensaje.setText("Registro eliminado del historial");
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

    private static class ConfirmacionDialog extends JDialog {
        private boolean confirmado;

        public ConfirmacionDialog(Frame propietario) {
            super(propietario, "Confirmar eliminación", true);
            construir();
        }

        public ConfirmacionDialog(Dialog propietario) {
            super(propietario, "Confirmar eliminación", true);
            construir();
        }

        private void construir() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            add(new JLabel("¿Desea eliminar el registro seleccionado del historial?"), gbc);

            JButton btnCancelar = new JButton("Cancelar");
            btnCancelar.addActionListener(e -> {
                confirmado = false;
                dispose();
            });

            JButton btnConfirmar = new JButton("Confirmar");
            btnConfirmar.addActionListener(e -> {
                confirmado = true;
                dispose();
            });

            gbc.gridy = 1;
            gbc.gridwidth = 1;
            add(btnCancelar, gbc);

            gbc.gridx = 1;
            add(btnConfirmar, gbc);

            pack();
            setLocationRelativeTo(getOwner());
        }

        public boolean isConfirmado() {
            return confirmado;
        }
    }
}
