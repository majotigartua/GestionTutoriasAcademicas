/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 26/05/2022.
 * Fecha más reciente de modificación: 09/06/2022.
 * Descripción: Controlador de las pantallas de 'Registrar problemática académica',
 * 'Consultar problemática académica' y 'Modificar problemática académica'.
 */
package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.interfaces.NotificacionProblematicaAcademica;
import gestiontutoriasacademicas.modelo.dao.ExperienciaEducativaDAO;
import gestiontutoriasacademicas.modelo.dao.ProblematicaAcademicaDAO;
import gestiontutoriasacademicas.modelo.dao.ProfesorDAO;
import gestiontutoriasacademicas.modelo.pojo.ExperienciaEducativa;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.Profesor;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.io.File;
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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javax.swing.WindowConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

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
    private TextArea textAreaDescripcion;
    @FXML
    private Button buttonEliminar;
    @FXML
    private Button buttonAceptar;
    @FXML
    private Button buttonDescargar;
    @FXML
    private Button buttonImprimir;
    @FXML
    private TextField textFieldNumEstudiantes;

    private int tipoEscenaPorConfigurar;
    private TutorAcademico tutorAcademico;
    private ProblematicaAcademica problematicaAcademicaSeleccionada;
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
        textFieldNumEstudiantes.setText("1");
    }

    public void configurarEscena(int tipoEscenaPorConfigurar, TutorAcademico tutorAcademico,
            NotificacionProblematicaAcademica interfaceProblematicaAcademica, ProblematicaAcademica problematicaAcademicaSeleccionada) {
        this.tipoEscenaPorConfigurar = tipoEscenaPorConfigurar;
        this.tutorAcademico = tutorAcademico;
        this.problematicaAcademicaSeleccionada = problematicaAcademicaSeleccionada;
        this.interfaceProblematicaAcademica = interfaceProblematicaAcademica;
        cargarExperienciasEducativas();
        switch (tipoEscenaPorConfigurar) {
            case Constantes.CODIGO_CONFIGURAR_ESCENA_REGISTRAR:
                buttonEliminar.setVisible(false);
                buttonDescargar.setVisible(false);
                buttonImprimir.setVisible(false);
                break;
            case Constantes.CODIGO_CONFIGURAR_ESCENA_MODIFICAR:
                buttonDescargar.setVisible(false);
                buttonImprimir.setVisible(false);
                cargarProblematicaAcademicaSeleccionada();
                break;
            case Constantes.CODIGO_CONFIGURAR_ESCENA_CONSULTAR:
                textFieldTitulo.setEditable(false);
                comboBoxExperienciaEducativa.setDisable(true);
                comboBoxProfesor.setDisable(true);
                textFieldNumEstudiantes.setEditable(false);
                textAreaDescripcion.setEditable(false);
                buttonEliminar.setVisible(false);
                buttonAceptar.setVisible(false);
                cargarProblematicaAcademicaSeleccionada();
                break;
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
        } else if (numEstudiantes == 0 || numEstudiantes > tutorAcademico.getNumEstudiantes()) {
            Utilidades.mostrarAlerta("AVISO",
                    "Los datos ingresados son inválidos. \n\nPor favor, compruebe la información ingresada e inténtelo nuevamente.\n",
                    Alert.AlertType.WARNING);
        } else {
            ProblematicaAcademica problematicaAcademica = new ProblematicaAcademica(titulo, descripcion, numEstudiantes,
                    experienciaEducativaSeleccionada.getIdExperienciaEducativa(), profesorSeleccionado.getIdProfesor());
            if (tipoEscenaPorConfigurar == Constantes.CODIGO_CONFIGURAR_ESCENA_REGISTRAR) {
                interfaceProblematicaAcademica.notificarProblematicaAcademica(problematicaAcademica);
                cerrarVentana();
            } else {
                problematicaAcademica.setIdProblematicaAcademica(problematicaAcademicaSeleccionada.getIdProblematicaAcademica());
                modificarProblematicaAcademica(problematicaAcademica);
            }
        }
    }

    @FXML
    private void clicButtonEliminar(ActionEvent event) {
        Optional<ButtonType> respuestaConfirmacion = Utilidades.mostrarConfirmacion("AVISO",
                "Una vez eliminado la problemática académica, no se podrá recuperar. \n\n¿Desea continuar?", Alert.AlertType.CONFIRMATION);
        if (respuestaConfirmacion.get() == ButtonType.OK) {
            int codigoRespuesta = ProblematicaAcademicaDAO.eliminarProblematicaAcademica(problematicaAcademicaSeleccionada.getIdProblematicaAcademica());
            if (codigoRespuesta == Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS) {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            } else {
                interfaceProblematicaAcademica.notificarProblematicaAcademica(problematicaAcademicaSeleccionada);
            }
            cerrarVentana();
        }
    }

    @FXML
    private void clicButtonDescargar(ActionEvent event) {
        boolean esImprimir = false;
        int posicionComboBoxExperienciaEducativa = obtenerIndiceExperienciaEducativa(problematicaAcademicaSeleccionada.getIdExperienciaEducativa());
        comboBoxExperienciaEducativa.getSelectionModel().select(posicionComboBoxExperienciaEducativa);
        ExperienciaEducativa experienciaEducativa = comboBoxExperienciaEducativa.getSelectionModel().getSelectedItem();
        String nombreReporteProblematicaAcademica = "\\" + problematicaAcademicaSeleccionada.getTitulo() + "_" + experienciaEducativa.getNombre() + ".pdf";
        nombreReporteProblematicaAcademica = nombreReporteProblematicaAcademica.replace(" ", "");
        DirectoryChooser seleccionarDirectorio = new DirectoryChooser();
        File directorio = seleccionarDirectorio.showDialog(textFieldTitulo.getScene().getWindow());
        String pathnombreDelReporte = directorio.getPath() + nombreReporteProblematicaAcademica;
        if (directorio != null) {
            exportar(esImprimir, problematicaAcademicaSeleccionada.getIdProblematicaAcademica(), nombreReporteProblematicaAcademica, pathnombreDelReporte);
        }
    }

    @FXML
    private void clicButtonImprimir(ActionEvent event) throws JRException {
        boolean esImprimir = true;
        int posicionComboBoxExperienciaEducativa = obtenerIndiceExperienciaEducativa(problematicaAcademicaSeleccionada.getIdExperienciaEducativa());
        comboBoxExperienciaEducativa.getSelectionModel().select(posicionComboBoxExperienciaEducativa);
        ExperienciaEducativa experienciaEducativa = comboBoxExperienciaEducativa.getSelectionModel().getSelectedItem();
        String nombreReporteProblematicaAcademica = problematicaAcademicaSeleccionada.getTitulo() + "_" + experienciaEducativa.getNombre() + ".pdf";
        nombreReporteProblematicaAcademica = nombreReporteProblematicaAcademica.replace(" ", "");
        exportar(esImprimir, problematicaAcademicaSeleccionada.getIdProblematicaAcademica(), nombreReporteProblematicaAcademica, null);
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void exportar(boolean esImprimir, int idProblematicaAcademica, String nombreReporteProblematicaAcademica, String path) {
        JasperPrint jasperPrint;
        if (esImprimir) {
            jasperPrint = ProblematicaAcademicaDAO.crearReporteProblematicaAcademica(idProblematicaAcademica, nombreReporteProblematicaAcademica);
            if (jasperPrint != null) {
                JasperViewer visualizarReportePtoblematicaAcademica = new JasperViewer(jasperPrint, false);
                visualizarReportePtoblematicaAcademica.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                visualizarReportePtoblematicaAcademica.setVisible(true);
            } else {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo crear correctamente el reporte de problemática académica. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        } else {
            jasperPrint = ProblematicaAcademicaDAO.crearReporteProblematicaAcademica(idProblematicaAcademica, path);
            if (jasperPrint != null) {
                Utilidades.mostrarAlerta("AVISO",
                        "La descarga se ha completado correctamente.\n",
                        Alert.AlertType.INFORMATION);
            } else {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo crear correctamente el reporte de problemática académica. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        }
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

    private void cargarExperienciasEducativas() {
        ArrayList<ExperienciaEducativa> consultaExperienciasEducativas;
        if (tipoEscenaPorConfigurar == Constantes.CODIGO_CONFIGURAR_ESCENA_CONSULTAR) {
            consultaExperienciasEducativas = ExperienciaEducativaDAO.obtenerExperienciaEducativas();
        } else {
            consultaExperienciasEducativas = ExperienciaEducativaDAO.obtenerExperienciasEducativasActuales();
        }
        experienciasEducativas = FXCollections.observableArrayList();
        experienciasEducativas.addAll(consultaExperienciasEducativas);
        comboBoxExperienciaEducativa.setItems(experienciasEducativas);
        comboBoxExperienciaEducativa.valueProperty().addListener(new ChangeListener<ExperienciaEducativa>() {
            @Override
            public void changed(ObservableValue<? extends ExperienciaEducativa> observable, ExperienciaEducativa oldValue, ExperienciaEducativa newValue) {
                if (newValue != null) {
                    cargarProfesoresPorExperienciaEducativa(newValue.getIdExperienciaEducativa());
                }
            }
        });
    }

    private void cargarProfesoresPorExperienciaEducativa(int idExperienciaEducativa) {
        ArrayList<Profesor> consultaProfesoresPorExperienciaEducativa = ProfesorDAO.obtenerProfesoresPorExperienciaEducativa(idExperienciaEducativa);
        profesores = FXCollections.observableArrayList();
        profesores.addAll(consultaProfesoresPorExperienciaEducativa);
        comboBoxProfesor.setItems(profesores);
    }

    private void cargarProblematicaAcademicaSeleccionada() {
        textFieldTitulo.setText(problematicaAcademicaSeleccionada.getTitulo());
        textFieldNumEstudiantes.setText(Integer.toString(problematicaAcademicaSeleccionada.getNumEstudiantes()));
        textAreaDescripcion.setText(problematicaAcademicaSeleccionada.getDescripcion());
        int posicionComboBoxExperienciaEducativa = obtenerIndiceExperienciaEducativa(problematicaAcademicaSeleccionada.getIdExperienciaEducativa());
        comboBoxExperienciaEducativa.getSelectionModel().select(posicionComboBoxExperienciaEducativa);
        int posicionComboBoxProfesor = obtenerIndiceProfesor(problematicaAcademicaSeleccionada.getIdProfesor());
        comboBoxProfesor.getSelectionModel().select(posicionComboBoxProfesor);
    }

    private int obtenerIndiceExperienciaEducativa(int idExperienciaEducativa) {
        int indiceExperienciaEducativa = 0;
        for (int i = 0; i < experienciasEducativas.size(); i++) {
            if (experienciasEducativas.get(i).getIdExperienciaEducativa() == idExperienciaEducativa) {
                indiceExperienciaEducativa = i;
            }
        }
        return indiceExperienciaEducativa;
    }

    private int obtenerIndiceProfesor(int idProfesor) {
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