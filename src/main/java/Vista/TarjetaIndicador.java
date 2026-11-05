package Vista;

import javax.swing.*;
import java.awt.*;

// Tarjeta de color con un titulo, un numero grande y un texto abajo.
// Se dibuja a mano con Graphics2D: un rectangulo redondeado con degradado y tres textos.
public class TarjetaIndicador extends JPanel {

    private String titulo = "";
    private String valor = "";
    private String pie = "";
    private Color colorArriba = Estilos.AZUL_1;
    private Color colorAbajo = Estilos.AZUL_2;

    public TarjetaIndicador() {
        setOpaque(false);
        setPreferredSize(new Dimension(240, 130));
    }

    public void mostrar(String titulo, String valor, String pie, Color colorArriba, Color colorAbajo) {
        this.titulo = titulo;
        this.valor = valor;
        this.pie = pie;
        this.colorArriba = colorArriba;
        this.colorAbajo = colorAbajo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Fondo con degradado de izquierda a derecha
        g.setPaint(new GradientPaint(0, 0, colorArriba, w, h, colorAbajo));
        g.fillRoundRect(0, 0, w - 1, h - 1, 14, 14);

        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.drawString(titulo, 20, 32);

        // Numero grande alineado a la derecha
        g.setFont(new Font("SansSerif", Font.BOLD, 34));
        int anchoValor = g.getFontMetrics().stringWidth(valor);
        g.drawString(valor, w - anchoValor - 20, 80);

        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g.drawString(pie, 20, h - 18);
    }
}
