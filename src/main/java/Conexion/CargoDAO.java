package Conexion;

import Modelo.Cargo;

// DAO de la tabla CARGO: solo se necesita leer la lista de cargos
public interface CargoDAO {

    Cargo[] listar();
}
