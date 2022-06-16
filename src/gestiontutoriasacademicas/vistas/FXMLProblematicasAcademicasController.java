/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 09/06/2022.
 * Descripción: Controlador de la pantalla de 'Problemáticas académicas'.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.interfaces.NotificacionProblematicaAcademica;
import gestiontutoriasacademicas.modelo.dao.ProblematicaAcademicaDAO;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import gestiontutoriasacademicas.util.Constantes;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLProblematicasAcademicasController implements Initializable, NotificacionProblematicaAcademica {

    @FXML
    private Label labelInstruccion;
    @FXML
    private TableView<ProblematicaAcademica> tableViewProblematicasAcademicas;
    @FXML
    private TableColumn columnTitulo;
    @FXML
    private Button buttonAccion;

    private int tipoEscenaPorConfigurar;
    private static TutorAcademico tutorAcademico;
    private ObservableList<ProblematicaAcademica> problematicasAcademicas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonAccion.disableProperty().bind(Bindings.isEmpty(tableViewProblematicasAcademicas.getSelectionModel().getSelectedItems()));
        configurarColumnsTableView();
    }

    public void configurarEscena(int tipoEscenaPorConfigurar, TutorAcademico tutorAcademico) {
        this.tipoEscenaPorConfigurar = tipoEscenaPorConfigurar;
        this.tutorAcademico = tutorAcademico;
        if (tipoEscenaPorConfigurar == Constantes.CODIGO_CONFIGURAR_ESCENA_CONSULTAR) {
            labelInstruccion.setText("Seleccione una problemática académica, y dé clic en el botón de \"Consultar\".");
            buttonAccion.setText("Consultar");

        }
        cargarProblematicasAcademicas();
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        irMenuPrincipal();
    }

    @FXML
    private void clicButtonAccion(ActionEvent event) {
        ProblematicaAcademica problematicaAcademicaSeleccionada = tableViewProblematicasAcademicas.getSelectionModel().getSelectedItem();
        if (tipoEscenaPorConfigurar == Constantes.CODIGO_CONFIGURAR_ESCENA_CONSULTAR) {
            irConsultarProblematicaAcademica(problematicaAcademicaSeleccionada);
        } else {
            irModificarProblematicaAcademica(problematicaAcademicaSeleccionada);
        }
    }

    private void configurarColumnsTableView() {
        problematicasAcademicas = FXCollections.observableArrayList();
        columnTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
    }

    private void cargarProblematicasAcademicas() {
        ArrayList<ProblematicaAcademica> consultaProblematicasAcademicas;
        if (tipoEscenaPorConfigurar == Constantes.CODIGO_CONFIGURAR_ESCENA_CONSULTAR) {
            consultaProblematicasAcademicas = ProblematicaAcademicaDAO.obtenerProblematicasAcademicas();
        } else {
            consultaProblematicasAcademicas = ProblematicaAcademicaDAO.obtenerProblematicaAcademicasPorTutorAcademico(tutorAcademico.getNombreUsuario());
        }
        problematicasAcademicas.clear();
        problematicasAcademicas.addAll(consultaProblematicasAcademicas);
        tableViewProblematicasAcademicas.setItems(problematicasAcademicas);
    }

    private void irModificarProblematicaAcademica(ProblematicaAcademica problematicaAcademica) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProblematicaAcademica.fxml"));
        try {
            Parent root = loader.load();
            FXMLProblematicaAcademicaController problematicaAcademicaController = loader.getController();
            problematicaAcademicaController.configurarEscena(Constantes.CODIGO_CONFIGURAR_ESCENA_MODIFICAR, tutorAcademico, this, problematicaAcademica);
            Stage escenarioProblematicaAcademica = new Stage();
            Scene pantallaProblematicaAcademica = new Scene(root);
            escenarioProblematicaAcademica.setScene(pantallaProblematicaAcademica);
            escenarioProblematicaAcademica.initModality(Modality.APPLICATION_MODAL);
            escenarioProblematicaAcademica.setTitle("Modificar problemática académica");
            escenarioProblematicaAcademica.showAndWait();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Modificar problemática académica'...");
        }
    }

    private void irConsultarProblematicaAcademica(ProblematicaAcademica problematicaAcademica) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProblematicaAcademica.fxml"));
        try {
            Parent root = loader.load();
            FXMLProblematicaAcademicaController problematicaAcademicaController = loader.getController();
            problematicaAcademicaController.configurarEscena(Constantes.CODIGO_CONFIGURAR_ESCENA_CONSULTAR, tutorAcademico, this, problematicaAcademica);
            Stage escenarioProblematicaAcademica = new Stage();
            Scene pantallaProblematicaAcademica = new Scene(root);
            escenarioProblematicaAcademica.setScene(pantallaProblematicaAcademica);
            escenarioProblematicaAcademica.initModality(Modality.APPLICATION_MODAL);
            escenarioProblematicaAcademica.setTitle("Consultar problemática académica");
            escenarioProblematicaAcademica.showAndWait();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Consultar problemática académica'...");
        }
    }

    private void irMenuPrincipal() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMenuPrincipal.fxml"));
        Parent root;
        try {
            root = loader.load();
            Scene pantallaMenuPrincipal = new Scene(root);
            Stage escenario = (Stage) labelInstruccion.getScene().getWindow();
            escenario.setScene(pantallaMenuPrincipal);
            escenario.setTitle("Menú principal");
            escenario.show();
        } catch (IOException ex) {
            System.err.println("Error al cargar la pantalla de 'Menú principal'...");
        }
    }

    @Override
    public void notificarProblematicaAcademica(ProblematicaAcademica problematicaAcademica) {
        cargarProblematicasAcademicas();
    }

}