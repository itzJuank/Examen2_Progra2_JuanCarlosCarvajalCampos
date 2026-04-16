package com.parqueo.presentacion;

import com.parqueo.entidades.Registro;
import com.parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

public class PanelHistorial extends JPanel {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final ParqueoService service;
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final JLabel lblMensaje;
    private final JLabel lblResumen;

    public PanelHistorial(ParqueoService service) {
        this.service = service;
        setLayout(new BorderLayout(0, 18));
        setOpaque(false);

        modelo = new DefaultTableModel(new Object[]{"ID", "Placa", "Tipo", "Entrada", "Salida", "Monto"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(modelo);
        EstilosUI.configurarTabla(tabla);
        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.addActionListener(e -> eliminarSeleccionado());
        JButton btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> refrescarTabla());
        lblMensaje = new JLabel(" ");
        lblResumen = EstilosUI.crearChip("0 registros", EstilosUI.COLOR_ACENTO_SUAVE, EstilosUI.COLOR_PRIMARIO_OSCURO);

        EstilosUI.configurarBotonPeligro(btnEliminar);
        EstilosUI.configurarBotonSecundario(btnRefrescar);
        EstilosUI.mostrarMensajeInformativo(lblMensaje, "El historial conserva los registros ya cobrados y permite depuración manual.");

        PanelTarjeta cabecera = new PanelTarjeta(new BorderLayout(16, 12), EstilosUI.COLOR_PANEL);
        cabecera.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel textos = new JPanel(new GridLayout(3, 1, 0, 8));
        textos.setOpaque(false);
        textos.add(EstilosUI.crearTitulo("Historial de cobros"));
        textos.add(EstilosUI.crearSubtitulo("Revise movimientos completados, montos cobrados y elimine registros si es necesario."));
        textos.add(lblMensaje);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setOpaque(false);
        acciones.add(lblResumen);
        acciones.add(btnRefrescar);
        acciones.add(btnEliminar);

        cabecera.add(textos, BorderLayout.CENTER);
        cabecera.add(acciones, BorderLayout.EAST);

        PanelTarjeta contenedorTabla = new PanelTarjeta(new BorderLayout(), EstilosUI.COLOR_PANEL);
        contenedorTabla.setBorder(new EmptyBorder(18, 18, 18, 18));
        contenedorTabla.add(EstilosUI.crearScrollTabla(tabla), BorderLayout.CENTER);

        add(cabecera, BorderLayout.NORTH);
        add(contenedorTabla, BorderLayout.CENTER);
        refrescarTabla();
    }

    private void eliminarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            EstilosUI.mostrarMensajeError(lblMensaje, "Seleccione un registro del historial para eliminar");
            return;
        }
        String id = String.valueOf(tabla.getValueAt(fila, 0));
        ConfirmacionDialog dialogo = new ConfirmacionDialog((Frame) SwingUtilities.getWindowAncestor(this));
        dialogo.setVisible(true);
        if (!dialogo.isConfirmado()) {
            EstilosUI.mostrarMensajeError(lblMensaje, "La eliminación fue cancelada");
            return;
        }
        service.eliminarHistorial(id);
        EstilosUI.mostrarMensajeExito(lblMensaje, "Registro eliminado del historial");
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
                EstilosUI.formatearMonto(registro.getMonto())
            });
        }
        lblResumen.setText(historial.size() + (historial.size() == 1 ? " registro" : " registros"));
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
            getContentPane().setBackground(EstilosUI.COLOR_FONDO);
            setLayout(new BorderLayout());

            PanelTarjeta contenedor = new PanelTarjeta(new GridBagLayout(), EstilosUI.COLOR_PANEL);
            contenedor.setBorder(new EmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            contenedor.add(EstilosUI.crearTitulo("Confirmar eliminación"), gbc);

            gbc.gridy = 1;
            contenedor.add(EstilosUI.crearSubtitulo("Esta acción quitará el registro seleccionado del historial."), gbc);

            gbc.gridy = 2;
            JLabel advertencia = EstilosUI.crearChip(
                    "Revise bien el ID antes de continuar",
                    EstilosUI.COLOR_ERROR_SUAVE,
                    EstilosUI.COLOR_ERROR);
            contenedor.add(advertencia, gbc);

            JButton btnCancelar = new JButton("Cancelar");
            EstilosUI.configurarBotonSecundario(btnCancelar);
            btnCancelar.addActionListener(e -> {
                confirmado = false;
                dispose();
            });

            JButton btnConfirmar = new JButton("Confirmar");
            EstilosUI.configurarBotonPeligro(btnConfirmar);
            btnConfirmar.addActionListener(e -> {
                confirmado = true;
                dispose();
            });

            gbc.gridy = 3;
            gbc.gridwidth = 1;
            contenedor.add(btnCancelar, gbc);

            gbc.gridx = 1;
            contenedor.add(btnConfirmar, gbc);

            add(contenedor, BorderLayout.CENTER);

            pack();
            setLocationRelativeTo(getOwner());
            setResizable(false);
        }

        public boolean isConfirmado() {
            return confirmado;
        }
    }
}
