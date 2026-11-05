package Estructura;
import Modelo.CambioSalario;


public class PilaCambios {

    private NodoPila cima;
    private int tamanio;

    public PilaCambios() {
        cima = null;
        tamanio = 0;
    }

    // Verificar si la pila esta vacia
    public boolean estaVacia() {
        return cima == null;
    }

    // Obtener el tamaño
    public int getTamanio() {
        return tamanio;
    }

    // Agregar un cambio salarial
    public void apilar(CambioSalario cambio) {

        if (cambio == null) {
            System.out.println("Cambio no valido");
            return;
        }

        NodoPila nuevo = new NodoPila(cambio);

        nuevo.setSiguiente(cima);
        cima = nuevo;

        tamanio++;
    }

    // Retirar el ultimo cambio registrado
    public CambioSalario desapilar() {

        if (estaVacia()) {
            return null;
        }

        CambioSalario cambio = cima.getDato();

        cima = cima.getSiguiente();

        tamanio--;

        return cambio;
    }

    // Consultar el ultimo cambio sin retirarlo
    public CambioSalario verCima() {

        if (estaVacia()) {
            return null;
        }

        return cima.getDato();
    }

    // Primer nodo de la pila (lo usa la pantalla para mostrar el historial)
    public NodoPila getCima() {
        return cima;
    }

    // Contar cuantos cambios tiene un empleado (recorre la pila)
    public int contarCambiosDe(String codigo) {
        int total = 0;
        NodoPila actual = cima;
        while (actual != null) {
            if (actual.getDato().getCodigoEmpleado().equals(codigo)) {
                total++;
            }
            actual = actual.getSiguiente();
        }
        return total;
    }

    // Mostrar el historial de cambios
    public void mostrarPila() {

        if (estaVacia()) {
            System.out.println("La pila esta vacia");
            return;
        }

        NodoPila actual = cima;

        while (actual != null) {
            System.out.println(actual.getDato());
            actual = actual.getSiguiente();
        }
    }
}
