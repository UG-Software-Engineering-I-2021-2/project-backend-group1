package config.endpointClasses.rubric;

public class RubricUpdate {
    Short dimensiones;
    String descriptores;
    String actividad;

    public RubricUpdate(Short dimensiones, String descriptores, String actividad) {
        this.dimensiones = dimensiones;
        this.descriptores = descriptores;
        this.actividad = actividad;
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
}
