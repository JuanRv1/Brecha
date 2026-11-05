package Vista;

import Estructura.NodoPila;
import Logica.Formato;
import Modelo.CambioSalario;
import Modelo.Empleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Modificar el salario de un empleado y deshacer el ultimo cambio.
// Abajo se muestra el historial de cambios (la pila, del mas reciente al mas antiguo).
public class PanelModificar extends PanelBase {

    private JTextField codigo = Estilos.campo(10);
    private JTextField nuevoSalario = Estilos.campo(14);
    private JLabel mensaje = Estilos.etiqueta(" ");
    private FichaEmpleado ficha = new FichaEmpleado();
    private JPanel filaSalario = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
    private JScrollPane historial = new JScrollPane();
    private Empleado actual;

    public PanelModificar(VentanaPrincipal ventana) {
        super(ventana, "Modificar salario",
                "Cambie el salario de una persona o deshaga el último cambio registrado");

        JPanel caja = Estilos.tarjeta();
        caja.setLayout(new BorderLayout(10, 10));

        JPanel filaBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        filaBuscar.setOpaque(false);
        filaBuscar.add(Estilos.etiqueta("Código:"));
        filaBuscar.add(codigo);
        JButton buscar = Estilos.boton("Buscar", Estilos.OSCURO);
        buscar.addActionListener(e -> buscar());
        codigo.addActionListener(e -> buscar());
        filaBuscar.add(buscar);
        JButton deshacer = Estilos.boton("Deshacer último cambio", Estilos.GRIS);
        deshacer.addActionListener(e -> deshacer());
        filaBuscar.add(deshacer);

        filaSalario.setOpaque(false);
        filaSalario.add(Estilos.etiqueta("Nuevo salario ($):"));
        filaSalario.add(nuevoSalario);
        JButton guardar = Estilos.boton("Guardar cambio", Estilos.VERDE);
        guardar.addActionListener(e -> guardar());
        filaSalario.add(guardar);

        JPanel abajo = new JPanel(new BorderLayout(6, 6));
        abajo.setOpaque(false);
        abajo.add(filaSalario, BorderLayout.NORTH);
        abajo.add(mensaje, BorderLayout.SOUTH);

        caja.add(filaBuscar, BorderLayout.NORTH);
        caja.add(ficha, BorderLayout.CENTER);
        caja.add(abajo, BorderLayout.SOUTH);

        JPanel parteHistorial = new JPanel(new BorderLayout(6, 6));
        parteHistorial.setOpaque(false);
        JLabel tituloHist = Estilos.etiqueta("Historial de cambios de salario (el más reciente primero)");
        tituloHist.setFont(Estilos.NEGRITA);
        parteHistorial.add(tituloHist, BorderLayout.NORTH);
        parteHistorial.add(historial, BorderLayout.CENTER);

        contenido.add(Estilos.alinearArriba(caja), BorderLayout.NORTH);
        contenido.add(parteHistorial, BorderLayout.CENTER);
    }

    private void buscar() {
        actual = ventana.getGestor().buscar(codigo.getText().trim().toUpperCase());
        ficha.mostrar(actual);
        filaSalario.setVisible(actual != null);
        if (actual == null) {
            Estilos.mensaje(mensaje, "No hay ningún empleado con ese código.", false);
        } else {
            mensaje.setText(" ");
        }
    }

    private void guardar() {
        double valor = Formato.leerPesos(nuevoSalario.getText());
        if (valor <= 0) {
            Estilos.mensaje(mensaje, "Escriba un salario mayor que cero.", false);
            return;
        }
        if (ventana.getGestor().modificarSalario(actual.getCodigo(), valor)) {
            ficha.mostrar(actual);
            nuevoSalario.setText("");
            Estilos.mensaje(mensaje, "Salario actualizado a " + Formato.pesos(valor) + ".", true);
            mostrarHistorial();
        }
    }

    private void deshacer() {
        CambioSalario c = ventana.getGestor().deshacerUltimoCambio();
        if (c == null) {
            Estilos.mensaje(mensaje, "No hay cambios para deshacer.", false);
            return;
        }
        Estilos.mensaje(mensaje, "Se deshizo el cambio de " + c.getCodigoEmpleado()
                + ". Vuelve a ganar " + Formato.pesos(c.getSalarioAnterior()) + ".", true);
        if (actual != null) {
            ficha.mostrar(actual);
        }
        mostrarHistorial();
    }

    // Recorre la pila desde la cima y llena la tabla
    private void mostrarHistorial() {
        String[] columnas = {"Código", "Salario anterior", "Salario nuevo"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int f, int c) {
                return false;
            }
        };
        NodoPila nodo = ventana.getGestor().getLista().getHistorial().getCima();
        while (nodo != null) {
            CambioSalario c = nodo.getDato();
            Object[] fila = {c.getCodigoEmpleado(), Formato.pesos(c.getSalarioAnterior()),
                             Formato.pesos(c.getSalarioNuevo())};
            modelo.addRow(fila);
            nodo = nodo.getSiguiente();
        }
        historial.setViewportView(Estilos.tabla(modelo));
    }

    @Override
    public void alMostrar() {
        actual = null;
        codigo.setText("");
        nuevoSalario.setText("");
        ficha.mostrar(null);
        filaSalario.setVisible(false);
        mensaje.setText(" ");
        mostrarHistorial();
    }
}
