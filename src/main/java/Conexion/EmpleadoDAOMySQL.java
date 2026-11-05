package Conexion;

import Modelo.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Acceso a las tablas EMPLEADO e HISTORIAL_SALARIAL de MySQL.
// Todas las sentencias usan PreparedStatement (los ? se llenan con setString, setDouble...).
public class EmpleadoDAOMySQL implements EmpleadoDAO {

    // Lee todos los empleados con su salario vigente (fecha_fin en NULL)
    @Override
    public Empleado[] listar() {
        String sql = "SELECT e.codigo_empleado, e.documento, e.nombres, e.apellidos, e.sexo, "
                   + "c.id_cargo, c.nombre AS cargo, a.nombre AS area, n.orden AS nivel, h.salario "
                   + "FROM empleado e "
                   + "INNER JOIN cargo c ON c.id_cargo = e.id_cargo "
                   + "INNER JOIN area a ON a.id_area = c.id_area "
                   + "INNER JOIN nivel_jerarquico n ON n.id_nivel = c.id_nivel "
                   + "INNER JOIN historial_salarial h ON h.codigo_empleado = e.codigo_empleado "
                   + "WHERE h.fecha_fin IS NULL "
                   + "ORDER BY e.fecha_ingreso, e.codigo_empleado";
        try {
            Connection con = ConexionBD.getConexion();

            // Se cuentan los empleados para saber el tamano del arreglo
            int total = 0;
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM empleado");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }

            Empleado[] empleados = new Empleado[total];
            int i = 0;
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next() && i < total) {
                    Empleado e = new Empleado(
                            rs.getString("codigo_empleado"),
                            rs.getString("nombres") + " " + rs.getString("apellidos"),
                            rs.getString("sexo").charAt(0),
                            rs.getString("cargo"),
                            rs.getString("area"),
                            rs.getInt("nivel"),
                            rs.getDouble("salario"));
                    e.setIdCargo(rs.getInt("id_cargo"));
                    e.setDocumento(rs.getString("documento"));
                    empleados[i] = e;
                    i++;
                }
            }

            // Si algun empleado no tenia salario vigente, el arreglo queda con espacios vacios
            if (i < total) {
                Empleado[] completos = new Empleado[i];
                for (int k = 0; k < i; k++) {
                    completos[k] = empleados[k];
                }
                return completos;
            }
            return empleados;
        } catch (Exception e) {
            System.out.println("Error al leer los empleados: " + e.getMessage());
            return new Empleado[0];
        }
    }

    // Guarda el empleado y su primer salario. Si una de las dos falla, no se guarda nada.
    @Override
    public boolean insertar(Empleado emp) {
        String sqlEmpleado = "INSERT INTO empleado (codigo_empleado, documento, nombres, apellidos, "
                           + "sexo, fecha_ingreso, id_cargo) VALUES (?, ?, ?, ?, ?, CURDATE(), ?)";
        String sqlSalario = "INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) "
                          + "VALUES (?, ?, NOW(), NULL)";

        // El nombre completo se parte en nombres (primera palabra) y apellidos (el resto)
        String completo = emp.getNombre().trim();
        String nombres = completo;
        String apellidos = "";
        int espacio = completo.indexOf(' ');
        if (espacio > 0) {
            nombres = completo.substring(0, espacio);
            apellidos = completo.substring(espacio + 1).trim();
        }

        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);           // las dos sentencias van juntas

            try (PreparedStatement ps = con.prepareStatement(sqlEmpleado)) {
                ps.setString(1, emp.getCodigo());
                ps.setString(2, emp.getDocumento());
                ps.setString(3, nombres);
                ps.setString(4, apellidos);
                ps.setString(5, String.valueOf(emp.getSexo()));
                ps.setInt(6, emp.getIdCargo());
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement(sqlSalario)) {
                ps.setString(1, emp.getCodigo());
                ps.setDouble(2, emp.getSalario());
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar el empleado: " + e.getMessage());
            deshacerTransaccion(con);
            return false;
        } finally {
            volverAutoCommit(con);
        }
    }

    // Cierra el salario vigente (le pone fecha_fin) y guarda el nuevo como vigente
    @Override
    public boolean actualizarSalario(String codigo, double nuevoSalario) {
        String sqlCerrar = "UPDATE historial_salarial SET fecha_fin = NOW() "
                         + "WHERE codigo_empleado = ? AND fecha_fin IS NULL";
        String sqlNuevo = "INSERT INTO historial_salarial (codigo_empleado, salario, fecha_inicio, fecha_fin) "
                        + "VALUES (?, ?, NOW(), NULL)";
        Connection con = null;
        try {
            con = ConexionBD.getConexion();
            con.setAutoCommit(false);

            try (PreparedStatement ps = con.prepareStatement(sqlCerrar)) {
                ps.setString(1, codigo);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement(sqlNuevo)) {
                ps.setString(1, codigo);
                ps.setDouble(2, nuevoSalario);
                ps.executeUpdate();
            }

            con.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error al actualizar el salario: " + e.getMessage());
            deshacerTransaccion(con);
            return false;
        } finally {
            volverAutoCommit(con);
        }
    }

    // Borra el empleado. Su historial se borra solo (ON DELETE CASCADE).
    @Override
    public boolean eliminar(String codigo) {
        String sql = "DELETE FROM empleado WHERE codigo_empleado = ?";
        try {
            Connection con = ConexionBD.getConexion();
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, codigo);
                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar el empleado: " + e.getMessage());
            return false;
        }
    }

    private void deshacerTransaccion(Connection con) {
        try {
            if (con != null) {
                con.rollback();
            }
        } catch (SQLException ex) {
            System.out.println("Error en rollback: " + ex.getMessage());
        }
    }

    private void volverAutoCommit(Connection con) {
        try {
            if (con != null) {
                con.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
