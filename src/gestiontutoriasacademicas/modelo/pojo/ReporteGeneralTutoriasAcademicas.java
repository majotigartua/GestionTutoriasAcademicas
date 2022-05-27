/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 23/05/2022.
 * Descripción: .
 */
package gestiontutoriasacademicas.modelo.pojo;

public class ReporteGeneralTutoriasAcademicas {
    
    private int idReporteGeneralTutoriasAcademicas;
    private int totalEstudiantesAsistentes;
    private int totalEstudiantesEnRiesgo;

    public ReporteGeneralTutoriasAcademicas() {
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