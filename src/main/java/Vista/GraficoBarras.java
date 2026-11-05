package Vista;

import javax.swing.*;
import java.awt.*;

// Grafica de barras agrupadas: para cada nivel, una barra de mujeres y una de hombres
// con su salario promedio. Debajo de cada grupo se escribe la brecha de ese nivel.
public class GraficoBarras extends JPanel {

    private String[] niveles = new String[0];
    private double[] mujeres = new double[0];    // salario promedio, -1 si no hay nadie
    private double[] hombres = new double[0];
    private double[] brechas = new double[0];
    private int[] estados = new int[0];

    public GraficoBarras() {
        setOpaque(false);
        setPreferredSize(new Dimension(420, 360));
    }

    public void mostrar(String[] niveles, double[] mujeres, double[] hombres, double[] brechas, int[] estados) {
        this.niveles = niveles;
        this.mujeres = mujeres;
        this.hombres = hombres;
        this.brechas = brechas;
        this.estados = estados;
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

        // Tarjeta blanca de fondo
        g.setColor(Color.WHITE);
        g.fillRoundRect(0, 0, w - 1, h - 1, 14, 14);
        g.setColor(Estilos.BORDE);
        g.drawRoundRect(0, 0, w - 1, h - 1, 14, 14);

        g.setColor(Estilos.OSCURO);
        g.setFont(new Font("SansSerif", Font.BOLD, 15));
        g.drawString("Salario promedio por nivel", 22, 32);
        g.setColor(Estilos.GRIS);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString("Debajo de cada nivel: su brecha salarial", 22, 50);

        // Leyenda
        g.setFont(new Font("SansSerif", Font.PLAIN, 13));
        int xLeyenda = w - 210;
        g.setColor(Estilos.MUJER);
        g.fillOval(xLeyenda, 22, 12, 12);
        g.setColor(Estilos.OSCURO);
        g.drawString("Mujeres", xLeyenda + 18, 33);
        g.setColor(Estilos.HOMBRE);
        g.fillOval(xLeyenda + 95, 22, 12, 12);
        g.setColor(Estilos.OSCURO);
        g.drawString("Hombres", xLeyenda + 113, 33);

        if (niveles.length == 0) {
            return;
        }

        // Zona de la grafica
        int izq = 70, der = w - 24, arriba = 74, abajo = h - 70;
        int alto = abajo - arriba;

        // Valor maximo para la escala (redondeado hacia arriba a millones)
        double maximo = 0;
        for (int i = 0; i < niveles.length; i++) {
            maximo = Math.max(maximo, Math.max(mujeres[i], hombres[i]));
        }
        double paso = pasoEscala(maximo);
        double tope = Math.ceil(maximo / paso) * paso;
        if (tope <= 0) {
            tope = 1;
        }

        // Lineas de fondo y valores del eje (en millones)
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        for (double v = 0; v <= tope + 1; v += paso) {
            int y = abajo - (int) (v / tope * alto);
            g.setColor(new Color(0xEE, 0xF0, 0xF3));
            g.drawLine(izq, y, der, y);
            g.setColor(Estilos.GRIS);
            String texto = String.format("%.0f M", v / 1_000_000);
            g.drawString(texto, izq - 10 - g.getFontMetrics().stringWidth(texto), y + 4);
        }

        // Barras
        int anchoGrupo = (der - izq) / niveles.length;
        int anchoBarra = Math.min(46, anchoGrupo / 4);
        for (int i = 0; i < niveles.length; i++) {
            int centro = izq + anchoGrupo * i + anchoGrupo / 2;
            barra(g, centro - anchoBarra - 3, anchoBarra, mujeres[i], tope, abajo, alto, Estilos.MUJER);
            barra(g, centro + 3, anchoBarra, hombres[i], tope, abajo, alto, Estilos.HOMBRE);

            // Nombre del nivel
            g.setColor(Estilos.OSCURO);
            g.setFont(new Font("SansSerif", Font.PLAIN, 13));
            centrar(g, niveles[i], centro, abajo + 22);

            // Brecha del nivel, con el color de su estado
            g.setFont(new Font("SansSerif", Font.BOLD, 12));
            g.setColor(Estilos.colorEstado(estados[i]));
            String b = Double.isNaN(brechas[i]) ? "sin datos" : Logica.Formato.porcentaje(brechas[i]);
            centrar(g, b, centro, abajo + 42);
        }
    }

    // Dibuja una barra y su valor encima
    private void barra(Graphics2D g, int x, int ancho, double valor, double tope, int abajo, int alto, Color color) {
        if (valor <= 0) {
            return;                              // no hay personas de ese sexo en el nivel
        }
        int largo = (int) (valor / tope * alto);
        g.setColor(color);
        g.fillRoundRect(x, abajo - largo, ancho, largo, 6, 6);
        g.fillRect(x, abajo - largo / 2, ancho, largo / 2);   // esquinas de abajo rectas
        g.setColor(Estilos.OSCURO);
        g.setFont(new Font("SansSerif", Font.PLAIN, 11));
        centrar(g, String.format("%.1f M", valor / 1_000_000).replace('.', ','), x + ancho / 2, abajo - largo - 6);
    }

    // Escoge de cuanto en cuanto van las lineas del eje
    private double pasoEscala(double maximo) {
        if (maximo <= 5_000_000) {
            return 1_000_000;
        } else if (maximo <= 12_000_000) {
            return 2_000_000;
        } else if (maximo <= 30_000_000) {
            return 5_000_000;
        }
        return 10_000_000;
    }

    private void centrar(Graphics2D g, String texto, int x, int y) {
        int ancho = g.getFontMetrics().stringWidth(texto);
        g.drawString(texto, x - ancho / 2, y);
    }
}
