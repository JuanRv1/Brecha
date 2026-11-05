package Vista;

import Estructura.ListaDobleEmpleados;
import Logica.CalculadoraIndicadores;
import Logica.Formato;
import Logica.Indicador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Tablero de indicadores: tarjetas de resumen, graficas y la tabla con el estado de cada indicador
public class PanelIndicadores extends PanelBase {

    private TarjetaIndicador tarjetaEmpleados = new TarjetaIndicador();
    private TarjetaIndicador tarjetaBrecha = new TarjetaIndicador();
    private TarjetaIndicador tarjetaMujeres = new TarjetaIndicador();
    private TarjetaIndicador tarjetaCasos = new TarjetaIndicador();
    private GraficoBarras barras = new GraficoBarras();
    private GraficoDona donaPlantilla = new GraficoDona("Plantilla por sexo");
    private GraficoDona donaDireccion = new GraficoDona("Alta dirección");
    private JPanel resumen = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    private JPanel zonaTabla = new JPanel(new BorderLayout());
    private Indicador[] indicadores;

    public PanelIndicadores(VentanaPrincipal ventana) {
        super(ventana, "Indicadores", "Brecha salarial = (promedio hombres − promedio mujeres) / promedio hombres × 100");

        // Fila 1: cuatro tarjetas
        JPanel filaTarjetas = new JPanel(new GridLayout(1, 4, 16, 0));
        filaTarjetas.setOpaque(false);
        filaTarjetas.add(tarjetaEmpleados);
        filaTarjetas.add(tarjetaBrecha);
        filaTarjetas.add(tarjetaMujeres);
        filaTarjetas.add(tarjetaCasos);

        // Fila 2: barras (mas ancha) y dos donas
        JPanel filaGraficos = new JPanel(new GridBagLayout());
        filaGraficos.setOpaque(false);
        GridBagConstraints g = new GridBagConstraints();
        g.fill = GridBagConstraints.BOTH;
        g.gridy = 0;
        g.weighty = 1;
        g.gridx = 0;
        g.weightx = 2;
        g.insets = new Insets(0, 0, 0, 16);
        filaGraficos.add(barras, g);
        g.gridx = 1;
        g.weightx = 1;
        filaGraficos.add(donaPlantilla, g);
        g.gridx = 2;
        g.insets = new Insets(0, 0, 0, 0);
        filaGraficos.add(donaDireccion, g);

        // Fila 3: titulo, conteo por estado y tabla
        JLabel tituloTabla = Estilos.etiqueta("Detalle de los indicadores");
        tituloTabla.setFont(Estilos.SUBTITULO);
        resumen.setOpaque(false);
        JPanel cabecera = new JPanel(new BorderLayout());
        cabecera.setOpaque(false);
        cabecera.add(tituloTabla, BorderLayout.WEST);
        cabecera.add(resumen, BorderLayout.EAST);
        zonaTabla.setOpaque(false);

        // Todo va en una columna que se puede desplazar hacia abajo
        JPanel columna = new ColumnaAjustada();
        columna.setLayout(new BoxLayout(columna, BoxLayout.Y_AXIS));
        columna.setBackground(Estilos.FONDO);
        columna.setBorder(new EmptyBorder(0, 0, 10, 6));
        agregar(columna, filaTarjetas, 130);
        agregar(columna, filaGraficos, 360);
        agregar(columna, cabecera, 40);
        columna.add(zonaTabla);

        JScrollPane scroll = new JScrollPane(columna);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        contenido.add(scroll, BorderLayout.CENTER);
    }

