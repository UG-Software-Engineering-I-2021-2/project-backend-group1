package config.endpoint_classes.student;

public class Student {
    String studentCode;
    String studentName;

    public Student(StudentInterface studentInterface) {
        this.studentCode = studentInterface.getStudentCode();
        this.studentName = studentInterface.getStudentName();
    }
    public String getName(){
        return this.studentName;
    }
}
