package Vista;

import Estructura.ListaDobleEmpleados;
import Logica.CalculadoraIndicadores;
import Logica.Formato;
import Modelo.Empleado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Consultar: se busca por codigo y se muestra una ficha tipo perfil con los datos del empleado
// y una comparacion con las personas del otro sexo en su mismo cargo.
public class PanelConsultar extends PanelBase {

    private JTextField codigo = Estilos.campoGrande(12);
    private JLabel mensaje = Estilos.etiqueta(" ");

    // Partes de la ficha
    private JPanel ficha;
    private Avatar avatar = new Avatar(84);
    private JLabel nombre = new JLabel();
    private JLabel cargoArea = new JLabel();
    private JPanel insignias = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
    private JLabel valDocumento = new JLabel();
    private JLabel valSalario = new JLabel();
    private JLabel valMinimos = new JLabel();
    private JLabel valCambios = new JLabel();
    private JTextArea comparacion = new JTextArea();
    private JPanel vacio;

    public PanelConsultar(VentanaPrincipal ventana) {
        super(ventana, "Consultar empleado", "Busque a una persona por su código y vea su ficha");

        // ---- Barra de busqueda ----
        PanelRedondeado busqueda = new PanelRedondeado(Estilos.BLANCO, 16);
        busqueda.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 0));
        busqueda.setBorder(new EmptyBorder(16, 18, 16, 18));
        busqueda.add(new JLabel(new Icono(Icono.CONSULTAR, 26, Estilos.OPC_CONSULTAR)));
        JLabel tituloBusqueda = Estilos.etiqueta("Código del empleado");
        tituloBusqueda.setFont(Estilos.NEGRITA);
        busqueda.add(tituloBusqueda);
        busqueda.add(codigo);
        JButton buscar = Estilos.boton("Buscar", Estilos.OPC_CONSULTAR);
        buscar.addActionListener(e -> buscar());
        codigo.addActionListener(e -> buscar());        // tambien al presionar Enter
        busqueda.add(buscar);
        busqueda.add(mensaje);

        // ---- Mensaje cuando todavia no se ha buscado ----
        vacio = new PanelRedondeado(Estilos.BLANCO, 16);
        vacio.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 46));
        JLabel textoVacio = new JLabel("Escriba un código, por ejemplo E005, y presione Buscar.",
                new Icono(Icono.CONSULTAR, 22, Estilos.BORDE), SwingConstants.CENTER);
        textoVacio.setIconTextGap(10);
        textoVacio.setFont(Estilos.NORMAL);
        textoVacio.setForeground(Estilos.GRIS);
        vacio.add(textoVacio);

        // ---- Ficha tipo perfil ----
        ficha = new PanelRedondeado(Estilos.BLANCO, 16);
        ficha.setLayout(new BorderLayout(0, 18));
        ficha.setBorder(new EmptyBorder(22, 24, 22, 24));

        JPanel cabecera = new JPanel(new BorderLayout(18, 0));
        cabecera.setOpaque(false);
        cabecera.add(avatar, BorderLayout.WEST);
        JPanel textos = new JPanel();
        textos.setOpaque(false);
        textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
        nombre.setFont(new Font("SansSerif", Font.BOLD, 22));
        nombre.setForeground(Estilos.OSCURO);
        cargoArea.setFont(Estilos.NORMAL);
        cargoArea.setForeground(Estilos.GRIS);
        insignias.setOpaque(false);
        alinear(nombre);
        alinear(cargoArea);
        alinear(insignias);
        textos.add(Box.createVerticalStrut(6));
        textos.add(nombre);
        textos.add(Box.createVerticalStrut(4));
        textos.add(cargoArea);
        textos.add(Box.createVerticalStrut(10));
        textos.add(insignias);
        cabecera.add(textos, BorderLayout.CENTER);

        JPanel datos = new JPanel(new GridLayout(1, 4, 12, 0));
        datos.setOpaque(false);
        datos.add(Estilos.dato("Documento", valDocumento));
        datos.add(Estilos.dato("Salario mensual", valSalario));
        datos.add(Estilos.dato("Equivale a", valMinimos));
        datos.add(Estilos.dato("Cambios de salario", valCambios));

        comparacion.setFont(Estilos.NORMAL);
        comparacion.setEditable(false);
        comparacion.setLineWrap(true);          // si el texto es largo pasa al siguiente renglon
        comparacion.setWrapStyleWord(true);
        comparacion.setRows(2);                 // siempre deja espacio para dos renglones
        comparacion.setOpaque(true);
        comparacion.setBorder(new EmptyBorder(12, 14, 12, 14));

        ficha.add(cabecera, BorderLayout.NORTH);
        ficha.add(datos, BorderLayout.CENTER);
        ficha.add(comparacion, BorderLayout.SOUTH);

        // ---- Todo en columna ----
        JPanel columna = new JPanel();
        columna.setOpaque(false);
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
        alinear(busqueda);
        alinear(vacio);
        alinear(ficha);
        columna.add(busqueda);
        columna.add(Box.createVerticalStrut(16));
        columna.add(vacio);
        columna.add(ficha);
        contenido.add(columna, BorderLayout.NORTH);
    }

    private void alinear(JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private void buscar() {
        String cod = codigo.getText().trim().toUpperCase();
        Empleado e = ventana.getGestor().buscar(cod);
        if (e == null) {
            Estilos.mensaje(mensaje, "No hay ningún empleado con el código \"" + cod + "\".", false);
            ficha.setVisible(false);
            vacio.setVisible(true);
            return;
        }
        mensaje.setText(" ");
        mostrarFicha(e);
        vacio.setVisible(false);
        ficha.setVisible(true);
    }

    private void mostrarFicha(Empleado e) {
        ListaDobleEmpleados lista = ventana.getGestor().getLista();
        avatar.mostrar(e.getNombre(), e.getSexo());
        nombre.setText(e.getNombre() + "   " + e.getCodigo());
        cargoArea.setText(e.getCargo() + " · " + e.getArea());

        insignias.removeAll();
        insignias.add(Estilos.insignia(e.getNombreNivel(), Estilos.AZUL_MEDIO));
        insignias.add(Estilos.insignia(e.getNombreSexo(),
                e.getSexo() == 'F' ? Estilos.MUJER : Estilos.HOMBRE));
        insignias.revalidate();

        valDocumento.setText(e.getDocumento() == null ? "—" : e.getDocumento());
        valSalario.setText(Formato.pesos(e.getSalario()));
        double minimos = e.getSalario() / CalculadoraIndicadores.SMLMV_2026;
        valMinimos.setText(String.format("%.1f salarios mínimos", minimos).replace('.', ','));
        int cambios = lista.getHistorial().contarCambiosDe(e.getCodigo());
        valCambios.setText(cambios + (cambios == 1 ? " cambio" : " cambios"));

        // Comparacion con el otro sexo en el mismo cargo
        char otro = (e.getSexo() == 'F') ? 'M' : 'F';
        String grupo = (otro == 'M') ? "los hombres" : "las mujeres";
        double promOtro = CalculadoraIndicadores.promedioCargo(lista, e.getCargo(), otro);
        if (promOtro <= 0) {
            ponerComparacion("En el cargo " + e.getCargo() + " no hay personas del otro sexo para comparar.",
                    Estilos.FONDO, Estilos.GRIS);
        } else {
            double diferencia = (e.getSalario() - promOtro) / promOtro * 100;
            String texto = "En el cargo " + e.getCargo() + ", " + grupo + " ganan en promedio "
                    + Formato.pesos(promOtro) + ". ";
            if (diferencia <= -CalculadoraIndicadores.BRECHA_ALERTA) {
                texto += "Esta persona gana " + Formato.porcentaje(-diferencia)
                        + " menos y aparece en Casos por revisar.";
                ponerComparacion(texto, new Color(0xFE, 0xF3, 0xC7), new Color(0x92, 0x40, 0x0E));
            } else if (diferencia < 0) {
                texto += "Esta persona gana " + Formato.porcentaje(-diferencia) + " menos, por debajo del límite de 5 %.";
                ponerComparacion(texto, new Color(0xE8, 0xF5, 0xEE), Estilos.VERDE_PALETA);
            } else {
                texto += "Esta persona gana lo mismo o más que ese promedio.";
                ponerComparacion(texto, new Color(0xE8, 0xF5, 0xEE), Estilos.VERDE_PALETA);
            }
        }
    }

    private void ponerComparacion(String texto, Color fondo, Color letra) {
        comparacion.setText(texto);
        comparacion.setBackground(fondo);
        comparacion.setForeground(letra);
    }

    @Override
    public void alMostrar() {
        codigo.setText("");
        mensaje.setText(" ");
        ficha.setVisible(false);
        vacio.setVisible(true);
    }
}
