/**
 * Nombre del programador: María José Torres Igartua.
 * Fecha de creación: 15/06/2022.
 * Fecha más reciente de modificación: 15/06/2022.
 * Descripción: Permite comunicar las pantallas de 'Llenar Reporte de Tutorías Académicas' y 'Registrar problemática académica'
 * para el registro del comentario general de un Reporte de Tutorías Académicas.
 */
package gestiontutoriasacademicas.interfaces;

import gestiontutoriasacademicas.modelo.pojo.ComentarioGeneral;

public interface NotificacionComentarioGeneral {
    
    public abstract void notificarComentarioGeneral(ComentarioGeneral comentarioGeneral);
    
}