/**
 * Nombre del programador: María José Torres Igartua y Ulises Ramos Mexicano
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 07/05/2022.
 * Descripción: Controlador de la pantalla de 'Llenar Reporte de Tutorías Académicas'.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.interfaces.NotificacionComentarioGeneral;
import gestiontutoriasacademicas.interfaces.NotificacionProblematicaAcademica;
import gestiontutoriasacademicas.modelo.dao.ComentarioGeneralDAO;
import gestiontutoriasacademicas.modelo.dao.EstudianteDAO;
import gestiontutoriasacademicas.modelo.dao.ProblematicaAcademicaDAO;
import gestiontutoriasacademicas.modelo.dao.ReporteTutoriasAcademicasDAO;
import gestiontutoriasacademicas.modelo.pojo.ComentarioGeneral;
import gestiontutoriasacademicas.modelo.pojo.Estudiante;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.ReporteTutoriasAcademicas;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import gestiontutoriasacademicas.modelo.pojo.TutoriaAcademica;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FXMLLlenarReporteTutoriasAcademicasController implements Initializable, NotificacionProblematicaAcademica, NotificacionComentarioGeneral {

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
    @FXML
    private TableView<Estudiante> tableViewEstudiantes;
    @FXML
    private TableColumn columnMatricula;
    @FXML
    private TableColumn columnNombreCompleto;
    @FXML
    private TableColumn columnEsAsistente;
    @FXML
    private TableColumn columnEnRiesgo;
    @FXML
    private Button buttonRegistrarComentarioGeneral;

    private TutorAcademico tutorAcademico;
    private TutoriaAcademica tutoriaAcademica;
    private ArrayList<ProblematicaAcademica> problematicasAcademicas;
    private ComentarioGeneral comentarioGeneral;
    private ObservableList<Estudiante> estudiantes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnasTableView();
    }

    public void configurarEscena(TutorAcademico tutorAcademico, TutoriaAcademica tutoriaAcademica) {
        this.tutorAcademico = tutorAcademico;
        this.tutoriaAcademica = tutoriaAcademica;
        textFieldPeriodoEscolar.setText(tutoriaAcademica.getPeriodoEscolar());
        textFieldFecha.setText(tutoriaAcademica.toString());
        textFieldNumSesion.setText(String.valueOf(tutoriaAcademica.getNumSesion()));
        problematicasAcademicas = new ArrayList<>();
        cargarEstudiantesPorTutorAcademico();
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        irMenuPrincipal();
    }

    @FXML
    private void clicButtonAceptar(ActionEvent event) {
        int numEstudiantesAsistentes = 0, numEstudiantesEnRiesgo = 0;
        for (int i = 0; i < estudiantes.size(); i++) {
            tableViewEstudiantes.getSelectionModel().select(i);
            Estudiante estudiante = tableViewEstudiantes.getSelectionModel().getSelectedItem();
            if (estudiante.getCheckBoxEsAsistente().isSelected()) {
                numEstudiantesAsistentes++;
                estudiante.setEsAsistente(true);
            }
            if (estudiante.getCheckBoxEnRiesgo().isSelected()) {
                numEstudiantesEnRiesgo++;
                estudiante.setEnRiesgo(true);
            }
        }
        textFieldNumEstudiantesAsistentes.setText(String.valueOf(numEstudiantesAsistentes));
        textFieldNumEstudiantesEnRiesgo.setText(String.valueOf(numEstudiantesEnRiesgo));
        Optional<ButtonType> respuestaConfirmacion = Utilidades.mostrarConfirmacion("AVISO",
                "Una vez enviado el Reporte de Tutorías Académicas no se podrá modificar. \n\n¿Desea continuar?", Alert.AlertType.CONFIRMATION);
        if (respuestaConfirmacion.get() == ButtonType.OK) {
            ReporteTutoriasAcademicas reporteTutoriasAcademicas
                    = new ReporteTutoriasAcademicas(numEstudiantesAsistentes, numEstudiantesEnRiesgo,
                            tutoriaAcademica.getIdTutoriaAcademica(), tutorAcademico.getNombreUsuario());
            registrarReporteTutoriasAcademicas(reporteTutoriasAcademicas);
        }
    }

    @FXML
    private void clicButtonRegistrarProblematicaAcademica(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProblematicaAcademica.fxml"));
        try {
            Parent root = loader.load();
            FXMLProblematicaAcademicaController problematicaAcademicaController = loader.getController();
            problematicaAcademicaController.configurarEscena(Constantes.CODIGO_CONFIGURAR_ESCENA_REGISTRAR, tutorAcademico, this, null);
            Stage escenarioProblematicaAcademica = new Stage();
            Scene pantallaProblematicaAcademica = new Scene(root);
            escenarioProblematicaAcademica.setScene(pantallaProblematicaAcademica);
            escenarioProblematicaAcademica.initModality(Modality.APPLICATION_MODAL);
            escenarioProblematicaAcademica.setTitle("Registrar problemática académica");
            escenarioProblematicaAcademica.showAndWait();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Registrar problemática académica'...");
        }
    }

    @FXML
    private void clicButtonRegistrarComentarioGeneral(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLRegistrarComentarioGeneral.fxml"));
        try {
            Parent root = loader.load();
            FXMLRegistrarComentarioGeneralController registrarComentarioGeneralController = loader.getController();
            registrarComentarioGeneralController.configurarEscena(this);
            Stage escenarioRegistrarComentarioGeneral = new Stage();
            Scene pantallaRegistrarComentarioGeneral = new Scene(root);
            escenarioRegistrarComentarioGeneral.setScene(pantallaRegistrarComentarioGeneral);
            escenarioRegistrarComentarioGeneral.initModality(Modality.APPLICATION_MODAL);
            escenarioRegistrarComentarioGeneral.setTitle("Registrar comentario general");
            escenarioRegistrarComentarioGeneral.showAndWait();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Registrar comentario general'...");
        }
    }

    private void registrarReporteTutoriasAcademicas(ReporteTutoriasAcademicas reporteTutoriasAcademicas) {
        int codigoRespuesta = ReporteTutoriasAcademicasDAO.registrarReporteTutoriasAcademicas(reporteTutoriasAcademicas);
        if (codigoRespuesta == Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS) {
            Utilidades.mostrarAlerta("ERROR",
                    "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        } else {
            Utilidades.mostrarAlerta("AVISO",
                    "La información se registró correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            asignarProblematicasAcademicas();
            asignarComentarioGeneral();
            asignarEstudiantes();
            irMenuPrincipal();
        }
    }

    private void asignarProblematicasAcademicas() {
        for (int i = 0; i < problematicasAcademicas.size(); i++) {
            int codigoRespuesta = ProblematicaAcademicaDAO.registrarProblematicaAcademica(problematicasAcademicas.get(i),
                    tutoriaAcademica.getIdTutoriaAcademica(), tutorAcademico.getNombreUsuario());
            if (codigoRespuesta == Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS) {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        }
    }

    private void asignarComentarioGeneral() {
        if (comentarioGeneral == null) {
            comentarioGeneral = new ComentarioGeneral("No se registró ningún comentario general.");
        }
        int codigoRespuesta = ComentarioGeneralDAO.registrarComentarioGeneral(comentarioGeneral,
                tutoriaAcademica.getIdTutoriaAcademica(), tutorAcademico.getNombreUsuario());
        if (codigoRespuesta == Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS) {
            Utilidades.mostrarAlerta("ERROR",
                    "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    private void asignarEstudiantes() {
        for (int i = 0; i < estudiantes.size(); i++) {
            int codigoRespuesta = EstudianteDAO.asignarReporteTutoriasAcademicas(estudiantes.get(i),
                    tutoriaAcademica.getIdTutoriaAcademica(), tutorAcademico.getNombreUsuario());
            if (codigoRespuesta == Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS) {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        }
    }

    private void irMenuPrincipal() {
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

    private void configurarColumnasTableView() {
        estudiantes = FXCollections.observableArrayList();
        columnMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        columnNombreCompleto.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Estudiante, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Estudiante, String> estudiante) {
                return new SimpleStringProperty(estudiante.getValue().getNombre() + " "
                        + estudiante.getValue().getApellidoPaterno() + " " + estudiante.getValue().getApellidoMaterno());
            }
        });
        columnEsAsistente.setCellValueFactory(new PropertyValueFactory("checkBoxEsAsistente"));
        columnEnRiesgo.setCellValueFactory(new PropertyValueFactory("checkBoxEnRiesgo"));
    }

    private void cargarEstudiantesPorTutorAcademico() {
        ArrayList<Estudiante> consultaEstudiantesPorTutorAcademico = EstudianteDAO.obtenerEstudiantesPorTutorAcademico(tutorAcademico.getNombreUsuario());
        estudiantes.addAll(consultaEstudiantesPorTutorAcademico);
        tableViewEstudiantes.setItems(estudiantes);
    }

    @Override
    public void notificarProblematicaAcademica(ProblematicaAcademica problematicaAcademica) {
        problematicasAcademicas.add(problematicaAcademica);
    }

    @Override
    public void notificarComentarioGeneral(ComentarioGeneral comentarioGeneral) {
        buttonRegistrarComentarioGeneral.setDisable(true);
        this.comentarioGeneral = comentarioGeneral;
    }

}