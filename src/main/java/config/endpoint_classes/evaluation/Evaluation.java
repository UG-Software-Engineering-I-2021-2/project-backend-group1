package config.endpoint_classes.evaluation;

import config.enums.State;

public class Evaluation {
    private String rubricCode;
    private String studentGrade;
    private String competenceGrade;
    private Boolean totalEvaluation;
    private State state;
    private Integer level;
    private String criteria;

    public Evaluation(EvaluationInterface evaluationInterface){
        this.rubricCode = evaluationInterface.getRubricCode();
        this.studentGrade = evaluationInterface.getStudentGrade();
        this.competenceGrade = evaluationInterface.getCompetenceGrade();
        this.totalEvaluation = evaluationInterface.getTotalEvaluation();
        this.state = evaluationInterface.getState();
        this.level = evaluationInterface.getLevel();
        this.criteria = evaluationInterface.getCriteria();
    }

    public String getRubricCode() {
        return rubricCode;
    }

    public String getStudentGrade() {
        return studentGrade;
    }

    public String getCompetenceGrade() {
        return competenceGrade;
    }

    public Boolean getTotalEvaluation() {
        return totalEvaluation;
    }

    public State getState() {
        return state;
    }

    public Integer getLevel() {
        return level;
    }

    public String getCriteria() {
        return criteria;
    }
}
