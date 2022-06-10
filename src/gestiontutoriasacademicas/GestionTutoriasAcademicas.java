/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 23/05/2022.
 * Descripción: .
 */
package gestiontutoriasacademicas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestionTutoriasAcademicas extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("vistas/FXMLIniciarSesion.fxml")); 
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Iniciar sesión");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}