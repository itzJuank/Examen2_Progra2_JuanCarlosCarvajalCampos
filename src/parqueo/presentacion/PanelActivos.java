package parqueo.presentacion;

import parqueo.entidades.Registro;
import parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;

public class PanelActivos extends JPanel {
    private final ParqueoService service;
    private final DefaultTableModel modelo;
    private final JTable tabla;
    private final JButton btnRefrescar;
    private final JLabel lblResumen;
    private final JLabel lblEstado;

    public PanelActivos(ParqueoService service) {
        this.service = service;
        setLayout(new BorderLayout(0, 18));
        setOpaque(false);

        modelo = new DefaultTableModel(new Object[]{"Placa", "Tipo", "Hora de Entrada"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        EstilosUI.configurarTabla(tabla);
        btnRefrescar = new JButton("Refrescar");
        btnRefrescar.addActionListener(e -> refrescarTabla());
        EstilosUI.configurarBotonSecundario(btnRefrescar);
        lblResumen = EstilosUI.crearChip("0 vehículos", EstilosUI.COLOR_EXITO_SUAVE, EstilosUI.COLOR_EXITO);
        lblEstado = new JLabel(" ");
        EstilosUI.mostrarMensajeInformativo(lblEstado, "La tabla refleja los vehículos actualmente dentro del parqueo.");

        PanelTarjeta cabecera = new PanelTarjeta(new BorderLayout(12, 12), EstilosUI.COLOR_PANEL);
        cabecera.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel textos = new JPanel(new GridLayout(3, 1, 0, 8));
        textos.setOpaque(false);
        textos.add(EstilosUI.crearTitulo("Vehículos activos"));
        textos.add(EstilosUI.crearSubtitulo("Consulte de forma rápida los vehículos que permanecen dentro del parqueo."));
        textos.add(lblEstado);

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        acciones.setOpaque(false);
        acciones.add(lblResumen);
        acciones.add(btnRefrescar);

        cabecera.add(textos, BorderLayout.CENTER);
        cabecera.add(acciones, BorderLayout.EAST);

        PanelTarjeta contenedorTabla = new PanelTarjeta(new BorderLayout(), EstilosUI.COLOR_PANEL);
        contenedorTabla.setBorder(new EmptyBorder(18, 18, 18, 18));
        contenedorTabla.add(EstilosUI.crearScrollTabla(tabla), BorderLayout.CENTER);

        add(cabecera, BorderLayout.NORTH);
        add(contenedorTabla, BorderLayout.CENTER);
        refrescarTabla();
    }

    public void refrescarTabla() {
        modelo.setRowCount(0);
        List<Registro> activos = service.obtenerActivos();
        for (Registro registro : activos) {
            modelo.addRow(new Object[]{
                registro.getVehiculo().getPlaca(),
                registro.getVehiculo().getTipo(),
                EstilosUI.formatearFechaHora(registro.getHoraEntrada())
            });
        }
        lblResumen.setText(activos.size() + (activos.size() == 1 ? " vehículo activo" : " vehículos activos"));
    }
}
