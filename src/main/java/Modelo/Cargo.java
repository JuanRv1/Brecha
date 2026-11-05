package Modelo;


// Cargo de la organizacion (tabla CARGO de la base de datos).
// Cada cargo pertenece a un area y a un nivel jerarquico.
public class Cargo {

    private int idCargo;
    private String nombre;
    private String area;
    private int nivel;

    public Cargo(int idCargo, String nombre, String area, int nivel) {
        this.idCargo = idCargo;
        this.nombre = nombre;
        this.area = area;
        this.nivel = nivel;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getArea() {
        return area;
    }

    public int getNivel() {
        return nivel;
    }

    // Texto que aparece en la lista desplegable del formulario
    @Override
    public String toString() {
        return nombre + "  (" + area + ", " + Empleado.NOMBRES_NIVEL[nivel] + ")";
    }
}
