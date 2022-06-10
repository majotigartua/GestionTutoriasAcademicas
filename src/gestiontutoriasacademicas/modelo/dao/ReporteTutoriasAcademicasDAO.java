/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 05/05/2022.
 * Fecha más reciente de modificación: 07/05/2022.
 * Descripción: DAO de Reporte de Tutorías Académicas.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.ReporteTutoriasAcademicas;
import gestiontutoriasacademicas.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReporteTutoriasAcademicasDAO {

    public static int registrarReporteTutoriasAcademicas(ReporteTutoriasAcademicas reporteTutoriasAcademicas) {
        int codigoRespuesta;
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String sentencia = "INSERT INTO reporteTutoriasAcademicas\n"
                    + "(numEstudiantesAsistentes, numEstudiantesEnRiesgo, idTutoriaAcademica, nombreUsuario)\n"
                    + "VALUES (?, ?, ?, ?);";
            try {
                PreparedStatement configurarSentencia = conexionBaseDatos.prepareStatement(sentencia);
                configurarSentencia.setInt(1, reporteTutoriasAcademicas.getNumEstudiantesAsistentes());
                configurarSentencia.setInt(2, reporteTutoriasAcademicas.getNumEstudiantesEnRiesgo());
                configurarSentencia.setInt(3, reporteTutoriasAcademicas.getIdTutoriaAcademica());
                configurarSentencia.setString(4, reporteTutoriasAcademicas.getTutorAcademico());
                int filasAfectadas = configurarSentencia.executeUpdate();
                codigoRespuesta = (filasAfectadas == 1) ? Constantes.CODIGO_OPERACION_CORRECTA : Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS;
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