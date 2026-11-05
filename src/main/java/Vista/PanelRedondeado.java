package Vista;

import javax.swing.*;
import java.awt.*;

// Panel con esquinas redondeadas: pinta un rectangulo redondeado de fondo y un borde suave
public class PanelRedondeado extends JPanel {

    private Color fondo;
    private int radio;

    public PanelRedondeado(Color fondo, int radio) {
        this.fondo = fondo;
        this.radio = radio;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(fondo);
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);
        g.setColor(Estilos.BORDE);
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);
        super.paintComponent(g0);
    }
}
