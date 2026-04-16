package parqueo.presentacion;

import parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
    private final ParqueoService service;
    private final PanelActivos panelActivos;
    private final PanelHistorial panelHistorial;
    private final PanelSalida panelSalida;
    private final PanelIngreso panelIngreso;
    private final TarjetaMetrica tarjetaActivos;
    private final TarjetaMetrica tarjetaHistorial;
    private final TarjetaMetrica tarjetaTarifa;
    private final TarjetaMetrica tarjetaHora;
    private final Timer timerDashboard;

    public MainFrame() {
        this.service = new ParqueoService();
        this.panelActivos = new PanelActivos(service);
        this.panelHistorial = new PanelHistorial(service);
        this.panelSalida = new PanelSalida(service, panelActivos, panelHistorial);
        this.panelIngreso = new PanelIngreso(service, panelActivos);
        this.tarjetaActivos = new TarjetaMetrica("Vehículos activos", "0", "En parqueo ahora", EstilosUI.COLOR_SECUNDARIO);
        this.tarjetaHistorial = new TarjetaMetrica("Registros cobrados", "0", "Movimientos completados", EstilosUI.COLOR_ACENTO);
        this.tarjetaTarifa = new TarjetaMetrica("Tarifa por hora", EstilosUI.formatearMonto(ParqueoService.TARIFA_POR_HORA),
                "Cobro mínimo de una hora", EstilosUI.COLOR_PRIMARIO);
        this.tarjetaHora = new TarjetaMetrica("Hora del sistema", EstilosUI.formatearFechaHora(LocalDateTime.now()),
                "Actualización en vivo", EstilosUI.COLOR_ERROR);
        this.timerDashboard = new Timer(1000, e -> actualizarDashboard());

        setTitle("Sistema de Parqueo Público");
        setSize(1120, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        EstilosUI.configurarTabs(tabs);
        tabs.addTab("Ingreso", panelIngreso);
        tabs.addTab("Salida", panelSalida);
        tabs.addTab("Activos", panelActivos);
        tabs.addTab("Historial", panelHistorial);
        tabs.addChangeListener(e -> {
            panelActivos.refrescarTabla();
            panelSalida.refrescarTabla();
            panelHistorial.refrescarTabla();
            EstilosUI.actualizarColoresTabs(tabs);
            actualizarDashboard();
        });

        PanelTarjeta contenedorTabs = new PanelTarjeta(new BorderLayout(), EstilosUI.COLOR_PANEL);
        contenedorTabs.setBorder(new EmptyBorder(10, 10, 10, 10));
        contenedorTabs.add(tabs, BorderLayout.CENTER);

        JPanel contenido = new JPanel(new BorderLayout(0, 18));
        contenido.setBackground(EstilosUI.COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(18, 18, 18, 18));
        contenido.add(crearEncabezado(), BorderLayout.NORTH);
        contenido.add(crearCentro(contenedorTabs), BorderLayout.CENTER);

        setLayout(new BorderLayout());
        setContentPane(contenido);
        EstilosUI.actualizarColoresTabs(tabs);
        actualizarDashboard();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    private JPanel crearEncabezado() {
        PanelTarjeta encabezado = new PanelTarjeta(new BorderLayout(18, 0), EstilosUI.COLOR_PRIMARIO_OSCURO);
        encabezado.setBorder(new EmptyBorder(20, 22, 20, 22));

        JPanel textos = new JPanel(new BorderLayout(0, 6));
        textos.setOpaque(false);

        JLabel titulo = new JLabel("Sistema de Parqueo Público");
        titulo.setFont(EstilosUI.FUENTE_TITULO);
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Control visual de ingresos, salidas, activos e historial.");
        subtitulo.setFont(EstilosUI.FUENTE_SUBTITULO);
        subtitulo.setForeground(new Color(220, 231, 247));

        textos.add(titulo, BorderLayout.NORTH);
        textos.add(subtitulo, BorderLayout.CENTER);

        JPanel indicadores = new JPanel(new BorderLayout());
        indicadores.setOpaque(false);
        JLabel chip = EstilosUI.crearChip(
                "Tarifa por hora: " + EstilosUI.formatearMonto(ParqueoService.TARIFA_POR_HORA),
                EstilosUI.COLOR_ACENTO,
                EstilosUI.COLOR_TEXTO);
        indicadores.add(chip, BorderLayout.NORTH);

        encabezado.add(textos, BorderLayout.CENTER);
        encabezado.add(indicadores, BorderLayout.EAST);
        return encabezado;
    }

    private JPanel crearCentro(JPanel contenedorTabs) {
        JPanel centro = new JPanel(new BorderLayout(0, 18));
        centro.setOpaque(false);
        centro.add(crearPanelMetricas(), BorderLayout.NORTH);
        centro.add(contenedorTabs, BorderLayout.CENTER);
        return centro;
    }

    private JPanel crearPanelMetricas() {
        JPanel metricas = new JPanel(new GridLayout(1, 4, 14, 0));
        metricas.setOpaque(false);
        metricas.add(tarjetaActivos);
        metricas.add(tarjetaHistorial);
        metricas.add(tarjetaTarifa);
        metricas.add(tarjetaHora);
        return metricas;
    }

    private void actualizarDashboard() {
        int activos = service.obtenerActivos().size();
        int historial = service.obtenerHistorial().size();
        tarjetaActivos.setValor(String.valueOf(activos));
        tarjetaActivos.setDetalle(activos == 1 ? "1 vehículo dentro" : activos + " vehículos dentro");
        tarjetaHistorial.setValor(String.valueOf(historial));
        tarjetaHistorial.setDetalle(historial == 1 ? "1 cobro registrado" : historial + " cobros registrados");
        tarjetaHora.setValor(EstilosUI.formatearFechaHora(LocalDateTime.now()));
        tarjetaHora.setDetalle("Panel principal actualizado");
    }

    @Override
    public void addNotify() {
        super.addNotify();
        timerDashboard.start();
    }

    @Override
    public void removeNotify() {
        timerDashboard.stop();
        super.removeNotify();
    }
}
