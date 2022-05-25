package gestiontutoriasacademicas.modelo.pojo;

public class ExperienciaEducativa {
    
    private int idExperienciaEducativa;
    private String nombre;
    private String descripcion;

    public ExperienciaEducativa() {
    }

    public ExperienciaEducativa(int idExperienciaEducativa, String nombre, String descripcion) {
        this.idExperienciaEducativa = idExperienciaEducativa;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdExperienciaEducativa() {
        return idExperienciaEducativa;
    }

    public void setIdExperienciaEducativa(int idExperienciaEducativa) {
        this.idExperienciaEducativa = idExperienciaEducativa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}