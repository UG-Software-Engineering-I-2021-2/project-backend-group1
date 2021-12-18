package config.endpointClasses.career;

public class Career {
    Integer id;
    String name;

    public Career(CareerInterface careerInterface){
        this.id = careerInterface.getId();
        this.name = careerInterface.getName();
    }
}
