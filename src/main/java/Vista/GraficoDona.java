package Vista;

import javax.swing.*;
import java.awt.*;

// Grafica de dona: muestra que parte de un grupo son mujeres y que parte hombres.
// El anillo se dibuja con dos arcos (drawArc) de grosor 22.
public class GraficoDona extends JPanel {

    private String titulo;
    private int mujeres;
    private int hombres;

    public GraficoDona(String titulo) {
        this.titulo = titulo;
        setOpaque(false);
        setPreferredSize(new Dimension(200, 360));
    }

    public void mostrar(int mujeres, int hombres) {
        this.mujeres = mujeres;
        this.hombres = hombres;
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

        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, w - 1, h - 1, 14, 14);
        g.setColor(Estilos.BORDE);
        g.drawRoundRect(0, 0, w - 1, h - 1, 14, 14);

        g.setColor(Estilos.OSCURO);
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.drawString(titulo, 22, 32);

        int total = mujeres + hombres;

        // Medidas del anillo
        int diametro = Math.min(w - 80, h - 150);
        int x = (w - diametro) / 2;
        int y = 60;
        g.setStroke(new BasicStroke(22, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));

        if (total == 0) {
            g.setColor(Estilos.BORDE);
            g.drawOval(x, y, diametro, diametro);
            g.setColor(Estilos.GRIS);
            g.setFont(new Font("SansSerif", Font.PLAIN, 13));
            centrar(g, "Sin datos", w / 2, y + diametro / 2 + 5);
            return;
        }

        // Los arcos se miden en grados: 360 grados es el circulo completo
        int gradosMujeres = (int) Math.round(360.0 * mujeres / total);
        g.setColor(Estilos.MUJER);
        g.drawArc(x, y, diametro, diametro, 90, -gradosMujeres);           // empieza arriba, va a la derecha
        g.setColor(Estilos.HOMBRE);
        g.drawArc(x, y, diametro, diametro, 90 - gradosMujeres, -(360 - gradosMujeres));
        g.setStroke(new BasicStroke(1));

        // Porcentaje de mujeres en el centro
        g.setColor(Estilos.OSCURO);
        g.setFont(new Font("SansSerif", Font.BOLD, 26));
        centrar(g, Math.round(100.0 * mujeres / total) + " %", w / 2, y + diametro / 2 + 6);
        g.setColor(Estilos.GRIS);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        centrar(g, "mujeres", w / 2, y + diametro / 2 + 26);

        // Cantidades abajo, como en la referencia
        int yAbajo = y + diametro + 52;
        dato(g, w / 4, yAbajo, mujeres, "Mujeres", Estilos.MUJER);
        dato(g, 3 * w / 4, yAbajo, hombres, "Hombres", Estilos.HOMBRE);
    }

    private void dato(Graphics2D g, int centro, int y, int cantidad, String texto, Color color) {
        g.setFont(new Font("SansSerif", Font.BOLD, 24));
        String numero = String.valueOf(cantidad);
        int ancho = g.getFontMetrics().stringWidth(numero);
        g.setColor(color);
        g.fillOval(centro - ancho / 2 - 16, y - 10, 9, 9);
        g.setColor(Estilos.OSCURO);
        g.drawString(numero, centro - ancho / 2, y);
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        centrar(g, texto, centro, y + 20);
    }

    private void centrar(Graphics2D g, String texto, int x, int y) {
        int ancho = g.getFontMetrics().stringWidth(texto);
        g.drawString(texto, x - ancho / 2, y);
    }
}
