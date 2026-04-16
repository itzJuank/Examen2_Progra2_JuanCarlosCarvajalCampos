package com.parqueo.presentacion;

import com.parqueo.negocio.ParqueoService;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
    public MainFrame() {
        ParqueoService service = new ParqueoService();
        PanelActivos panelActivos = new PanelActivos(service);
        PanelHistorial panelHistorial = new PanelHistorial(service);
        PanelSalida panelSalida = new PanelSalida(service, panelActivos, panelHistorial);
        PanelIngreso panelIngreso = new PanelIngreso(service, panelActivos);

        setTitle("Sistema de Parqueo Público");
        setSize(900, 600);
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
        });

        PanelTarjeta contenedorTabs = new PanelTarjeta(new BorderLayout(), EstilosUI.COLOR_PANEL);
        contenedorTabs.setBorder(new EmptyBorder(10, 10, 10, 10));
        contenedorTabs.add(tabs, BorderLayout.CENTER);

        JPanel contenido = new JPanel(new BorderLayout(0, 18));
        contenido.setBackground(EstilosUI.COLOR_FONDO);
        contenido.setBorder(new EmptyBorder(18, 18, 18, 18));
        contenido.add(crearEncabezado(), BorderLayout.NORTH);
        contenido.add(contenedorTabs, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        setContentPane(contenido);
        EstilosUI.actualizarColoresTabs(tabs);
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
        JLabel chip = EstilosUI.crearChip("NetBeans + Swing + Ant", EstilosUI.COLOR_ACENTO, EstilosUI.COLOR_TEXTO);
        indicadores.add(chip, BorderLayout.NORTH);

        encabezado.add(textos, BorderLayout.CENTER);
        encabezado.add(indicadores, BorderLayout.EAST);
        return encabezado;
    }
}
