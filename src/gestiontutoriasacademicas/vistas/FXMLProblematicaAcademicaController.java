/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 26/05/2022.
 * Fecha más reciente de modificación: 26/05/2022.
 * Descripción: Controlador de la pantalla de registrar/modificar problemática académica.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.interfaces.NotificacionProblematicaAcademica;
import gestiontutoriasacademicas.modelo.dao.ExperienciaEducativaDAO;
import gestiontutoriasacademicas.modelo.dao.ProblematicaAcademicaDAO;
import gestiontutoriasacademicas.modelo.dao.ProfesorDAO;
import gestiontutoriasacademicas.modelo.pojo.ExperienciaEducativa;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.Profesor;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private ProblematicaAcademica problematicaAcademicaPorModificar;
    private NotificacionProblematicaAcademica interfaceProblematicaAcademica;

    private ObservableList<ExperienciaEducativa> experienciasEducativas;
    private ObservableList<Profesor> profesores;

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
        textFieldNumEstudiantes.setText("0");
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

    public void configurarEscena(boolean esRegistro, NotificacionProblematicaAcademica interfaceProblematicaAcademica,
            ProblematicaAcademica problematicaAcademicaPorModificar) {
        this.esRegistro = esRegistro;
        this.problematicaAcademicaPorModificar = problematicaAcademicaPorModificar;
        this.interfaceProblematicaAcademica = interfaceProblematicaAcademica;
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
        ExperienciaEducativa experienciaEducativaSeleccionada = comboBoxExperienciaEducativa.getSelectionModel().getSelectedItem();
        Profesor profesorSeleccionado = comboBoxProfesor.getSelectionModel().getSelectedItem();
        if (titulo.isEmpty() || textFieldNumEstudiantes.getText().isEmpty() || descripcion.isEmpty()
                || experienciaEducativaSeleccionada == null || profesorSeleccionado == null) {
            labelCamposVacios.setText(" No se puede dejar ningún campo vacío.");
        } else if (numEstudiantes <= 0) {
            Utilidades.mostrarAlerta("AVISO",
                    "Los datos ingresados son inválidos. \n\nPor favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        } else {
            ProblematicaAcademica problematicaAcademica = new ProblematicaAcademica(titulo, descripcion, numEstudiantes,
                    experienciaEducativaSeleccionada.getIdExperienciaEducativa(), profesorSeleccionado.getIdProfesor());
            if (esRegistro) {
                interfaceProblematicaAcademica.notificarProblematicaAcademica(problematicaAcademica);
            } else {
                problematicaAcademica.setIdProblematicaAcademica(problematicaAcademicaPorModificar.getIdProblematicaAcademica());
                modificarProblematicaAcademica(problematicaAcademica);
            }
        }
    }

    @FXML
    private void clicButtonEliminar(ActionEvent event) {
        Optional<ButtonType> respuestaConfirmacion = Utilidades.mostrarConfirmacion("AVISO",
                "Una vez eliminado la problemática académica, no se podrá recuperar. \n\n¿Desea continuar?", Alert.AlertType.CONFIRMATION);
        if (respuestaConfirmacion.get() == ButtonType.OK) {
            int codigoRespuesta = ProblematicaAcademicaDAO.eliminarProblematicaAcademica(problematicaAcademicaPorModificar.getIdProblematicaAcademica());
            if (codigoRespuesta == Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS) {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            } else {
                interfaceProblematicaAcademica.notificarProblematicaAcademica(problematicaAcademicaPorModificar);
            }
            cerrarVentana();
        }
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void modificarProblematicaAcademica(ProblematicaAcademica problematicaAcademica) {
        int codigoRespuesta = ProblematicaAcademicaDAO.modificarProblematicaAcademica(problematicaAcademica);
        if (codigoRespuesta == Constantes.CODIGO_OPERACION_CORRECTA) {
            Utilidades.mostrarAlerta("AVISO",
                    "La información se modificó correctamente en el sistema.\n",
                    Alert.AlertType.INFORMATION);
            interfaceProblematicaAcademica.notificarProblematicaAcademica(problematicaAcademica);
        } else {
            Utilidades.mostrarAlerta("ERROR",
                    "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
        cerrarVentana();
    }

    private void cargarExperienciasEducativasPorPeriodoEscolar() {
        ArrayList<ExperienciaEducativa> consultaExperienciasEducativas = ExperienciaEducativaDAO.obtenerExperienciasEducativasPorPeriodoEscolar();
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

    private void cerrarVentana() {
        Stage escenario = (Stage) textFieldTitulo.getScene().getWindow();
        escenario.close();
    }

}