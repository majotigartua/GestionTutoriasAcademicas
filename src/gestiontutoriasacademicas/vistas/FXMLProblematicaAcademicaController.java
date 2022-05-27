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
import gestiontutoriasacademicas.util.Utilidades;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FXMLProblematicaAcademicaController implements Initializable {

    @FXML
    private Label labelCamposVacios;
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
    private TutorAcademico tutorAcademico;
    private ProblematicaAcademica problematicaAcademicaPorModificar;
    private ObservableList<ExperienciaEducativa> experienciasEducativas;
    private ExperienciaEducativa experienciaEducativaSeleccionada;
    private ObservableList<Profesor> profesores;
    private Profesor profesorSeleccionado;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textFieldNumEstudiantes.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    textFieldNumEstudiantes.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
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
        if (esRegistro) {
            buttonEliminar.setVisible(false);
        } else {
            cargarProblematicaAcademicaPorModificar();
        }
    }

    @FXML
    private void clicButtonAceptar(ActionEvent event) {
        labelCamposVacios.setText("");
        String titulo = textFieldTitulo.getText();
        int numEstudiantes = Integer.valueOf(textFieldNumEstudiantes.getText());
        String descripcion = textAreaDescripcion.getText();
        experienciaEducativaSeleccionada = comboBoxExperienciaEducativa.getSelectionModel().getSelectedItem();
        profesorSeleccionado = comboBoxProfesor.getSelectionModel().getSelectedItem();
        if (titulo.isEmpty() || textFieldNumEstudiantes.getText().isEmpty() || descripcion.isEmpty()
                || experienciaEducativaSeleccionada == null || profesorSeleccionado == null) {
            labelCamposVacios.setText("No se puede dejar ningún campo vacío.");
        } else if (numEstudiantes <= 0 || numEstudiantes > tutorAcademico.getNumEstudiantes()) {
            Utilidades.mostrarAlerta("AVISO",
                    "Los datos ingresados son inválidos. \n\nPor favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        } else {
            ProblematicaAcademica problematicaAcademica = new ProblematicaAcademica(titulo, descripcion, numEstudiantes,
            experienciaEducativaSeleccionada.getIdExperienciaEducativa(), profesorSeleccionado.getIdProfesor());
            if (esRegistro) {
                // TODO
            } else {
                modificarProblematicaAcademica(problematicaAcademica);
            }
        }
    }

    @FXML
    private void clicButtonEliminar(ActionEvent event) {
        // TODO
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        Stage escenario = (Stage) (Stage) ((Node) event.getSource()).getScene().getWindow();
        escenario.close();
    }

    private void modificarProblematicaAcademica(ProblematicaAcademica problematicaAcademica){
        // TODO
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