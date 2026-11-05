package Vista;

import Conexion.CargoDAO;
import Logica.GestorEmpleados;

import javax.swing.*;
import java.awt.*;

// Unica ventana de la aplicacion. Tiene dos niveles de CardLayout:
//   1. la pantalla de inicio o la aplicacion (menu lateral + pantallas)
//   2. dentro de la aplicacion, una carta por cada pantalla
public class VentanaPrincipal extends JFrame {

    public static final String INICIO = "inicio";
    public static final String MENU = "menu";
    public static final String REGISTRAR = "registrar";
    public static final String CONSULTAR = "consultar";
    public static final String MODIFICAR = "modificar";
    public static final String RETIRAR = "retirar";
    public static final String NOMINA = "nomina";
    public static final String SALARIOS = "salarios";
    public static final String INDICADORES = "indicadores";
    public static final String INFORME = "informe";
    public static final String CASOS = "casos";

    private static final String APP = "app";

    private CardLayout cartasRaiz = new CardLayout();
    private JPanel raiz = new JPanel(cartasRaiz);
    private CardLayout cartas = new CardLayout();
    private JPanel contenedor = new JPanel(cartas);
    private MenuLateral menuLateral;
    private GestorEmpleados gestor;

    // Pantallas, para actualizarlas cada vez que se abren
    private String[] nombres = new String[10];
    private PanelBase[] paneles = new PanelBase[10];
    private int cantidad = 0;

    public VentanaPrincipal(GestorEmpleados gestor, CargoDAO cargoDAO) {
        super("Diagnóstico de Equidad Salarial");
        this.gestor = gestor;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 760);
        setMinimumSize(new Dimension(1050, 650));
        setLocationRelativeTo(null);

        agregar(MENU, new PanelMenu(this));
        agregar(REGISTRAR, new PanelRegistrar(this, cargoDAO));
        agregar(CONSULTAR, new PanelConsultar(this));
        agregar(MODIFICAR, new PanelModificar(this));
        agregar(RETIRAR, new PanelRetirar(this));
        agregar(NOMINA, new PanelNomina(this));
        agregar(SALARIOS, new PanelSalarios(this, cargoDAO));
        agregar(INDICADORES, new PanelIndicadores(this));
        agregar(INFORME, new PanelInforme(this));
        agregar(CASOS, new PanelCasos(this));

        // La aplicacion: menu a la izquierda y pantallas a la derecha
        menuLateral = new MenuLateral(this);
        JPanel app = new JPanel(new BorderLayout());
        app.add(menuLateral, BorderLayout.WEST);
        app.add(contenedor, BorderLayout.CENTER);

        raiz.add(new PanelInicio(this), INICIO);
        raiz.add(app, APP);
        setContentPane(raiz);
        mostrar(INICIO);
    }

    private void agregar(String nombre, PanelBase panel) {
        contenedor.add(panel, nombre);
        nombres[cantidad] = nombre;
        paneles[cantidad] = panel;
        cantidad++;
    }

    // Cambia a la pantalla indicada
    public void mostrar(String nombre) {
        if (nombre.equals(INICIO)) {
            cartasRaiz.show(raiz, INICIO);
            return;
        }
        for (int i = 0; i < cantidad; i++) {
            if (nombres[i].equals(nombre)) {
                paneles[i].alMostrar();
            }
        }
        cartas.show(contenedor, nombre);
        menuLateral.marcar(nombre);
        cartasRaiz.show(raiz, APP);
    }

    public GestorEmpleados getGestor() {
        return gestor;
    }
}
