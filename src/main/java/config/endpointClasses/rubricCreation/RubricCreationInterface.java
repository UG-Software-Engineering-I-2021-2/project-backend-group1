package config.endpointClasses.rubricCreation;

import java.time.LocalDate;

public interface RubricCreationInterface {
    String getCourse();
    String getActivity();
    Integer getWeek();
    String getCompetence();
    String getCriteria();
    Integer getcriteriaLevel();
    LocalDate getDate();
    String getContent();
    String getEvaluation();
    String getEvidence();
    Integer getCycle();
    String getTitle();
}
