package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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
    }

    @FXML
    private void clicButtonConsultarReporteTutorAcademico(ActionEvent event) {
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
