package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Pantalla de bienvenida: franja azul oscuro arriba, ilustracion, titulo y boton naranja
public class PanelInicio extends JPanel {

    public PanelInicio(VentanaPrincipal ventana) {
        setLayout(new GridBagLayout());           // centra la tarjeta en la ventana
        setBackground(Estilos.FONDO_INICIO);

        // Tarjeta blanca redondeada
        PanelRedondeado tarjeta = new PanelRedondeado(Estilos.BLANCO, 22);
        tarjeta.setLayout(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(640, 560));

        // Franja superior azul oscuro, con las esquinas de arriba redondeadas
        JPanel franja = new JPanel(new FlowLayout(FlowLayout.LEFT, 28, 18)) {
            @Override
            protected void paintComponent(Graphics g0) {
                Graphics2D g = (Graphics2D) g0;
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(Estilos.OSCURO);
                g.fillRoundRect(0, 0, getWidth(), getHeight() + 22, 22, 22);
                g.setColor(Estilos.NARANJA);
                g.fillRect(0, getHeight() - 4, getWidth(), 4);
            }
        };
        franja.setOpaque(false);
        JLabel ods = new JLabel("ODS 5 · Igualdad de Género");
        ods.setFont(new Font("SansSerif", Font.BOLD, 16));
        ods.setForeground(Estilos.BLANCO);
        franja.add(ods);
        tarjeta.add(franja, BorderLayout.NORTH);

        // Cuerpo
        JPanel cuerpo = new JPanel();
        cuerpo.setOpaque(false);
        cuerpo.setLayout(new BoxLayout(cuerpo, BoxLayout.Y_AXIS));
        cuerpo.setBorder(new EmptyBorder(26, 46, 30, 46));

        IlustracionEquidad dibujo = new IlustracionEquidad();

        // Cada renglon es una etiqueta aparte para que quede centrado
        Font fTitulo = new Font("SansSerif", Font.BOLD, 26);
        Font fDesc = new Font("SansSerif", Font.PLAIN, 15);
        JLabel titulo1 = linea("Diagnóstico de Equidad Salarial", fTitulo, Estilos.OSCURO);
        JLabel titulo2 = linea("entre Mujeres y Hombres", fTitulo, Estilos.OSCURO);
        JLabel desc1 = linea("Analice la nómina, detecte brechas salariales y genere", fDesc, Estilos.GRIS);
        JLabel desc2 = linea("recomendaciones basadas en indicadores.", fDesc, Estilos.GRIS);

        JButton ingresar = Estilos.boton("INGRESAR", Estilos.NARANJA);
        ingresar.setFont(new Font("SansSerif", Font.BOLD, 16));
        ingresar.setBorder(new EmptyBorder(12, 46, 12, 46));
        ingresar.addActionListener(e -> ventana.mostrar(VentanaPrincipal.MENU));

        int cargados = ventana.getGestor().getLista().getTamanio();
        String origen = ventana.getGestor().getOrigenDatos();
        JLabel estado = new JLabel("Empleados registrados: " + cargados + "  ·  " + origen);
        estado.setFont(Estilos.NORMAL);
        estado.setForeground(origen.startsWith("Conectado") ? Estilos.VERDE_PALETA : Estilos.AMARILLO);

        JLabel creditos = new JLabel("Proyecto Integrador · Universidad Simón Bolívar · 2026");
        creditos.setFont(new Font("SansSerif", Font.PLAIN, 12));
        creditos.setForeground(Estilos.GRIS);

        agregar(cuerpo, dibujo, 18);
        agregar(cuerpo, titulo1, 2);
        agregar(cuerpo, titulo2, 14);
        agregar(cuerpo, desc1, 2);
        agregar(cuerpo, desc2, 24);
        agregar(cuerpo, ingresar, 20);
        agregar(cuerpo, estado, 14);
        agregar(cuerpo, creditos, 0);
        tarjeta.add(cuerpo, BorderLayout.CENTER);

        add(tarjeta);
    }

    private JLabel linea(String texto, Font fuente, Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(fuente);
        l.setForeground(color);
        return l;
    }

    private void agregar(JPanel caja, JComponent c, int espacioDebajo) {
        c.setAlignmentX(Component.CENTER_ALIGNMENT);
        caja.add(c);
        caja.add(Box.createVerticalStrut(espacioDebajo));
    }
}
