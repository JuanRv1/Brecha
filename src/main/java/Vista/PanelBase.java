package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Plantilla de las pantallas: arriba una barra blanca con el titulo y una descripcion;
// en el centro, el contenido de cada pantalla. Para cambiar de pantalla se usa el menu lateral.
public abstract class PanelBase extends JPanel {

    protected VentanaPrincipal ventana;
    protected JPanel contenido = new JPanel(new BorderLayout(12, 12));

    public PanelBase(VentanaPrincipal ventana, String titulo, String descripcion) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Estilos.FONDO);

        JPanel barra = new JPanel(new GridLayout(2, 1, 0, 2));
        barra.setBackground(Estilos.BLANCO);
        barra.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Estilos.BORDE),
                new EmptyBorder(16, 26, 14, 26)));
        JLabel t = new JLabel(titulo);
        t.setFont(new Font("SansSerif", Font.BOLD, 20));
        t.setForeground(Estilos.OSCURO);
        JLabel d = new JLabel(descripcion);
        d.setFont(Estilos.NORMAL);
        d.setForeground(Estilos.GRIS);
        barra.add(t);
        barra.add(d);
        add(barra, BorderLayout.NORTH);

        contenido.setOpaque(false);
        contenido.setBorder(new EmptyBorder(20, 26, 20, 26));
        add(contenido, BorderLayout.CENTER);
    }

    // Se ejecuta cada vez que se abre la pantalla, para mostrar datos actualizados
    public void alMostrar() {
    }
}
