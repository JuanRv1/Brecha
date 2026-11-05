package Vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Tarjeta de color del panel principal: icono, titulo y descripcion.
// Al pasar el mouse se aclara un poco; al hacer clic abre su pantalla.
public class TarjetaOpcion extends JComponent {

    private String titulo;
    private String descripcion;
    private Icono icono;
    private Color color;
    private Color colorTexto;
    private boolean encima = false;

    public TarjetaOpcion(String titulo, String descripcion, int tipoIcono, Color color,
                         Color colorTexto, Runnable alHacerClic) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.color = color;
        this.colorTexto = colorTexto;
        this.icono = new Icono(tipoIcono, 30, colorTexto);
        setPreferredSize(new Dimension(220, 120));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                encima = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                encima = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                alHacerClic.run();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        Color fondo = encima ? color.brighter() : color;
        g.setPaint(new GradientPaint(0, 0, fondo, w, h, mezclarConBlanco(fondo)));
        g.fillRoundRect(0, 0, w - 1, h - 1, 18, 18);

        // Circulo claro detras del icono
        g.setColor(new Color(255, 255, 255, 50));
        g.fillOval(18, 18, 46, 46);
        icono.paintIcon(this, g, 26, 26);

        g.setColor(colorTexto);
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        g.drawString(titulo, 78, 40);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
        g.drawString(descripcion, 78, 60);
    }

    // Color un poco mas claro para el degradado
    private Color mezclarConBlanco(Color c) {
        int r = c.getRed() + (255 - c.getRed()) / 4;
        int gr = c.getGreen() + (255 - c.getGreen()) / 4;
        int b = c.getBlue() + (255 - c.getBlue()) / 4;
        return new Color(r, gr, b);
    }
}
