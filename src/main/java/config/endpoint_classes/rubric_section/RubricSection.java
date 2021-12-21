package config.endpoint_classes.rubric_section;

import config.endpoint_classes.rubric_creation.RubricCreationInterface;
import config.enums.Role;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RubricSection {
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
    List<String> sections;

    public RubricSection(RubricCreationInterface rubricCreationInterface, String role){
        this.course = rubricCreationInterface.getCourse();
        this.activity = rubricCreationInterface.getActivity();
        this.week = rubricCreationInterface.getWeek();
        this.codCompetence = rubricCreationInterface.getCodCompetence();
        this.competence = rubricCreationInterface.getCompetence();
        this.criteria = rubricCreationInterface.getCriteria();
        this.criteriaLevel = rubricCreationInterface.getcriteriaLevel();
        this.date = rubricCreationInterface.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.content = rubricCreationInterface.getContent();
        this.evaluation = rubricCreationInterface.getEvaluation();
        this.evidence = rubricCreationInterface.getEvidence();
        this.cycles = new ArrayList<>();
        this.cycles.addAll(Arrays.asList(rubricCreationInterface.getCycle().split("\\|")));
        this.title = rubricCreationInterface.getTitle();
        this.state = rubricCreationInterface.getState().toString();

        this.sections = new ArrayList<>();
        if(role.equals(Role.Calidad.toString()) || rubricCreationInterface.getGrade() == 0){
            this.sections.addAll(Arrays.asList(rubricCreationInterface.getAllSections().split("\\|")));
        }else{
            this.sections.addAll(Arrays.asList(rubricCreationInterface.getSections().split("\\|")));
        }
    }
}
