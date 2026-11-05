package Vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Menu de la izquierda, azul oscuro. Se ve en todas las pantallas menos en la de inicio.
// La opcion de la pantalla abierta se marca con otro color.
public class MenuLateral extends JPanel {

    private static final Color FONDO_ACTIVO = Estilos.AZUL_MEDIO;
    private static final Color TEXTO = new Color(0xC9, 0xD3, 0xE3);

    private VentanaPrincipal ventana;
    private String[] pantallas = new String[10];
    private JButton[] botones = new JButton[10];
    private int cantidad = 0;

    public MenuLateral(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        setBackground(Estilos.OSCURO);
        setPreferredSize(new Dimension(220, 600));

        // Encabezado con el nombre del sistema
        JPanel logo = new JPanel();
        logo.setOpaque(false);
        logo.setLayout(new BoxLayout(logo, BoxLayout.Y_AXIS));
        logo.setBorder(new EmptyBorder(24, 22, 22, 16));
        JLabel ods = new JLabel("ODS 5");
        ods.setFont(new Font("SansSerif", Font.BOLD, 22));
        ods.setForeground(Estilos.NARANJA);
        JLabel nombre = new JLabel("Equidad Salarial");
        nombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        nombre.setForeground(Estilos.BLANCO);
        logo.add(ods);
        logo.add(nombre);
        add(logo, BorderLayout.NORTH);

        // Opciones
        JPanel opciones = new JPanel();
        opciones.setOpaque(false);
        opciones.setLayout(new BoxLayout(opciones, BoxLayout.Y_AXIS));
        opciones.setBorder(new EmptyBorder(4, 10, 4, 10));
        agregar(opciones, "Inicio", Icono.INICIO, VentanaPrincipal.MENU);
        agregar(opciones, "Registrar", Icono.REGISTRAR, VentanaPrincipal.REGISTRAR);
        agregar(opciones, "Consultar", Icono.CONSULTAR, VentanaPrincipal.CONSULTAR);
        agregar(opciones, "Salario", Icono.SALARIO, VentanaPrincipal.MODIFICAR);
        agregar(opciones, "Retirar", Icono.RETIRAR, VentanaPrincipal.RETIRAR);
        agregar(opciones, "Nómina", Icono.NOMINA, VentanaPrincipal.NOMINA);
        agregar(opciones, "Consulta por salario", Icono.CONSULTAR, VentanaPrincipal.SALARIOS);
        agregar(opciones, "Indicadores", Icono.INDICADORES, VentanaPrincipal.INDICADORES);
        agregar(opciones, "Informe", Icono.INFORME, VentanaPrincipal.INFORME);
        agregar(opciones, "Casos por revisar", Icono.CASOS, VentanaPrincipal.CASOS);
        add(opciones, BorderLayout.CENTER);

        // Salir, abajo
        JButton salir = botonMenu("Salir", Icono.SALIR);
        salir.addActionListener(e -> System.exit(0));
        JPanel abajo = new JPanel(new BorderLayout());
        abajo.setOpaque(false);
        abajo.setBorder(new EmptyBorder(4, 10, 18, 10));
        abajo.add(salir, BorderLayout.CENTER);
        add(abajo, BorderLayout.SOUTH);
    }

    private void agregar(JPanel opciones, String texto, int icono, String pantalla) {
        JButton b = botonMenu(texto, icono);
        b.addActionListener(e -> ventana.mostrar(pantalla));
        opciones.add(b);
        opciones.add(Box.createVerticalStrut(4));
        pantallas[cantidad] = pantalla;
        botones[cantidad] = b;
        cantidad++;
    }

    private JButton botonMenu(String texto, int icono) {
        JButton b = new JButton(texto, new Icono(icono, 20, TEXTO));
        b.setFont(new Font("SansSerif", Font.PLAIN, 14));
        b.setForeground(TEXTO);
        b.setBackground(Estilos.OSCURO);
        b.setOpaque(true);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setIconTextGap(12);
        b.setBorder(new EmptyBorder(9, 12, 9, 12));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        b.setAlignmentX(Component.LEFT_ALIGNMENT);
        return b;
    }

    // Marca la opcion de la pantalla que esta abierta
    public void marcar(String pantalla) {
        for (int i = 0; i < cantidad; i++) {
            boolean activo = pantallas[i].equals(pantalla);
            botones[i].setBackground(activo ? FONDO_ACTIVO : Estilos.OSCURO);
            botones[i].setForeground(activo ? Estilos.BLANCO : TEXTO);
            botones[i].setFont(new Font("SansSerif", activo ? Font.BOLD : Font.PLAIN, 14));
        }
    }
}
