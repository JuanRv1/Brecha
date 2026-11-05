package Vista;

import Logica.Formato;
import Modelo.Empleado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Retirar: se busca al empleado, se muestra un resumen y una zona roja para confirmar.
// La confirmacion queda en la misma pantalla (no en una ventana emergente).
public class PanelRetirar extends PanelBase {

    private static final Color ROJO_CLARO = new Color(0xFD, 0xEC, 0xEC);
    private static final Color VERDE_CLARO = new Color(0xE8, 0xF5, 0xEE);

    private JTextField codigo = Estilos.campoGrande(12);
    private JLabel mensaje = Estilos.etiqueta(" ");

    private JPanel resumen;
    private Avatar avatar = new Avatar(58);
    private JLabel nombre = new JLabel();
    private JLabel detalle = new JLabel();

    private JPanel confirmacion;
    private JLabel pregunta = new JLabel();

    private JPanel exito;
    private JLabel textoExito = new JLabel();

    private Empleado actual;

    public PanelRetirar(VentanaPrincipal ventana) {
        super(ventana, "Retirar empleado", "Quite a una persona de la nómina y de la base de datos");

        // ---- Busqueda ----
        PanelRedondeado busqueda = new PanelRedondeado(Estilos.BLANCO, 16);
        busqueda.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
        busqueda.setBorder(new EmptyBorder(16, 18, 16, 18));
        busqueda.add(new JLabel(new Icono(Icono.RETIRAR, 26, Estilos.OPC_RETIRAR)));
        JLabel tituloBusqueda = Estilos.etiqueta("¿A quién desea retirar?");
        tituloBusqueda.setFont(Estilos.NEGRITA);
        busqueda.add(tituloBusqueda);
        busqueda.add(codigo);
        JButton buscar = Estilos.boton("Buscar", Estilos.OPC_RETIRAR);
        buscar.addActionListener(e -> buscar());
        codigo.addActionListener(e -> buscar());
        busqueda.add(buscar);
        busqueda.add(mensaje);

        // ---- Resumen del empleado encontrado ----
        resumen = new PanelRedondeado(Estilos.BLANCO, 16);
        resumen.setLayout(new BorderLayout(16, 0));
        resumen.setBorder(new EmptyBorder(16, 20, 16, 20));
        resumen.add(avatar, BorderLayout.WEST);
        JPanel textos = new JPanel(new GridLayout(2, 1, 0, 2));
        textos.setOpaque(false);
        nombre.setFont(new Font("SansSerif", Font.BOLD, 18));
        nombre.setForeground(Estilos.OSCURO);
        detalle.setFont(Estilos.NORMAL);
        detalle.setForeground(Estilos.GRIS);
        textos.add(nombre);
        textos.add(detalle);
        resumen.add(textos, BorderLayout.CENTER);

        // ---- Zona de confirmacion (roja) ----
        confirmacion = new PanelRedondeado(ROJO_CLARO, 16);
        confirmacion.setLayout(new BorderLayout(16, 10));
        confirmacion.setBorder(new EmptyBorder(18, 20, 18, 20));
        confirmacion.add(new JLabel(new Icono(Icono.CASOS, 34, Estilos.ROJO)), BorderLayout.WEST);
        JPanel textosConf = new JPanel(new GridLayout(2, 1, 0, 4));
        textosConf.setOpaque(false);
        pregunta.setFont(new Font("SansSerif", Font.BOLD, 16));
        pregunta.setForeground(Estilos.ROJO);
        JLabel aviso = new JLabel("Se elimina de la nómina y de la base de datos, con su historial de salarios. "
                + "Esta acción no se puede deshacer.");
        aviso.setFont(Estilos.NORMAL);
        aviso.setForeground(new Color(0x7F, 0x1D, 0x1D));
        textosConf.add(pregunta);
        textosConf.add(aviso);
        confirmacion.add(textosConf, BorderLayout.CENTER);
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        botones.setOpaque(false);
        JButton si = Estilos.boton("Sí, retirar", Estilos.ROJO);
        si.addActionListener(e -> retirar());
        JButton cancelar = Estilos.boton("Cancelar", Estilos.GRIS);
        cancelar.addActionListener(e -> alMostrar());
        botones.add(si);
        botones.add(cancelar);
        confirmacion.add(botones, BorderLayout.SOUTH);

        // ---- Mensaje de exito (verde) ----
        exito = new PanelRedondeado(VERDE_CLARO, 16);
        exito.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
        exito.setBorder(new EmptyBorder(16, 20, 16, 20));
        textoExito.setFont(Estilos.NEGRITA);
        textoExito.setForeground(Estilos.VERDE_PALETA);
        exito.add(textoExito);

        JPanel columna = new JPanel();
        columna.setOpaque(false);
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
        agregar(columna, busqueda);
        agregar(columna, resumen);
        agregar(columna, confirmacion);
        agregar(columna, exito);
        contenido.add(columna, BorderLayout.NORTH);
    }

    private void agregar(JPanel columna, JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        columna.add(c);
        columna.add(Box.createVerticalStrut(14));
    }

    private void buscar() {
        exito.setVisible(false);
        actual = ventana.getGestor().buscar(codigo.getText().trim().toUpperCase());
        if (actual == null) {
            Estilos.mensaje(mensaje, "No hay ningún empleado con ese código.", false);
            resumen.setVisible(false);
            confirmacion.setVisible(false);
            return;
        }
        mensaje.setText(" ");
        avatar.mostrar(actual.getNombre(), actual.getSexo());
        nombre.setText(actual.getNombre() + "   " + actual.getCodigo());
        detalle.setText(actual.getCargo() + " · " + actual.getArea() + " · " + Formato.pesos(actual.getSalario()));
        pregunta.setText("¿Retirar a " + actual.getNombre() + " de la nómina?");
        resumen.setVisible(true);
        confirmacion.setVisible(true);
    }

    private void retirar() {
        String cod = actual.getCodigo();
        String nom = actual.getNombre();
        if (ventana.getGestor().retirar(cod)) {
            actual = null;
            codigo.setText("");
            resumen.setVisible(false);
            confirmacion.setVisible(false);
            int quedan = ventana.getGestor().getLista().getTamanio();
            textoExito.setText(nom + " (" + cod + ") fue retirado. La nómina queda con " + quedan + " personas.");
            exito.setVisible(true);
        }
    }

    @Override
    public void alMostrar() {
        actual = null;
        codigo.setText("");
        mensaje.setText(" ");
        resumen.setVisible(false);
        confirmacion.setVisible(false);
        exito.setVisible(false);
    }
}
