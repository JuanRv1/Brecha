package com.mycompany.brecha;

import Conexion.CargoDAO;
import Conexion.CargoDAOMySQL;
import Conexion.CargoDAOPrueba;
import Conexion.ConexionBD;
import Conexion.EmpleadoDAO;
import Conexion.EmpleadoDAOMySQL;
import Conexion.EmpleadoDAOPrueba;
import Logica.GestorEmpleados;
import Vista.VentanaPrincipal;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Brecha {

    public static void main(String[] args) {
        EmpleadoDAO empleadoDAO;
        CargoDAO cargoDAO;
        String origen;

        // 1. Se intenta conectar con MySQL. Si no se puede (MySQL apagado, clave
        //    equivocada...), se avisa y se usan los datos de prueba.
        try {
            ConexionBD.getConexion();
            empleadoDAO = new EmpleadoDAOMySQL();
            cargoDAO = new CargoDAOMySQL();
            origen = "Conectado a MySQL";
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar con MySQL:\n" + ex.getMessage()
                    + "\n\nSe usarán los datos de prueba y los cambios no se guardarán.",
                    "Sin conexión", JOptionPane.WARNING_MESSAGE);
            empleadoDAO = new EmpleadoDAOPrueba();
            cargoDAO = new CargoDAOPrueba();
            origen = "Datos de prueba (sin conexión)";
        }

        // 2. Se pasan los empleados de la base de datos a la lista
        GestorEmpleados gestor = new GestorEmpleados(empleadoDAO, origen);
        gestor.cargar();

        // 3. Se abre la ventana
        CargoDAO cargos = cargoDAO;
        SwingUtilities.invokeLater(() -> new VentanaPrincipal(gestor, cargos).setVisible(true));
    }
}
