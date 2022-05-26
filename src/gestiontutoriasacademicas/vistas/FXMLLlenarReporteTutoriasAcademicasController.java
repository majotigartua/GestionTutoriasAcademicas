package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    private static TutorAcademico tutorAcademico;
    private ArrayList<ProblematicaAcademica> problematicasAcademicas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configurarEscena(TutorAcademico tutorAcademico) {
        this.tutorAcademico = tutorAcademico;
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        irMenuPrincipal();
    }

    @FXML
    private void clicButtonAceptar(ActionEvent event) {
    }

    @FXML
    private void clicButtonRegistrarProblematicaAcademica(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProblematicaAcademica.fxml"));
        try {
            Parent root = loader.load();
            FXMLProblematicaAcademicaController problematicaAcademicaController = loader.getController();
            Stage escenarioProblematicaAcademica = new Stage();
            Scene pantallaProblematicaAcademica = new Scene(root);
            escenarioProblematicaAcademica.setScene(pantallaProblematicaAcademica);
            escenarioProblematicaAcademica.initModality(Modality.APPLICATION_MODAL);
            escenarioProblematicaAcademica.setTitle("Llenar Reporte de Tutorías Académicas");
            escenarioProblematicaAcademica.showAndWait();

        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Problemáticas académicas'...");
        }
    }

    @FXML
    private void clicButtonRegistrarComentarioGeneral(ActionEvent event) {
    }

    private void irMenuPrincipal(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMenuPrincipal.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene pantallaMenuPrincipal = new Scene(root);
            Stage escenario = (Stage) textFieldNumSesion.getScene().getWindow();
            escenario.setScene(pantallaMenuPrincipal);
            escenario.setTitle("Menú principal");
            escenario.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de menú principal...");
        }
    }

}