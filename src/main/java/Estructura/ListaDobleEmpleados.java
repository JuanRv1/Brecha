package Estructura;
import Modelo.Empleado;
import Modelo.CambioSalario;

public class ListaDobleEmpleados {

    private NodoDoble cabeza;
    private NodoDoble cola;
    private int tamanio;
    private PilaCambios historial = new PilaCambios();

    public PilaCambios getHistorial() {
        return historial;
    }

    public ListaDobleEmpleados() {
        cabeza = null;
        cola = null;
        tamanio = 0;
    }

    // Verificar si la lista esta vacia
    public boolean estaVacia() {
        return cabeza == null;
    }

    // Obtener el tamaño de la lista
    public int getTamanio() {
        return tamanio;
    }

    // Obtener el primer nodo
    public NodoDoble getCabeza() {
        return cabeza;
    }

    // Obtener el ultimo nodo
    public NodoDoble getCola() {
        return cola;
    }

    // Insertar empleado al final.
    // Devuelve false si el empleado es nulo o si el codigo ya existe.
    public boolean insertarAlFinal(Empleado empleado) {

        if (empleado == null) {
            return false;
        }

        if (buscarPorCodigo(empleado.getCodigo()) != null) {
            return false;
        }

        NodoDoble nuevo = new NodoDoble(empleado);

        if (estaVacia()) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.setSiguiente(nuevo);
            nuevo.setAnterior(cola);
            cola = nuevo;
        }

        tamanio++;
        return true;
    }

    // Buscar empleado por codigo
    public Empleado buscarPorCodigo(String codigo) {

        if (codigo == null) {
            return null;
        }

        NodoDoble actual = cabeza;

        while (actual != null) {

            if (actual.getDato().getCodigo().equals(codigo)) {
                return actual.getDato();
            }

            actual = actual.getSiguiente();
        }

        return null;
    }

    // Modificar salario de un empleado.
    // Antes de cambiarlo, guarda el cambio en la pila (historial).
    public boolean modificarSalario(String codigo, double nuevoSalario) {

        if (nuevoSalario <= 0) {
            return false;
        }

        Empleado empleado = buscarPorCodigo(codigo);

        if (empleado == null) {
            return false;
        }

        historial.apilar(new CambioSalario(codigo, empleado.getSalario(), nuevoSalario));
        empleado.setSalario(nuevoSalario);

        return true;
    }

    // Deshacer el ultimo cambio de salario usando la pila.
    // Saca el ultimo cambio (desapilar) y le devuelve al empleado su salario anterior.
    // Devuelve el cambio deshecho, o null si no hay cambios para deshacer.
    public CambioSalario deshacerUltimoCambio() {

        CambioSalario cambio = historial.desapilar();

        if (cambio == null) {
            return null;                       // la pila estaba vacia
        }

        Empleado empleado = buscarPorCodigo(cambio.getCodigoEmpleado());

        if (empleado != null) {                // puede que ya lo hayan retirado
            empleado.setSalario(cambio.getSalarioAnterior());
        }

        return cambio;
    }

    // Eliminar empleado por codigo
    public boolean eliminarPorCodigo(String codigo) {

        if (codigo == null) {
            return false;
        }

        NodoDoble actual = cabeza;

        while (actual != null &&
               !actual.getDato().getCodigo().equals(codigo)) {

            actual = actual.getSiguiente();
        }

        if (actual == null) {
            return false;
        }

        // Si tiene un nodo anterior
        if (actual.getAnterior() != null) {
            actual.getAnterior().setSiguiente(actual.getSiguiente());
        } else {
            cabeza = actual.getSiguiente();
        }

        // Si tiene un nodo siguiente
        if (actual.getSiguiente() != null) {
            actual.getSiguiente().setAnterior(actual.getAnterior());
        } else {
            cola = actual.getAnterior();
        }

        tamanio--;

        return true;
    }

    // Recorrer la lista desde el inicio
    public void recorrerAdelante() {

        if (estaVacia()) {
            System.out.println("La lista esta vacia");
            return;
        }

        NodoDoble actual = cabeza;

        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        }
    }

    // Recorrer la lista desde el final
    public void recorrerAtras() {

        if (estaVacia()) {
            System.out.println("La lista esta vacia");
            return;
        }

        NodoDoble actual = cola;

        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getAnterior();
        }
    }
}
