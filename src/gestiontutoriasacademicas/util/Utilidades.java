/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 23/05/2022.
 * Descripción: Utilidades para la ejecución correcta del programa informático.
 */
package gestiontutoriasacademicas.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Utilidades {

    public static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    public static Optional<ButtonType> mostrarConfirmacion(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(null);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        return alerta.showAndWait();
    }

    public static String convertirFechaAString(Date fecha) {
        DateFormat formatoFechaPorConvertir = new SimpleDateFormat("dd/MM/yyyy");
        String fechaEnString = formatoFechaPorConvertir.format(fecha);
        return fechaEnString;
    }

}