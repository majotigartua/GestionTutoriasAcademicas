/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 09/06/2022.
 * Descripción: DAO de Problemática académica.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
import gestiontutoriasacademicas.util.Constantes;
import gestiontutoriasacademicas.util.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

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

}