package gestiontutoriasacademicas.vistas;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLIniciarSesionController implements Initializable {

    @FXML
    private TextField textFieldNombreUsuario;
    @FXML
    private TextField textFieldContrasenia;
    @FXML
    private Label labelCamposVacios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clicButtonIngresar(ActionEvent event) {
    }
    
}