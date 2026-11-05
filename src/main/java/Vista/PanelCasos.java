package Vista;

import Estructura.NodoCola;
import Logica.Formato;
import Logica.GestorEmpleados;
import Logica.RevisionCasos;
import Modelo.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Casos por revisar: personas que ganan menos que el otro sexo en el mismo cargo.
// Se atienden en orden de llegada (cola): el primero de la fila es el que se revisa.
public class PanelCasos extends PanelBase {

    private JLabel pendientes = Estilos.etiqueta(" ");
    private JLabel tituloCaso = new JLabel();
    private JTextArea casoActual = new JTextArea();
    private JButton revisado = Estilos.boton("Marcar como revisado", Estilos.VERDE);
    private JScrollPane scroll = new JScrollPane();

    public PanelCasos(VentanaPrincipal ventana) {
        super(ventana, "Casos por revisar",
                "Diferencias de salario entre mujeres y hombres que ocupan el mismo cargo");

        pendientes.setFont(Estilos.SUBTITULO);
        tituloCaso.setFont(Estilos.NEGRITA);
        tituloCaso.setForeground(Estilos.OSCURO);
        casoActual.setFont(Estilos.NORMAL);
        casoActual.setForeground(Estilos.OSCURO);
        casoActual.setEditable(false);
        casoActual.setLineWrap(true);          // parte las lineas largas
        casoActual.setWrapStyleWord(true);
        casoActual.setOpaque(false);

        JButton generar = Estilos.boton("Volver a buscar casos", Estilos.OSCURO);
        generar.addActionListener(e -> {
            ventana.getGestor().generarCasos();
            alMostrar();
        });
        revisado.addActionListener(e -> {
            ventana.getGestor().marcarCasoRevisado();
            alMostrar();
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botones.setOpaque(false);
        botones.add(revisado);
        botones.add(generar);

        JPanel tarjeta = Estilos.tarjeta();
        tarjeta.setLayout(new BorderLayout(10, 12));
        JPanel arriba = new JPanel(new BorderLayout(4, 6));
        arriba.setOpaque(false);
        arriba.add(pendientes, BorderLayout.NORTH);
        arriba.add(tituloCaso, BorderLayout.SOUTH);
        tarjeta.add(arriba, BorderLayout.NORTH);
        tarjeta.add(casoActual, BorderLayout.CENTER);
        tarjeta.add(botones, BorderLayout.SOUTH);

        JPanel parteLista = new JPanel(new BorderLayout(6, 6));
        parteLista.setOpaque(false);
        JLabel titulo = Estilos.etiqueta("Casos en espera, en orden de atención");
        titulo.setFont(Estilos.NEGRITA);
        parteLista.add(titulo, BorderLayout.NORTH);
        parteLista.add(scroll, BorderLayout.CENTER);

        contenido.add(tarjeta, BorderLayout.NORTH);
        contenido.add(parteLista, BorderLayout.CENTER);
    }

    @Override
    public void alMostrar() {
        GestorEmpleados g = ventana.getGestor();
        int total = g.getCasos().getTamanio();
        Empleado primero = g.getCasos().verFrente();

        if (primero == null) {
            pendientes.setText("No hay casos pendientes");
            pendientes.setForeground(Estilos.VERDE);
            tituloCaso.setText(" ");
            casoActual.setText("No se encontraron personas que ganen 5 % o más por debajo "
                    + "del otro sexo en su mismo cargo.");
            revisado.setVisible(false);
        } else {
            pendientes.setText(total + (total == 1 ? " caso pendiente" : " casos pendientes"));
            pendientes.setForeground(Estilos.ROJO);
            tituloCaso.setText("Caso actual: " + primero.getCodigo() + ", " + primero.getNombre());
            casoActual.setText(RevisionCasos.explicar(g.getLista(), primero));
            revisado.setVisible(true);
        }

        // Recorre la cola desde el frente
        DefaultTableModel modelo = new DefaultTableModel(
                new String[] {"Turno", "Código", "Nombre", "Cargo", "Salario", "Gana menos que el otro sexo"}, 0) {
            @Override
            public boolean isCellEditable(int f, int c) {
                return false;
            }
        };
        int turno = 1;
        NodoCola nodo = g.getCasos().getFrente();
        while (nodo != null) {
            Empleado e = nodo.getDato();
            Object[] fila = {turno, e.getCodigo(), e.getNombre(), e.getCargo(), Formato.pesos(e.getSalario()),
                             Formato.porcentaje(RevisionCasos.diferenciaConOtroSexo(g.getLista(), e))};
            modelo.addRow(fila);
            turno++;
            nodo = nodo.getSiguiente();
        }
        scroll.setViewportView(Estilos.tabla(modelo));
    }
}
