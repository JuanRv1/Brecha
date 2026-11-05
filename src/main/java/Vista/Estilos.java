package Vista;

import Estructura.ListaDobleEmpleados;
import Estructura.NodoDoble;
import Logica.Formato;
import Modelo.Empleado;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Colores, letras y metodos de ayuda que usan todas las pantallas,
// para que la aplicacion se vea igual en todas partes.
public class Estilos {

    // Paleta principal
    public static final Color OSCURO = new Color(0x14, 0x21, 0x3D);      // azul oscuro
    public static final Color AZUL_MEDIO = new Color(0x1F, 0x4E, 0x79);
    public static final Color NARANJA = new Color(0xFF, 0x6B, 0x35);
    public static final Color VERDE_PALETA = new Color(0x2E, 0x8B, 0x57);
    public static final Color FONDO = new Color(0xF5, 0xF7, 0xFA);       // gris claro
    public static final Color FONDO_INICIO = new Color(0xF8, 0xFA, 0xFC);
    public static final Color ODS5 = NARANJA;                             // color de acento

    // Color de cada opcion del menu
    public static final Color OPC_REGISTRAR = new Color(0x25, 0x63, 0xEB);   // azul
    public static final Color OPC_CONSULTAR = new Color(0x0E, 0xA5, 0xE9);   // celeste
    public static final Color OPC_SALARIO = VERDE_PALETA;                    // verde
    public static final Color OPC_RETIRAR = new Color(0x64, 0x74, 0x8B);     // gris azulado
    public static final Color OPC_NOMINA = new Color(0x7C, 0x3A, 0xED);      // morado
    public static final Color OPC_INDICADORES = NARANJA;                     // naranja
    public static final Color OPC_INFORME = new Color(0xE5, 0x5B, 0x5B);     // rojo suave
    public static final Color OPC_CASOS = new Color(0xF2, 0xB1, 0x1A);       // amarillo
    public static final Color BLANCO = Color.WHITE;
    public static final Color GRIS = new Color(0x6B, 0x72, 0x80);
    public static final Color BORDE = new Color(0xD9, 0xDD, 0xE3);
    public static final Color VERDE = new Color(0x1E, 0x8E, 0x3E);
    public static final Color AMARILLO = new Color(0xC7, 0x7C, 0x02);
    public static final Color ROJO = new Color(0xC6, 0x28, 0x28);

    // Colores del tablero de indicadores
    public static final Color AZUL_1 = new Color(0x4F, 0x8B, 0xFF);
    public static final Color AZUL_2 = new Color(0x6F, 0xB1, 0xFF);
    public static final Color VERDE_1 = new Color(0x2E, 0xC4, 0x9F);
    public static final Color VERDE_2 = new Color(0x5B, 0xE0, 0xC4);
    public static final Color NARANJA_1 = new Color(0xFF, 0xA9, 0x3E);
    public static final Color NARANJA_2 = new Color(0xFF, 0xCB, 0x6B);
    public static final Color ROSA_1 = new Color(0xF5, 0x4B, 0x6A);
    public static final Color ROSA_2 = new Color(0xFF, 0x7B, 0x90);
    public static final Color MUJER = new Color(0x8E, 0x44, 0xAD);
    public static final Color HOMBRE = new Color(0x16, 0x9A, 0x9A);

    public static final Font TITULO = new Font("SansSerif", Font.BOLD, 26);
    public static final Font SUBTITULO = new Font("SansSerif", Font.BOLD, 17);
    public static final Font NORMAL = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font NEGRITA = new Font("SansSerif", Font.BOLD, 14);

    // Boton redondeado con fondo de color
    public static JButton boton(String texto, Color fondo) {
        return new BotonRedondeado(texto, fondo);
    }

