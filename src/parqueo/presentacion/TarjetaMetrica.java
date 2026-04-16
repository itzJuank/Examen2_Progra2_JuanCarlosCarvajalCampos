package parqueo.presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TarjetaMetrica extends PanelTarjeta {
    private final JLabel lblTitulo;
    private final JLabel lblValor;
    private final JLabel lblDetalle;

    public TarjetaMetrica(String titulo, String valor, String detalle, Color barraColor) {
        super(new BorderLayout(0, 14), EstilosUI.COLOR_PANEL);
        setBorder(new EmptyBorder(16, 16, 16, 16));
        setPreferredSize(new Dimension(0, 118));

        JPanel barra = new JPanel();
        barra.setBackground(barraColor);
        barra.setPreferredSize(new Dimension(0, 6));

        JPanel contenido = new JPanel(new GridLayout(3, 1, 0, 6));
        contenido.setOpaque(false);

        lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(EstilosUI.FUENTE_ETIQUETA);
        lblTitulo.setForeground(EstilosUI.COLOR_SUBTEXTO);

        lblValor = new JLabel(valor);
        lblValor.setFont(EstilosUI.FUENTE_METRICA);
        lblValor.setForeground(EstilosUI.COLOR_TEXTO);

        lblDetalle = new JLabel(detalle);
        lblDetalle.setFont(EstilosUI.FUENTE_SUBTITULO);
        lblDetalle.setForeground(EstilosUI.COLOR_SUBTEXTO);

        contenido.add(lblTitulo);
        contenido.add(lblValor);
        contenido.add(lblDetalle);

        add(barra, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
    }

    public void setValor(String valor) {
        lblValor.setText(valor);
    }

    public void setDetalle(String detalle) {
        lblDetalle.setText(detalle);
    }
}
