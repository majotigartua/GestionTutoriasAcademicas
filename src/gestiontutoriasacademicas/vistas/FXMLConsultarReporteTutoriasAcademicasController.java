/**
 * Nombre del programador: Sebastián Bello Trejo.
 * Fecha de creación: 27/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: Controlador de la pantalla de consultar reporte de tutorías académicas.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.modelo.dao.EstudianteDAO;
import gestiontutoriasacademicas.modelo.dao.ProblematicaAcademicaDAO;
import gestiontutoriasacademicas.modelo.dao.ReporteTutoriasAcademicasDAO;
import gestiontutoriasacademicas.modelo.dao.TutorAcademicoDAO;
import gestiontutoriasacademicas.modelo.pojo.Estudiante;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.ReporteTutoriasAcademicas;
import gestiontutoriasacademicas.util.Utilidades;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLConsultarReporteTutoriasAcademicasController implements Initializable {

    @FXML
    private TextField textFieldFecha;
    @FXML
    private TextField textFieldPeriodoEscolar;
    @FXML
    private TextField textFieldEstudiantesEnRiesgo;
    @FXML
    private TextField textFieldEstudiantesAsistentes;
    @FXML
    private TextField textFieldNumSesion;
    @FXML
    private TableView<Estudiante> tableViewEstudiantes;
    @FXML
    private TableColumn tableColumnMatricula;
    @FXML
    private TableColumn tableColumnNombre;
    @FXML
    private TableColumn tableColumnAsistio;
    @FXML
    private TableColumn tableColumnEnRiesgo;
    @FXML
    private TextArea textAreaComentarioGeneral;
    @FXML
    private TableView<ProblematicaAcademica> tableViewProblematicasAcademicas;
    @FXML
    private TableColumn tableColumnExperienciaEducativa;
    @FXML
    private TableColumn tableColumnProfesor;
    @FXML
    private TableColumn tableColumnTitulo;
    @FXML
    private TableColumn tableColumnNumeroEstudiantes;

    private ReporteTutoriasAcademicas reporteTutoriasAcademicas;
    private ObservableList<Estudiante> listaEstudiantes;
    private ObservableList<ProblematicaAcademica> listaProblematicaAcademicas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configurarEscena(ReporteTutoriasAcademicas reporteTutoriasAcademicas) {
        this.reporteTutoriasAcademicas = reporteTutoriasAcademicas;
        configurarCampos();
        configurarColumnasTableViewEstudiantes();
        configurarColumnasTableViewProblematicasAcademicas();
        cargarEstudiantes();
        cargarProblematicasAcademicas();
        textFieldPeriodoEscolar.setDisable(true);
        textFieldFecha.setDisable(true);
        textFieldNumSesion.setDisable(true);
        textFieldEstudiantesEnRiesgo.setDisable(true);
        textFieldEstudiantesAsistentes.setDisable(true);
        textAreaComentarioGeneral.setEditable(false);
    }

    @FXML
    private void clicButtonDescargar(ActionEvent event) {
        boolean esImprimir = false;
        String apellidoPaternoNombreTutorAcademico = obtenerNombreTutorAcademico(reporteTutoriasAcademicas.getIdReporteTutoriasAcademicas());
        if (apellidoPaternoNombreTutorAcademico != null) {
            String nombreReporteTutoriasAcademicas = "\\" + apellidoPaternoNombreTutorAcademico + "_" + "ReporteTutoríaAcadémica.pdf";
            nombreReporteTutoriasAcademicas = nombreReporteTutoriasAcademicas.replace(" ", "");
            DirectoryChooser seleccionarDirectorio = new DirectoryChooser();
            File directorio = seleccionarDirectorio.showDialog(textFieldFecha.getScene().getWindow());
            String path = directorio.getPath() + nombreReporteTutoriasAcademicas;
            if (directorio != null) {
                exportar(esImprimir, reporteTutoriasAcademicas.getIdReporteTutoriasAcademicas(), nombreReporteTutoriasAcademicas, path);
            }
        } else {
            Utilidades.mostrarAlerta("AVISO",
                    "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clicButtonImprimir(ActionEvent event) {
        boolean esImprimir = true;
        String apellidoPaternoNombreTutorAcademico = obtenerNombreTutorAcademico(reporteTutoriasAcademicas.getIdReporteTutoriasAcademicas());
        if (apellidoPaternoNombreTutorAcademico != null) {
            String nombreReporteTutoriasAcademicas = apellidoPaternoNombreTutorAcademico + "_" + "ReporteTutoríaAcadémica.pdf";
            nombreReporteTutoriasAcademicas = nombreReporteTutoriasAcademicas.replace(" ", "");
            exportar(esImprimir, reporteTutoriasAcademicas.getIdReporteTutoriasAcademicas(), nombreReporteTutoriasAcademicas, null);
        } else {
            Utilidades.mostrarAlerta("AVISO",
                    "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        irReportesTutoriasAcademicas();
    }

    private void irReportesTutoriasAcademicas() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLReportesTutoriasAcademicas.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene pantallaReportesTutoriasAcademicas = new Scene(root);
            Stage escenario = (Stage) textFieldNumSesion.getScene().getWindow();
            escenario.setScene(pantallaReportesTutoriasAcademicas);
            escenario.setTitle("Reportes de Tutorías Académicas");
            escenario.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de menú principal...");
        }
    }

    private void exportar(boolean esImprimir, int idReporteTutoriasAcademicas, String nombreDelReporte, String pathnombreDelReporte) {
        if (esImprimir) {
            JasperPrint jprint = ReporteTutoriasAcademicasDAO.crearArchivoReporteTutoriasAcademicas(idReporteTutoriasAcademicas, nombreDelReporte);
            if (jprint != null) {
                JasperViewer reporteVisualizado = new JasperViewer(jprint, false);
                reporteVisualizado.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                reporteVisualizado.setVisible(true);
            } else {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo crear correctamente el Reporte de Tutorías Académicas. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        } else {
            JasperPrint jprint = ReporteTutoriasAcademicasDAO.crearArchivoReporteTutoriasAcademicas(idReporteTutoriasAcademicas, pathnombreDelReporte);
            if (jprint != null) {
                Utilidades.mostrarAlerta("AVISO",
                        "La descarga se ha completado correctamente.\n",
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo crear correctamente el Reporte de Tutorías Académicas. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        }
    }

    public void configurarCampos() {
        ReporteTutoriasAcademicasDAO.obtenerConsultaReporteTutoriasAcademicas(reporteTutoriasAcademicas);
        textFieldPeriodoEscolar.setText(reporteTutoriasAcademicas.getFechasPeriodoEscolar());
        textFieldFecha.setText(reporteTutoriasAcademicas.getFechasTutoriaAcademica());
        textAreaComentarioGeneral.setText(reporteTutoriasAcademicas.getComentarioGeneral());
        textFieldNumSesion.setText(String.valueOf(reporteTutoriasAcademicas.getNumSesion()));
        textFieldEstudiantesEnRiesgo.setText(String.valueOf(reporteTutoriasAcademicas.getNumEstudiantesEnRiesgo()));
        textFieldEstudiantesAsistentes.setText(String.valueOf(reporteTutoriasAcademicas.getNumEstudiantesAsistentes()));
    }

    public void cargarEstudiantes() {
        ArrayList<Estudiante> consultaEstudiantesPorTutorAcademico = EstudianteDAO.obtenerEstudiantesPorReporteTutoriasAcademicas(this.reporteTutoriasAcademicas);
        listaEstudiantes.addAll(consultaEstudiantesPorTutorAcademico);
        tableViewEstudiantes.setItems(listaEstudiantes);
    }

    private void configurarColumnasTableViewEstudiantes() {
        listaEstudiantes = FXCollections.observableArrayList();
        tableColumnMatricula.setCellValueFactory(new PropertyValueFactory("matricula"));
        tableColumnNombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Estudiante, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<Estudiante, String> estudiante) {
                return new SimpleStringProperty(estudiante.getValue().getNombre() + " "
                        + estudiante.getValue().getApellidoPaterno() + " " + estudiante.getValue().getApellidoMaterno());
            }
        });
        tableColumnAsistio.setCellValueFactory(new PropertyValueFactory("checkBoxEsAsistente"));
        tableColumnEnRiesgo.setCellValueFactory(new PropertyValueFactory("checkBoxEnRiesgo"));
    }

    public void cargarProblematicasAcademicas() {
        ArrayList<ProblematicaAcademica> consultaProblematicasAcademicasPorReporteTutoriasAcademicas = ProblematicaAcademicaDAO.obtenerProblematicaAcademicasPorReporteTutoriasAcademicas(this.reporteTutoriasAcademicas);
        listaProblematicaAcademicas.addAll(consultaProblematicasAcademicasPorReporteTutoriasAcademicas);
        tableViewProblematicasAcademicas.setItems(listaProblematicaAcademicas);
    }

    private void configurarColumnasTableViewProblematicasAcademicas() {
        listaProblematicaAcademicas = FXCollections.observableArrayList();
        tableColumnExperienciaEducativa.setCellValueFactory(new PropertyValueFactory("nombreExperienciaEducativa"));
        tableColumnProfesor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProblematicaAcademica, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(
                    TableColumn.CellDataFeatures<ProblematicaAcademica, String> problematicaAcademica) {
                return new SimpleStringProperty(problematicaAcademica.getValue().getNombreProfesor() + " "
                        + problematicaAcademica.getValue().getApellidoPaternoProfesor() + " " + problematicaAcademica.getValue().getApellidoMaternoProfesor());
            }
        });
        tableColumnTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
        tableColumnNumeroEstudiantes.setCellValueFactory(new PropertyValueFactory("numEstudiantes"));
    }

    private String obtenerNombreTutorAcademico(int idReporteTutoriasAcademicas) {
        String apellidoPaternoNombreTutorAcademico = TutorAcademicoDAO.obtenerApellidoPaternoNombreTutorAcademico(idReporteTutoriasAcademicas);
        return apellidoPaternoNombreTutorAcademico;
    }

}
