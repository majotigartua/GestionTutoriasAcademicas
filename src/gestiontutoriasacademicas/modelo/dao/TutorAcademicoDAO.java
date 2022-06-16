/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 15/06/2022.
 * Descripción: Objeto de acceso de datos que suministra una interfaz común entre el
 * programa informático y la base de de datos para la información de 'Tutor académico'.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class TutorAcademicoDAO {

    public static TutorAcademico iniciarSesion(String nombreUsuario, String contrasenia) {
        TutorAcademico tutorAcademico = new TutorAcademico();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT * FROM tutorAcademico\n"
                        + "INNER JOIN (SELECT COUNT(*) AS numEstudiantes FROM estudiante\n"
                        + "WHERE nombreUsuario = ?) AS tutorAcademicoEstudiante\n"
                        + "WHERE nombreUsuario = ? AND contrasenia = ?";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setString(1, nombreUsuario);
                configurarConsulta.setString(2, nombreUsuario);
                configurarConsulta.setString(3, contrasenia);
                ResultSet resultado = configurarConsulta.executeQuery();
                if (resultado.next()) {
                    tutorAcademico.setNombreUsuario(resultado.getString("nombreUsuario"));
                    tutorAcademico.setNombre(resultado.getString("nombre"));
                    tutorAcademico.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    tutorAcademico.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    tutorAcademico.setIdRol(resultado.getInt("idRol"));
                    tutorAcademico.setNumEstudiantes(resultado.getInt("numEstudiantes"));
                    tutorAcademico.setCodigoRespuesta(Constantes.CODIGO_INICIO_SESION_CORRECTO);
                } else {
                    tutorAcademico.setCodigoRespuesta(Constantes.CODIGO_CREDENCIALES_INCORRECTAS);
                }
                conexionBaseDatos.close();
            } catch (SQLException ex) {
                tutorAcademico.setCodigoRespuesta(Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS);
            }
        } else {
            tutorAcademico.setCodigoRespuesta(Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS);
        }
        return tutorAcademico;
    }

    public static int llenarReporteTutoriasAcademicas(int idTutoriaAcademica, String nombreUsuario) {
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
                    codigoRespuesta = Constantes.CODIGO_REPORTE_TUTORIAS_ACADEMICAS_REGISTRADO;
                } else {
                    codigoRespuesta = Constantes.CODIGO_OPERACION_CORRECTA;
                }
            } catch (SQLException ex) {
                codigoRespuesta = Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS;
            }
        } else {
            codigoRespuesta = Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS;
        }
        return codigoRespuesta;
    }

    public static ArrayList<TutorAcademico> obtenerTutoresAcademicos() {
        ArrayList<TutorAcademico> tutoresAcademicos = new ArrayList<>();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT nombre, apellidoPaterno, apellidoMaterno, nombreUsuario\n"
                        + "FROM tutorAcademico\n";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    TutorAcademico tutorAcademico = new TutorAcademico();
                    tutorAcademico.setNombre(resultado.getString("nombre"));
                    tutorAcademico.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    tutorAcademico.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    tutorAcademico.setNombreUsuario(resultado.getString("nombreUsuario"));
                    tutorAcademico.setCodigoRespuesta(Constantes.CODIGO_INICIO_SESION_CORRECTO);
                    tutoresAcademicos.add(tutorAcademico);
                }
                conexionBaseDatos.close();
            } catch (SQLException ex) {
                Utilidades.mostrarAlerta("ERROR",
                        "No hay conexión con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        } else {
            tutoresAcademicos = null;
        }
        return tutoresAcademicos;
    }

    public static String obtenerApellidoPaternoNombreTutorAcademico(int idReporteTutoriasAcademicas) {
        String apellidoPaternoNombreTutorAcademico = null;
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT\n"
                        + "CONCAT(tutoracademico.`apellidoPaterno`,tutoracademico.`nombre`) AS 'apellidoPaternoNombreTutorAcademico'\n"
                        + "FROM\n"
                        + "`tutoracademico` tutoracademico INNER JOIN `reportetutoriasacademicas` reportetutoriasacademicas ON tutoracademico.`nombreUsuario` = reportetutoriasacademicas.`nombreUsuario`\n"
                        + "WHERE\n"
                        + "idReporteTutoriasAcademicas = ?";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setInt(1, idReporteTutoriasAcademicas);
                ResultSet resultado = configurarConsulta.executeQuery();
                resultado.next();
                apellidoPaternoNombreTutorAcademico = resultado.getString("apellidoPaternoNombreTutorAcademico");
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
        return apellidoPaternoNombreTutorAcademico;
    }

}
