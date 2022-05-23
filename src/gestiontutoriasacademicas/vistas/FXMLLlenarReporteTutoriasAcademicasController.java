package gestiontutoriasacademicas.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class FXMLLlenarReporteTutoriasAcademicasController implements Initializable {

    @FXML
    private TextField textFieldPeriodoEscolar;
    @FXML
    private TextField textFieldFecha;
    @FXML
    private TextField textFieldNumSesion;
    @FXML
    private TextField textFieldNumEstudiantesAsistentes;
    @FXML
    private TextField textFieldNumEstudiantesEnRiesgo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
    }

    @FXML
    private void clicButtonAceptar(ActionEvent event) {
    }

    @FXML
    private void clicButtonRegistrarProblematicaAcademica(ActionEvent event) {
    }

    @FXML
    private void clicButtonRegistrarComentarioGeneral(ActionEvent event) {
    }
    
}