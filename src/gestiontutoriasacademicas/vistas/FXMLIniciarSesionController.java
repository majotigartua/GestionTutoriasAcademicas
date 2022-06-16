/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: Controlador de la pantalla de 'Iniciar sesión'.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.modelo.dao.TutorAcademicoDAO;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLIniciarSesionController implements Initializable {

    @FXML
    private TextField textFieldNombreUsuario;
    @FXML
    private Label labelCamposVacios;
    @FXML
    private PasswordField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void clicButtonIngresar(ActionEvent event) {
        labelCamposVacios.setText("");
        String nombreUsuario = textFieldNombreUsuario.getText();
        String contrasenia = passwordField.getText();
        if (nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
            labelCamposVacios.setText("No se puede dejar ningún campo vacío.");
        } else {
            validarIniciarSesion(nombreUsuario, contrasenia);
        }
    }

    private void validarIniciarSesion(String nombreUsuario, String contrasenia) {
        TutorAcademico tutorAcademico = TutorAcademicoDAO.iniciarSesion(nombreUsuario, contrasenia);
        switch (tutorAcademico.getCodigoRespuesta()) {
            case Constantes.CODIGO_INICIO_SESION_CORRECTO:
                Utilidades.mostrarAlerta("AVISO",
                        "El nombre de usuario y contraseña son correctos. \n\nBienvenido/a al sistema, "
                        + tutorAcademico.getNombre() + " " + tutorAcademico.getApellidoPaterno() + " " + tutorAcademico.getApellidoMaterno() + ".\n",
                        Alert.AlertType.INFORMATION);
                irMenuPrincipal(tutorAcademico);
                break;
            case Constantes.CODIGO_CREDENCIALES_INCORRECTAS:
                Utilidades.mostrarAlerta("AVISO",
                        "Los datos ingresados son inválidos. \n\nPor favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
                passwordField.setText("");
                break;
            case Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS:
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                break;
        }
    }

    private void irMenuPrincipal(TutorAcademico tutorAcademico) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMenuPrincipal.fxml"));
        try {
            Parent root = loader.load();
            FXMLMenuPrincipalController menuPrincipal = loader.getController();
            menuPrincipal.configurarEscena(tutorAcademico);
            Stage escenarioPrincipal = (Stage) textFieldNombreUsuario.getScene().getWindow();
            Scene pantallaMenuPrincipal = new Scene(root);
            escenarioPrincipal.setScene(pantallaMenuPrincipal);
            escenarioPrincipal.setTitle("Menú principal");
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Menú principal'...");
        }
    }

}