package Estructura;
import Modelo.Empleado;


public class NodoCola {

    private Empleado dato;
    private NodoCola siguiente;

    public NodoCola(Empleado dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public Empleado getDato() {
        return dato;
    }

    public NodoCola getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCola siguiente) {
        this.siguiente = siguiente;
    }
}



