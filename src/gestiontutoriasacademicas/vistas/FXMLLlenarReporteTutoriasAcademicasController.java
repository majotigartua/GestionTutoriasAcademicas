/**
 * Nombre del programador: María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: Controlador de la pantalla de Llenar Reporte de Tutorías Académicas.
 */
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

    private TutorAcademico tutorAcademico;
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
            problematicaAcademicaController.configurarEscena(true, tutorAcademico, null);
            Stage escenarioProblematicaAcademica = new Stage();
            Scene pantallaProblematicaAcademica = new Scene(root);
            escenarioProblematicaAcademica.setScene(pantallaProblematicaAcademica);
            escenarioProblematicaAcademica.initModality(Modality.APPLICATION_MODAL);
            escenarioProblematicaAcademica.setTitle("Registrar problemática académica");
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