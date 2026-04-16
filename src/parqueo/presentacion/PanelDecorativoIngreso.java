package parqueo.presentacion;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class PanelDecorativoIngreso extends JPanel {
    public PanelDecorativoIngreso() {
        setOpaque(false);
        setPreferredSize(new Dimension(320, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        g2.setPaint(new GradientPaint(0, 0, new Color(18, 76, 145), ancho, alto, new Color(31, 151, 155)));
        g2.fillRoundRect(8, 8, ancho - 16, alto - 16, 34, 34);

        g2.setColor(new Color(255, 255, 255, 28));
        g2.fillOval(ancho - 150, 30, 120, 120);
        g2.fillOval(30, alto - 170, 150, 150);
        g2.fillOval(ancho / 2 - 55, alto / 2 - 55, 110, 110);

        pintarPista(g2, 36, 42, ancho - 72, alto - 84);
        pintarEspacio(g2, 66, 78, ancho - 132, 72, new Color(255, 255, 255, 235));
        pintarEspacio(g2, 66, 170, ancho - 132, 72, new Color(255, 255, 255, 210));
        pintarEspacio(g2, 66, 262, ancho - 132, 72, new Color(255, 255, 255, 235));

        pintarVehiculo(g2, 92, 98, new Color(255, 193, 87));
        pintarVehiculo(g2, ancho / 2 - 48, 190, new Color(98, 202, 166));
        pintarVehiculo(g2, ancho - 178, 282, new Color(224, 99, 99));

        g2.dispose();
    }

    private void pintarPista(Graphics2D g2, int x, int y, int ancho, int alto) {
        g2.setColor(new Color(10, 40, 80, 55));
        g2.fillRoundRect(x, y, ancho, alto, 28, 28);
        g2.setColor(new Color(255, 255, 255, 90));
        g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{12f, 12f}, 0f));
        g2.drawLine(x + 24, y + alto / 2, x + ancho - 24, y + alto / 2);
    }

    private void pintarEspacio(Graphics2D g2, int x, int y, int ancho, int alto, Color color) {
        RoundRectangle2D forma = new RoundRectangle2D.Double(x, y, ancho, alto, 24, 24);
        g2.setColor(color);
        g2.fill(forma);
        g2.setColor(new Color(19, 102, 179, 60));
        g2.setStroke(new BasicStroke(2f));
        g2.draw(forma);
    }

    private void pintarVehiculo(Graphics2D g2, int x, int y, Color color) {
        g2.setColor(new Color(17, 24, 39, 25));
        g2.fillRoundRect(x + 4, y + 6, 92, 36, 18, 18);
        g2.setColor(color);
        g2.fillRoundRect(x, y, 92, 36, 18, 18);
        g2.setColor(new Color(255, 255, 255, 180));
        g2.fillRoundRect(x + 18, y + 8, 40, 12, 10, 10);
        g2.setColor(new Color(37, 48, 65));
        g2.fillOval(x + 14, y + 28, 14, 14);
        g2.fillOval(x + 64, y + 28, 14, 14);
    }
}
