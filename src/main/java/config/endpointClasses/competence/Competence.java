package config.endpointClasses.competence;

public class Competence {
    String code;
    String description;

    public Competence(CompetenceInterface competenceInterface){
        this.code = competenceInterface.getCode();
        this.description = competenceInterface.getDescription();
    }
}
