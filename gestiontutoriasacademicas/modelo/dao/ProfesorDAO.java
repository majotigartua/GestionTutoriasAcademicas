/**
 * Nombre del programador: María José Torres Igartua.
 * Fecha de creación: 26/05/2022.
 * Fecha más reciente de modificación: 30/05/2022.
 * Descripción: Objeto de acceso de datos que suministra una interfaz común entre el
 * programa informático y la base de de datos para la información de 'Profesor'.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.Profesor;
import gestiontutoriasacademicas.util.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class ProfesorDAO {

    public static ArrayList<Profesor> obtenerProfesoresPorExperienciaEducativa(int idExperienciaEducativa) {
        ArrayList<Profesor> profesores = new ArrayList<>();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String consulta = "SELECT profesor.*\n"
                    + "FROM profesor\n"
                    + "INNER JOIN ofertaAcademica\n"
                    + "ON profesor.idProfesor = ofertaAcademica.idProfesor\n"
                    + "WHERE idExperienciaEducativa = ?\n"
                    + "ORDER BY (profesor.nombre) ASC;";
            try {
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setInt(1, idExperienciaEducativa);
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    Profesor profesor = new Profesor();
                    profesor.setIdProfesor(resultado.getInt("idProfesor"));
                    profesor.setNombre(resultado.getString("nombre"));
                    profesor.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    profesor.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    profesor.setCorreoElectronicoInstitucional(resultado.getString("correoElectronicoInstitucional"));
                    profesor.setCorreoElectronicoPersonal(resultado.getString("correoElectronicoPersonal"));
                    profesores.add(profesor);
                }
                conexionBaseDatos.close();
            } catch (SQLException ex) {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlerta("ERROR",
                    "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                    Alert.AlertType.ERROR);
        }
        return profesores;
    }
}
