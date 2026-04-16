package com.parqueo.presentacion;

import com.parqueo.negocio.ParqueoService;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

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
        tabs.addTab("Ingreso", panelIngreso);
        tabs.addTab("Salida", panelSalida);
        tabs.addTab("Activos", panelActivos);
        tabs.addTab("Historial", panelHistorial);
        tabs.addChangeListener(e -> {
            panelActivos.refrescarTabla();
            panelSalida.refrescarTabla();
            panelHistorial.refrescarTabla();
        });
        add(tabs);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
