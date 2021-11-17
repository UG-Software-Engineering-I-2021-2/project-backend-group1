package config.endpointClasses.rubric;

import config.enums.State;

import java.time.format.DateTimeFormatter;

public class Rubric {
    private String code;
    private String state;
    private String evaluation;
    private String date;
    private String week;
    private String evidence;
    private String activity;
    private Boolean canEdit;
    private String students;
    private String level;

    public Rubric(RubricInterface rubricInterface){
        this.code = rubricInterface.getCode();
        this.state = State.valueOf(rubricInterface.getState()).toString();
        this.evaluation = rubricInterface.getEvaluation();
        this.date = rubricInterface.getDdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.week = String.valueOf(rubricInterface.getWeek());
        this.evidence = rubricInterface.getEvidence();
        this.activity = rubricInterface.getActivity();
        this.canEdit = rubricInterface.getCoordinates() == 1;
        this.students = String.valueOf(rubricInterface.getStudents());
        this.level = String.valueOf(rubricInterface.getDlevel());
    }

    public String getDate(){
        return this.date;
    }

    public String getState(){
        return this.state;
    }
}
