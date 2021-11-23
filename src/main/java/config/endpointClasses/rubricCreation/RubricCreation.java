package config.endpointClasses.rubricCreation;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RubricCreation {
    String course;
    String activity;
    Integer week;
    String competence;
    String criteria;
    Integer criteriaLevel;
    String date;
    String content;
    String evaluation;
    String evidence;
    String cycles;
    String title;
    String state;

    public RubricCreation(RubricCreationInterface rubricInterface){
        this.course = rubricInterface.getCourse();
        this.activity = rubricInterface.getActivity();
        this.week = rubricInterface.getWeek();
        this.competence = rubricInterface.getCompetence();
        this.criteria = rubricInterface.getCriteria();
        this.criteriaLevel = rubricInterface.getcriteriaLevel();
        this.date = rubricInterface.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.content = rubricInterface.getContent();
        this.evaluation = rubricInterface.getEvaluation();
        this.evidence = rubricInterface.getEvidence();
        this.cycles = "";
        this.title = rubricInterface.getTitle();
        this.state = rubricInterface.getState().toString();
    }

    public void setCycles(List<RubricCreationInterface> rubricInterfaceList){
        StringBuilder bld = new StringBuilder();
        for(int i = 0; i < rubricInterfaceList.size(); i++){
            String cycle = (rubricInterfaceList.get(i).getCycle() <= 10) ? String.valueOf(rubricInterfaceList.get(i).getCycle()) :"Electivo";
            if(i != rubricInterfaceList.size() - 1)
                cycle += ", ";
            bld.append(cycle);
        }
        this.cycles = bld.toString();
    }

}
