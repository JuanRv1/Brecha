package Modelo;


public class CambioSalario {

    private String codigoEmpleado;
    private double salarioAnterior;
    private double salarioNuevo;

    public CambioSalario(String codigoEmpleado, double salarioAnterior, double salarioNuevo) {
        this.codigoEmpleado = codigoEmpleado;
        this.salarioAnterior = salarioAnterior;
        this.salarioNuevo = salarioNuevo;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public double getSalarioAnterior() {
        return salarioAnterior;
    }

    public double getSalarioNuevo() {
        return salarioNuevo;
    }

    @Override
    public String toString() {
        return "Empleado: " + codigoEmpleado
                + " | Salario anterior: " + salarioAnterior
                + " | Salario nuevo: " + salarioNuevo;
    }
}


