package controller;

import business.CourseService;
import business.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import data.entities.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

class Rubric{
    private String code;
    private String state;
    private String evaluation;
    private String date;
    private String week;
    private String evidence;
    private String activity;
    private String canEdit;
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

    public String getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(String canEdit) {
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
    public String getCoursecode() {return courseCode;}
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricsCourseController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @PostMapping("/rubrics_course")
    public ResponseEntity<String> rubricsCourseController(@RequestHeader(value="Authorization") String authorization, @RequestBody RubricsCourseBody rubricsCourseBody) throws JSONException, GeneralSecurityException, IOException {
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);

        if(payload == null){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "token not verified");
            return  ResponseEntity.status(404).body(gson.toJson(errorMap));
        }

        ArrayList<Rubric> response = new ArrayList<>();

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));

        if(!userService.isUser(username)){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "user is not valid");
            return  ResponseEntity.status(404).body(gson.toJson(errorMap));
        }

        User user = userService.findByUsername(username);
        Set<Seccion> seccionesCoordina = user.getSeccionesCoordina(rubricsCourseBody.getSemester());
        boolean coordina = false;
        for(Seccion seccion : seccionesCoordina){
            if(seccion.getCursoSeccion().getCodCurso().equals(rubricsCourseBody.getCoursecode())){
                coordina = true;
                break;
            }
        }

        Curso course = courseService.findOneByCodCurso(rubricsCourseBody.getCoursecode());
        Set<RubricaBase> setRubricsBase = course.getRubricasBase();
        for(RubricaBase rubricaBase : setRubricsBase){
            Set<Rubrica> setRubrics = rubricaBase.getRubricas(rubricsCourseBody.getSemester());
            for(Rubrica rubrica : setRubrics){
                Rubric rubric = new Rubric(
                        rubricaBase.getCodRubrica(),
                        rubrica.getEstado(),
                        rubricaBase.getEvaluacion(),
                        rubrica.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        "Semana " + rubricaBase.getSemana(),
                        rubricaBase.getEvidencia(),
                        rubricaBase.getActividadBase()
                );

                rubric.setCanEdit((coordina) ? "1" : "0");
                rubric.setStudents("0");
                response.add(rubric);
            }
        }
        //map.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v));

        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}

