/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: Define atributos y métodos de 'Tutoría académica'.
 */
package gestiontutoriasacademicas.modelo.pojo;

import gestiontutoriasacademicas.util.Utilidades;
import java.sql.Date;
import java.time.LocalDate;

public class TutoriaAcademica {

    private int idTutoriaAcademica;
    private int numSesion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaCierreEntregaReporte;
    private String codigoPeriodoEscolar;
    private String periodoEscolar;
    private int codigoRespuesta;

    public TutoriaAcademica() {
    }

    public TutoriaAcademica(int numSesion, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaCierreEntregaReporte) {
        this.numSesion = numSesion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaCierreEntregaReporte = fechaCierreEntregaReporte;
    }

    public int getIdTutoriaAcademica() {
        return idTutoriaAcademica;
    }

    public void setIdTutoriaAcademica(int idTutoriaAcademica) {
        this.idTutoriaAcademica = idTutoriaAcademica;
    }

    public int getNumSesion() {
        return numSesion;
    }

    public void setNumSesion(int numSesion) {
        this.numSesion = numSesion;
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

    public LocalDate getFechaCierreEntregaReporte() {
        return fechaCierreEntregaReporte;
    }

    public void setFechaCierreEntregaReporte(LocalDate fechaCierreEntregaReporte) {
        this.fechaCierreEntregaReporte = fechaCierreEntregaReporte;
    }

    public String getCodigoPeriodoEscolar() {
        return codigoPeriodoEscolar;
    }

    public void setCodigoPeriodoEscolar(String codigoPeriodoEscolar) {
        this.codigoPeriodoEscolar = codigoPeriodoEscolar;
    }

    public String getPeriodoEscolar() {
        return periodoEscolar;
    }

    public void setPeriodoEscolar(String periodoEscolar) {
        this.periodoEscolar = periodoEscolar;
    }

    public int getCodigoRespuesta() {
        return codigoRespuesta;
    }

    public void setCodigoRespuesta(int codigoRespuesta) {
        this.codigoRespuesta = codigoRespuesta;
    }

    @Override
    public String toString() {
        return Utilidades.convertirFechaAString(Date.valueOf(fechaInicio)) + " - " + Utilidades.convertirFechaAString(Date.valueOf(fechaFin));
    }
}