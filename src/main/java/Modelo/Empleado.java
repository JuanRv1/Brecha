package Modelo;


public class Empleado {

    public static final String[] NOMBRES_NIVEL = {
        "Todos", "Operativo", "Profesional/Técnico",
        "Mando medio", "Alta dirección"
    };

    private String codigo;
    private String nombre;
    private char sexo;        // solo 'F' (femenino) o 'M' (masculino)
    private String cargo;
    private String area;
    private int nivel;        // 1 = Operativo ... 4 = Alta dirección
    private double salario;
    private int idCargo;      // id del cargo en la base de datos
    private String documento; // cedula (la base de datos no permite repetirla)

    // Constructor
    public Empleado(String codigo, String nombre, char sexo,
                    String cargo, String area, int nivel, double salario) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.sexo = sexo;
        this.cargo = cargo;
        this.area = area;
        this.nivel = nivel;
        this.salario = salario;
    }

    // Revisa que los datos sean correctos antes de guardar el empleado.
    // Devuelve null si todo esta bien, o el mensaje del error.
    public String validar() {
        if (codigo == null || codigo.trim().isEmpty()) {
            return "El código es obligatorio";
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            return "El nombre es obligatorio";
        }
        if (documento == null || documento.trim().isEmpty()) {
            return "El documento es obligatorio";
        }
        if (sexo != 'F' && sexo != 'M') {
            return "El sexo debe ser femenino o masculino";
        }
        if (nivel < 1 || nivel > 4) {
            return "El nivel debe estar entre 1 y 4";
        }
        if (salario <= 0) {
            return "El salario debe ser mayor que cero";
        }
        return null;
    }

    // Metodos para obtener los datos
    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public char getSexo() {
        return sexo;
    }

    public String getCargo() {
        return cargo;
    }

    public String getArea() {
        return area;
    }

    public int getNivel() {
        return nivel;
    }

    public double getSalario() {
        return salario;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    // Nombre del nivel: "Operativo", "Mando medio", etc.
    public String getNombreNivel() {
        return NOMBRES_NIVEL[nivel];
    }

    // Texto del sexo para mostrar en pantalla
    public String getNombreSexo() {
        if (sexo == 'F') {
            return "Femenino";
        }
        return "Masculino";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    // Validacion
    public void setSalario(double salario) {
        if (salario > 0) {
            this.salario = salario;
        } else {
            System.out.println("El salario debe ser mayor que cero");
        }
    }

    // Mostrar informacion del empleado
    public String Infoempleado() {
        return "Codigo: " + codigo +
               "\nNombre: " + nombre +
               "\nSexo: " + sexo +
               "\nCargo: " + cargo +
               "\nArea: " + area +
               "\nNivel: " + nivel +
               "\nSalario: " + salario;
    }

    @Override
    public String toString() {
        return Infoempleado();
    }
}
