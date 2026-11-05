package Vista;

import Logica.CalculadoraIndicadores;
import Logica.GestorEmpleados;

import javax.swing.*;
import java.awt.*;

// Listado completo de la nomina
public class PanelNomina extends PanelBase {

    private JComboBox<String> orden = new JComboBox<>(new String[] {
        "Por orden de ingreso", "Ingresos más recientes primero"
    });
    private JLabel resumen = Estilos.etiqueta(" ");
    private JScrollPane scroll = new JScrollPane();

    public PanelNomina(VentanaPrincipal ventana) {
        super(ventana, "Nómina", "Todas las personas registradas en la organización");
        orden.setFont(Estilos.NORMAL);
        orden.addActionListener(e -> alMostrar());

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        arriba.setOpaque(false);
        arriba.add(Estilos.etiqueta("Ordenar:"));
        arriba.add(orden);
        arriba.add(resumen);

        contenido.add(arriba, BorderLayout.NORTH);
        contenido.add(scroll, BorderLayout.CENTER);
    }

    @Override
    public void alMostrar() {
        GestorEmpleados g = ventana.getGestor();
        if (orden.getSelectedIndex() == 0) {
            scroll.setViewportView(Estilos.tablaEmpleados(g.getLista()));      // de la cabeza a la cola
        } else {
            scroll.setViewportView(Estilos.tablaEmpleadosRecientes(g.getLista())); // de la cola a la cabeza
        }
        int total = g.getLista().getTamanio();
        int mujeres = CalculadoraIndicadores.contar(g.getLista(), 'F', 0);
        resumen.setText("     " + total + " personas: " + mujeres + " mujeres y "
                + (total - mujeres) + " hombres");
    }
}
