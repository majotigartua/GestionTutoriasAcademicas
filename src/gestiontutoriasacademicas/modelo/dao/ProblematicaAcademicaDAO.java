/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: DAO de Problemática académica.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;
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
                String consulta = "SELECT problematicaAcademica.titulo, problematicaAcademica.descripcion, problematicaAcademica.numEstudiantes,\n"
                        + "ofertaAcademica.idExperienciaEducativa, ofertaAcademica.idProfesor \n"
                        + "FROM problematicaAcademica\n"
                        + "INNER JOIN ofertaAcademica\n"
                        + "ON problematicaAcademica.idOfertaAcademica = ofertaAcademica.idOfertaAcademica;";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    ProblematicaAcademica problematicaAcademica = new ProblematicaAcademica();
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
                String consulta = "SELECT problematicaAcademica.titulo, problematicaAcademica.descripcion, problematicaAcademica.numEstudiantes,\n"
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
    
}