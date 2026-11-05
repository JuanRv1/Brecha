package Logica;


// Un indicador calculado: su nombre, su valor y en que estado quedo.
public class Indicador {

    public static final int SIN_DATOS = -1;
    public static final int ADECUADO = 0;
    public static final int ALERTA = 1;
    public static final int CRITICO = 2;

    private String nombre;
    private double valor;
    private String unidad;       // "%", "puntos" o "cargos"
    private int estado;
    private String criterio;     // como se decide el estado

    public Indicador(String nombre, double valor, String unidad, int estado, String criterio) {
        this.nombre = nombre;
        this.valor = valor;
        this.unidad = unidad;
        this.estado = estado;
        this.criterio = criterio;
    }

    public String getNombre() {
        return nombre;
    }

    public double getValor() {
        return valor;
    }

    public int getEstado() {
        return estado;
    }

    public String getCriterio() {
        return criterio;
    }

    // Valor listo para mostrar, por ejemplo "25,2 %" o "3 cargos"
    public String getValorTexto() {
        if (estado == SIN_DATOS) {
            return "Sin datos";
        }
        if (unidad.equals("cargos")) {
            return (int) valor + " cargos";
        }
        return String.format("%.1f %s", valor, unidad).replace('.', ',');
    }

    public String getEstadoTexto() {
        if (estado == ADECUADO) {
            return "Adecuado";
        } else if (estado == ALERTA) {
            return "Alerta";
        } else if (estado == CRITICO) {
            return "Crítico";
        }
        return "Sin datos";
    }
}
