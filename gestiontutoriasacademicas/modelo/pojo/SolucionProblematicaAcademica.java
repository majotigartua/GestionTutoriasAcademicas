/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 23/05/2022.
 * Descripción: Define atributos y métodos de 'Solución a problemática académica'.
 */
package gestiontutoriasacademicas.modelo.pojo;

import java.time.LocalDate;

public class SolucionProblematicaAcademica {
    
    private int idSolucionProblematicaAcademica;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;

    public SolucionProblematicaAcademica() {
    }

    public SolucionProblematicaAcademica(String titulo, String descripcion, LocalDate fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getIdSolucionProblematicaAcademica() {
        return idSolucionProblematicaAcademica;
    }

    public void setIdSolucionProblematicaAcademica(int idSolucionProblematicaAcademica) {
        this.idSolucionProblematicaAcademica = idSolucionProblematicaAcademica;
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    
}