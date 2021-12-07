package config.endpointClasses.rubricStudents;

public class RubricStudent {
    String studentCode;
    String studentGrade;
    String competenceGrade;
    Boolean finished;

    public RubricStudent(RubricStudentInterface rubricStudentInterface){
        this.studentCode = rubricStudentInterface.getStudentCode();
        this.studentGrade = rubricStudentInterface.getCalificacionAlumno();
        this.competenceGrade = rubricStudentInterface.getCalificacionCompetencia();
        this.finished = rubricStudentInterface.getEvaluacionTotal();
    }
}
