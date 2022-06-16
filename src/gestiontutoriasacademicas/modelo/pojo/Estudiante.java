/**
 * Nombre del programador: Sebastián Bello Trejo, Ulises Ramos Mexicano y María José Torres Igartua.
 * Fecha de creación: 23/05/2022.
 * Fecha más reciente de modificación: 27/05/2022.
 * Descripción: Define atributos y métodos de 'Estudiante'.
 */
package gestiontutoriasacademicas.modelo.pojo;

import javafx.scene.control.CheckBox;

public class Estudiante {

    private String matricula;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correoElectronicoInstitucional;
    private String tutorAcademico;
    private boolean esAsistente;
    private boolean enRiesgo;
    private CheckBox checkBoxEsAsistente;
    private CheckBox checkBoxEnRiesgo;

    public Estudiante() {
        this.checkBoxEsAsistente = new CheckBox();
        this.checkBoxEnRiesgo = new CheckBox();
    }

    public Estudiante(String matricula, String nombre, String apellidoPaterno, String apellidoMaterno, String correoElectronicoInstitucional) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correoElectronicoInstitucional = correoElectronicoInstitucional;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreoElectronicoInstitucional() {
        return correoElectronicoInstitucional;
    }

    public void setCorreoElectronicoInstitucional(String correoElectronicoInstitucional) {
        this.correoElectronicoInstitucional = correoElectronicoInstitucional;
    }

    public String getTutorAcademico() {
        return tutorAcademico;
    }

    public void setTutorAcademico(String tutorAcademico) {
        this.tutorAcademico = tutorAcademico;
    }

    public boolean esAsistente() {
        return esAsistente;
    }

    public void setEsAsistente(boolean esAsistente) {
        this.esAsistente = esAsistente;
    }

    public boolean enRiesgo() {
        return enRiesgo;
    }

    public void setEnRiesgo(boolean enRiesgo) {
        this.enRiesgo = enRiesgo;
    }

    public CheckBox getCheckBoxEsAsistente() {
        return checkBoxEsAsistente;
    }

    public void setCheckBoxEsAsistente(CheckBox checkBoxEsAsistente) {
        this.checkBoxEsAsistente = checkBoxEsAsistente;
    }

    public CheckBox getCheckBoxEnRiesgo() {
        return checkBoxEnRiesgo;
    }

    public void setCheckBoxEnRiesgo(CheckBox checkBoxEnRiesgo) {
        this.checkBoxEnRiesgo = checkBoxEnRiesgo;
    }

}