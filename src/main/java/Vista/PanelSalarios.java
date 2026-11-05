package Vista;

import Conexion.CargoDAO;
import Estructura.NodoDoble;
import Logica.Formato;
import Modelo.Cargo;
import Modelo.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Consulta de personas por rango salarial y, si se quiere, por cargo.
// Recorre la lista y se queda con quienes cumplen las condiciones.
public class PanelSalarios extends PanelBase {

    private JTextField minimo = Estilos.campo(11);
    private JTextField maximo = Estilos.campo(11);
    private JComboBox<String> cargo = new JComboBox<>();
    private JLabel mensaje = Estilos.etiqueta(" ");
    private JScrollPane scroll = new JScrollPane();

    public PanelSalarios(VentanaPrincipal ventana, CargoDAO cargoDAO) {
        super(ventana, "Consulta por salario", "Personas que ganan dentro de un rango, en todos los cargos o en uno");
        cargo.setFont(Estilos.NORMAL);
        cargo.addItem("Todos los cargos");
        Cargo[] cargos = cargoDAO.listar();
        for (int i = 0; i < cargos.length; i++) {
            cargo.addItem(cargos[i].getNombre());
        }

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        arriba.setOpaque(false);
        arriba.add(Estilos.etiqueta("Desde ($):"));
        arriba.add(minimo);
        arriba.add(Estilos.etiqueta("Hasta ($):"));
        arriba.add(maximo);
        arriba.add(Estilos.etiqueta("Cargo:"));
        arriba.add(cargo);
        JButton consultar = Estilos.boton("Consultar", Estilos.OSCURO);
        consultar.addActionListener(e -> consultar());
        arriba.add(consultar);

        JPanel norte = new JPanel(new BorderLayout(4, 8));
        norte.setOpaque(false);
        norte.add(arriba, BorderLayout.NORTH);
        norte.add(mensaje, BorderLayout.SOUTH);

        contenido.add(norte, BorderLayout.NORTH);
        contenido.add(scroll, BorderLayout.CENTER);
    }

    private void consultar() {
        double min = Formato.leerPesos(minimo.getText());
        double max = Formato.leerPesos(maximo.getText());
        if (min < 0 || max < 0) {
            Estilos.mensaje(mensaje, "Escriba los dos valores del rango.", false);
            return;
        }
        if (min > max) {
            Estilos.mensaje(mensaje, "El valor \"desde\" no puede ser mayor que \"hasta\".", false);
            return;
        }
        String filtroCargo = (String) cargo.getSelectedItem();
        boolean todos = cargo.getSelectedIndex() == 0;

        DefaultTableModel modelo = Estilos.modeloEmpleados();
        int encontrados = 0;
        NodoDoble actual = ventana.getGestor().getLista().getCabeza();
        while (actual != null) {
            Empleado e = actual.getDato();
            boolean enRango = e.getSalario() >= min && e.getSalario() <= max;
            boolean enCargo = todos || e.getCargo().equals(filtroCargo);
            if (enRango && enCargo) {
                Estilos.agregarFila(modelo, e);
                encontrados++;
            }
            actual = actual.getSiguiente();
        }
        scroll.setViewportView(Estilos.tabla(modelo));
        if (encontrados == 0) {
            Estilos.mensaje(mensaje, "Nadie cumple esas condiciones.", false);
        } else {
            Estilos.mensaje(mensaje, encontrados + " persona(s) encontradas.", true);
        }
    }

    @Override
    public void alMostrar() {
        minimo.setText("1.000.000");
        maximo.setText("5.000.000");
        cargo.setSelectedIndex(0);
        scroll.setViewportView(null);
        mensaje.setText(" ");
    }
}
