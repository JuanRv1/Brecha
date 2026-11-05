package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

// Dibujo de la pantalla de inicio: una mujer y un hombre sobre una balanza equilibrada.
public class IlustracionEquidad extends JComponent {

    public IlustracionEquidad() {
        setPreferredSize(new Dimension(260, 150));
        setMaximumSize(new Dimension(260, 150));
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int cx = getWidth() / 2;

        // Circulo de fondo suave
        g.setColor(new Color(0xFF, 0x6B, 0x35, 30));
        g.fill(new Ellipse2D.Double(cx - 75, 0, 150, 150));

        // Balanza: barra y base
        g.setColor(Estilos.OSCURO);
        g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(new Line2D.Double(cx - 95, 104, cx + 95, 104));
        Path2D base = new Path2D.Double();
        base.moveTo(cx, 104);
        base.lineTo(cx - 16, 132);
        base.lineTo(cx + 16, 132);
        base.closePath();
        g.fill(base);

        // Mujer (izquierda): cabeza y vestido en forma de triangulo
        int xm = cx - 55;
        g.setColor(Estilos.MUJER);
        g.fill(new Ellipse2D.Double(xm - 13, 32, 26, 26));
        Path2D vestido = new Path2D.Double();
        vestido.moveTo(xm, 60);
        vestido.lineTo(xm - 22, 100);
        vestido.lineTo(xm + 22, 100);
        vestido.closePath();
        g.fill(vestido);

        // Hombre (derecha): cabeza y cuerpo rectangular
        int xh = cx + 55;
        g.setColor(Estilos.HOMBRE);
        g.fill(new Ellipse2D.Double(xh - 13, 32, 26, 26));
        g.fill(new RoundRectangle2D.Double(xh - 17, 60, 34, 40, 10, 10));

        // Signo igual en el centro
        g.setColor(Estilos.NARANJA);
        g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.draw(new Line2D.Double(cx - 12, 66, cx + 12, 66));
        g.draw(new Line2D.Double(cx - 12, 78, cx + 12, 78));
    }
}
