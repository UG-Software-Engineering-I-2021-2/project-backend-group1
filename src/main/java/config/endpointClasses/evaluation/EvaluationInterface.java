package config.endpointClasses.evaluation;

import config.enums.State;

public interface EvaluationInterface {
    String getRubricCode();
    String getStudentGrade();
    String getCompetenceGrade();
    Boolean getTotalEvaluation();
    State getState();
    Integer getLevel();
    String getCriteria();
}
