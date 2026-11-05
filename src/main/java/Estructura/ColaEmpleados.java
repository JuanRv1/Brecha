package Estructura;
import Modelo.Empleado;


public class ColaEmpleados {

    private NodoCola frente;
    private NodoCola fin;
    private int tamanio;

    public ColaEmpleados() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    // Verificar si la cola esta vacia
    public boolean estaVacia() {
        return frente == null;
    }

    // Obtener tamaño
    public int getTamanio() {
        return tamanio;
    }

    // Agregar empleado a la cola
    public void encolar(Empleado empleado) {

        if (empleado == null) {
            System.out.println("Empleado no valido");
            return;
        }

        NodoCola nuevo = new NodoCola(empleado);

        if (estaVacia()) {
            frente = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }

        tamanio++;
    }

    // Retirar el primer empleado
    public Empleado desencolar() {

        if (estaVacia()) {
            return null;
        }

        Empleado empleado = frente.getDato();

        frente = frente.getSiguiente();

        if (frente == null) {
            fin = null;
        }

        tamanio--;

        return empleado;
    }

    // Consultar el primer empleado sin retirarlo
    public Empleado verFrente() {

        if (estaVacia()) {
            return null;
        }

        return frente.getDato();
    }

    // Saber si un empleado ya esta en la cola (para no repetirlo)
    public boolean contiene(String codigo) {
        NodoCola actual = frente;
        while (actual != null) {
            if (actual.getDato().getCodigo().equals(codigo)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    // Vaciar la cola
    public void vaciar() {
        frente = null;
        fin = null;
        tamanio = 0;
    }

    // Primer nodo de la cola (lo usa la pantalla para mostrar los pendientes)
    public NodoCola getFrente() {
        return frente;
    }

    // Mostrar empleados pendientes
    public void mostrarCola() {

        NodoCola actual = frente;

        if (estaVacia()) {
            System.out.println("La cola esta vacia");
            return;
        }

        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        }
    }
}
