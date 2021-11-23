package config.endpointClasses.rubric;

import java.time.LocalDate;

public interface RubricInterface {
    String getCode();
    String getState();
    String getEvaluation();
    LocalDate getDdate();
    Integer getWeek();
    String getEvidence();
    String getActivity();
    Integer getCoordinates();
    Integer getStudents();
    Integer getDlevel();
    String getTitle();
}
