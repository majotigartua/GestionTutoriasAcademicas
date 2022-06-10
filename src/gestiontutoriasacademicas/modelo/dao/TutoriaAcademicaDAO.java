/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 05/05/2022.
 * Fecha más reciente de modificación: 07/05/2022.
 * Descripción: DAO de Tutoría Académica.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.TutoriaAcademica;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TutoriaAcademicaDAO {

    public static TutoriaAcademica obtenerTutoriaAcademicaActual() {
        TutoriaAcademica tutoriaAcademica = new TutoriaAcademica();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String consultaPeriodoEscolar = "SELECT * FROM periodoEscolar\n"
                    + "WHERE (NOW() BETWEEN periodoEscolar.fechaInicio AND periodoEscolar.fechaFin);";
            try {
                PreparedStatement configurarConsultaPeriodoEscolar = conexionBaseDatos.prepareStatement(consultaPeriodoEscolar);
                ResultSet resultadoPeriodoEscolar = configurarConsultaPeriodoEscolar.executeQuery();
                if (resultadoPeriodoEscolar.next()) {
                    String fechaInicioPeriodoEscolar = Utilidades.convertirFechaAString(resultadoPeriodoEscolar.getDate("fechaInicio"));
                    String fechaFinPeriodoEscolar = Utilidades.convertirFechaAString(resultadoPeriodoEscolar.getDate("fechaFin"));
                    String periodoEscolar = fechaInicioPeriodoEscolar + " - " + fechaFinPeriodoEscolar;
                    String consultaTutoriaAcademica = "SELECT * FROM tutoriaAcademica\n"
                            + "WHERE (NOW() BETWEEN tutoriaAcademica.fechaInicio AND tutoriaAcademica.fechaCierreEntregaReporte)";
                    PreparedStatement configurarConsultaTutoriaAcademica = conexionBaseDatos.prepareStatement(consultaTutoriaAcademica);
                    ResultSet resultadoTutoriaAcademica = configurarConsultaTutoriaAcademica.executeQuery();
                    if(resultadoTutoriaAcademica.next()){
                     tutoriaAcademica.setIdTutoriaAcademica(resultadoTutoriaAcademica.getInt("idTutoriaAcademica"));
                    tutoriaAcademica.setNumSesion(resultadoTutoriaAcademica.getInt("numSesion"));
                    tutoriaAcademica.setFechaInicio(resultadoTutoriaAcademica.getDate("fechaInicio").toLocalDate());
                    tutoriaAcademica.setFechaFin(resultadoTutoriaAcademica.getDate("fechaFin").toLocalDate());
                    tutoriaAcademica.setPeriodoEscolar(periodoEscolar);
                    } else {
                        tutoriaAcademica.setCodigoRespuesta(Constantes.CODIGO_ERROR_FECHA_ACTUAL);
                    }             
                } else {
                    tutoriaAcademica.setCodigoRespuesta(Constantes.CODIGO_ERROR_FECHA_ACTUAL);
                }
                conexionBaseDatos.close();
            } catch (SQLException ex) {
                tutoriaAcademica.setCodigoRespuesta(Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS);
            }
        } else {
            tutoriaAcademica.setCodigoRespuesta(Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS);
        }
        return tutoriaAcademica;
    }

}