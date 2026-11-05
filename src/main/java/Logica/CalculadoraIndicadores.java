package Logica;

import Estructura.ListaDobleEmpleados;
import Estructura.NodoDoble;
import Modelo.Empleado;

// Calcula los indicadores recorriendo la lista de empleados y decide,
// con condicionales, si cada uno esta adecuado, en alerta o critico.
public class CalculadoraIndicadores {

    // ----- Criterios (umbrales) -----
    // La ley colombiana no fija un porcentaje maximo de brecha para empresas privadas.
    // Se usa como referencia el 5 % de la Directiva (UE) 2023/970: en Europa, una
    // brecha de 5 % o mas sin justificar obliga a la empresa a revisarla.
    public static final double BRECHA_ALERTA = 5;
    public static final double BRECHA_CRITICA = 10;

    // Participacion equilibrada: entre 40 % y 60 % de mujeres
    public static final double PARTICIPACION_MIN = 40;
    public static final double PARTICIPACION_MAX = 60;
    public static final double PARTICIPACION_MIN_ALERTA = 30;
    public static final double PARTICIPACION_MAX_ALERTA = 70;

    // Salario bajo: hasta 2 salarios minimos de 2026
    public static final double SMLMV_2026 = 1750905;
    public static final double SALARIO_BAJO = 2 * SMLMV_2026;

    // ----- Calculos basicos (todos recorren la lista) -----

    // Cuenta empleados de un sexo en un nivel. sexo ' ' = ambos; nivel 0 = todos.
    public static int contar(ListaDobleEmpleados lista, char sexo, int nivel) {
        int total = 0;
        NodoDoble actual = lista.getCabeza();
        while (actual != null) {
            Empleado e = actual.getDato();
            if ((sexo == ' ' || e.getSexo() == sexo) && (nivel == 0 || e.getNivel() == nivel)) {
                total++;
            }
            actual = actual.getSiguiente();
        }
        return total;
    }

    // Salario promedio de un sexo en un nivel. Devuelve -1 si no hay nadie.
    public static double promedio(ListaDobleEmpleados lista, char sexo, int nivel) {
        double suma = 0;
        int cantidad = 0;
        NodoDoble actual = lista.getCabeza();
        while (actual != null) {
            Empleado e = actual.getDato();
            if (e.getSexo() == sexo && (nivel == 0 || e.getNivel() == nivel)) {
                suma = suma + e.getSalario();
                cantidad++;
            }
            actual = actual.getSiguiente();
        }
        if (cantidad == 0) {
            return -1;
        }
        return suma / cantidad;
    }

    // Brecha salarial (%) = (promedio hombres - promedio mujeres) / promedio hombres * 100
    // Si es positiva, las mujeres ganan menos. Devuelve NaN si falta uno de los dos grupos.
    public static double brecha(ListaDobleEmpleados lista, int nivel) {
        double hombres = promedio(lista, 'M', nivel);
        double mujeres = promedio(lista, 'F', nivel);
        if (hombres < 0 || mujeres < 0) {
            return Double.NaN;
        }
        return (hombres - mujeres) / hombres * 100;
    }

    // Porcentaje de mujeres en un nivel (0 = toda la organizacion)
    public static double porcentajeMujeres(ListaDobleEmpleados lista, int nivel) {
        int total = contar(lista, ' ', nivel);
        if (total == 0) {
            return Double.NaN;
        }
        return contar(lista, 'F', nivel) * 100.0 / total;
    }

    // Salario promedio de un sexo dentro de un cargo. Devuelve -1 si no hay nadie.
    public static double promedioCargo(ListaDobleEmpleados lista, String cargo, char sexo) {
        double suma = 0;
        int cantidad = 0;
        NodoDoble actual = lista.getCabeza();
        while (actual != null) {
            Empleado e = actual.getDato();
            if (e.getCargo().equals(cargo) && e.getSexo() == sexo) {
                suma = suma + e.getSalario();
                cantidad++;
            }
            actual = actual.getSiguiente();
        }
        if (cantidad == 0) {
            return -1;
        }
        return suma / cantidad;
    }

    // Cuenta los cargos donde hay hombres y mujeres y la diferencia de salario es de 5 % o mas.
    public static int cargosConDiferencia(ListaDobleEmpleados lista) {
        int total = 0;
        NodoDoble actual = lista.getCabeza();
        while (actual != null) {
            String cargo = actual.getDato().getCargo();
            if (esPrimeraVez(lista, actual, cargo)) {         // cada cargo se revisa una sola vez
                double h = promedioCargo(lista, cargo, 'M');
                double m = promedioCargo(lista, cargo, 'F');
                if (h > 0 && m > 0) {
                    double mayor = Math.max(h, m);
                    double diferencia = Math.abs(h - m) / mayor * 100;
                    if (diferencia >= BRECHA_ALERTA) {
                        total++;
                    }
                }
            }
            actual = actual.getSiguiente();
        }
        return total;
    }

