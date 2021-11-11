package config.enums;

public enum State {
    Sin_asignar("Sin asignar"),
    Aprobacion_pendiente("Aprobacion pendiente"),
    Disponible_para_calificar("Disponible para calificar"),
    Fuera_de_fecha("Fuera de fecha"),
    Cumplidos("Cumplidos");

    private String name;

    State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
