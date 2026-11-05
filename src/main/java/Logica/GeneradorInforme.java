package Logica;

import Estructura.ListaDobleEmpleados;
import Modelo.Empleado;

// Escribe el informe para la organizacion. Primero cuenta lo que se encontro
// con los datos y despues da recomendaciones. Cada parrafo depende de un
// condicional sobre los indicadores: si un indicador esta bien, no se menciona
// como problema.
public class GeneradorInforme {

    public static String generar(ListaDobleEmpleados lista) {
        StringBuilder t = new StringBuilder();

        if (lista.estaVacia()) {
            return "No hay empleados registrados. Registre la información de la plantilla "
                    + "para poder hacer el diagnóstico.";
        }

        Indicador[] ind = CalculadoraIndicadores.calcular(lista);
        int total = CalculadoraIndicadores.contar(lista, ' ', 0);
        int mujeres = CalculadoraIndicadores.contar(lista, 'F', 0);
        int hombres = CalculadoraIndicadores.contar(lista, 'M', 0);
        double promF = CalculadoraIndicadores.promedio(lista, 'F', 0);
        double promM = CalculadoraIndicadores.promedio(lista, 'M', 0);
        double brecha = ind[0].getValor();

        // Cuantos indicadores quedaron en cada estado
        int criticos = 0, alertas = 0, adecuados = 0;
        for (int i = 0; i < ind.length; i++) {
            if (ind[i].getEstado() == Indicador.CRITICO) {
                criticos++;
            } else if (ind[i].getEstado() == Indicador.ALERTA) {
                alertas++;
            } else if (ind[i].getEstado() == Indicador.ADECUADO) {
                adecuados++;
            }
        }

        // ---------- 1. Lo que se encontro ----------
        t.append("DIAGNÓSTICO DE EQUIDAD SALARIAL\n\n");
        t.append("1. Lo que se encontró\n\n");
        t.append("Se analizó la información de ").append(total).append(" personas: ")
         .append(mujeres).append(" mujeres (").append(Formato.porcentaje(mujeres * 100.0 / total))
         .append(") y ").append(hombres).append(" hombres. ");

        if (mujeres == 0 || hombres == 0) {
            t.append("Como la plantilla tiene un solo sexo, no se puede calcular la brecha salarial.\n\n");
        } else {
            t.append("El salario promedio de las mujeres es ").append(Formato.pesos(promF))
             .append(" y el de los hombres es ").append(Formato.pesos(promM)).append(". ");
            if (Math.abs(brecha) < 0.05) {
                t.append("Los dos promedios son prácticamente iguales.\n\n");
            } else if (brecha > 0) {
                t.append("En promedio, las mujeres ganan ").append(Formato.porcentaje(brecha))
                 .append(" menos que los hombres.\n\n");
            } else {
                t.append("En promedio, los hombres ganan ").append(Formato.porcentaje(-brecha))
                 .append(" menos que las mujeres.\n\n");
            }
        }

        // Brecha por nivel: se mencionan solo los niveles con alerta o critico
        String nivelesConBrecha = "";
        for (int n = 1; n <= 4; n++) {
            if (ind[n].getEstado() == Indicador.ALERTA || ind[n].getEstado() == Indicador.CRITICO) {
                if (!nivelesConBrecha.isEmpty()) {
                    nivelesConBrecha += ", ";
                }
                nivelesConBrecha += Empleado.NOMBRES_NIVEL[n].toLowerCase()
                        + " (" + Formato.porcentaje(ind[n].getValor()) + ")";
            }
        }
        if (!nivelesConBrecha.isEmpty()) {
            t.append("Al comparar personas del mismo nivel jerárquico, la diferencia sigue presente en: ")
             .append(nivelesConBrecha).append(". ");
            if (ind[4].getEstado() == Indicador.CRITICO && ind[4].getValor() > ind[1].getValor()) {
                t.append("La brecha es mayor en los cargos más altos que en los operativos. ");
            }
            t.append("\n\n");
        } else if (mujeres > 0 && hombres > 0) {
            t.append("Dentro de cada nivel jerárquico la diferencia de salario es menor al 5 %.\n\n");
        }

        // Participacion de mujeres
        if (ind[5].getEstado() != Indicador.ADECUADO) {
            t.append("Las mujeres son el ").append(Formato.porcentaje(ind[5].getValor()))
             .append(" de la plantilla, fuera del rango equilibrado de 40 % a 60 %. ");
        }
        if (ind[7].getEstado() == Indicador.ALERTA || ind[7].getEstado() == Indicador.CRITICO) {
            t.append("En alta dirección ocupan el ").append(Formato.porcentaje(ind[7].getValor()))
             .append(" de los cargos. ");
        }
        if (ind[6].getEstado() == Indicador.ALERTA || ind[6].getEstado() == Indicador.CRITICO) {
            t.append("En mando medio son el ").append(Formato.porcentaje(ind[6].getValor())).append(". ");
        }
        if (ind[5].getEstado() != Indicador.ADECUADO || ind[6].getEstado() >= Indicador.ALERTA
                || ind[7].getEstado() >= Indicador.ALERTA) {
            t.append("\n\n");
        }

        // Cargos iguales con salarios distintos
        int cargos = (int) ind[8].getValor();
        if (cargos > 0) {
            t.append("Hay ").append(cargos).append(cargos == 1 ? " cargo" : " cargos")
             .append(" donde hombres y mujeres hacen el mismo trabajo y la diferencia de salario es de 5 % o más. ")
             .append("Las personas afectadas aparecen en la opción Casos por revisar.\n\n");
        }

        // Salarios bajos
        if (ind[9].getEstado() == Indicador.ALERTA || ind[9].getEstado() == Indicador.CRITICO) {
            String grupo = ind[9].getValor() > 0 ? "las mujeres" : "los hombres";
            t.append("Entre quienes ganan hasta 2 salarios mínimos hay más proporción de ").append(grupo)
             .append(": una diferencia de ").append(String.format("%.1f", Math.abs(ind[9].getValor())))
             .append(" puntos.\n\n");
        }

        t.append("Resultado de los ").append(ind.length).append(" indicadores: ")
         .append(criticos).append(" en estado crítico, ")
         .append(alertas).append(" en alerta y ").append(adecuados).append(" en estado adecuado.\n\n");

        // ---------- 2. Recomendaciones ----------
        t.append("2. Recomendaciones\n\n");
        int num = 1;

        if (criticos == 0 && alertas == 0) {
            t.append("La organización no presenta brechas por encima de los criterios usados en este diagnóstico. "
                    + "Se recomienda mantener las prácticas actuales y repetir el análisis una vez al año o "
                    + "cuando haya cambios grandes en la plantilla.\n\n");
        }

        if (cargos > 0) {
            t.append(num++).append(". Revisar uno por uno los casos de la opción Casos por revisar. ")
             .append("La Ley 1496 de 2011 obliga a pagar igual por un trabajo de igual valor y presume que ")
             .append("toda diferencia salarial es injustificada hasta que el empleador demuestre factores ")
             .append("objetivos, como capacidades, esfuerzo, responsabilidades o condiciones de trabajo. ")
             .append("Si la diferencia no se puede justificar, conviene ajustar el salario.\n\n");
        }

        if (ind[0].getEstado() == Indicador.ALERTA || ind[0].getEstado() == Indicador.CRITICO) {
            t.append(num++).append(". Fijar una meta para reducir la brecha general por debajo del 5 %. ")
             .append("La ley colombiana no establece un porcentaje máximo para las empresas privadas, pero ")
             .append("en la Unión Europea la Directiva 2023/970 exige revisar los salarios cuando la ")
             .append("brecha llega al 5 % sin justificación, y ese valor sirve como referencia.\n\n");
        }

        if (ind[7].getEstado() == Indicador.ALERTA || ind[7].getEstado() == Indicador.CRITICO
                || ind[6].getEstado() == Indicador.ALERTA || ind[6].getEstado() == Indicador.CRITICO) {
            t.append(num++).append(". Abrir más espacio a mujeres en los cargos de dirección, por ejemplo ")
             .append("con planes de ascenso y procesos de selección con candidatas de ambos sexos. ")
             .append("La Ley 2424 de 2024 exige que el 50 % de los cargos directivos del sector público ")
             .append("sea ocupado por mujeres. Para una empresa privada no es obligatoria, pero es una meta de referencia.\n\n");
        }

        if (ind[5].getEstado() == Indicador.ALERTA || ind[5].getEstado() == Indicador.CRITICO) {
            t.append(num++).append(". Revisar los procesos de contratación para que la plantilla se acerque ")
             .append("a una proporción de entre 40 % y 60 % de cada sexo.\n\n");
        }

        if (ind[9].getEstado() == Indicador.ALERTA || ind[9].getEstado() == Indicador.CRITICO) {
            t.append(num++).append(". Revisar por qué un sexo se concentra en los salarios más bajos y ")
             .append("ofrecer formación y oportunidades de ascenso a esas personas.\n\n");
        }

        if (criticos > 0 || alertas > 0) {
            t.append(num++).append(". Considerar la certificación Sello Equipares del Ministerio del Trabajo, ")
             .append("que acompaña a las empresas en la implementación de un sistema de igualdad de género.\n\n");
        }

        t.append(num).append(". Mantener actualizado el registro de cargos por sexo, funciones y remuneración ")
         .append("que exige el artículo 5 de la Ley 1496 de 2011. Su incumplimiento puede generar multas ")
         .append("de hasta 150 salarios mínimos.\n\n");

        t.append("Nota: los límites usados para clasificar los indicadores (5 % y 10 % para la brecha, ")
         .append("40 % a 60 % para la participación) son criterios de este sistema basados en referencias ")
         .append("internacionales. La brecha calculada es la diferencia entre promedios y no tiene en cuenta ")
         .append("experiencia, formación ni horas trabajadas.");

        return t.toString();
    }
}
