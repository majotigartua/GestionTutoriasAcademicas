package gestiontutoriasacademicas.modelo.pojo;

public class ComentarioGeneral {
    
    private int idComentarioGeneral;
    private String descripcion;
    private int idReporteTutoriasAcademicas;

    public ComentarioGeneral() {
    }

    public ComentarioGeneral(int idComentarioGeneral, String descripcion, int idReporteTutoriasAcademicas) {
        this.idComentarioGeneral = idComentarioGeneral;
        this.descripcion = descripcion;
        this.idReporteTutoriasAcademicas = idReporteTutoriasAcademicas;
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