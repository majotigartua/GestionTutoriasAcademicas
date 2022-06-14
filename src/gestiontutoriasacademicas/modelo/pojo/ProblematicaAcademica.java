/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: .
 */
package gestiontutoriasacademicas.modelo.pojo;

public class ProblematicaAcademica {

    private int idProblematicaAcademica;
    private String titulo;
    private String descripcion;
    private int numEstudiantes;
    private int idProfesor;
    private String nombreProfesor;
    private String apellidoPaternoProfesor;
    private String apellidoMaternoProfesor;
    private int idExperienciaEducativa;
    private String nombreExperienciaEducativa;
    private String codigoPeriodoEscolar;
    private int idReporteTutoriasAcademicas;
    private int idSolucionProblematicaAcademica;
    
    
    public ProblematicaAcademica() {
    }

    public ProblematicaAcademica(String titulo, String descripcion, int numEstudiantes,
            int idExperienciaEducativa, int idProfesor) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.numEstudiantes = numEstudiantes;
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.idProfesor = idProfesor;
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

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public int getIdProfesor() {
        return idProfesor;
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
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

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getApellidoPaternoProfesor() {
        return apellidoPaternoProfesor;
    }

    public void setApellidoPaternoProfesor(String apellidoPaternoProfesor) {
        this.apellidoPaternoProfesor = apellidoPaternoProfesor;
    }

    public String getApellidoMaternoProfesor() {
        return apellidoMaternoProfesor;
    }

    public void setApellidoMaternoProfesor(String apellidoMaternoProfesor) {
        this.apellidoMaternoProfesor = apellidoMaternoProfesor;
    }

    public String getNombreExperienciaEducativa() {
        return nombreExperienciaEducativa;
    }

    public void setNombreExperienciaEducativa(String nombreExperienciaEducativa) {
        this.nombreExperienciaEducativa = nombreExperienciaEducativa;
    }
    
    @Override
    public String toString() {
        return titulo;
    }
 
}