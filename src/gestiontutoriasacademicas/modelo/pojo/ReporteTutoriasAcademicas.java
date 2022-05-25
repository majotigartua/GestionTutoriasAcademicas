package gestiontutoriasacademicas.modelo.pojo;

public class ReporteTutoriasAcademicas {
    
    private int idReporteTutoriasAcademicas;
    private int numEstudiantesAsistentes;
    private int numEstudiantesEnRiesgo;
    private int idTutoriaAcademica;
    private String tutorAcademico;
    private int idReporteGeneralTutoriasAcademicas;

    public ReporteTutoriasAcademicas() {
    }

    public ReporteTutoriasAcademicas(int idReporteTutoriasAcademicas, int idTutoriaAcademica, String tutorAcademico) {
        this.idReporteTutoriasAcademicas = idReporteTutoriasAcademicas;
        this.idTutoriaAcademica = idTutoriaAcademica;
        this.tutorAcademico = tutorAcademico;
    }

    public int getIdReporteTutoriasAcademicas() {
        return idReporteTutoriasAcademicas;
    }

    public void setIdReporteTutoriasAcademicas(int idReporteTutoriasAcademicas) {
        this.idReporteTutoriasAcademicas = idReporteTutoriasAcademicas;
    }

    public int getNumEstudiantesAsistentes() {
        return numEstudiantesAsistentes;
    }

    public void setNumEstudiantesAsistentes(int numEstudiantesAsistentes) {
        this.numEstudiantesAsistentes = numEstudiantesAsistentes;
    }

    public int getNumEstudiantesEnRiesgo() {
        return numEstudiantesEnRiesgo;
    }

    public void setNumEstudiantesEnRiesgo(int numEstudiantesEnRiesgo) {
        this.numEstudiantesEnRiesgo = numEstudiantesEnRiesgo;
    }

    public int getIdTutoriaAcademica() {
        return idTutoriaAcademica;
    }

    public void setIdTutoriaAcademica(int idTutoriaAcademica) {
        this.idTutoriaAcademica = idTutoriaAcademica;
    }

    public String getTutorAcademico() {
        return tutorAcademico;
    }

    public void setTutorAcademico(String tutorAcademico) {
        this.tutorAcademico = tutorAcademico;
    }

    public int getIdReporteGeneralTutoriasAcademicas() {
        return idReporteGeneralTutoriasAcademicas;
    }

    public void setIdReporteGeneralTutoriasAcademicas(int idReporteGeneralTutoriasAcademicas) {
        this.idReporteGeneralTutoriasAcademicas = idReporteGeneralTutoriasAcademicas;
    }
   
}