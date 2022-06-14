/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 09/06/2022.
 * Descripción: DAO de Problemática académica.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.modelo.pojo.ReporteTutoriasAcademicas;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class ProblematicaAcademicaDAO {

    public static ArrayList<ProblematicaAcademica> obtenerProblematicasAcademicas() {
        ArrayList<ProblematicaAcademica> problematicasAcademicas = new ArrayList<>();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT problematicaAcademica.*,\n"
                        + "ofertaAcademica.idExperienciaEducativa, ofertaAcademica.idProfesor \n"
                        + "FROM problematicaAcademica\n"
                        + "INNER JOIN ofertaAcademica\n"
                        + "ON problematicaAcademica.idOfertaAcademica = ofertaAcademica.idOfertaAcademica;";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    ProblematicaAcademica problematicaAcademica = new ProblematicaAcademica();
                    problematicaAcademica.setIdProblematicaAcademica(resultado.getInt("idProblematicaAcademica"));
                    problematicaAcademica.setTitulo(resultado.getString("titulo"));
                    problematicaAcademica.setDescripcion(resultado.getString("descripcion"));
                    problematicaAcademica.setNumEstudiantes(resultado.getInt("numEstudiantes"));
                    problematicaAcademica.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    problematicaAcademica.setIdProfesor(resultado.getInt("idProfesor"));
                    problematicasAcademicas.add(problematicaAcademica);
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
        return problematicasAcademicas;
    }

    public static ArrayList<ProblematicaAcademica> obtenerProblematicaAcademicasPorTutorAcademico(String nombreUsuario) {
        ArrayList<ProblematicaAcademica> problematicasAcademicas = new ArrayList<>();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT problematicaAcademica.*,\n"
                        + "ofertaAcademica.idExperienciaEducativa, ofertaAcademica.idProfesor \n"
                        + "FROM problematicaAcademica\n"
                        + "INNER JOIN ofertaAcademica\n"
                        + "ON problematicaAcademica.idOfertaAcademica = ofertaAcademica.idOfertaAcademica\n"
                        + "INNER JOIN periodoEscolar\n"
                        + "ON ofertaAcademica.codigoPeriodoEscolar = periodoEscolar.codigoPeriodoEscolar\n"
                        + "INNER JOIN reporteTutoriasAcademicas\n"
                        + "ON problematicaAcademica.idReporteTutoriasAcademicas = reporteTutoriasAcademicas.idReporteTutoriasAcademicas\n"
                        + "WHERE (NOW() BETWEEN periodoEscolar.fechaInicio AND periodoEscolar.fechaFin)\n"
                        + "AND nombreUsuario = ?";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setString(1, nombreUsuario);
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    ProblematicaAcademica problematicaAcademica = new ProblematicaAcademica();
                    problematicaAcademica.setIdProblematicaAcademica(resultado.getInt("idProblematicaAcademica"));
                    problematicaAcademica.setTitulo(resultado.getString("titulo"));
                    problematicaAcademica.setDescripcion(resultado.getString("descripcion"));
                    problematicaAcademica.setNumEstudiantes(resultado.getInt("numEstudiantes"));
                    problematicaAcademica.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    problematicaAcademica.setIdProfesor(resultado.getInt("idProfesor"));
                    problematicasAcademicas.add(problematicaAcademica);
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
        return problematicasAcademicas;
    }

    public static int registrarProblematicaAcademica(ProblematicaAcademica problematicaAcademica, int idTutoriaAcademica, String nombreUsuario) {
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
                    String consultaOfertaAcademica = "SELECT idOfertaAcademica\n"
                            + "FROM ofertaAcademica\n"
                            + "WHERE idExperienciaEducativa = ? AND idProfesor = ?";
                    PreparedStatement configurarConsultaOfertaAcademica = conexionBaseDatos.prepareStatement(consultaOfertaAcademica);
                    configurarConsultaOfertaAcademica.setInt(1, problematicaAcademica.getIdExperienciaEducativa());
                    configurarConsultaOfertaAcademica.setInt(2, problematicaAcademica.getIdProfesor());
                    ResultSet resultadoOfertaAcademica = configurarConsultaOfertaAcademica.executeQuery();
                    if (resultadoOfertaAcademica.next()) {
                        int idOfertaAcademica = resultadoOfertaAcademica.getInt("idOfertaAcademica");
                        String sentencia = "INSERT INTO problematicaAcademica\n"
                                + "(titulo, descripcion, numEstudiantes, idOfertaAcademica, idReporteTutoriasAcademicas)\n"
                                + "VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement configurarSentencia = conexionBaseDatos.prepareStatement(sentencia);
                        configurarSentencia.setString(1, problematicaAcademica.getTitulo());
                        configurarSentencia.setString(2, problematicaAcademica.getDescripcion());
                        configurarSentencia.setInt(3, problematicaAcademica.getNumEstudiantes());
                        configurarSentencia.setInt(4, idOfertaAcademica);
                        configurarSentencia.setInt(5, idReporteTutoriasAcademicas);
                        int filasAfectadas = configurarSentencia.executeUpdate();
                        codigoRespuesta = (filasAfectadas == 1) ? Constantes.CODIGO_OPERACION_CORRECTA : Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS;
                    } else {
                        codigoRespuesta = Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS;
                    }
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

    public static int modificarProblematicaAcademica(ProblematicaAcademica problematicaAcademica) {
        int codigoRespuesta;
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String consulta = "SELECT idOfertaAcademica\n"
                    + "FROM ofertaAcademica\n"
                    + "WHERE idExperienciaEducativa = ? AND idProfesor = ?";
            try {
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setInt(1, problematicaAcademica.getIdExperienciaEducativa());
                configurarConsulta.setInt(2, problematicaAcademica.getIdProfesor());
                ResultSet resultado = configurarConsulta.executeQuery();
                if (resultado.next()) {
                    int idOfertaAcademica = resultado.getInt("idOfertaAcademica");
                    String sentencia = "UPDATE problematicaAcademica\n"
                            + "SET titulo = ?, descripcion = ?, numEstudiantes = ?, idOfertaAcademica = ?\n"
                            + "WHERE idProblematicaAcademica = ?";
                    PreparedStatement configurarSentencia = conexionBaseDatos.prepareStatement(sentencia);
                    configurarSentencia.setString(1, problematicaAcademica.getTitulo());
                    configurarSentencia.setString(2, problematicaAcademica.getDescripcion());
                    configurarSentencia.setInt(3, problematicaAcademica.getNumEstudiantes());
                    configurarSentencia.setInt(4, idOfertaAcademica);
                    configurarSentencia.setInt(5, problematicaAcademica.getIdProblematicaAcademica());
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

    public static int eliminarProblematicaAcademica(int idProblematicaAcademica) {
        int codigoRespuesta;
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String sentencia = "DELETE FROM problematicaAcademica\n"
                    + "WHERE idProblematicaAcademica = ?";
            try {
                PreparedStatement configurarSentencia = conexionBaseDatos.prepareStatement(sentencia);
                configurarSentencia.setInt(1, idProblematicaAcademica);
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
    
    public static ArrayList<ProblematicaAcademica> obtenerProblematicaAcademicasPorReporteTutoriasAcademicas(ReporteTutoriasAcademicas reporteTutoriasAcademicas) {
        ArrayList<ProblematicaAcademica> problematicasAcademicas = new ArrayList<>();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT problematicaacademica.idProblematicaAcademica, experienciaeducativa.nombre as nombreExperienciaEducativa, profesor.nombre as nombreProfesor, profesor.apellidoPaterno, profesor.apellidoMaterno, titulo, numEstudiantes "
                                + "FROM problematicaacademica\n" +
                                "INNER JOIN ofertaacademica ON ofertaacademica.idOfertaAcademica = problematicaacademica.idOfertaAcademica\n" +
                                "INNER JOIN experienciaeducativa ON experienciaeducativa.idExperienciaEducativa = ofertaacademica.idExperienciaEducativa\n" +
                                "INNER JOIN profesor ON profesor.idProfesor = ofertaacademica.idProfesor\n" +
                                "WHERE problematicaacademica.idReporteTutoriasAcademicas = ?";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                configurarConsulta.setInt(1, reporteTutoriasAcademicas.getIdReporteTutoriasAcademicas());
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    ProblematicaAcademica problematicaAcademica = new ProblematicaAcademica();
                    problematicaAcademica.setIdProblematicaAcademica(resultado.getInt("idProblematicaAcademica"));
                    problematicaAcademica.setTitulo(resultado.getString("titulo"));
                    problematicaAcademica.setNumEstudiantes(resultado.getInt("numEstudiantes"));
                    problematicaAcademica.setNombreExperienciaEducativa(resultado.getString("nombreExperienciaEducativa"));
                    problematicaAcademica.setNombreProfesor(resultado.getString("nombreProfesor"));
                    problematicaAcademica.setApellidoPaternoProfesor(resultado.getString("apellidoPaterno"));
                    problematicaAcademica.setApellidoMaternoProfesor(resultado.getString("apellidoMaterno"));
                    problematicasAcademicas.add(problematicaAcademica);
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
        return problematicasAcademicas;
    }
    
    public static JasperPrint crearReporteProblematicaAcademica(int idProblematicaAcademica, String nombreDelReporte){
        JasperPrint jprint = null;
        String pathArchivoJRXML = "src\\gestiontutoriasacademicas\\util\\reporteProblematicaAcademica.jrxml";        
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String sentencia = "SELECT CONCAT(profesor.`nombre`, ' ',profesor.`apellidoPaterno`, ' ', profesor.`apellidoMaterno`) AS PROFESOR,\n" +
                "CONCAT(tutoracademico.`nombre`, ' ', tutoracademico.`apellidoPaterno`, ' ', tutoracademico.`apellidoMaterno`) AS TUTOR,\n" +
                "problematicaacademica.`titulo` AS Titulo, problematicaacademica.`descripcion` AS Descripcion,\n" +
                "problematicaacademica.`numEstudiantes` AS Número_de_Estudiantes, experienciaeducativa.`nombre` AS EENombre\n" +
                "FROM\n" +
                "`ofertaacademica` ofertaacademica INNER JOIN `problematicaacademica` problematicaacademica ON ofertaacademica.`idOfertaAcademica` = problematicaacademica.`idOfertaAcademica`\n" +
                "INNER JOIN `profesor` profesor ON ofertaacademica.`idProfesor` = profesor.`idProfesor`\n" +
                "INNER JOIN `experienciaeducativa` experienciaeducativa ON ofertaacademica.`idExperienciaEducativa` = experienciaeducativa.`idExperienciaEducativa`\n" +
                "INNER JOIN `reportetutoriasacademicas` reportetutoriasacademicas ON problematicaacademica.`idReporteTutoriasAcademicas` = reportetutoriasacademicas.`idReporteTutoriasAcademicas`\n" +
                "INNER JOIN `tutoracademico` tutoracademico ON reportetutoriasacademicas.`nombreUsuario` = tutoracademico.`nombreUsuario`\n" +
                "WHERE problematicaacademica.`idProblematicaAcademica` = " + idProblematicaAcademica + ";";
            try {
            JasperDesign jasperDesing = JRXmlLoader.load(pathArchivoJRXML);
            JRDesignQuery query = new JRDesignQuery();
            query.setText(sentencia);
            jasperDesing.setQuery(query);
            JasperReport jasperReport =  JasperCompileManager.compileReport(jasperDesing);
            jprint = JasperFillManager.fillReport(jasperReport, null, conexionBaseDatos);
            JasperExportManager.exportReportToPdfFile(jprint,nombreDelReporte);
            } catch (JRException ex) {
                Utilidades.mostrarAlerta("ERROR",
                        "No se pudo crear el reporte de problemática académica correctamente. \n\nPor favor, inténtelo más tarde.\n",
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