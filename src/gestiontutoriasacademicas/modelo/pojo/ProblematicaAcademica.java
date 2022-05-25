package gestiontutoriasacademicas.modelo.pojo;

public class ProblematicaAcademica {
    
    private int idProblematicaAcademica;
    private String titulo;
    private String descripcion;
    private int numEstudiantes;
    private int idProfesor;
    private int idExperienciaEducativa;
    private String codigoPeriodoEscolar;
    private int idReporteTutoriasAcademicas;
    private int idSolucionProblematicaAcademica;

    public ProblematicaAcademica() {
    }

    public ProblematicaAcademica(int idProblematicaAcademica, String titulo, String descripcion, int numEstudiantes,
            int idProfesor, int idExperienciaEducativa, String codigoPeriodoEscolar, int idReporteTutoriasAcademicas) {
        this.idProblematicaAcademica = idProblematicaAcademica;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.numEstudiantes = numEstudiantes;
        this.idProfesor = idProfesor;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.codigoPeriodoEscolar = codigoPeriodoEscolar;
        this.idReporteTutoriasAcademicas = idReporteTutoriasAcademicas;
    }

    public int getIdProblematicaAcademica() {
        return idProblematicaAcademica;
    }

    public void setIdProblematicaAcademica(int idProblematicaAcademica) {
        this.idProblematicaAcademica = idProblematicaAcademica;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getNumEstudiantes() {
        return numEstudiantes;
    }

    public void setNumEstudiantes(int numEstudiantes) {
        this.numEstudiantes = numEstudiantes;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getCodigoPeriodoEscolar() {
        return codigoPeriodoEscolar;
    }

    public void setCodigoPeriodoEscolar(String codigoPeriodoEscolar) {
        this.codigoPeriodoEscolar = codigoPeriodoEscolar;
    }

    public int getIdReporteTutoriasAcademicas() {
        return idReporteTutoriasAcademicas;
    }

    public void setIdReporteTutoriasAcademicas(int idReporteTutoriasAcademicas) {
        this.idReporteTutoriasAcademicas = idReporteTutoriasAcademicas;
    }

    public int getIdSolucionProblematicaAcademica() {
        return idSolucionProblematicaAcademica;
    }

    public void setIdSolucionProblematicaAcademica(int idSolucionProblematicaAcademica) {
        this.idSolucionProblematicaAcademica = idSolucionProblematicaAcademica;
    }
      
}