/**
 * Nombre del programador: María José Torres Igartua.
 * Fecha de creación: 15/06/2022.
 * Fecha más reciente de modificación: 15/06/2022.
 * Descripción: Objeto de acceso de datos que suministra una interfaz común entre el
 * programa informático y la base de de datos para la información de 'Comentario general'.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.ComentarioGeneral;
import gestiontutoriasacademicas.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComentarioGeneralDAO {

    public static int registrarComentarioGeneral(ComentarioGeneral comentarioGeneral, int idTutoriaAcademica, String nombreUsuario) {
        int codigoRespuesta;
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String consultaReporteTutoriasAcademicas = "SELECT idReporteTutoriasAcademicas\n"
                    + "FROM reporteTutoriasAcademicas\n"
                    + "WHERE idTutoriaAcademica = ? AND nombreUsuario = ?";
            try {
                PreparedStatement configurarConsultaReporteTutoriasAcademicas = conexionBaseDatos.prepareStatement(consultaReporteTutoriasAcademicas);
                configurarConsultaReporteTutoriasAcademicas.setInt(1, idTutoriaAcademica);
                configurarConsultaReporteTutoriasAcademicas.setString(2, nombreUsuario);
                ResultSet resultadoReporteTutoriasAcademicas = configurarConsultaReporteTutoriasAcademicas.executeQuery();
                if (resultadoReporteTutoriasAcademicas.next()) {
                    int idReporteTutoriasAcademicas = resultadoReporteTutoriasAcademicas.getInt("idReporteTutoriasAcademicas");
                    String sentencia = "INSERT INTO comentarioGeneral\n"
                            + "(descripcion, idReporteTutoriasAcademicas)\n"
                            + "VALUES (?, ?)";
                    PreparedStatement configurarSentencia = conexionBaseDatos.prepareStatement(sentencia);
                    configurarSentencia.setString(1, comentarioGeneral.getDescripcion());
                    configurarSentencia.setInt(2, idReporteTutoriasAcademicas);
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