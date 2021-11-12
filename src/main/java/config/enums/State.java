package config.enums;

public enum State {
    SinAsignar("Sin asignar"),
    AprobacionPendiente("Aprobacion pendiente"),
    DisponibleParaCalificar("Disponible para calificar"),
    FueraDeFecha("Fuera de fecha"),
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

    public static State getByName(String name){
        return State.valueOf(name);
    }

}
