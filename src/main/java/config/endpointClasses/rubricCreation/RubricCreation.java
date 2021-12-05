package config.endpointClasses.rubricCreation;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RubricCreation {
    String course;
    String activity;
    Integer week;
    String codCompetence;
    String competence;
    String criteria;
    Integer criteriaLevel;
    String date;
    String content;
    String evaluation;
    String evidence;
    List<String> cycles;
    String title;
    String state;

    public RubricCreation(RubricCreationInterface rubricInterface){
        this.course = rubricInterface.getCourse();
        this.activity = rubricInterface.getActivity();
        this.week = rubricInterface.getWeek();
        this.codCompetence = rubricInterface.getCodCompetence();
        this.competence = rubricInterface.getCompetence();
        this.criteria = rubricInterface.getCriteria();
        this.criteriaLevel = rubricInterface.getcriteriaLevel();
        this.date = rubricInterface.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.content = rubricInterface.getContent();
        this.evaluation = rubricInterface.getEvaluation();
        this.evidence = rubricInterface.getEvidence();
        this.cycles = new ArrayList<>();
        this.cycles.addAll(Arrays.asList(rubricInterface.getCycle().split("\\|")));
        this.title = rubricInterface.getTitle();
        this.state = rubricInterface.getState().toString();
    }
}
