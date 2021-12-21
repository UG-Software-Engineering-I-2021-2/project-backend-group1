package config.endpoint_classes.rubric;

import config.enums.Role;
import config.enums.State;

import java.time.format.DateTimeFormatter;

public class Rubric {
    String code;
    String state;
    String evaluation;
    String date;
    String week;
    String evidence;
    String activity;
    Boolean canEdit;
    String students;
    String level;
    String title;
    String competenceCode;
    String criteriaCode;
    Boolean canGrade;

    public Rubric(RubricInterface rubricInterface, String role){
        this.code = rubricInterface.getCode();
        this.state = State.valueOf(rubricInterface.getState()).toString();
        this.evaluation = rubricInterface.getEvaluation();
        this.date = rubricInterface.getDdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.week = "Semana " + rubricInterface.getWeek();
        this.evidence = rubricInterface.getEvidence();
        this.activity = rubricInterface.getActivity();
        if(this.state.equals(State.SIN_ASIGNAR.toString())){
            if(role.equals(Role.DOCENTE.toString()))
                this.canEdit = rubricInterface.getCoordinates() == 1;
            else
                this.canEdit = false;
        }else {
            this.canEdit = true;
        }
        this.students = String.valueOf(rubricInterface.getStudents());
        this.level = String.valueOf(rubricInterface.getDlevel());
        this.title = rubricInterface.getTitle();
        this.competenceCode = rubricInterface.getCompetenceCode();
        String[] criteriaCodeParts = rubricInterface.getCriteriaCode().split("\\.");
        this.criteriaCode = criteriaCodeParts[0] + "." + criteriaCodeParts[1] + ".";
        if(role.equals(Role.CALIDAD.toString()))
            this.canGrade = Boolean.FALSE;
        else
            this.canGrade = rubricInterface.getGrade() == 1;
        if(this.state.equals(State.SIN_ASIGNAR.toString()) || this.state.equals(State.APROBACION_PENDIENTE.toString()))
            this.canGrade = false;
    }

    public String getDate(){
        return this.date;
    }

    public String getState(){
        return this.state;
    }
}
