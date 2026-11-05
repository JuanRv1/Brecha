package Conexion;

import Modelo.Empleado;

// Patron DAO: dice QUE se le puede pedir a la base de datos, sin decir COMO.
// Asi el resto del programa no depende de que base de datos se use.
public interface EmpleadoDAO {

    // Leer todos los empleados con su salario actual (se usa al abrir el programa)
    Empleado[] listar();

    // Guardar un empleado nuevo
    boolean insertar(Empleado empleado);

    // Cambiar el salario de un empleado
    boolean actualizarSalario(String codigo, double nuevoSalario);

    // Borrar un empleado
    boolean eliminar(String codigo);
}
