package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Boton con esquinas redondeadas. Al pasar el mouse se aclara un poco.
public class BotonRedondeado extends JButton {

    private Color fondo;
    private boolean encima = false;

    public BotonRedondeado(String texto, Color fondo) {
        super(texto);
        this.fondo = fondo;
        setFont(Estilos.NEGRITA);
        setForeground(Color.WHITE);
        setContentAreaFilled(false);     // el fondo lo pintamos nosotros
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new EmptyBorder(10, 20, 10, 20));
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
        });
    }

    public void setFondo(Color fondo) {
        this.fondo = fondo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0.create();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(encima ? fondo.brighter() : fondo);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
        g.dispose();
        super.paintComponent(g0);        // dibuja el texto y el icono
    }
}
