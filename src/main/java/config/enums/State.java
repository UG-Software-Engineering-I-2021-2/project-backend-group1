package config.enums;

public enum State {
    SIN_ASIGNAR("SinAsignar"),
    APROBACION_PENDIENTE("AprobacionPendiente"),
    DISPONIBLE_PARA_CALIFICAR("DisponibleParaCalificar"),
    FUERA_DE_FECHA("FueraDeFecha"),
    CUMPLIDOS("Cumplidos");

    private final String name;

    State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        switch (name){
            case "SinAsignar":
                return "Sin asignar";
            case "AprobacionPendiente":
                return "Aprobacion pendiente";
            case "DisponibleParaCalificar":
                return "Disponible para calificar";
            case "FueraDeFecha":
                return "Fuera de fecha";
            case "Cumplidos":
                return "Cumplidos";
            default:
                return name;
        }
    }
}
