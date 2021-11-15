package config.endpointClasses.rubricCreationEndpoint;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Rubric {
    String course;
    String activity;
    Integer week;
    String competence;
    String criteria;
    Integer criteriaLevel;
    String date;
    Integer dimensions;
    String descriptors;
    String evaluation;
    String evidence;
    List<String> cycles;

    public Rubric(RubricInterface rubricInterface){
        this.course = rubricInterface.getCourse();
        this.activity = rubricInterface.getActivity();
        this.week = rubricInterface.getWeek();
        this.competence = rubricInterface.getCompetence();
        this.criteria = rubricInterface.getCriteria();
        this.criteriaLevel = rubricInterface.getcriteriaLevel();
        this.date = rubricInterface.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.dimensions = rubricInterface.getDimensions();
        this.descriptors = rubricInterface.getDescriptors();
        this.evaluation = rubricInterface.getEvaluation();
        this.evidence = rubricInterface.getEvidence();
        this.cycles = new ArrayList<>();
    }

    public void setCycles(List<RubricInterface> rubricInterfaceList){
        for(RubricInterface rubricInterface : rubricInterfaceList){
            if(rubricInterface.getCycle() <= 10)
                this.cycles.add(String.valueOf(rubricInterface.getCycle()));
            else
                this.cycles.add("Electivo");
        }
    }

}
