/**
 * Nombre del programador: María José Torres Igartua.
 * Fecha de creación: 05/05/2022.
 * Fecha más reciente de modificación: 07/05/2022.
 * Descripción: DAO de Estudiante.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.Estudiante;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class EstudianteDAO {

    public static ArrayList<Estudiante> obtenerEstudiantesPorTutorAcademico(String nombreUsuario) {
        ArrayList<Estudiante> estudiantes = new ArrayList<>();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String consulta = "SELECT * FROM estudiante WHERE nombreUsuario = ?";
            try {
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setString(1, nombreUsuario);
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    Estudiante estudiante = new Estudiante();
                    estudiante.setMatricula(resultado.getString("matricula"));
                    estudiante.setNombre(resultado.getString("nombre"));
                    estudiante.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    estudiante.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    estudiante.setCorreoElectronicoInstitucional(resultado.getString("correoElectronicoInstitucional"));
                    estudiantes.add(estudiante);
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
        return estudiantes;
    }

    public static int asignarReporteTutoriasAcademicas(Estudiante estudiante, int idTutoriaAcademica, String nombreUsuario) {
        int codigoRespuesta;
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String consulta = "SELECT idReporteTutoriasAcademicas\n"
                    + "FROM reporteTutoriasAcademicas\n"
                    + "WHERE idTutoriaAcademica = ? AND nombreUsuario = ?";
            try {
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setInt(1, idTutoriaAcademica);
                configurarConsulta.setString(2, nombreUsuario);
                ResultSet resultado = configurarConsulta.executeQuery();
                if (resultado.next()) {
                    int idReporteTutoriasAcademicas = resultado.getInt("idReporteTutoriasAcademicas");
                    String sentencia = "INSERT INTO estudianteReporteTutoriasAcademicas\n"
                            + "(esAsistente, enRiesgo, matricula, idReporteTutoriasAcademicas)\n"
                            + "VALUES (?, ?, ?, ?)";
                    PreparedStatement configurarSentencia = conexionBaseDatos.prepareStatement(sentencia);
                    configurarSentencia.setInt(1, estudiante.esAsistente() ? 1 : 0);
                    configurarSentencia.setInt(2, estudiante.enRiesgo() ? 1 : 0);
                    configurarSentencia.setString(3, estudiante.getMatricula());
                    configurarSentencia.setInt(4, idReporteTutoriasAcademicas);
                    int filasAfectadas = configurarSentencia.executeUpdate();
                    codigoRespuesta = (filasAfectadas == 1) ? Constantes.CODIGO_OPERACION_CORRECTA : Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS;
                } else {
                    codigoRespuesta = Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS;
                }
                conexionBaseDatos.close();
            } catch (SQLException ex) {
                codigoRespuesta = Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS;
            }
        } else {
            codigoRespuesta = Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS;
        }
        return codigoRespuesta;
    }

}
