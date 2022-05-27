package gestiontutoriasacademicas.modelo.dao;

import gestiontutoriasacademicas.modelo.ConexionBaseDatos;
import gestiontutoriasacademicas.modelo.pojo.PeriodoEscolar;
import gestiontutoriasacademicas.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PeriodoEscolarDAO {
    
    public static PeriodoEscolar obtenerPeriodoEscolarActual() {
        PeriodoEscolar periodoEscolar = new PeriodoEscolar();
        Connection conexionBaseDatos = ConexionBaseDatos.abrirConexion();
        if (conexionBaseDatos != null) {
            String consulta = "SELECT * FROM periodoEscolar\n"
                    + "WHERE (NOW() BETWEEN periodoEscolar.fechaInicio AND periodoEscolar.fechaFin)";
            try {
                PreparedStatement configurarConsulta = conexionBaseDatos.prepareStatement(consulta);
                ResultSet resultado = configurarConsulta.executeQuery();
                if (resultado.next()) {
                    periodoEscolar.setCodigo(resultado.getString("codigoPeriodoEscolar"));
                    periodoEscolar.setFechaInicio(resultado.getDate("fechaInicio").toLocalDate());
                    periodoEscolar.setFechaFin(resultado.getDate("fechaFin").toLocalDate());
                    periodoEscolar.setCodigoRespuesta(Constantes.CODIGO_OPERACION_CORRECTA);
                } else {
                    periodoEscolar.setCodigoRespuesta(Constantes.CODIGO_ERROR_FECHA_ACTUAL);
                }
            } catch (SQLException ex) {
                periodoEscolar.setCodigoRespuesta(Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS);
            }
        } else {
            periodoEscolar.setCodigoRespuesta(Constantes.CODIGO_ERROR_CONEXION_BASE_DATOS);
        }
        return periodoEscolar;
    }
    
}