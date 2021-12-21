package controller;

import business.EvaluationService;
import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpoint_classes.rubric_section.RubricSection;
import config.endpoint_classes.rubric_students.RubricStudent;
import config.endpoint_classes.student.Student;
import config.endpoint_classes.student.StudentInterface;
import config.enums.State;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

class RubricGradeBody {
    private String studentGrade;
    private String competenceGrade;
    private Boolean finished;
    private String rubricCode;
    private String semester;
    private String courseCode;
    private Boolean onlySave;
    private String studentCode;

    public String getStudentGrade() {
        return studentGrade;
    }

    public String getCompetenceGrade() {
        return competenceGrade;
    }

    public Boolean getFinished() {
        return finished;
    }

    public String getRubricCode() {
        return rubricCode;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public Boolean getOnlySave() { return onlySave; }

    public String getStudentCode() { return studentCode; }
}

class StudentBySectionResponse{
    List<Student> studentList;
    Integer studentTotal;
    Integer studentFinished;
    public StudentBySectionResponse(List<Student> studentList, Integer studentTotal, Integer studentFinished){
        this.studentList = studentList;
        this.studentTotal = studentTotal;
        this.studentFinished = studentFinished;
    }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricGradeController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final MsgReturn msgReturn = new MsgReturn();

    private static final String TOKEN_NOT_VERIFIED = "token not verified";
    private static final String SEMESTER_STR = "semester";
    private static final String COURSE_CODE_STR = "courseCode";
    private static final String RUBRIC_CODE_STR = "rubricCode";
    private static final String SEMESTER_EMPTY_STR = "semester empty";
    private static final String COURSE_CODE_EMPTY_STR = "course code empty";
    private static final String RUBRIC_CODE_EMPTY_STR = "rubric code empty";

    @Autowired
    private RubricService rubricService;

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/rubric_sections")
    public ResponseEntity<String> rubricSectionsController(@RequestHeader(value = "Authorization") String authorization,
                                                           @RequestParam Map<String, String> requestParam) {
        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, TOKEN_NOT_VERIFIED);

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));

        String role = requestParam.get("role");
        String semester = requestParam.get(SEMESTER_STR);
        String courseCode = requestParam.get(COURSE_CODE_STR);
        String rubricCode = requestParam.get(RUBRIC_CODE_STR);

        if (role == null || role.isEmpty())
            return msgReturn.callError(404, "role empty");
        if (semester == null || semester.isEmpty())
            return msgReturn.callError(404, SEMESTER_EMPTY_STR);
        if (courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, COURSE_CODE_EMPTY_STR);
        if (rubricCode == null || rubricCode.isEmpty())
            return msgReturn.callError(404, RUBRIC_CODE_EMPTY_STR);

        List<RubricSection> response = new ArrayList<>();
        response.add(rubricService.getRubricSection(rubricCode, semester, courseCode, username, role));

        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @GetMapping("/students_by_sections")
    public ResponseEntity<String> studentsBySectionsController(@RequestHeader(value = "Authorization") String authorization,
                                                           @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, TOKEN_NOT_VERIFIED);

        String semester = requestParam.get(SEMESTER_STR);
        String courseCode = requestParam.get(COURSE_CODE_STR);
        String rubricCode = requestParam.get(RUBRIC_CODE_STR);
        String section = requestParam.get("section");

        if (semester == null || semester.isEmpty())
            return msgReturn.callError(404, SEMESTER_EMPTY_STR);
        if (courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, COURSE_CODE_EMPTY_STR);
        if (rubricCode == null || rubricCode.isEmpty())
            return msgReturn.callError(404, RUBRIC_CODE_EMPTY_STR);
        if (section == null || section.isEmpty())
            return msgReturn.callError(404, "section empty");

        List<StudentInterface> studentInterfaceList = rubricService.getStudents(rubricCode, semester, courseCode, section);

        List<Student> studentList = new ArrayList<>();
        Integer studentTotal = 0;
        Integer studentFinished = 0;
        for(StudentInterface studentInterface : studentInterfaceList){
            studentList.add(new Student(studentInterface));
            studentTotal += 1;
            if(studentInterface.getTotalEvaluation())
                studentFinished += 1;
        }
        studentList.sort(Comparator.comparing(Student::getName));
        StudentBySectionResponse studentBySectionResponse = new StudentBySectionResponse(studentList, studentTotal, studentFinished);
        List<StudentBySectionResponse> response = new ArrayList<>();
        response.add(studentBySectionResponse);
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @GetMapping("/rubric_grade")
    public ResponseEntity<String> rubricGradeControllerGet(@RequestHeader(value = "Authorization") String authorization,
                                                               @RequestParam Map<String, String> requestParam) {
        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, TOKEN_NOT_VERIFIED);

        String semester = requestParam.get(SEMESTER_STR);
        String courseCode = requestParam.get(COURSE_CODE_STR);
        String rubricCode = requestParam.get(RUBRIC_CODE_STR);
        String studentCode = requestParam.get("studentCode");

        if (semester == null || semester.isEmpty())
            return msgReturn.callError(404, SEMESTER_EMPTY_STR);
        if (courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, COURSE_CODE_EMPTY_STR);
        if (rubricCode == null || rubricCode.isEmpty())
            return msgReturn.callError(404, RUBRIC_CODE_EMPTY_STR);
        if (studentCode == null || studentCode.isEmpty())
            return msgReturn.callError(404, "student code empty");

        RubricStudent response = rubricService.getRubricStudent(rubricCode, semester, courseCode, studentCode);

        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @PostMapping("/rubric_grade")
    public ResponseEntity<String> rubricGradeControllerPost(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestBody RubricGradeBody rubricGradeBody) {

        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, TOKEN_NOT_VERIFIED);

        String semester = rubricGradeBody.getSemester();
        String rubricCode = rubricGradeBody.getRubricCode();
        String studentGrade = rubricGradeBody.getStudentGrade();
        String competenceGrade = rubricGradeBody.getCompetenceGrade();
        Boolean finished = rubricGradeBody.getFinished();
        String courseCode = rubricGradeBody.getCourseCode();
        String studentCode = rubricGradeBody.getStudentCode();

        if(Boolean.FALSE.equals(rubricGradeBody.getOnlySave())){
            rubricService.updateRubricState(rubricCode, semester, State.Cumplidos);
        }
        evaluationService.updateEvaluation(rubricCode, semester, courseCode, studentCode, studentGrade, competenceGrade, finished);

        return msgReturn.callMsg(200, "msg", "Evaluación guardada correctamente");
    }

    @PostMapping("/rubric_finish")
    public ResponseEntity<String> rubricFinishControllerPost(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestBody RubricGradeBody rubricGradeBody) {

        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, TOKEN_NOT_VERIFIED);

        String semester = rubricGradeBody.getSemester();
        String rubricCode = rubricGradeBody.getRubricCode();

        rubricService.updateRubricState(rubricCode, semester, State.Cumplidos);

        return msgReturn.callMsg(200, "msg", "Rúbrica finalizada correctamente");
    }
}



