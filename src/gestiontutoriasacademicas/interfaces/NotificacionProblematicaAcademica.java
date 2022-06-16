/**
 * Nombre del programador: María José Torres Igartua.
 * Fecha de creación: 30/05/2022.
 * Fecha más reciente de modificación: 30/05/2022.
 * Descripción: Permite comunicar las pantallas de 'Llenar Reporte de Tutorías Académicas' y 'Registrar problemática académica'
 * para el registro de las problemáticas académicas de un Reporte de Tutorías Académicas.
 */
package gestiontutoriasacademicas.interfaces;

import gestiontutoriasacademicas.modelo.pojo.ProblematicaAcademica;

public interface NotificacionProblematicaAcademica {
   
    public abstract void notificarProblematicaAcademica(ProblematicaAcademica problematicaAcademica);
    
}