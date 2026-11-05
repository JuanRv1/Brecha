
package Conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author juani
 */
// Abre la conexion con MySQL usando JDBC.
// Los datos de conexion (url, usuario, clave) NO estan en el codigo:
// se leen del archivo db.properties que esta en la carpeta del proyecto.
public class ConexionBD {

    private static Connection conexion;

    public static Connection getConexion() throws SQLException, IOException {
        if (conexion == null || conexion.isClosed()) {
            Properties datos = new Properties();
            try (FileInputStream archivo = new FileInputStream("db.properties")) {
                datos.load(archivo);
            }
            conexion = DriverManager.getConnection(
                    datos.getProperty("db.url"),
                    datos.getProperty("db.usuario"),
                    datos.getProperty("db.clave"));
        }
        return conexion;
    }

    public static void cerrar() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexion: " + e.getMessage());
        }
    }
}
