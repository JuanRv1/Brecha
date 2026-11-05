package Vista;

import Logica.Formato;
import Modelo.Empleado;

import javax.swing.*;

// Muestra los datos de un empleado (la usan Consultar, Modificar y Retirar)
public class FichaEmpleado extends JLabel {

    public FichaEmpleado() {
        setFont(Estilos.NORMAL);
        setForeground(Estilos.OSCURO);
    }

    public void mostrar(Empleado e) {
        if (e == null) {
            setText("");
            return;
        }
        setText("<html><table cellpadding='4'>"
                + "<tr><td><b>Código</b></td><td>" + e.getCodigo() + "</td></tr>"
                + "<tr><td><b>Nombre</b></td><td>" + e.getNombre() + "</td></tr>"
                + "<tr><td><b>Documento</b></td><td>" + e.getDocumento() + "</td></tr>"
                + "<tr><td><b>Sexo</b></td><td>" + e.getNombreSexo() + "</td></tr>"
                + "<tr><td><b>Cargo</b></td><td>" + e.getCargo() + "</td></tr>"
                + "<tr><td><b>Área</b></td><td>" + e.getArea() + "</td></tr>"
                + "<tr><td><b>Nivel</b></td><td>" + e.getNombreNivel() + "</td></tr>"
                + "<tr><td><b>Salario</b></td><td>" + Formato.pesos(e.getSalario()) + "</td></tr>"
                + "</table></html>");
    }
}
