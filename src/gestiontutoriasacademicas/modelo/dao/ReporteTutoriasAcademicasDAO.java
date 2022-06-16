/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 05/05/2022.
 * Fecha más reciente de modificación: 11/06/2022.
 * Descripción: Objeto de acceso de datos que suministra una interfaz común entre el
 * programa informático y la base de de datos para la información de 'Reporte de Tutorías Académicas'.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.ReporteTutoriasAcademicas;
import gestiontutoriasacademicas.modelo.pojo.TutorAcademico;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

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
    
    public static ArrayList<ReporteTutoriasAcademicas> obtenerReportesTutoriasAcademicas(TutorAcademico tutorAcademico) {
        ArrayList<ReporteTutoriasAcademicas> reportesTutoriasAcademicas = new ArrayList<>();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT idReporteTutoriasAcademicas, concat(tutoriaacademica.fechaInicio, ' - ',tutoriaacademica.fechaFin) as tutoriaAcademica, "
                            + "concat(periodoescolar.fechaInicio, ' - ', periodoescolar.fechaFin) as periodoEscolar, concat(tutoracademico.nombre, ' ', tutoracademico.apellidoPaterno, ' ', tutoracademico.apellidoMaterno) as tutorAcademico\n"
                            + "FROM reportetutoriasacademicas \n" +
                            "INNER JOIN tutoracademico ON tutoracademico.nombreUsuario = reportetutoriasacademicas.nombreUsuario\n" +
                            "INNER JOIN tutoriaacademica ON tutoriaacademica.idTutoriaAcademica = reportetutoriasacademicas.idTutoriaAcademica\n" +
                            "LEFT JOIN periodoescolar ON periodoescolar.codigoPeriodoEscolar = tutoriaacademica.codigoPeriodoEscolar\n" +
                            "WHERE concat(tutoracademico.nombre, ' ', tutoracademico.apellidoPaterno, ' ', tutoracademico.apellidoMaterno) = ?";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setString(1, tutorAcademico.toString());
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    ReporteTutoriasAcademicas reporteTutoriasAcademicas = new ReporteTutoriasAcademicas();
                    reporteTutoriasAcademicas.setIdReporteTutoriasAcademicas(resultado.getInt("idReporteTutoriasAcademicas"));
                    reporteTutoriasAcademicas.setFechasTutoriaAcademica(resultado.getString("tutoriaAcademica"));
                    reporteTutoriasAcademicas.setFechasPeriodoEscolar(resultado.getString("periodoEscolar"));
                    reporteTutoriasAcademicas.setTutorAcademico(resultado.getString("tutorAcademico"));
                    reportesTutoriasAcademicas.add(reporteTutoriasAcademicas);
                }
                conexionBaseDatos.close();
            } catch (SQLException ex) {
                Utilidades.mostrarAlerta("ERROR",
                        "No hay conexión con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        } else {
            reportesTutoriasAcademicas = null;
        }
        return reportesTutoriasAcademicas;
    }
    
    public static void obtenerConsultaReporteTutoriasAcademicas(ReporteTutoriasAcademicas reporteTutoriasAcademicas) {
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT nombreUsuario, numEstudiantesAsistentes, numEstudiantesEnRiesgo, tutoriaacademica.numSesion, comentariogeneral.descripcion as comentarioGeneral FROM reportetutoriasacademicas\n" +
                                "INNER JOIN tutoriaacademica ON tutoriaacademica.idTutoriaAcademica = reportetutoriasacademicas.idTutoriaAcademica\n" +
                                "LEFT JOIN comentariogeneral ON comentariogeneral.idReporteTutoriasAcademicas = reportetutoriasacademicas.idReporteTutoriasAcademicas\n" +
                                "WHERE reportetutoriasacademicas.idReporteTutoriasAcademicas = ?";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setInt(1, reporteTutoriasAcademicas.getIdReporteTutoriasAcademicas());
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    reporteTutoriasAcademicas.setNombreUsuario(resultado.getString("nombreUsuario"));
                    reporteTutoriasAcademicas.setNumSesion(resultado.getInt("numSesion"));
                    reporteTutoriasAcademicas.setNumEstudiantesEnRiesgo(resultado.getInt("numEstudiantesEnRiesgo"));
                    reporteTutoriasAcademicas.setNumEstudiantesAsistentes(resultado.getInt("numEstudiantesAsistentes"));
                    reporteTutoriasAcademicas.setComentarioGeneral(resultado.getString("comentarioGeneral"));
                }
                conexionBaseDatos.close();
            } catch (SQLException ex) {
                Utilidades.mostrarAlerta("ERROR",
                        "No hay conexión con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
            }
        }
    }
    
    public static JasperPrint crearArchivoReporteTutoriasAcademicas(int idReporteTutoriasAcademicas, String nombreDelReporte){
        JasperPrint jprint = null;
        String pathArchivoJRXML = "src\\gestiontutoriasacademicas\\util\\reporteTutoriasAcademicas.jrxml";        
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
            JasperDesign jasperDesing = JRXmlLoader.load(pathArchivoJRXML);
            Map parametro = new HashMap();
            parametro.put("idReporteTutoriasAcademicas", idReporteTutoriasAcademicas);
            JasperReport jasperReport =  JasperCompileManager.compileReport(jasperDesing);
            jprint = JasperFillManager.fillReport(jasperReport, parametro, conexionBaseDatos);
            JasperExportManager.exportReportToPdfFile(jprint,nombreDelReporte);
            } catch (JRException ex) {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo crear el Reporte de Tutorías Académicas correctamente. \n\nPor favor, inténtelo más tarde.\n",
                        Alert.AlertType.ERROR);
                System.err.println(ex);
            }
        } else {
            Utilidades.mostrarAlerta("ERROR",
                "No se pudo conectar con la base de datos. \n\nPor favor, inténtelo más tarde.\n",
                Alert.AlertType.ERROR);
        }
        return jprint;
    }
    
}