package com.parqueo.presentacion;

import com.parqueo.entidades.Registro;
import com.parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

public class PanelSalida extends JPanel {
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final ParqueoService service;
    private final PanelActivos panelActivos;
    private final PanelHistorial panelHistorial;
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final JLabel lblMonto;
    private final JLabel lblMensaje;
    private final JLabel lblResumen;

    public PanelSalida(ParqueoService service, PanelActivos panelActivos, PanelHistorial panelHistorial) {
        this.service = service;
        this.panelActivos = panelActivos;
        this.panelHistorial = panelHistorial;
        setLayout(new BorderLayout(0, 18));
        setOpaque(false);

        modelo = new DefaultTableModel(new Object[]{"ID", "Placa", "Tipo", "Hora de Entrada"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        EstilosUI.configurarTabla(tabla);
        lblMonto = EstilosUI.crearIndicadorMonto("Monto a pagar: " + EstilosUI.formatearMonto(0.0));
        lblMensaje = new JLabel(" ");
        lblResumen = EstilosUI.crearChip("0 pendientes", EstilosUI.COLOR_ACENTO_SUAVE, EstilosUI.COLOR_PRIMARIO_OSCURO);

        JButton btnRegistrarSalida = new JButton("Registrar salida");
        btnRegistrarSalida.addActionListener(e -> registrarSalida());
        EstilosUI.configurarBotonPrimario(btnRegistrarSalida);
        EstilosUI.mostrarMensajeInformativo(lblMensaje, "Seleccione un activo para calcular el cobro y moverlo al historial.");

        PanelTarjeta cabecera = new PanelTarjeta(new BorderLayout(16, 12), EstilosUI.COLOR_PANEL);
        cabecera.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel textos = new JPanel(new GridLayout(3, 1, 0, 8));
        textos.setOpaque(false);
        textos.add(EstilosUI.crearTitulo("Registro de salida"));
        textos.add(EstilosUI.crearSubtitulo("Procese el cobro, libere el espacio y actualice el historial de movimientos."));
        textos.add(lblMensaje);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setOpaque(false);
        acciones.add(lblResumen);
        acciones.add(btnRegistrarSalida);

        JPanel cabeceraSuperior = new JPanel(new BorderLayout(12, 12));
        cabeceraSuperior.setOpaque(false);
        cabeceraSuperior.add(textos, BorderLayout.CENTER);
        cabeceraSuperior.add(acciones, BorderLayout.EAST);

        cabecera.add(cabeceraSuperior, BorderLayout.NORTH);
        cabecera.add(lblMonto, BorderLayout.SOUTH);

        PanelTarjeta contenedorTabla = new PanelTarjeta(new BorderLayout(), EstilosUI.COLOR_PANEL);
        contenedorTabla.setBorder(new EmptyBorder(18, 18, 18, 18));
        contenedorTabla.add(EstilosUI.crearScrollTabla(tabla), BorderLayout.CENTER);

        add(cabecera, BorderLayout.NORTH);
        add(contenedorTabla, BorderLayout.CENTER);
        refrescarTabla();
    }

    private void registrarSalida() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            EstilosUI.mostrarMensajeError(lblMensaje, "Seleccione un registro activo para procesar la salida");
            return;
        }
        String id = String.valueOf(tabla.getValueAt(fila, 0));
        try {
            Registro registro = service.registrarSalida(id);
            lblMonto.setText("Monto a pagar: " + EstilosUI.formatearMonto(registro.getMonto()));
            EstilosUI.mostrarMensajeExito(lblMensaje, "Salida registrada correctamente");
            refrescarTabla();
            panelActivos.refrescarTabla();
            panelHistorial.refrescarTabla();
        } catch (Exception ex) {
            EstilosUI.mostrarMensajeError(lblMensaje, ex.getMessage());
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
        lblResumen.setText(activos.size() + (activos.size() == 1 ? " pendiente" : " pendientes"));
    }
}
