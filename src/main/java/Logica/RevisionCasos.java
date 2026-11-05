package Logica;

import Estructura.ColaEmpleados;
import Estructura.ListaDobleEmpleados;
import Estructura.NodoDoble;
import Modelo.Empleado;

// Arma la cola de casos que talento humano debe revisar:
// personas que ganan 5 % o mas por debajo del promedio del otro sexo en su mismo cargo.
// La Ley 1496 de 2011 presume injustificada esa diferencia hasta que la empresa
// demuestre factores objetivos, por eso cada caso se revisa uno por uno, en orden.
public class RevisionCasos {

    // Llena la cola recorriendo la lista. Devuelve cuantos casos encontro.
    public static int generar(ListaDobleEmpleados lista, ColaEmpleados cola) {
        cola.vaciar();
        NodoDoble actual = lista.getCabeza();
        while (actual != null) {
            Empleado e = actual.getDato();
            if (diferenciaConOtroSexo(lista, e) >= CalculadoraIndicadores.BRECHA_ALERTA
                    && !cola.contiene(e.getCodigo())) {
                cola.encolar(e);
            }
            actual = actual.getSiguiente();
        }
        return cola.getTamanio();
    }

    // Cuanto menos gana (en %) esta persona frente al promedio del otro sexo en su cargo.
    // Devuelve 0 si en el cargo no hay personas del otro sexo o si gana igual o mas.
    public static double diferenciaConOtroSexo(ListaDobleEmpleados lista, Empleado e) {
        char otro = (e.getSexo() == 'F') ? 'M' : 'F';
        double promedioOtro = CalculadoraIndicadores.promedioCargo(lista, e.getCargo(), otro);
        if (promedioOtro <= 0 || e.getSalario() >= promedioOtro) {
            return 0;
        }
        return (promedioOtro - e.getSalario()) / promedioOtro * 100;
    }

    // Texto que explica el caso, para mostrarlo en pantalla
    public static String explicar(ListaDobleEmpleados lista, Empleado e) {
        char otro = (e.getSexo() == 'F') ? 'M' : 'F';
        String grupo = (otro == 'M') ? "los hombres" : "las mujeres";
        double promedioOtro = CalculadoraIndicadores.promedioCargo(lista, e.getCargo(), otro);
        if (promedioOtro <= 0) {
            return "Ya no hay personas del otro sexo en este cargo para comparar.";
        }
        return e.getNombre() + " gana " + Formato.pesos(e.getSalario())
                + " como " + e.getCargo() + ". En el mismo cargo, " + grupo + " ganan en promedio "
                + Formato.pesos(promedioOtro) + ", así que esta persona gana "
                + Formato.porcentaje(diferenciaConOtroSexo(lista, e)) + " menos. "
                + "La empresa debe confirmar si la diferencia se explica por factores objetivos "
                + "(capacidades, esfuerzo, responsabilidades o condiciones de trabajo) o corregirla.";
    }
}
