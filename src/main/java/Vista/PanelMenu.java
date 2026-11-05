package Vista;

import Estructura.ListaDobleEmpleados;
import Logica.CalculadoraIndicadores;
import Logica.Indicador;

import javax.swing.*;
import java.awt.*;

// Panel principal: resumen con cuatro numeros y una tarjeta de color por cada opcion
public class PanelMenu extends PanelBase {

    private TarjetaResumen resEmpleados = new TarjetaResumen("Empleados", Estilos.OPC_REGISTRAR);
    private TarjetaResumen resMujeres = new TarjetaResumen("Mujeres en la plantilla", Estilos.OPC_NOMINA);
    private TarjetaResumen resBrecha = new TarjetaResumen("Brecha salarial", Estilos.OPC_INDICADORES);
    private TarjetaResumen resCasos = new TarjetaResumen("Casos por revisar", Estilos.OPC_CASOS);

    public PanelMenu(VentanaPrincipal ventana) {
        super(ventana, "Panel principal", ventana.getGestor().getOrigenDatos());

        JPanel filaResumen = new JPanel(new GridLayout(1, 4, 16, 0));
        filaResumen.setOpaque(false);
        filaResumen.add(resEmpleados);
        filaResumen.add(resMujeres);
        filaResumen.add(resBrecha);
        filaResumen.add(resCasos);

        JLabel tituloOpciones = Estilos.etiqueta("¿Qué desea hacer?");
        tituloOpciones.setFont(Estilos.SUBTITULO);

        Color blanco = Estilos.BLANCO;
        JPanel grilla = new JPanel(new GridLayout(3, 3, 16, 16));
        grilla.setOpaque(false);
        grilla.add(opcion("Registrar", "Agregar un empleado", Icono.REGISTRAR, Estilos.OPC_REGISTRAR, blanco, VentanaPrincipal.REGISTRAR));
        grilla.add(opcion("Consultar", "Buscar por código", Icono.CONSULTAR, Estilos.OPC_CONSULTAR, blanco, VentanaPrincipal.CONSULTAR));
        grilla.add(opcion("Salario", "Modificar o deshacer", Icono.SALARIO, Estilos.OPC_SALARIO, blanco, VentanaPrincipal.MODIFICAR));
        grilla.add(opcion("Nómina", "Listado completo", Icono.NOMINA, Estilos.OPC_NOMINA, blanco, VentanaPrincipal.NOMINA));
        grilla.add(opcion("Indicadores", "Tablero y gráficas", Icono.INDICADORES, Estilos.OPC_INDICADORES, blanco, VentanaPrincipal.INDICADORES));
        grilla.add(opcion("Casos por revisar", "Mismo cargo, distinto salario", Icono.CASOS, Estilos.OPC_CASOS, Estilos.OSCURO, VentanaPrincipal.CASOS));
        grilla.add(opcion("Informe", "Hallazgos y recomendaciones", Icono.INFORME, Estilos.OPC_INFORME, blanco, VentanaPrincipal.INFORME));
        grilla.add(opcion("Consulta por salario", "Por rango o por cargo", Icono.CONSULTAR, Estilos.AZUL_MEDIO, blanco, VentanaPrincipal.SALARIOS));
        grilla.add(opcion("Retirar", "Quitar de la nómina", Icono.RETIRAR, Estilos.OPC_RETIRAR, blanco, VentanaPrincipal.RETIRAR));

        JPanel columna = new JPanel();
        columna.setOpaque(false);
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
        fijar(filaResumen, 96);
        columna.add(filaResumen);
        columna.add(Box.createVerticalStrut(24));
        tituloOpciones.setAlignmentX(Component.LEFT_ALIGNMENT);
        columna.add(tituloOpciones);
        columna.add(Box.createVerticalStrut(12));
        fijar(grilla, 3 * 110 + 2 * 16);
        columna.add(grilla);

        contenido.add(columna, BorderLayout.NORTH);
    }

    private void fijar(JComponent c, int alto) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, alto));
        c.setPreferredSize(new Dimension(800, alto));
    }

    private TarjetaOpcion opcion(String titulo, String desc, int icono, Color color, Color texto, String pantalla) {
        return new TarjetaOpcion(titulo, desc, icono, color, texto, () -> ventana.mostrar(pantalla));
    }

    @Override
    public void alMostrar() {
        ListaDobleEmpleados lista = ventana.getGestor().getLista();
        Indicador[] ind = CalculadoraIndicadores.calcular(lista);
        resEmpleados.mostrar(String.valueOf(lista.getTamanio()));
        resMujeres.mostrar(ind[5].getValorTexto());
        resBrecha.mostrar(ind[0].getValorTexto());
        resCasos.mostrar(String.valueOf(ventana.getGestor().getCasos().getTamanio()));
    }
}
