package config.endpointClasses.rubricCreation;

import config.enums.State;

import java.time.LocalDate;

public interface RubricCreationInterface {
    String getCourse();
    String getActivity();
    Integer getWeek();
    String getCodCompetence();
    String getCompetence();
    String getCriteria();
    Integer getcriteriaLevel();
    LocalDate getDate();
    String getContent();
    String getEvaluation();
    String getEvidence();
    String getCycle();
    String getTitle();
    State getState();
    String getSections();
    String getAllSections();
    Integer getGrade();
}
