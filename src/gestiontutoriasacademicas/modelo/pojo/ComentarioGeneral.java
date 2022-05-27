/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: .
 */
package gestiontutoriasacademicas.modelo.pojo;

public class ComentarioGeneral {
    
    private int idComentarioGeneral;
    private String descripcion;
    private int idReporteTutoriasAcademicas;

    public ComentarioGeneral() {
    }

    public ComentarioGeneral(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdComentarioGeneral() {
        return idComentarioGeneral;
    }

    public void setIdComentarioGeneral(int idComentarioGeneral) {
        this.idComentarioGeneral = idComentarioGeneral;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdReporteTutoriasAcademicas() {
        return idReporteTutoriasAcademicas;
    }

    public void setIdReporteTutoriasAcademicas(int idReporteTutoriasAcademicas) {
        this.idReporteTutoriasAcademicas = idReporteTutoriasAcademicas;
    }
   
}