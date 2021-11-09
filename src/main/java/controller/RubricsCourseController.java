package controller;

import business.CourseService;
import business.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import data.entities.*;
import data.repositories.RubricBaseRepository;
import data.repositories.RubricRepository;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

class Rubric{
    private String code;
    private String state;
    private String evaluation;
    private String date;
    private String week;
    private String evidence;
    private String activity;
    private Boolean canEdit;
    private String students;

    public Rubric(String code, String state, String evaluation, String date, String week, String evidence, String activity) {
        this.code = code;
        this.state = state;
        this.evaluation = evaluation;
        this.date = date;
        this.week = week;
        this.evidence = evidence;
        this.activity = activity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getEvidence() {
        return evidence;
    }

    public void setEvidence(String evidence) {
        this.evidence = evidence;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }
}

class RubricsCourseBody {
    private String semester;
    private String courseCode;
    public String getSemester() {return semester;}
    public String getCourseCode() {return courseCode;}
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricsCourseController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final ErrorReturn errorReturn = new ErrorReturn();

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private RubricBaseRepository rubricBaseRepository;

    @Autowired
    private RubricRepository rubricRepository;

    @PostMapping("/rubrics_course")
    public ResponseEntity<String> rubricsCourseController(@RequestHeader(value="Authorization") String authorization, @RequestBody RubricsCourseBody rubricsCourseBody) throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC");
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null)
            return errorReturn.callError(404, "token not verified");

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));

        if(!userService.isUser(username))
            return errorReturn.callError(404, "user is not valid");

        String semester = rubricsCourseBody.getSemester();
        String courseCode = rubricsCourseBody.getCourseCode();

        if(semester == null || semester.isEmpty())
            return errorReturn.callError(404, "semester empty");
        if(courseCode == null || courseCode.isEmpty())
            return errorReturn.callError(404, "course code empty");

        List<Curso> listCursoCoordina = courseService.findCursoCoordinedByUsername(semester, username);

        boolean coordinates = false;
        for(Curso course : listCursoCoordina){
            if(course.getCodCurso().equals(courseCode)){
                coordinates = true;
                break;
            }
        }

        List<Rubric> response = new ArrayList<>();

        List<RubricaBase> listRubricaBase = rubricBaseRepository.getRubricBaseByCodCourse(courseCode);
        for(RubricaBase rubricaBase : listRubricaBase){
            List<Rubrica> listRubrica = rubricRepository.getRubricByCodRubricBaseAndSemester(rubricaBase.getCodRubrica(), semester);
            for(Rubrica rubrica : listRubrica){
                Rubric rubric = new Rubric(
                        rubricaBase.getCodRubrica(),
                        rubrica.getEstado(),
                        rubricaBase.getEvaluacion(),
                        rubrica.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        "Semana " + rubricaBase.getSemana(),
                        rubricaBase.getEvidencia(),
                        rubricaBase.getActividadBase()
                );

                rubric.setCanEdit(coordinates);
                rubric.setStudents("0");
                response.add(rubric);
            }
        }

        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}

