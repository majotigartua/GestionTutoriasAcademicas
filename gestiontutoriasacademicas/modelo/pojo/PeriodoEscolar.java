/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 23/05/2022.
 * Descripción: Define atributos y métodos de 'Periodo escolar'.
 */
package gestiontutoriasacademicas.modelo.pojo;

import java.time.LocalDate;


public class PeriodoEscolar {
    
    private String codigo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int codigoRespuesta;

    public PeriodoEscolar() {
    }

    public PeriodoEscolar(String codigo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.codigo = codigo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }
       
}