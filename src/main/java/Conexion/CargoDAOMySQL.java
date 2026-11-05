package Conexion;

import Modelo.Cargo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Lee los cargos de la tabla CARGO de MySQL, con su area y su nivel
public class CargoDAOMySQL implements CargoDAO {

    @Override
    public Cargo[] listar() {
        String sql = "SELECT c.id_cargo, c.nombre, a.nombre AS area, n.orden AS nivel "
                   + "FROM cargo c "
                   + "INNER JOIN area a ON a.id_area = c.id_area "
                   + "INNER JOIN nivel_jerarquico n ON n.id_nivel = c.id_nivel "
                   + "ORDER BY c.id_cargo";
        try {
            Connection con = ConexionBD.getConexion();

            // Primero se cuentan los cargos para saber de que tamano hacer el arreglo
            int total = 0;
            try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM cargo");
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }

            Cargo[] cargos = new Cargo[total];
            try (PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                int i = 0;
                while (rs.next() && i < total) {
                    cargos[i] = new Cargo(rs.getInt("id_cargo"), rs.getString("nombre"),
                                          rs.getString("area"), rs.getInt("nivel"));
                    i++;
                }
            }
            return cargos;
        } catch (Exception e) {
            System.out.println("Error al leer los cargos: " + e.getMessage());
            return new Cargo[0];
        }
    }
}
