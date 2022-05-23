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
                configurarConsulta.setString(2, contrasenia);
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

}
