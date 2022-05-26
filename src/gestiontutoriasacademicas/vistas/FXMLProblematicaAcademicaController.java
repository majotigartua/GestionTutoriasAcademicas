/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 26/05/2022.
 * Fecha más reciente de modificación: 26/05/2022.
 * Descripción: Controlador de la pantalla de registrar/modificar problemática académica.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.modelo.dao.ExperienciaEducativaDAO;
import gestiontutoriasacademicas.modelo.dao.ProfesorDAO;
import gestiontutoriasacademicas.modelo.pojo.ExperienciaEducativa;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.Profesor;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLProblematicaAcademicaController implements Initializable {

    @FXML
    private TextField textFieldTitulo;
    @FXML
    private ComboBox<ExperienciaEducativa> comboBoxExperienciaEducativa;
    @FXML
    private ComboBox<Profesor> comboBoxProfesor;
    @FXML
    private TextField textFieldNumEstudiantes;
    @FXML
    private TextArea textAreaDescripcion;
    @FXML
    private Button buttonEliminar;

    private boolean esRegistro;
    private static TutorAcademico tutorAcademico;
    private ProblematicaAcademica problematicaAcademicaPorModificar;
    private ObservableList<ExperienciaEducativa> experienciasEducativas;
    private ObservableList<Profesor> profesores;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textAreaDescripcion.setWrapText(true);
        cargarExperienciasEducativasPorPeriodoEscolar();
        comboBoxExperienciaEducativa.valueProperty().addListener(new ChangeListener<ExperienciaEducativa>() {
            @Override
            public void changed(ObservableValue<? extends ExperienciaEducativa> observable, ExperienciaEducativa oldValue, ExperienciaEducativa newValue) {
                if (newValue != null) {
                    cargarProfesoresPorExperienciaEducativa(newValue.getIdExperienciaEducativa());
                }
            }
        });
    }

    public void configurarEscena(boolean esRegistro, TutorAcademico tutorAcademico, ProblematicaAcademica problematicaAcademicaPorModificar) {
        this.esRegistro = esRegistro;
        this.tutorAcademico = tutorAcademico;
        this.problematicaAcademicaPorModificar = problematicaAcademicaPorModificar;
        if (!esRegistro) {
            buttonEliminar.setVisible(false);
            cargarProblematicaAcademicaPorModificar();
        }
    }

    @FXML
    private void clicButtonAceptar(ActionEvent event) {
        if (esRegistro) {
            // TODO
        } else{
            
        }
    }

    @FXML
    private void clicButtonEliminar(ActionEvent event) {
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        if (esRegistro) {
            Stage escenario = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
            escenario.close();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLProblematicasAcademicas.fxml"));
            try {
                Parent root = loader.load();
                FXMLProblematicasAcademicasController problematicasAcademicasController = loader.getController();
                problematicasAcademicasController.configurarEscena(false, tutorAcademico);
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

    private void cargarExperienciasEducativasPorPeriodoEscolar() {
        ArrayList<ExperienciaEducativa> consultaExperienciasEducativas = ExperienciaEducativaDAO.obtenerExperienciasEducativasEnPeriodoEscolarActual();
        experienciasEducativas = FXCollections.observableArrayList();
        experienciasEducativas.addAll(consultaExperienciasEducativas);
        comboBoxExperienciaEducativa.setItems(experienciasEducativas);
    }

    private void cargarProfesoresPorExperienciaEducativa(int idExperienciaEducativa) {
        ArrayList<Profesor> consultaProfesores = ProfesorDAO.obtenerProfesoresPorExperienciaEducativa(idExperienciaEducativa);
        profesores = FXCollections.observableArrayList();
        profesores.addAll(consultaProfesores);
        comboBoxProfesor.setItems(profesores);
    }

    private void cargarProblematicaAcademicaPorModificar() {
        textFieldTitulo.setText(problematicaAcademicaPorModificar.getTitulo());
        textFieldNumEstudiantes.setText(Integer.toString(problematicaAcademicaPorModificar.getNumEstudiantes()));
        textAreaDescripcion.setText(problematicaAcademicaPorModificar.getDescripcion());
        int posicionComboBoxExperienciaEducativa = getIndiceExperienciaEducativa(problematicaAcademicaPorModificar.getIdExperienciaEducativa());
        comboBoxExperienciaEducativa.getSelectionModel().select(posicionComboBoxExperienciaEducativa);
        int posicionComboBoxProfesor = getIndiceProfesor(problematicaAcademicaPorModificar.getIdProfesor());
        comboBoxProfesor.getSelectionModel().select(posicionComboBoxProfesor);
    }

    private int getIndiceExperienciaEducativa(int idExperienciaEducativa) {
        int indiceExperienciaEducativa = 0;
        for (int i = 0; i < experienciasEducativas.size(); i++) {
            if (experienciasEducativas.get(i).getIdExperienciaEducativa() == idExperienciaEducativa) {
                indiceExperienciaEducativa = i;
            }
        }
        return indiceExperienciaEducativa;
    }

    private int getIndiceProfesor(int idProfesor) {
        int indiceProfesor = 0;
        for (int i = 0; i < profesores.size(); i++) {
            if (profesores.get(i).getIdProfesor() == idProfesor) {
                indiceProfesor = i;
            }
        }
        return indiceProfesor;
    }

}