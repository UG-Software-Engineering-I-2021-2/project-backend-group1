package config.endpointClasses.rubricStudents;

public class RubricStudents {
    String studentCode;
    String student;
    String studentGrade;
    String competenceGrade;
    Boolean finished;

    public RubricStudents(RubricStudentsInterface rubricStudentsInterface){
        this.studentCode = rubricStudentsInterface.getCodAlumno();
        this.student = rubricStudentsInterface.getAlumno();
        this.studentGrade = rubricStudentsInterface.getCalificacionAlumno();
        this.competenceGrade = rubricStudentsInterface.getCalificacionCompetencia();
        this.finished = rubricStudentsInterface.getEvaluacionTotal();
    }
}
