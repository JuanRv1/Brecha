package Vista;

import Logica.GeneradorInforme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

// Muestra el informe de analisis con hallazgos y recomendaciones
public class PanelInforme extends PanelBase {

    private JTextArea texto = new JTextArea();
    private JLabel mensaje = Estilos.etiqueta(" ");

    public PanelInforme(VentanaPrincipal ventana) {
        super(ventana, "Informe de análisis", "Lo que muestran los datos y lo que se recomienda hacer");

        texto.setEditable(false);
        texto.setLineWrap(true);
        texto.setWrapStyleWord(true);
        texto.setFont(new Font("SansSerif", Font.PLAIN, 15));
        texto.setForeground(Estilos.OSCURO);
        texto.setBorder(new EmptyBorder(18, 22, 18, 22));

        JPanel arriba = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        arriba.setOpaque(false);
        JButton actualizar = Estilos.boton("Actualizar informe", Estilos.OSCURO);
        actualizar.addActionListener(e -> alMostrar());
        JButton copiar = Estilos.boton("Copiar texto", Estilos.GRIS);
        copiar.addActionListener(e -> copiar());
        arriba.add(actualizar);
        arriba.add(copiar);
        arriba.add(mensaje);

        contenido.add(arriba, BorderLayout.NORTH);
        contenido.add(new JScrollPane(texto), BorderLayout.CENTER);
    }

    private void copiar() {
        StringSelection seleccion = new StringSelection(texto.getText());
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(seleccion, null);
        Estilos.mensaje(mensaje, "Informe copiado. Puede pegarlo en Word o en un correo.", true);
    }

    @Override
    public void alMostrar() {
        texto.setText(GeneradorInforme.generar(ventana.getGestor().getLista()));
        texto.setCaretPosition(0);          // mostrar desde el principio
        mensaje.setText(" ");
    }
}
