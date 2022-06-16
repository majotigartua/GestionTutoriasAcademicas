/**
 * Nombre del programador: Sebastián Bello Trejo.
 * Fecha de creación: 27/05/2022.
 * Fecha más reciente de modificación: 10/06/2022.
 * Descripción: Controlador de la pantalla de 'Reportes de Tutorías Académicas'.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.modelo.dao.ReporteTutoriasAcademicasDAO;
import gestiontutoriasacademicas.modelo.dao.TutorAcademicoDAO;
import gestiontutoriasacademicas.modelo.pojo.ReporteTutoriasAcademicas;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import gestiontutoriasacademicas.util.Utilidades;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Sebastián
 */
public class FXMLReportesTutoriasAcademicasController implements Initializable {

    @FXML
    private ComboBox<TutorAcademico> comboBoxTutorAcademico;
    @FXML
    private TableView<ReporteTutoriasAcademicas> tableViewReportesTutoriasAcademicas;
    @FXML
    private TableColumn tableColumnTutoriaAcademica;
    @FXML
    private TableColumn tableColumnPeriodoEscolar;
    @FXML
    private TableColumn tableColumnTutorAcademico;
    @FXML
    private Button buttonConsultar;

    private ObservableList<TutorAcademico> listaTutoresAcademicos;
    private ObservableList<ReporteTutoriasAcademicas> listaReportesTutoriasAcademicas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonConsultar.disableProperty().bind(Bindings.isEmpty(tableViewReportesTutoriasAcademicas.getSelectionModel().getSelectedItems()));
        cargarComboBoxTutoresAcademicos();
        configurarColumnasTabla();
        seleccionarTutorAcademico();
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        irMenuPrincipal();
    }

    @FXML
    private void clicButtonConsultar(ActionEvent event) {
        int filaSeleccionada = tableViewReportesTutoriasAcademicas.getSelectionModel().getSelectedIndex();
        if (filaSeleccionada >= 0) {
            ReporteTutoriasAcademicas reporteTutoriasAcademicas = listaReportesTutoriasAcademicas.get(filaSeleccionada);
            irConsultarReporteTutoriasAcademicas(reporteTutoriasAcademicas);
        }
    }

    private void irMenuPrincipal() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMenuPrincipal.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene pantallaMenuPrincipal = new Scene(root);
            Stage escenario = (Stage) tableViewReportesTutoriasAcademicas.getScene().getWindow();
            escenario.setScene(pantallaMenuPrincipal);
            escenario.setTitle("Menú principal");
            escenario.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de menú principal...");
        }
    }

    private void irConsultarReporteTutoriasAcademicas(ReporteTutoriasAcademicas reporteTutoriasAcademicas) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLConsultarReporteTutoriasAcademicas.fxml"));
        Parent root;
        try {
            root = loader.load();
            FXMLConsultarReporteTutoriasAcademicasController consultarReporteTutoriasAcademicas = loader.getController();
            consultarReporteTutoriasAcademicas.configurarEscena(reporteTutoriasAcademicas);
            Scene pantallaConsultarReporteTutoriasAcademicas = new Scene(root);
            Stage escenario = (Stage) tableViewReportesTutoriasAcademicas.getScene().getWindow();
            escenario.setScene(pantallaConsultarReporteTutoriasAcademicas);
            escenario.setTitle("Consultar Reporte de Tutorías Académicas");
            escenario.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de menú principal...");
        }
    }

    public void cargarComboBoxTutoresAcademicos() {
        ArrayList<TutorAcademico> resultadoConsulta = TutorAcademicoDAO.obtenerTutoresAcademicos();
        if (resultadoConsulta != null) {
            listaTutoresAcademicos = FXCollections.observableArrayList();
            listaTutoresAcademicos.addAll(resultadoConsulta);
            comboBoxTutorAcademico.setItems(listaTutoresAcademicos);
        } else {
            Utilidades.mostrarAlerta("ERROR",
                    "No hay conexión con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

    public void seleccionarTutorAcademico() {
        comboBoxTutorAcademico.valueProperty().addListener(new ChangeListener<TutorAcademico>() {
            @Override
            public void changed(ObservableValue<? extends TutorAcademico> observable, TutorAcademico oldValue, TutorAcademico newValue) {
                try {
                    tableViewReportesTutoriasAcademicas.getItems().clear();
                    cargarReportesTutoriasAcademicasPorTutorAcademico(newValue);
                } catch (Exception e) {
                    System.out.println(e.getClass());
                }
            }
        });
    }

    private void configurarColumnasTabla() {
        tableColumnTutoriaAcademica.setCellValueFactory(new PropertyValueFactory("fechasTutoriaAcademica"));
        tableColumnPeriodoEscolar.setCellValueFactory(new PropertyValueFactory("fechasPeriodoEscolar"));
        tableColumnTutorAcademico.setCellValueFactory(new PropertyValueFactory("tutorAcademico"));
        listaReportesTutoriasAcademicas = FXCollections.observableArrayList();
    }

    public void cargarReportesTutoriasAcademicasPorTutorAcademico(TutorAcademico tutorAcademico) {
        ArrayList<ReporteTutoriasAcademicas> resultadoConsulta = ReporteTutoriasAcademicasDAO.obtenerReportesTutoriasAcademicas(tutorAcademico);
        if (resultadoConsulta != null) {
            listaReportesTutoriasAcademicas.clear();
            listaReportesTutoriasAcademicas.addAll(resultadoConsulta);
            tableViewReportesTutoriasAcademicas.setItems(listaReportesTutoriasAcademicas);
        } else {
            Utilidades.mostrarAlerta("ERROR",
                    "No hay conexión con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
    }

}