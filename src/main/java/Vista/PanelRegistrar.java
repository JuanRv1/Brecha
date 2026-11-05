package Vista;

import Conexion.CargoDAO;
import Logica.Formato;
import Modelo.Cargo;
import Modelo.Empleado;

import javax.swing.*;
import java.awt.*;

// Formulario para registrar un empleado nuevo
public class PanelRegistrar extends PanelBase {

    private JTextField codigo = Estilos.campo(10);
    private JTextField nombre = Estilos.campo(24);
    private JTextField documento = Estilos.campo(14);
    private JComboBox<String> sexo = new JComboBox<>(new String[] {"Femenino", "Masculino"});
    private JComboBox<Cargo> cargo;
    private JTextField salario = Estilos.campo(14);
    private JLabel mensaje = Estilos.etiqueta(" ");

    public PanelRegistrar(VentanaPrincipal ventana, CargoDAO cargoDAO) {
        super(ventana, "Registrar empleado", "Agregue una persona a la nómina de la organización");
        cargo = new JComboBox<>(cargoDAO.listar());
        sexo.setFont(Estilos.NORMAL);
        cargo.setFont(Estilos.NORMAL);

        JPanel form = Estilos.tarjeta();
        form.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 6, 8, 6);
        g.anchor = GridBagConstraints.WEST;

        fila(form, g, 0, "Código (ej. E017):", codigo);
        fila(form, g, 1, "Nombre completo:", nombre);
        fila(form, g, 2, "Documento:", documento);
        fila(form, g, 3, "Sexo:", sexo);
        fila(form, g, 4, "Cargo:", cargo);
        fila(form, g, 5, "Salario mensual ($):", salario);

        JButton guardar = Estilos.boton("Guardar empleado", Estilos.VERDE);
        guardar.addActionListener(e -> guardar());
        g.gridx = 1;
        g.gridy = 6;
        form.add(guardar, g);
        g.gridy = 7;
        form.add(mensaje, g);

        contenido.add(Estilos.alinearArriba(form), BorderLayout.NORTH);
    }

    private void fila(JPanel form, GridBagConstraints g, int y, String texto, JComponent campo) {
        g.gridx = 0;
        g.gridy = y;
        form.add(Estilos.etiqueta(texto), g);
        g.gridx = 1;
        form.add(campo, g);
    }

    private void guardar() {
        double valor = Formato.leerPesos(salario.getText());
        if (valor < 0) {
            Estilos.mensaje(mensaje, "Escriba un salario válido, por ejemplo 2.500.000", false);
            return;
        }
        char sx = (sexo.getSelectedIndex() == 0) ? 'F' : 'M';
        Cargo c = (Cargo) cargo.getSelectedItem();
        String cod = codigo.getText().trim().toUpperCase();

        Empleado e = new Empleado(cod, nombre.getText().trim(), sx,
                c.getNombre(), c.getArea(), c.getNivel(), valor);
        e.setIdCargo(c.getIdCargo());
        e.setDocumento(documento.getText().trim());

        String error = ventana.getGestor().registrar(e);
        if (error == null) {
            Estilos.mensaje(mensaje, "Empleado " + cod + " registrado.", true);
            codigo.setText("");
            nombre.setText("");
            documento.setText("");
            salario.setText("");
        } else {
            Estilos.mensaje(mensaje, error, false);
        }
    }

    @Override
    public void alMostrar() {
        mensaje.setText(" ");
    }
}
