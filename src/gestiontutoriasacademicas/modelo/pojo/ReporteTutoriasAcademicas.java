/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: Define atributos y métodos de 'Reporte de Tutorías Académicas'.
 */
package gestiontutoriasacademicas.modelo.pojo;

public class ReporteTutoriasAcademicas {
    
    private int idReporteTutoriasAcademicas;
    private int numSesion;
    private int numEstudiantesAsistentes;
    private int numEstudiantesEnRiesgo;
    private int idTutoriaAcademica;
    private String tutorAcademico;
    private String fechasTutoriaAcademica;
    private String fechasPeriodoEscolar;
    private String comentarioGeneral;
    private String nombreUsuario;
    private int idReporteGeneralTutoriasAcademicas;

    public ReporteTutoriasAcademicas() {
    }

    public ReporteTutoriasAcademicas(int numEstudiantesAsistentes, int numEstudiantesEnRiesgo, int idTutoriaAcademica, String tutorAcademico) {
        this.numEstudiantesAsistentes = numEstudiantesAsistentes;
        this.numEstudiantesEnRiesgo = numEstudiantesEnRiesgo;
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

    public String getFechasTutoriaAcademica() {
        return fechasTutoriaAcademica;
    }

    public void setFechasTutoriaAcademica(String fechasTutoriaAcademica) {
        this.fechasTutoriaAcademica = fechasTutoriaAcademica;
    }
    
    public String getFechasPeriodoEscolar() {
        return fechasPeriodoEscolar;
    }

    public void setFechasPeriodoEscolar(String fechasPeriodoEscolar) {
        this.fechasPeriodoEscolar = fechasPeriodoEscolar;
    }

    public int getNumSesion() {
        return numSesion;
    }

    public void setNumSesion(int numSesion) {
        this.numSesion = numSesion;
    }

    public String getComentarioGeneral() {
        return comentarioGeneral;
    }

    public void setComentarioGeneral(String comentarioGeneral) {
        this.comentarioGeneral = comentarioGeneral;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
   
}