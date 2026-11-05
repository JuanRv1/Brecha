package Logica;

import Conexion.EmpleadoDAO;
import Estructura.ColaEmpleados;
import Estructura.ListaDobleEmpleados;
import Modelo.CambioSalario;
import Modelo.Empleado;

// Une las estructuras con la base de datos. Las pantallas solo hablan con esta clase,
// asi cada operacion actualiza la lista (y su pila de historial) y la base de datos a la vez.
//
//   lista: todos los empleados (registrar, buscar, modificar, retirar, recorrer)
//   pila:  historial de cambios de salario (esta dentro de la lista)
//   cola:  casos de diferencia salarial pendientes por revisar
public class GestorEmpleados {

    private ListaDobleEmpleados lista = new ListaDobleEmpleados();
    private ColaEmpleados casos = new ColaEmpleados();
    private EmpleadoDAO dao;
    private String origenDatos;      // texto que se muestra en la pantalla de inicio

    public GestorEmpleados(EmpleadoDAO dao, String origenDatos) {
        this.dao = dao;
        this.origenDatos = origenDatos;
    }

    public String getOrigenDatos() {
        return origenDatos;
    }

    // Al abrir el programa: pasa los empleados de la base de datos a la lista
    public int cargar() {
        Empleado[] datos = dao.listar();
        for (int i = 0; i < datos.length; i++) {
            lista.insertarAlFinal(datos[i]);
        }
        RevisionCasos.generar(lista, casos);
        return lista.getTamanio();
    }

    // Registrar. Devuelve null si salio bien, o el mensaje de error.
    public String registrar(Empleado e) {
        String error = e.validar();
        if (error != null) {
            return error;
        }
        if (lista.buscarPorCodigo(e.getCodigo()) != null) {
            return "Ya existe un empleado con el código " + e.getCodigo();
        }
        // Primero se guarda en la base de datos: si falla (por ejemplo, documento repetido),
        // no se agrega a la lista para que las dos queden iguales.
        if (!dao.insertar(e)) {
            return "No se pudo guardar en la base de datos. Revise que el documento no esté repetido.";
        }
        lista.insertarAlFinal(e);
        return null;
    }

    public Empleado buscar(String codigo) {
        return lista.buscarPorCodigo(codigo);
    }

    public boolean modificarSalario(String codigo, double nuevoSalario) {
        if (!lista.modificarSalario(codigo, nuevoSalario)) {
            return false;
        }
        dao.actualizarSalario(codigo, nuevoSalario);
        return true;
    }

    // Deshace el ultimo cambio de salario (pila). Devuelve null si no habia cambios.
    public CambioSalario deshacerUltimoCambio() {
        CambioSalario cambio = lista.deshacerUltimoCambio();
        if (cambio != null && lista.buscarPorCodigo(cambio.getCodigoEmpleado()) != null) {
            dao.actualizarSalario(cambio.getCodigoEmpleado(), cambio.getSalarioAnterior());
        }
        return cambio;
    }

    public boolean retirar(String codigo) {
        if (!lista.eliminarPorCodigo(codigo)) {
            return false;
        }
        dao.eliminar(codigo);
        RevisionCasos.generar(lista, casos);   // si estaba en la cola de casos, ya no aparece
        return true;
    }

    // Vuelve a armar la cola de casos con los datos actuales
    public int generarCasos() {
        return RevisionCasos.generar(lista, casos);
    }

    // Marca como revisado el primer caso de la cola (desencolar)
    public Empleado marcarCasoRevisado() {
        return casos.desencolar();
    }

    public ListaDobleEmpleados getLista() {
        return lista;
    }

    public ColaEmpleados getCasos() {
        return casos;
    }
}
