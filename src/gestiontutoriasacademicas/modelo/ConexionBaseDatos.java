/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 23/05/2022.
 * Descripción: .
 */
package gestiontutoriasacademicas.modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBaseDatos {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE = "tutoriasacademicas";
    private static final String HOSTNAME = "localhost";
    private static final String PORT = "3306";
    private static final String URL_CONEXION = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE + "?serverTimezone=UTC";

    private static final String USERNAME = "username";
    private static final String PASSWORD = "contrasenia";

    public static Connection abrirConexion() {
        Connection conexionBaseDatos = null;
        try {
            Class.forName(DRIVER);
            conexionBaseDatos = DriverManager.getConnection(URL_CONEXION, USERNAME, PASSWORD);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return conexionBaseDatos;
    }

}