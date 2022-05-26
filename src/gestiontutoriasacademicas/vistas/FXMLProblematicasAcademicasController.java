/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 26/05/2022.
 * Descripción: Controlador de la pantalla de problemáticas académicas.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.interfaces.InterfaceProblematicaAcademica;
import gestiontutoriasacademicas.modelo.dao.ProblematicaAcademicaDAO;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLProblematicasAcademicasController implements Initializable {

    @FXML
    private Label labelInstruccion;
    @FXML
    private TableView<ProblematicaAcademica> tableViewProblematicasAcademicas;
    @FXML
    private TableColumn columnTitulo;
    @FXML
    private Button buttonAccion;

    private static boolean esConsulta;
    private static TutorAcademico tutorAcademico;
    private ObservableList<ProblematicaAcademica> problematicasAcademicas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonAccion.disableProperty().bind(Bindings.isEmpty(tableViewProblematicasAcademicas.getSelectionModel().getSelectedItems()));
        configurarColumnsTableView();
    }

    public void configurarEscena(boolean esConsulta, TutorAcademico tutorAcademico) {
        this.esConsulta = esConsulta;
        this.tutorAcademico = tutorAcademico;
        if (esConsulta) {
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
        if (esConsulta) {

        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProblematicaAcademica.fxml"));
            try {
                Parent root = loader.load();
                FXMLProblematicaAcademicaController problematicaAcademicaController = loader.getController();
                problematicaAcademicaController.configurarEscena(false, tutorAcademico, problematicaAcademicaSeleccionada);
                Stage escenarioPrincipal = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene pantallaProblematicaAcademica = new Scene(root);
                escenarioPrincipal.setScene(pantallaProblematicaAcademica);
                escenarioPrincipal.setTitle("Modificar problemática académica");
                escenarioPrincipal.show();
            } catch (IOException ex) {
                System.err.println("Error al cargar la pantalla de 'Problemáticas académicas'...");
            }
        }
    }

    private void configurarColumnsTableView() {
        problematicasAcademicas = FXCollections.observableArrayList();
        columnTitulo.setCellValueFactory(new PropertyValueFactory("titulo"));
    }

    private void cargarProblematicasAcademicas() {
        ArrayList<ProblematicaAcademica> consultaProblematicasAcademicas;
        if (esConsulta) {
            consultaProblematicasAcademicas = ProblematicaAcademicaDAO.obtenerProblematicasAcademicas();
        } else {
            consultaProblematicasAcademicas = ProblematicaAcademicaDAO.obtenerProblematicaAcademicasPorTutorAcademico(tutorAcademico.getNombreUsuario());
        }
        if (consultaProblematicasAcademicas != null) {
            problematicasAcademicas.addAll(consultaProblematicasAcademicas);
            tableViewProblematicasAcademicas.setItems(problematicasAcademicas);
        } else {
            irMenuPrincipal();
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
            System.err.println("Error al cargar la pantalla de menú principal...");
        }
    }
    
}