    // Campo de texto mas alto, con margen interno, para las busquedas
    public static JTextField campoGrande(int columnas) {
        JTextField t = new JTextField(columnas);
        t.setFont(new Font("SansSerif", Font.PLAIN, 16));
        t.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE, 1, true), new EmptyBorder(8, 12, 8, 12)));
        return t;
    }

    // Etiqueta pequena con fondo de color (por ejemplo, el nivel del empleado)
    public static JLabel insignia(String texto, Color color) {
        JLabel l = new JLabel(texto);
        l.setOpaque(true);
        l.setBackground(new Color(color.getRed(), color.getGreen(), color.getBlue(), 35));
        l.setForeground(color.darker());
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setBorder(new EmptyBorder(4, 10, 4, 10));
        return l;
    }

    // Cuadrito gris claro con un titulo pequeno y un valor grande
    public static JPanel dato(String titulo, JLabel valor) {
        PanelRedondeado p = new PanelRedondeado(FONDO, 12);
        p.setLayout(new BorderLayout(0, 4));
        p.setBorder(new EmptyBorder(12, 14, 12, 14));
        JLabel t = new JLabel(titulo);
        t.setFont(new Font("SansSerif", Font.PLAIN, 12));
        t.setForeground(GRIS);
        valor.setFont(new Font("SansSerif", Font.BOLD, 16));
        valor.setForeground(OSCURO);
        p.add(t, BorderLayout.NORTH);
        p.add(valor, BorderLayout.CENTER);
        return p;
    }

    public static JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(NORMAL);
        l.setForeground(OSCURO);
        return l;
    }

    public static JTextField campo(int columnas) {
        JTextField t = new JTextField(columnas);
        t.setFont(NORMAL);
        return t;
    }

    // Muestra un mensaje en verde (bien) o en rojo (error)
    public static void mensaje(JLabel destino, String texto, boolean bien) {
        destino.setText(texto);
        destino.setForeground(bien ? VERDE : ROJO);
    }

    // Color de cada estado de un indicador
    public static Color colorEstado(int estado) {
        if (estado == 0) {
            return VERDE;
        } else if (estado == 1) {
            return AMARILLO;
        } else if (estado == 2) {
            return ROJO;
        }
        return GRIS;
    }

    // Tabla de empleados recorriendo la lista desde la cabeza (orden de ingreso)
    public static JTable tablaEmpleados(ListaDobleEmpleados lista) {
        DefaultTableModel modelo = modeloEmpleados();
        NodoDoble actual = lista.getCabeza();
        while (actual != null) {
            agregarFila(modelo, actual.getDato());
            actual = actual.getSiguiente();
        }
        return tabla(modelo);
    }

    // Tabla de empleados recorriendo la lista desde la cola (mas recientes primero)
    public static JTable tablaEmpleadosRecientes(ListaDobleEmpleados lista) {
        DefaultTableModel modelo = modeloEmpleados();
        NodoDoble actual = lista.getCola();
        while (actual != null) {
            agregarFila(modelo, actual.getDato());
            actual = actual.getAnterior();
        }
        return tabla(modelo);
    }

    public static DefaultTableModel modeloEmpleados() {
        String[] columnas = {"Código", "Nombre", "Sexo", "Cargo", "Área", "Nivel", "Salario"};
        return new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
    }

    public static void agregarFila(DefaultTableModel modelo, Empleado e) {
        Object[] fila = {e.getCodigo(), e.getNombre(), e.getNombreSexo(), e.getCargo(),
                         e.getArea(), e.getNombreNivel(), Formato.pesos(e.getSalario())};
        modelo.addRow(fila);
    }

    public static JTable tabla(DefaultTableModel modelo) {
        JTable t = new JTable(modelo);
        t.setFont(NORMAL);
        t.setRowHeight(26);
        t.getTableHeader().setFont(NEGRITA);
        t.getTableHeader().setBackground(OSCURO);
        t.getTableHeader().setForeground(BLANCO);
        t.setGridColor(BORDE);
        t.setFillsViewportHeight(true);
        return t;
    }

    // Caja blanca con borde
    public static JPanel tarjeta() {
        JPanel p = new JPanel();
        p.setBackground(BLANCO);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDE), new EmptyBorder(16, 18, 16, 18)));
        return p;
    }

    // Pone un componente arriba a la izquierda, sin estirarlo
    public static JPanel alinearArriba(JComponent c) {
        JPanel envoltura = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        envoltura.setOpaque(false);
        envoltura.add(c);
        return envoltura;
    }
}
