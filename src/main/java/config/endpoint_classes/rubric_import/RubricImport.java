package config.endpoint_classes.rubric_import;

public class RubricImport {
    String filter;
    String content;
    String semester;

    public RubricImport(RubricImportInterface rubricImportInterface) {
        this.filter = rubricImportInterface.getCodCourse() + " " + rubricImportInterface.getCourse() + " " + rubricImportInterface.getTitle();
        this.content = rubricImportInterface.getContent();
        this.semester = rubricImportInterface.getSemester();
    }

    public String getSemester() { return this.semester; }

    public String getContent() { return this.content; }
}