    // true si el cargo no aparecio en ningun nodo antes de "nodo"
    private static boolean esPrimeraVez(ListaDobleEmpleados lista, NodoDoble nodo, String cargo) {
        NodoDoble actual = lista.getCabeza();
        while (actual != nodo) {
            if (actual.getDato().getCargo().equals(cargo)) {
                return false;
            }
            actual = actual.getSiguiente();
        }
        return true;
    }

    // Porcentaje de personas de un sexo que ganan hasta 2 salarios minimos
    public static double porcentajeSalarioBajo(ListaDobleEmpleados lista, char sexo) {
        int total = 0;
        int bajos = 0;
        NodoDoble actual = lista.getCabeza();
        while (actual != null) {
            Empleado e = actual.getDato();
            if (e.getSexo() == sexo) {
                total++;
                if (e.getSalario() <= SALARIO_BAJO) {
                    bajos++;
                }
            }
            actual = actual.getSiguiente();
        }
        if (total == 0) {
            return Double.NaN;
        }
        return bajos * 100.0 / total;
    }

    // ----- Condicionales: deciden el estado de cada indicador -----

    public static int estadoBrecha(double brecha) {
        if (Double.isNaN(brecha)) {
            return Indicador.SIN_DATOS;
        }
        double b = Math.abs(brecha);            // tambien cuenta si los hombres ganan menos
        if (b < BRECHA_ALERTA) {
            return Indicador.ADECUADO;
        } else if (b < BRECHA_CRITICA) {
            return Indicador.ALERTA;
        } else {
            return Indicador.CRITICO;
        }
    }

    public static int estadoParticipacion(double porcentaje) {
        if (Double.isNaN(porcentaje)) {
            return Indicador.SIN_DATOS;
        }
        if (porcentaje >= PARTICIPACION_MIN && porcentaje <= PARTICIPACION_MAX) {
            return Indicador.ADECUADO;
        } else if (porcentaje >= PARTICIPACION_MIN_ALERTA && porcentaje <= PARTICIPACION_MAX_ALERTA) {
            return Indicador.ALERTA;
        } else {
            return Indicador.CRITICO;
        }
    }

    public static int estadoCargos(int cantidad) {
        if (cantidad == 0) {
            return Indicador.ADECUADO;
        } else if (cantidad <= 2) {
            return Indicador.ALERTA;
        } else {
            return Indicador.CRITICO;
        }
    }

    public static int estadoDiferenciaPuntos(double puntos) {
        if (Double.isNaN(puntos)) {
            return Indicador.SIN_DATOS;
        }
        double p = Math.abs(puntos);
        if (p < 10) {
            return Indicador.ADECUADO;
        } else if (p < 20) {
            return Indicador.ALERTA;
        } else {
            return Indicador.CRITICO;
        }
    }

    // ----- Todos los indicadores juntos -----

    public static Indicador[] calcular(ListaDobleEmpleados lista) {
        Indicador[] r = new Indicador[10];
        String critBrecha = "Adecuado si es menor a 5 %, alerta entre 5 % y 10 %, crítico desde 10 %";
        String critPart = "Adecuado entre 40 % y 60 %, alerta entre 30 % y 70 %, crítico fuera de ese rango";

        double b = brecha(lista, 0);
        r[0] = new Indicador("Brecha salarial general", b, "%", estadoBrecha(b), critBrecha);

        for (int nivel = 1; nivel <= 4; nivel++) {
            double bn = brecha(lista, nivel);
            r[nivel] = new Indicador("Brecha salarial: " + Empleado.NOMBRES_NIVEL[nivel],
                    bn, "%", estadoBrecha(bn), critBrecha);
        }

        double p = porcentajeMujeres(lista, 0);
        r[5] = new Indicador("Mujeres en la plantilla", p, "%", estadoParticipacion(p), critPart);

        double pm = porcentajeMujeres(lista, 3);
        r[6] = new Indicador("Mujeres en mando medio", pm, "%", estadoParticipacion(pm), critPart);

        double pa = porcentajeMujeres(lista, 4);
        r[7] = new Indicador("Mujeres en alta dirección", pa, "%", estadoParticipacion(pa), critPart);

        int c = cargosConDiferencia(lista);
        r[8] = new Indicador("Cargos iguales con salarios distintos", c, "cargos", estadoCargos(c),
                "Adecuado si no hay ninguno, alerta con 1 o 2, crítico con 3 o más");

        double bajoF = porcentajeSalarioBajo(lista, 'F');
        double bajoM = porcentajeSalarioBajo(lista, 'M');
        double dif = bajoF - bajoM;
        r[9] = new Indicador("Diferencia en salarios bajos (hasta 2 SMLMV)", dif, "puntos",
                estadoDiferenciaPuntos(dif),
                "Resta del % de mujeres y el % de hombres que ganan hasta 2 salarios mínimos. "
                + "Adecuado si es menor a 10 puntos, alerta entre 10 y 20, crítico desde 20");
        return r;
    }
}
