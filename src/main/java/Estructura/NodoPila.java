package Estructura;
import Modelo.CambioSalario;

public class NodoPila {

    private CambioSalario dato;
    private NodoPila siguiente;

    public NodoPila(CambioSalario dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public CambioSalario getDato() {
        return dato;
    }

    public NodoPila getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoPila siguiente) {
        this.siguiente = siguiente;
    }
}
