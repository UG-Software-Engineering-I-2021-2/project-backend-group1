package config.endpoint_classes.career;

public class Career {
    Integer id;
    String name;

    public Career(CareerInterface careerInterface){
        this.id = careerInterface.getId();
        this.name = careerInterface.getName();
    }
    public String getName(){
        return this.name;
    }
}
