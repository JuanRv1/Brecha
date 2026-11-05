package Estructura;
import Modelo.Empleado;

public class NodoDoble {
    private Empleado dato;
    private NodoDoble anterior;
    private NodoDoble siguiente;

    public NodoDoble(Empleado dato) {
        this.dato = dato;
        this.anterior = null;
        this.siguiente = null;
    }

    public Empleado getDato() {
        return dato;
    }

    public NodoDoble getAnterior() {
        return anterior;
    }

    public NodoDoble getSiguiente() {
        return siguiente;
    }

    public void setDato(Empleado dato) {
        this.dato = dato;
    }

    public void setAnterior(NodoDoble anterior) {
        this.anterior = anterior;
    }

    public void setSiguiente(NodoDoble siguiente) {
        this.siguiente = siguiente;
    }
}
