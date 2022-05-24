package gestiontutoriasacademicas.vistas;

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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class FXMLMenuPrincipalController implements Initializable {

    private static TutorAcademico tutorAcademico;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configurarEscena(TutorAcademico tutorAcademico) {
        this.tutorAcademico = tutorAcademico;
    }

    @FXML
    private void clicButtonLlenarReporteTutoriasAcademicas(ActionEvent event) {
    }

    @FXML
    private void clicButtonEditarProblematicaAcademica(ActionEvent event) {
    }

    @FXML
    private void clicButtonConsultarProblematicaAcademica(ActionEvent event) {
        if (tutorAcademico.getIdRol() == Constantes.ID_ROL_COORDINADOR_TUTORIAS_ACADEMICAS || tutorAcademico.getIdRol() == Constantes.ID_ROL_JEFE_CARRERA) {
            // TODO
        } else {
             Utilidades.mostrarAlerta("AVISO",
                        "No tiene permiso de acceso a esta funcionalidad. \n\nPor favor, inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicButtonConsultarReporteTutorAcademico(ActionEvent event) {
        if (tutorAcademico.getIdRol() == Constantes.ID_ROL_COORDINADOR_TUTORIAS_ACADEMICAS || tutorAcademico.getIdRol() == Constantes.ID_ROL_JEFE_CARRERA) {
            // TODO
        } else {
             Utilidades.mostrarAlerta("AVISO",
                        "No tiene permiso de acceso a esta funcionalidad. \n\nPor favor, inténtelo nuevamente.\n",
                        Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicButtonCerrarSesion(ActionEvent event) {
        Stage escenario = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Scene pantallaIniciarSesion = new Scene(FXMLLoader.load(getClass().getResource("FXMLIniciarSesion.fxml")));
            escenario.setScene(pantallaIniciarSesion);
            escenario.setTitle("Iniciar sesión");
            escenario.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Iniciar sesión'...");
        }
    }

}