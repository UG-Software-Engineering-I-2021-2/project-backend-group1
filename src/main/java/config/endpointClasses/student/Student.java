package config.endpointClasses.student;

public class Student {
    String studentCode;
    String studentName;

    public Student(StudentInterface studentInterface) {
        this.studentCode = studentInterface.getStudentCode();
        this.studentName = studentInterface.getStudentName();
    }
}
