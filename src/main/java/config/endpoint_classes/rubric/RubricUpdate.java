package config.endpoint_classes.rubric;

import config.enums.State;

public class RubricUpdate {
    Short dimensiones;
    String descriptores;
    String actividad;
    String title;
    State state;

    public RubricUpdate(Short dimensiones, String descriptores, String actividad, String title, State state) {
        this.dimensiones = dimensiones;
        this.descriptores = descriptores;
        this.actividad = actividad;
        this.title = title;
        this.state = state;
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

    public State getState() { return state; }

    public void setState(State state) { this.state = state; }
}
