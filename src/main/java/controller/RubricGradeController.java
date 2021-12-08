package controller;

import business.EvaluationService;
import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpointClasses.rubricSection.RubricSection;
import config.endpointClasses.rubricStudents.RubricStudent;
import config.endpointClasses.student.Student;
import config.enums.State;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
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

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricGradeController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final MsgReturn msgReturn = new MsgReturn();

    @Autowired
    private RubricService rubricService;

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/rubric_sections")
    public ResponseEntity<String> rubricSectionsController(@RequestHeader(value = "Authorization") String authorization,
                                                           @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC SECTIONS GET");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));

        String role = requestParam.get("role");
        String semester = requestParam.get("semester");
        String courseCode = requestParam.get("courseCode");
        String rubricCode = requestParam.get("rubricCode");

        if (role == null || role.isEmpty())
            return msgReturn.callError(404, "role empty");
        if (semester == null || semester.isEmpty())
            return msgReturn.callError(404, "semester empty");
        if (courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, "course code empty");
        if (rubricCode == null || rubricCode.isEmpty())
            return msgReturn.callError(404, "rubric code empty");

        System.out.println("Role " + role);
        System.out.println("Semester " + semester);
        System.out.println("CourseCode " + courseCode);
        System.out.println("RubricCode " + rubricCode);
        System.out.println("Username " + username);

        List<RubricSection> response = new ArrayList<>();
        response.add(rubricService.getRubricSection(rubricCode, semester, courseCode, username, role));
        System.out.println(gson.toJson(response));
        System.out.println("RETURN");
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @GetMapping("/students_by_sections")
    public ResponseEntity<String> studentsBySectionsController(@RequestHeader(value = "Authorization") String authorization,
                                                           @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST STUDENTS BY SECTION GET");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = requestParam.get("semester");
        String courseCode = requestParam.get("courseCode");
        String rubricCode = requestParam.get("rubricCode");
        String section = requestParam.get("section");

        if (semester == null || semester.isEmpty())
            return msgReturn.callError(404, "semester empty");
        if (courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, "course code empty");
        if (rubricCode == null || rubricCode.isEmpty())
            return msgReturn.callError(404, "rubric code empty");
        if (section == null || section.isEmpty())
            return msgReturn.callError(404, "section empty");

        System.out.println("Semester " + semester);
        System.out.println("CourseCode " + courseCode);
        System.out.println("RubricCode " + rubricCode);
        System.out.println("Section " + section);

        List<Student> response = rubricService.getStudents(rubricCode, semester, courseCode, section);
        System.out.println(gson.toJson(response));
        System.out.println("RETURN");
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @GetMapping("/rubric_grade")
    public ResponseEntity<String> rubricGradeControllerGet(@RequestHeader(value = "Authorization") String authorization,
                                                               @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC GRADE GET");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = requestParam.get("semester");
        String courseCode = requestParam.get("courseCode");
        String rubricCode = requestParam.get("rubricCode");
        String studentCode = requestParam.get("studentCode");

        if (semester == null || semester.isEmpty())
            return msgReturn.callError(404, "semester empty");
        if (courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, "course code empty");
        if (rubricCode == null || rubricCode.isEmpty())
            return msgReturn.callError(404, "rubric code empty");
        if (studentCode == null || studentCode.isEmpty())
            return msgReturn.callError(404, "student code empty");

        System.out.println("Semester " + semester);
        System.out.println("CourseCode " + courseCode);
        System.out.println("RubricCode " + rubricCode);
        System.out.println("StudentCode " + studentCode);

        RubricStudent response = rubricService.getRubricStudent(rubricCode, semester, courseCode, studentCode);
        System.out.println(gson.toJson(response));
        System.out.println("RETURN");
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @PostMapping("/rubric_grade")
    public ResponseEntity<String> rubricGradeControllerPost(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestBody RubricGradeBody rubricGradeBody)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC GRADE POST");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = rubricGradeBody.getSemester();
        String rubricCode = rubricGradeBody.getRubricCode();
        String studentGrade = rubricGradeBody.getStudentGrade();
        String competenceGrade = rubricGradeBody.getCompetenceGrade();
        Boolean finished = rubricGradeBody.getFinished();
        String courseCode = rubricGradeBody.getCourseCode();
        String studentCode = rubricGradeBody.getStudentCode();

        System.out.println("\nsemester: " + semester);
        System.out.println("\nrubricCode: " + rubricCode);
        System.out.println("\ncourseCode: " + courseCode);
        System.out.println("\nstudentCode: " + studentCode);
        System.out.println("\nonlySave: " + rubricGradeBody.getOnlySave());

        if(Boolean.FALSE.equals(rubricGradeBody.getOnlySave())){
            rubricService.updateRubricState(rubricCode, semester, State.Cumplidos);
        }
        evaluationService.updateEvaluation(rubricCode, semester, courseCode, studentCode, studentGrade, competenceGrade, finished);

        System.out.println("\nRETURN");
        return msgReturn.callMsg(200, "msg", "Evaluación guardada correctamente");
    }
}



