/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 24/05/2022.
 * Descripción: Controlador de la pantalla de nicio de sesión.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void configurarEscena(boolean esConsulta, TutorAcademico tutorAcademico) {
        this.esConsulta = esConsulta;
        this.tutorAcademico = tutorAcademico;
        if (esConsulta) {
            labelInstruccion.setText("Seleccione una problemática académica, y dé clic en el botón de \"Consultar\".");
            buttonAccion.setText("Consultar");
        }
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
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

    @FXML
    private void clicButtonAccion(ActionEvent event) {
        if (esConsulta) {

        } else {

        }
    }

    private void configurarColumnsTableView() {
        if (esConsulta) {

        } else {

        }
    }

}
