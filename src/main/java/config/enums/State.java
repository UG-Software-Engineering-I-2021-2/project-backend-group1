package config.enums;

public enum State {
    SinAsignar("SinAsignar"),
    AprobacionPendiente("AprobacionPendiente"),
    DisponibleParaCalificar("DisponibleParaCalificar"),
    FueraDeFecha("FueraDeFecha"),
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
        }

        return name;
    }

    public static State getByName(String name){
        return State.valueOf(name.replaceAll(" ", "_"));
    }

}