    private void agregar(JPanel columna, JComponent c, int alto) {
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, alto));
        c.setPreferredSize(new Dimension(900, alto));
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        columna.add(c);
        columna.add(Box.createVerticalStrut(18));
    }

    @Override
    public void alMostrar() {
        ListaDobleEmpleados lista = ventana.getGestor().getLista();
        indicadores = CalculadoraIndicadores.calcular(lista);

        actualizarTarjetas(lista);
        actualizarGraficos(lista);
        actualizarTabla();
    }

    private void actualizarTarjetas(ListaDobleEmpleados lista) {
        int total = CalculadoraIndicadores.contar(lista, ' ', 0);
        int mujeres = CalculadoraIndicadores.contar(lista, 'F', 0);
        tarjetaEmpleados.mostrar("Empleados", String.valueOf(total),
                mujeres + " mujeres · " + (total - mujeres) + " hombres", Estilos.AZUL_1, Estilos.AZUL_2);

        // Las otras tarjetas toman el color del estado de su indicador
        Indicador brecha = indicadores[0];
        tarjetaBrecha.mostrar("Brecha salarial", brecha.getValorTexto(),
                "Estado: " + brecha.getEstadoTexto(), colorFuerte(brecha.getEstado()), colorSuave(brecha.getEstado()));

        Indicador part = indicadores[5];
        tarjetaMujeres.mostrar("Mujeres en la plantilla", part.getValorTexto(),
                "Meta: 40 % a 60 %", colorFuerte(part.getEstado()), colorSuave(part.getEstado()));

        int casos = ventana.getGestor().getCasos().getTamanio();
        int estadoCasos = indicadores[8].getEstado();
        tarjetaCasos.mostrar("Casos por revisar", String.valueOf(casos),
                "Mismo cargo, distinto salario", colorFuerte(estadoCasos), colorSuave(estadoCasos));
    }

    private void actualizarGraficos(ListaDobleEmpleados lista) {
        // Nombres cortos para que quepan debajo de las barras
        String[] nombres = {"Operativo", "Profesional", "Mando medio", "Dirección"};
        double[] promMujeres = new double[4];
        double[] promHombres = new double[4];
        double[] brechas = new double[4];
        int[] estados = new int[4];
        for (int n = 1; n <= 4; n++) {
            promMujeres[n - 1] = CalculadoraIndicadores.promedio(lista, 'F', n);
            promHombres[n - 1] = CalculadoraIndicadores.promedio(lista, 'M', n);
            brechas[n - 1] = indicadores[n].getValor();
            estados[n - 1] = indicadores[n].getEstado();
        }
        barras.mostrar(nombres, promMujeres, promHombres, brechas, estados);

        donaPlantilla.mostrar(CalculadoraIndicadores.contar(lista, 'F', 0),
                              CalculadoraIndicadores.contar(lista, 'M', 0));
        donaDireccion.mostrar(CalculadoraIndicadores.contar(lista, 'F', 4),
                              CalculadoraIndicadores.contar(lista, 'M', 4));
    }

    private void actualizarTabla() {
        int criticos = 0, alertas = 0, adecuados = 0;
        for (int i = 0; i < indicadores.length; i++) {
            if (indicadores[i].getEstado() == Indicador.CRITICO) {
                criticos++;
            } else if (indicadores[i].getEstado() == Indicador.ALERTA) {
                alertas++;
            } else if (indicadores[i].getEstado() == Indicador.ADECUADO) {
                adecuados++;
            }
        }
        resumen.removeAll();
        resumen.add(chip(criticos + (criticos == 1 ? " crítico" : " críticos"), Estilos.ROJO));
        resumen.add(chip(alertas + " en alerta", Estilos.AMARILLO));
        resumen.add(chip(adecuados + (adecuados == 1 ? " adecuado" : " adecuados"), Estilos.VERDE));
        resumen.revalidate();
        resumen.repaint();

        String[] columnas = {"Indicador", "Valor", "Estado", "Cómo se clasifica"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int f, int c) {
                return false;
            }
        };
        for (int i = 0; i < indicadores.length; i++) {
            Indicador ind = indicadores[i];
            Object[] fila = {ind.getNombre(), ind.getValorTexto(), ind.getEstadoTexto(), ind.getCriterio()};
            modelo.addRow(fila);
        }
        JTable tabla = Estilos.tabla(modelo);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(320);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(520);
        tabla.setRowHeight(30);
        tabla.getColumnModel().getColumn(2).setCellRenderer(new ColorEstado());
        tabla.getColumnModel().getColumn(3).setCellRenderer(new TextoChico());

        // La tabla va sin su propio scroll: se ve completa dentro de la pantalla
        zonaTabla.removeAll();
        zonaTabla.add(tabla.getTableHeader(), BorderLayout.NORTH);
        zonaTabla.add(tabla, BorderLayout.CENTER);
        zonaTabla.setAlignmentX(Component.LEFT_ALIGNMENT);
        zonaTabla.revalidate();
        zonaTabla.repaint();
    }

    // Colores del degradado segun el estado
    private Color colorFuerte(int estado) {
        if (estado == Indicador.ADECUADO) {
            return Estilos.VERDE_1;
        } else if (estado == Indicador.ALERTA) {
            return Estilos.NARANJA_1;
        } else if (estado == Indicador.CRITICO) {
            return Estilos.ROSA_1;
        }
        return Estilos.GRIS;
    }

    private Color colorSuave(int estado) {
        if (estado == Indicador.ADECUADO) {
            return Estilos.VERDE_2;
        } else if (estado == Indicador.ALERTA) {
            return Estilos.NARANJA_2;
        } else if (estado == Indicador.CRITICO) {
            return Estilos.ROSA_2;
        }
        return Estilos.BORDE;
    }

    private JLabel chip(String texto, Color color) {
        JLabel l = new JLabel(texto);
        l.setOpaque(true);
        l.setBackground(color);
        l.setForeground(Estilos.BLANCO);
        l.setFont(Estilos.NEGRITA);
        l.setBorder(new EmptyBorder(6, 12, 6, 12));
        return l;
    }

    // Panel que se ajusta al ancho de la ventana: solo se desplaza hacia abajo, nunca de lado
    private static class ColumnaAjustada extends JPanel implements Scrollable {
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(Rectangle r, int orientacion, int direccion) {
            return 16;
        }

        @Override
        public int getScrollableBlockIncrement(Rectangle r, int orientacion, int direccion) {
            return 200;
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

    // Pinta la celda del estado con su color
    private class ColorEstado extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object valor, boolean sel,
                                                       boolean foco, int fila, int col) {
            JLabel l = (JLabel) super.getTableCellRendererComponent(t, valor, sel, foco, fila, col);
            l.setBackground(Estilos.colorEstado(indicadores[fila].getEstado()));
            l.setForeground(Estilos.BLANCO);
            l.setFont(Estilos.NEGRITA);
            l.setHorizontalAlignment(SwingConstants.CENTER);
            return l;
        }
    }

    // Letra mas pequena y gris para la columna del criterio
    private class TextoChico extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object valor, boolean sel,
                                                       boolean foco, int fila, int col) {
            JLabel l = (JLabel) super.getTableCellRendererComponent(t, valor, sel, foco, fila, col);
            l.setFont(new Font("SansSerif", Font.PLAIN, 12));
            l.setForeground(Estilos.GRIS);
            l.setToolTipText((String) valor);
            return l;
        }
    }
}
