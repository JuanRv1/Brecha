package Conexion;

import Modelo.Cargo;

// Cargos de prueba TEMPORALES: los mismos 13 cargos del script 02_dml_oracle.sql.
// Se reemplaza por la version con base de datos cuando ConexionBD este lista.
public class CargoDAOPrueba implements CargoDAO {

    private Cargo[] cargos = {
        new Cargo(1,  "Auxiliar administrativo",       "Administracion", 1),
        new Cargo(2,  "Auxiliar de bodega",            "Logistica",      1),
        new Cargo(3,  "Recepcionista",                 "Administracion", 1),
        new Cargo(4,  "Operario de produccion",        "Produccion",     1),
        new Cargo(5,  "Analista de datos",             "Tecnologia",     2),
        new Cargo(6,  "Contador",                      "Finanzas",       2),
        new Cargo(7,  "Ingeniero de procesos",         "Produccion",     2),
        new Cargo(8,  "Coordinador de talento humano", "Talento Humano", 3),
        new Cargo(9,  "Jefe de produccion",            "Produccion",     3),
        new Cargo(10, "Jefe de tecnologia",            "Tecnologia",     3),
        new Cargo(11, "Director financiero",           "Finanzas",       4),
        new Cargo(12, "Gerente general",               "Gerencia",       4),
        new Cargo(13, "Director comercial",            "Comercial",      4)
    };

    @Override
    public Cargo[] listar() {
        return cargos;
    }

    // Buscar un cargo por su id (los ids van de 1 a 13)
    public Cargo buscar(int idCargo) {
        return cargos[idCargo - 1];
    }
}
