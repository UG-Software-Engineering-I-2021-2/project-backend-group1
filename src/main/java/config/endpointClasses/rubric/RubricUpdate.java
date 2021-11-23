package config.endpointClasses.rubric;

public class RubricUpdate {
    Short dimensiones;
    String descriptores;
    String actividad;
    String title;

    public RubricUpdate(Short dimensiones, String descriptores, String actividad, String title) {
        this.dimensiones = dimensiones;
        this.descriptores = descriptores;
        this.actividad = actividad;
        this.title = title;
    }

    public Short getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(Short dimensiones) {
        this.dimensiones = dimensiones;
    }

    public String getDescriptores() {
        return descriptores;
    }

    public void setDescriptores(String descriptores) {
        this.descriptores = descriptores;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
