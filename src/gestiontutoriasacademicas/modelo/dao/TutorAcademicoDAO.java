/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: DAO de Tutor académico.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import gestiontutoriasacademicas.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TutorAcademicoDAO {

    public static TutorAcademico iniciarSesion(String nombreUsuario, String contrasenia) {
        TutorAcademico tutorAcademico = new TutorAcademico();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT nombreUsuario, nombre, apellidoPaterno, apellidoMaterno, idRol\n"
                        + "FROM tutorAcademico\n"
                        + "WHERE nombreUsuario = ? AND contrasenia = ?";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setString(1, nombreUsuario);
                configurarConsulta.setString(2, contrasenia);;
                ResultSet resultado = configurarConsulta.executeQuery();
                if (resultado.next()) {
                    tutorAcademico.setNombreUsuario(resultado.getString("nombreUsuario"));
                    tutorAcademico.setNombre(resultado.getString("nombre"));
                    tutorAcademico.setApellidoPaterno(resultado.getString("apellidoPaterno"));
                    tutorAcademico.setApellidoMaterno(resultado.getString("apellidoMaterno"));
                    tutorAcademico.setIdRol(resultado.getInt("idRol"));
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

}