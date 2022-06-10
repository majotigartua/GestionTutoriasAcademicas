/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: Controlador de la pantalla de menú principal.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.modelo.dao.TutorAcademicoDAO;
import gestiontutoriasacademicas.modelo.dao.TutoriaAcademicaDAO;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import gestiontutoriasacademicas.modelo.pojo.TutoriaAcademica;
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
        TutoriaAcademica tutoriaAcademica = TutoriaAcademicaDAO.obtenerTutoriaAcademicaActual();
        if (tutoriaAcademica.getCodigoRespuesta() == Constantes.CODIGO_ERROR_FECHA_ACTUAL) {
            Utilidades.mostrarAlerta("AVISO",
                    "Está fuera del periodo definido para la entrega del Reporte de Tutorías Académicas. "
                    + "\n\nPor favor, comuníquese con el coordinador de tutorías académicas para la modificación"
                    + "de la fecha de cierre para la entrega del reporte.\n",
                    Alert.AlertType.INFORMATION);
        } else {
            int codigoRespuesta = TutorAcademicoDAO.llenarReporteTutoriasAcademicas(tutoriaAcademica.getIdTutoriaAcademica(), tutorAcademico.getNombreUsuario());
            if (codigoRespuesta == Constantes.CODIGO_REPORTE_TUTORIAS_ACADEMICAS_REGISTRADO) {
                Utilidades.mostrarAlerta("AVISO",
                        "Ya registró un Reporte de Tutorías Académicas para la tutoría académica actual."
                        + "\n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.INFORMATION);
            } else {
                irLlenarReporteTutoriasAcademicas(tutoriaAcademica, event);
            }
        }
    }

    @FXML
    private void clicButtonEditarProblematicaAcademica(ActionEvent event) {
        irProblematicasAcademicas(event, Constantes.CODIGO_CONFIGURAR_ESCENA_MODIFICAR);
    }

    @FXML
    private void clicButtonConsultarProblematicaAcademica(ActionEvent event) {
        if (tutorAcademico.getIdRol() == Constantes.ID_ROL_COORDINADOR_TUTORIAS_ACADEMICAS
                || tutorAcademico.getIdRol() == Constantes.ID_ROL_JEFE_CARRERA) {
            irProblematicasAcademicas(event, Constantes.CODIGO_CONFIGURAR_ESCENA_CONSULTAR);
        } else {
            Utilidades.mostrarAlerta("AVISO",
                    "Actualmente no tiene permiso de acceso a esta acción. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void clicButtonConsultarReporteTutorAcademico(ActionEvent event) {
        if (tutorAcademico.getIdRol() == Constantes.ID_ROL_COORDINADOR_TUTORIAS_ACADEMICAS
                || tutorAcademico.getIdRol() == Constantes.ID_ROL_JEFE_CARRERA) {
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

    private void irLlenarReporteTutoriasAcademicas(TutoriaAcademica tutoriaAcademica, ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLLlenarReporteTutoriasAcademicas.fxml"));
        try {
            Parent root = loader.load();
            FXMLLlenarReporteTutoriasAcademicasController llenarReporteTutoriasAcademicasController = loader.getController();
            llenarReporteTutoriasAcademicasController.configurarEscena(tutorAcademico, tutoriaAcademica);
            Stage escenarioPrincipal = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene pantallaLlenarReporteTutoriasAcademicas = new Scene(root);
            escenarioPrincipal.setScene(pantallaLlenarReporteTutoriasAcademicas);
            escenarioPrincipal.setTitle("Llenar Reporte de Tutorías Académicas");
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Llenar Reporte de Tutorías Académicas'...");
        }
    }

    private void irProblematicasAcademicas(ActionEvent event, int tipoEscenaPorConfigurar) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProblematicasAcademicas.fxml"));
        try {
            Parent root = loader.load();
            FXMLProblematicasAcademicasController problematicasAcademicasController = loader.getController();
            problematicasAcademicasController.configurarEscena(tipoEscenaPorConfigurar, tutorAcademico);
            Stage escenarioPrincipal = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene pantallaProblematicasAcademicas = new Scene(root);
            escenarioPrincipal.setScene(pantallaProblematicasAcademicas);
            escenarioPrincipal.setTitle("");
            escenarioPrincipal.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Problemáticas académicas'...");
        }
    }

}