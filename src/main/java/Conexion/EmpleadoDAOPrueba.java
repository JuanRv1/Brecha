package Conexion;

import Modelo.Cargo;
import Modelo.Empleado;

// Empleados de prueba TEMPORALES: los mismos de los scripts 02_dml_oracle.sql
// y 04_empleados_adicionales.sql. Se usan mientras se termina ConexionBD.
// Los cambios que se hagan en el programa NO se guardan al cerrarlo.
public class EmpleadoDAOPrueba implements EmpleadoDAO {

    public static final double SMLMV_2026 = 1750905;

    private CargoDAOPrueba cargos = new CargoDAOPrueba();

    @Override
    public Empleado[] listar() {
        Empleado[] empleados = {
            crear("E001", "Ana Perez",      'F', 1,  SMLMV_2026),
            crear("E002", "Carlos Diaz",    'M', 2,  1850000),
            crear("E003", "Maria Gomez",    'F', 3,  SMLMV_2026),
            crear("E004", "Luis Torres",    'M', 4,  1900000),
            crear("E005", "Laura Ruiz",     'F', 5,  4200000),
            crear("E006", "Jorge Castro",   'M', 5,  4600000),
            crear("E007", "Sofia Herrera",  'F', 6,  4500000),
            crear("E008", "Andres Molina",  'M', 7,  4800000),
            crear("E009", "Paola Vargas",   'F', 8,  7000000),
            crear("E010", "Diego Rojas",    'M', 9,  8200000),
            crear("E011", "Ricardo Silva",  'M', 10, 8800000),
            crear("E012", "Claudia Mejia",  'F', 11, 15000000),
            crear("E013", "Fernando Ortiz", 'M', 12, 13 * SMLMV_2026),
            crear("E014", "Javier Pineda",  'M', 13, 16500000),
            crear("E015", "Pedro Gil",      'M', 1,  1900000),
            crear("E016", "Camilo Reyes",   'M', 6,  4900000)
        };
        return empleados;
    }

    private Empleado crear(String codigo, String nombre, char sexo, int idCargo, double salario) {
        Cargo c = cargos.buscar(idCargo);
        Empleado e = new Empleado(codigo, nombre, sexo, c.getNombre(), c.getArea(), c.getNivel(), salario);
        e.setIdCargo(idCargo);
        e.setDocumento("1001001" + codigo.substring(1));   // E005 -> 1001001005
        return e;
    }

    // En la version con base de datos estos metodos hacen INSERT, UPDATE y DELETE.
    // Por ahora solo muestran en consola lo que se haria.

    @Override
    public boolean insertar(Empleado empleado) {
        System.out.println("[Prueba] INSERT empleado " + empleado.getCodigo());
        return true;
    }

    @Override
    public boolean actualizarSalario(String codigo, double nuevoSalario) {
        System.out.println("[Prueba] UPDATE salario " + codigo + " = " + nuevoSalario);
        return true;
    }

    @Override
    public boolean eliminar(String codigo) {
        System.out.println("[Prueba] DELETE empleado " + codigo);
        return true;
    }
}
