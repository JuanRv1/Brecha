package Vista;

import javax.swing.*;
import java.awt.*;

// Circulo con las iniciales del empleado. Morado si es mujer, verde azulado si es hombre.
public class Avatar extends JComponent {

    private String iniciales = "";
    private Color color = Estilos.GRIS;
    private int tamano;

    public Avatar(int tamano) {
        this.tamano = tamano;
        setPreferredSize(new Dimension(tamano, tamano));
        setMinimumSize(new Dimension(tamano, tamano));
    }

    public void mostrar(String nombre, char sexo) {
        // Iniciales: primera letra de la primera y de la segunda palabra
        String[] partes = nombre.trim().split(" ");
        iniciales = partes[0].substring(0, 1).toUpperCase();
        if (partes.length > 1 && !partes[1].isEmpty()) {
            iniciales += partes[1].substring(0, 1).toUpperCase();
        }
        color = (sexo == 'F') ? Estilos.MUJER : Estilos.HOMBRE;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(color);
        g.fillOval(0, 0, tamano, tamano);
        g.setColor(Color.WHITE);
        g.setFont(new Font("SansSerif", Font.BOLD, tamano * 2 / 5));
        FontMetrics fm = g.getFontMetrics();
        int x = (tamano - fm.stringWidth(iniciales)) / 2;
        int y = (tamano - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(iniciales, x, y);
    }
}
