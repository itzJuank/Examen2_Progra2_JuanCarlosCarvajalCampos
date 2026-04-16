package parqueo.presentacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelTarjeta extends JPanel {
    private final Color colorFondo;

    public PanelTarjeta(LayoutManager layout) {
        this(layout, EstilosUI.COLOR_PANEL);
    }

    public PanelTarjeta(LayoutManager layout, Color colorFondo) {
        super(layout);
        this.colorFondo = colorFondo;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(17, 24, 39, 20));
        g2.fillRoundRect(6, 8, getWidth() - 12, getHeight() - 12, 28, 28);
        g2.setColor(colorFondo);
        g2.fillRoundRect(0, 0, getWidth() - 12, getHeight() - 12, 28, 28);
        g2.dispose();
        super.paintComponent(g);
    }
}
