package com.parqueo.presentacion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public final class EstilosUI {
    public static final Color COLOR_FONDO = new Color(241, 246, 255);
    public static final Color COLOR_PANEL = new Color(255, 255, 255);
    public static final Color COLOR_PANEL_SUAVE = new Color(248, 251, 255);
    public static final Color COLOR_PRIMARIO = new Color(19, 102, 179);
    public static final Color COLOR_PRIMARIO_OSCURO = new Color(16, 53, 104);
    public static final Color COLOR_SECUNDARIO = new Color(15, 143, 128);
    public static final Color COLOR_ACENTO = new Color(255, 175, 62);
    public static final Color COLOR_ACENTO_SUAVE = new Color(255, 243, 221);
    public static final Color COLOR_EXITO = new Color(31, 138, 84);
    public static final Color COLOR_EXITO_SUAVE = new Color(226, 247, 235);
    public static final Color COLOR_ERROR = new Color(194, 59, 63);
    public static final Color COLOR_ERROR_SUAVE = new Color(252, 232, 232);
    public static final Color COLOR_TEXTO = new Color(35, 41, 53);
    public static final Color COLOR_SUBTEXTO = new Color(98, 108, 124);
    public static final Color COLOR_BORDE = new Color(217, 225, 236);
    public static final Color COLOR_FILA_ALTERNA = new Color(247, 250, 255);
    public static final Color COLOR_SELECCION = new Color(225, 238, 255);
    public static final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 26);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FUENTE_SECCION = new Font("Segoe UI", Font.BOLD, 18);
    public static final Font FUENTE_ETIQUETA = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FUENTE_TABS = new Font("Segoe UI", Font.BOLD, 13);
    public static final Font FUENTE_TABLA = new Font("Segoe UI", Font.PLAIN, 13);
    private static final DecimalFormat FORMATO_MONTO = new DecimalFormat("#,##0.00");

    private EstilosUI() {
    }

    public static JLabel crearTitulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_TITULO);
        lbl.setForeground(COLOR_TEXTO);
        return lbl;
    }

    public static JLabel crearSubtitulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_SUBTITULO);
        lbl.setForeground(COLOR_SUBTEXTO);
        return lbl;
    }

    public static JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(FUENTE_ETIQUETA);
        lbl.setForeground(COLOR_PRIMARIO_OSCURO);
        return lbl;
    }

    public static JLabel crearValorDestacado(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lbl.setForeground(COLOR_TEXTO);
        return lbl;
    }

    public static JLabel crearChip(String texto, Color fondo, Color textoColor) {
        JLabel lbl = new JLabel(texto);
        lbl.setOpaque(true);
        lbl.setBackground(fondo);
        lbl.setForeground(textoColor);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(mezclarConBlanco(textoColor, 0.55f)),
                new EmptyBorder(6, 12, 6, 12)));
        return lbl;
    }

    public static JLabel crearIndicadorMonto(String texto) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setOpaque(true);
        lbl.setBackground(COLOR_ACENTO_SUAVE);
        lbl.setForeground(COLOR_PRIMARIO_OSCURO);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lbl.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 215, 153)),
                new EmptyBorder(14, 18, 14, 18)));
        return lbl;
    }

    public static void configurarCampo(JTextField campo) {
        campo.setFont(FUENTE_NORMAL);
        campo.setForeground(COLOR_TEXTO);
        campo.setCaretColor(COLOR_PRIMARIO_OSCURO);
        campo.setBackground(COLOR_PANEL);
        campo.setBorder(crearBordeCampo());
        campo.setMargin(new Insets(8, 10, 8, 10));
    }

    public static void configurarCombo(JComboBox<String> combo) {
        combo.setFont(FUENTE_NORMAL);
        combo.setForeground(COLOR_TEXTO);
        combo.setBackground(COLOR_PANEL);
        combo.setBorder(crearBordeCampo());
    }

    public static void configurarBotonPrimario(JButton boton) {
        prepararBotonBase(boton);
        boton.setBackground(COLOR_PRIMARIO);
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_PRIMARIO.darker()),
                new EmptyBorder(10, 18, 10, 18)));
    }

    public static void configurarBotonSecundario(JButton boton) {
        prepararBotonBase(boton);
        boton.setBackground(COLOR_PANEL);
        boton.setForeground(COLOR_PRIMARIO_OSCURO);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(10, 18, 10, 18)));
    }

    public static void configurarBotonPeligro(JButton boton) {
        prepararBotonBase(boton);
        boton.setBackground(COLOR_ERROR);
        boton.setForeground(Color.WHITE);
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(163, 34, 39)),
                new EmptyBorder(10, 18, 10, 18)));
    }

    public static void configurarTabla(JTable tabla) {
        tabla.setFont(FUENTE_TABLA);
        tabla.setForeground(COLOR_TEXTO);
        tabla.setRowHeight(28);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setFillsViewportHeight(true);
        tabla.setSelectionBackground(COLOR_SELECCION);
        tabla.setSelectionForeground(COLOR_TEXTO);
        tabla.setBackground(COLOR_PANEL);

        JTableHeader encabezado = tabla.getTableHeader();
        encabezado.setFont(new Font("Segoe UI", Font.BOLD, 13));
        encabezado.setBackground(COLOR_PRIMARIO_OSCURO);
        encabezado.setForeground(Color.WHITE);
        encabezado.setReorderingAllowed(false);
        encabezado.setOpaque(true);
        encabezado.setBorder(BorderFactory.createEmptyBorder());

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel componente = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                componente.setBorder(new EmptyBorder(0, 12, 0, 12));
                componente.setFont(FUENTE_TABLA);
                if (isSelected) {
                    componente.setBackground(COLOR_SELECCION);
                    componente.setForeground(COLOR_TEXTO);
                } else {
                    componente.setBackground(row % 2 == 0 ? COLOR_PANEL : COLOR_FILA_ALTERNA);
                    componente.setForeground(COLOR_TEXTO);
                }
                return componente;
            }
        };

        for (int i = 0; i < tabla.getColumnModel().getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    public static JScrollPane crearScrollTabla(JTable tabla) {
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
        scroll.getViewport().setBackground(COLOR_PANEL);
        return scroll;
    }

    public static void configurarTabs(JTabbedPane tabs) {
        tabs.setFont(FUENTE_TABS);
        tabs.setBackground(COLOR_PANEL);
        tabs.setForeground(COLOR_PRIMARIO_OSCURO);
        tabs.setOpaque(true);
        tabs.setBorder(BorderFactory.createEmptyBorder());
    }

    public static void actualizarColoresTabs(JTabbedPane tabs) {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            boolean seleccionado = i == tabs.getSelectedIndex();
            tabs.setBackgroundAt(i, seleccionado ? COLOR_ACENTO_SUAVE : COLOR_PANEL_SUAVE);
            tabs.setForegroundAt(i, seleccionado ? COLOR_PRIMARIO_OSCURO : COLOR_SUBTEXTO);
        }
    }

    public static void mostrarMensajeExito(JLabel lbl, String mensaje) {
        aplicarMensaje(lbl, mensaje, COLOR_EXITO, COLOR_EXITO_SUAVE);
    }

    public static void mostrarMensajeError(JLabel lbl, String mensaje) {
        aplicarMensaje(lbl, mensaje, COLOR_ERROR, COLOR_ERROR_SUAVE);
    }

    public static void mostrarMensajeInformativo(JLabel lbl, String mensaje) {
        aplicarMensaje(lbl, mensaje, COLOR_PRIMARIO_OSCURO, new Color(229, 239, 253));
    }

    public static String formatearMonto(double monto) {
        return "₡" + FORMATO_MONTO.format(monto);
    }

    private static void aplicarMensaje(JLabel lbl, String mensaje, Color texto, Color fondo) {
        lbl.setText(mensaje == null || mensaje.trim().isEmpty() ? " " : mensaje);
        lbl.setForeground(texto);
        lbl.setOpaque(true);
        lbl.setBackground(fondo);
        lbl.setBorder(new EmptyBorder(10, 12, 10, 12));
        lbl.setFont(FUENTE_NORMAL);
    }

    private static void prepararBotonBase(JButton boton) {
        boton.setFont(FUENTE_BOTON);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setOpaque(true);
    }

    private static Border crearBordeCampo() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                new EmptyBorder(6, 8, 6, 8));
    }

    private static Color mezclarConBlanco(Color color, float factor) {
        int red = (int) (color.getRed() + (255 - color.getRed()) * factor);
        int green = (int) (color.getGreen() + (255 - color.getGreen()) * factor);
        int blue = (int) (color.getBlue() + (255 - color.getBlue()) * factor);
        return new Color(red, green, blue);
    }

    public static void hacerTransparente(JComponent componente) {
        componente.setOpaque(false);
    }
}
