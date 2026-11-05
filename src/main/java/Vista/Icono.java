package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

// Iconos dibujados con figuras simples (circulos, lineas, rectangulos).
// Se dibujan en vez de usar emojis porque los emojis no se ven bien en Java en Windows.
public class Icono implements Icon {

    public static final int INICIO = 0, REGISTRAR = 1, CONSULTAR = 2, SALARIO = 3, RETIRAR = 4,
                            NOMINA = 5, INDICADORES = 6, INFORME = 7, CASOS = 8, SALIR = 9;

    private int tipo;
    private int tamano;
    private Color color;

    public Icono(int tipo, int tamano, Color color) {
        this.tipo = tipo;
        this.tamano = tamano;
        this.color = color;
    }

    @Override
    public int getIconWidth() {
        return tamano;
    }

    @Override
    public int getIconHeight() {
        return tamano;
    }

    @Override
    public void paintIcon(Component c, Graphics g0, int x, int y) {
        Graphics2D g = (Graphics2D) g0.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.translate(x, y);
        g.scale(tamano / 24.0, tamano / 24.0);     // todos se dibujan en una cuadricula de 24 x 24
        g.setColor(color);
        g.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (tipo == INICIO) {                       // casa
            g.draw(new Line2D.Double(3, 11, 12, 3));
            g.draw(new Line2D.Double(12, 3, 21, 11));
            g.draw(new Rectangle2D.Double(6, 11, 12, 10));
            g.draw(new Rectangle2D.Double(10, 15, 4, 6));
        } else if (tipo == REGISTRAR || tipo == RETIRAR) {   // persona con + o -
            g.draw(new Ellipse2D.Double(5, 3, 8, 8));
            g.draw(new Arc2D.Double(2, 13, 14, 14, 0, 180, Arc2D.OPEN));
            g.draw(new Line2D.Double(17, 10, 23, 10));
            if (tipo == REGISTRAR) {
                g.draw(new Line2D.Double(20, 7, 20, 13));
            }
        } else if (tipo == CONSULTAR) {             // lupa
            g.draw(new Ellipse2D.Double(3, 3, 13, 13));
            g.draw(new Line2D.Double(14, 14, 21, 21));
        } else if (tipo == SALARIO) {               // moneda con signo $
            g.draw(new Ellipse2D.Double(2, 2, 20, 20));
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            FontMetrics fm = g.getFontMetrics();
            g.drawString("$", 12 - fm.stringWidth("$") / 2, 17);
        } else if (tipo == NOMINA) {                // lista
            g.draw(new RoundRectangle2D.Double(4, 2, 16, 20, 3, 3));
            g.draw(new Line2D.Double(8, 8, 16, 8));
            g.draw(new Line2D.Double(8, 12, 16, 12));
            g.draw(new Line2D.Double(8, 16, 13, 16));
        } else if (tipo == INDICADORES) {           // barras
            g.draw(new Line2D.Double(3, 21, 21, 21));
            g.fill(new Rectangle2D.Double(5, 12, 3.5, 8));
            g.fill(new Rectangle2D.Double(10.5, 6, 3.5, 14));
            g.fill(new Rectangle2D.Double(16, 9, 3.5, 11));
        } else if (tipo == INFORME) {               // hoja con esquina doblada
            Path2D hoja = new Path2D.Double();
            hoja.moveTo(5, 2);
            hoja.lineTo(14, 2);
            hoja.lineTo(19, 7);
            hoja.lineTo(19, 22);
            hoja.lineTo(5, 22);
            hoja.closePath();
            g.draw(hoja);
            g.draw(new Line2D.Double(14, 2, 14, 7));
            g.draw(new Line2D.Double(14, 7, 19, 7));
            g.draw(new Line2D.Double(8, 13, 16, 13));
            g.draw(new Line2D.Double(8, 17, 16, 17));
        } else if (tipo == CASOS) {                 // triangulo de advertencia
            Path2D t = new Path2D.Double();
            t.moveTo(12, 2);
            t.lineTo(22, 21);
            t.lineTo(2, 21);
            t.closePath();
            g.draw(t);
            g.draw(new Line2D.Double(12, 9, 12, 14));
            g.fill(new Ellipse2D.Double(10.8, 16.5, 2.4, 2.4));
        } else if (tipo == SALIR) {                 // puerta con flecha
            g.draw(new Line2D.Double(10, 3, 4, 3));
            g.draw(new Line2D.Double(4, 3, 4, 21));
            g.draw(new Line2D.Double(4, 21, 10, 21));
            g.draw(new Line2D.Double(9, 12, 21, 12));
            g.draw(new Line2D.Double(17, 8, 21, 12));
            g.draw(new Line2D.Double(17, 16, 21, 12));
        }
        g.dispose();
    }
}
