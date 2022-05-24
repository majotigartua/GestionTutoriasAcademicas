package gestiontutoriasacademicas.modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBaseDatos {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE = "tutoriasAcademicas";
    private static final String HOSTNAME = "localhost";
    private static final String PORT = "3306";
    private static final String URL_CONEXION = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" + DATABASE + "?serverTimezone=UTC";

    private static final String USERNAME = "majotigartua";
    private static final String PASSWORD = "INGSOFTMySQL3306";

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