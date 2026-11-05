package Vista;

import javax.swing.*;
import java.awt.*;

// Tarjeta blanca con un numero grande, usada en el resumen del panel principal
public class TarjetaResumen extends JComponent {

    private String valor = "";
    private String etiqueta;
    private Color color;

    public TarjetaResumen(String etiqueta, Color color) {
        this.etiqueta = etiqueta;
        this.color = color;
        setPreferredSize(new Dimension(200, 96));
    }

    public void mostrar(String valor) {
        this.valor = valor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();

        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, w - 1, h - 1, 16, 16);
        g.setColor(Estilos.BORDE);
        g.drawRoundRect(0, 0, w - 1, h - 1, 16, 16);

        // Raya de color a la izquierda
        g.setColor(color);
        g.fillRoundRect(0, 0, 8, h - 1, 8, 8);
        g.fillRect(4, 0, 4, h - 1);

        g.setColor(Estilos.OSCURO);
        g.setFont(new Font("SansSerif", Font.BOLD, 30));
        g.drawString(valor, 26, 48);
        g.setColor(Estilos.GRIS);
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g.drawString(etiqueta, 26, 74);
    }
}
