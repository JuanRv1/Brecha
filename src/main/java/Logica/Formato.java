package Logica;


// Ayudas para escribir numeros en pantalla
public class Formato {

    // 1750905 -> "$ 1.750.905"
    public static String pesos(double valor) {
        return "$ " + String.format(java.util.Locale.US, "%,.0f", valor).replace(',', '.');
    }

    // 25.234 -> "25,2 %"
    public static String porcentaje(double valor) {
        //   es un espacio que no se parte: asi el % no queda solo en otra linea
        return String.format("%.1f", valor).replace('.', ',') + " %";
    }

    // Convierte lo que escribe el usuario ("4.200.000" o "$4200000") en numero.
    // Devuelve -1 si no es un numero valido.
    public static double leerPesos(String texto) {
        String limpio = texto.replace("$", "").replace(".", "").replace(",", "").replace(" ", "").trim();
        if (limpio.isEmpty()) {
            return -1;
        }
        try {
            return Double.parseDouble(limpio);
        } catch (NumberFormatException ex) {
            return -1;
        }
    }
}
