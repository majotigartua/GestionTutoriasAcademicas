package gestiontutoriasacademicas.vistas;

import gestiontutoriasacademicas.interfaces.NotificacionComentarioGeneral;
import gestiontutoriasacademicas.modelo.pojo.ComentarioGeneral;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class FXMLRegistrarComentarioGeneralController implements Initializable {

    @FXML
    private TextArea textAreaDescripcion;
    @FXML
    private Label labelCamposVacios;

    private NotificacionComentarioGeneral interfaceComentarioGeneral;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    public void configurarEscena(NotificacionComentarioGeneral interfaceComentarioGeneral){
        this.interfaceComentarioGeneral = interfaceComentarioGeneral;
    }

    @FXML
    private void clicButtonAceptar(ActionEvent event) {
        String descripcion = textAreaDescripcion.getText();
        if (descripcion.isEmpty()) {
            labelCamposVacios.setText(" No se puede dejar ningún campo vacío.");
        } else {
            ComentarioGeneral comentarioGeneral = new ComentarioGeneral(descripcion);
            interfaceComentarioGeneral.notificarComentarioGeneral(comentarioGeneral);
            cerrarVentana();
        }
    }

    @FXML
    private void clicButtonCancelar(ActionEvent event) {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage escenario = (Stage) textAreaDescripcion.getScene().getWindow();
        escenario.close();
    }

}