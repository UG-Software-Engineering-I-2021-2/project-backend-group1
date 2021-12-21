package config.endpoint_classes.competence;

public class Competence {
    String code;
    String description;

    public Competence(CompetenceInterface competenceInterface){
        this.code = competenceInterface.getCode();
        this.description = competenceInterface.getDescription();
    }
    public String getCode(){
        return this.code;
    }
}
