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
    Integer getDimensions();
    String getDescriptors();
    String getEvaluation();
    String getEvidence();
    Integer getCycle();
}
