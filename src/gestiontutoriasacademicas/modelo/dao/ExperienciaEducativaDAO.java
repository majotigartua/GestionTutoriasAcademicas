/**
 * Nombre del programador: Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 26/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: DAO de Experiecia educativa.
 */
package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.ExperienciaEducativa;
import gestiontutoriasacademicas.util.Utilidades;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class ExperienciaEducativaDAO {

    public static ArrayList<ExperienciaEducativa> obtenerExperienciasEducativasPorPeriodoEscolar() {
        ArrayList<ExperienciaEducativa> experienciasEducativas = new ArrayList<>();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            try {
                String consulta = "SELECT DISTINCT experienciaEducativa.*\n"
                        + "FROM experienciaEducativa\n"
                        + "INNER JOIN ofertaAcademica\n"
                        + "ON experienciaEducativa.idExperienciaEducativa = ofertaAcademica.idExperienciaEducativa\n"
                        + "INNER JOIN periodoEscolar\n"
                        + "ON ofertaAcademica.codigoPeriodoEscolar = periodoEscolar.codigoPeriodoEscolar\n"
                        + "WHERE (NOW() BETWEEN periodoEscolar.fechaInicio AND periodoEscolar.fechaFin)\n"
                        + "ORDER BY (experienciaEducativa.nombre) ASC;";
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                ResultSet resultado = configurarConsulta.executeQuery();
                while (resultado.next()) {
                    ExperienciaEducativa experienciaEducativa = new ExperienciaEducativa();
                    experienciaEducativa.setIdExperienciaEducativa(resultado.getInt("idExperienciaEducativa"));
                    experienciaEducativa.setNombre(resultado.getString("nombre"));
                    experienciasEducativas.add(experienciaEducativa);
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
        return experienciasEducativas;
    }

}
