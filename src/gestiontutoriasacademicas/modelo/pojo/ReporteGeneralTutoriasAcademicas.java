package gestiontutoriasacademicas.modelo.pojo;

public class ReporteGeneralTutoriasAcademicas {
    
    private int idReporteGeneralTutoriasAcademicas;
    private int totalEstudiantesAsistentes;
    private int totalEstudiantesEnRiesgo;

    public ReporteGeneralTutoriasAcademicas() {
    }

    public ReporteGeneralTutoriasAcademicas(int idReporteGeneralTutoriasAcademicas) {
        this.idReporteGeneralTutoriasAcademicas = idReporteGeneralTutoriasAcademicas;
    }

    public int getIdReporteGeneralTutoriasAcademicas() {
        return idReporteGeneralTutoriasAcademicas;
    }

    public void setIdReporteGeneralTutoriasAcademicas(int idReporteGeneralTutoriasAcademicas) {
        this.idReporteGeneralTutoriasAcademicas = idReporteGeneralTutoriasAcademicas;
    }

    public int getTotalEstudiantesAsistentes() {
        return totalEstudiantesAsistentes;
    }

    public void setTotalEstudiantesAsistentes(int totalEstudiantesAsistentes) {
        this.totalEstudiantesAsistentes = totalEstudiantesAsistentes;
    }

    public int getTotalEstudiantesEnRiesgo() {
        return totalEstudiantesEnRiesgo;
    }

    public void setTotalEstudiantesEnRiesgo(int totalEstudiantesEnRiesgo) {
        this.totalEstudiantesEnRiesgo = totalEstudiantesEnRiesgo;
    }
    
}