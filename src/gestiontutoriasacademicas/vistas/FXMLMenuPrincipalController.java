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
import javafx.scene.Parent;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProblematicasAcademicas.fxml"));
            try {
                Parent root = loader.load();
                FXMLProblematicasAcademicasController problematicasAcademicas = loader.getController();
                problematicasAcademicas.configurarEscena(false, tutorAcademico);
                Stage escenarioPrincipal = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene pantallaProblematicasAcademicas = new Scene(root);
                escenarioPrincipal.setScene(pantallaProblematicasAcademicas);
                escenarioPrincipal.setTitle("");
                escenarioPrincipal.show();
            } catch (IOException ex) {
                System.err.println("Error al cargar la pantalla de 'Problemáticas académicas'...");
            }
    }

    @FXML
    private void clicButtonConsultarProblematicaAcademica(ActionEvent event) {
        if (tutorAcademico.getIdRol() == Constantes.ID_ROL_COORDINADOR_TUTORIAS_ACADEMICAS || tutorAcademico.getIdRol() == Constantes.ID_ROL_JEFE_CARRERA) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProblematicasAcademicas.fxml"));
            try {
                Parent root = loader.load();
                FXMLProblematicasAcademicasController problematicasAcademicas = loader.getController();
                problematicasAcademicas.configurarEscena(true, tutorAcademico);
                Stage escenarioPrincipal = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene pantallaProblematicasAcademicas = new Scene(root);
                escenarioPrincipal.setScene(pantallaProblematicasAcademicas);
                escenarioPrincipal.setTitle("");
                escenarioPrincipal.show();
            } catch (IOException ex) {
                System.err.println("Error al cargar la pantalla de 'Problemáticas académicas'...");
            }
        } else {
            Utilidades.mostrarAlerta("AVISO",
                    "Actualmente no tiene permiso de acceso a esta acción. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicButtonConsultarReporteTutorAcademico(ActionEvent event) {
        if (tutorAcademico.getIdRol() == Constantes.ID_ROL_COORDINADOR_TUTORIAS_ACADEMICAS || tutorAcademico.getIdRol() == Constantes.ID_ROL_JEFE_CARRERA) {
            // TODO
        } else {
            Utilidades.mostrarAlerta("AVISO",
                    "Actualmente no tiene permiso de acceso a esta acción. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicButtonCerrarSesion(ActionEvent event) {
        Stage escenario = (Stage) ((Node) event.getSource()).getScene().getWindow();
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