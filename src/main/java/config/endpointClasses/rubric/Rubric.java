package config.endpointClasses.rubric;

import config.enums.Role;
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

    public Rubric(RubricInterface rubricInterface, String role){
        this.code = rubricInterface.getCode();
        this.state = State.valueOf(rubricInterface.getState()).toString();
        this.evaluation = rubricInterface.getEvaluation();
        this.date = rubricInterface.getDdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.week = "Semana " + rubricInterface.getWeek();
        this.evidence = rubricInterface.getEvidence();
        this.activity = rubricInterface.getActivity();
        if(this.state.equals(State.SinAsignar.toString())){
            if(role.equals(Role.Docente.toString()))
                this.canEdit = rubricInterface.getCoordinates() == 1;
            else
                this.canEdit = false;
        }else {
            this.canEdit = true;
        }
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