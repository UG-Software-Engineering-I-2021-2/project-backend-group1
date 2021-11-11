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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    private String level;

    public Rubric(String code, String state, String evaluation, String date, String week, String evidence, String activity) {
        this.code = code;
        this.state = state;
        this.evaluation = evaluation;
        this.date = date;
        this.week = week;
        this.evidence = evidence;
        this.activity = activity;
    }

    public String getState() {
        return state;
    }
    public String getDate() {
        return date;
    }
    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }
    public void setStudents(String students) {
        this.students = students;
    }
    public void setLevel(String level) {
        this.level = level;
    }
}

class RubricsCourseBody {
    private String semester;
    private String courseCode;
    private String role;
    public String getSemester() {return semester;}
    public String getCourseCode() {return courseCode;}
    public String getRole() {return role;}
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricsCourseController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final ErrorReturn errorReturn = new ErrorReturn();

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

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

        String semester = rubricsCourseBody.getSemester();
        String courseCode = rubricsCourseBody.getCourseCode();
        String role = rubricsCourseBody.getRole();

        System.out.println("\nSemester " + semester);
        System.out.println("\nCourseCode " + courseCode);
        System.out.println("\nRole " + role);

        if(semester == null || semester.isEmpty())
            return errorReturn.callError(404, "semester empty");
        if(courseCode == null || courseCode.isEmpty())
            return errorReturn.callError(404, "course code empty");
        if(role == null || role.isEmpty())
            return errorReturn.callError(404, "role empty");


        boolean coordinates = false;
        Curso curso = null;

        if(role.equals("Docente")){
            User user = userService.findByUsername(username);
            Set<Seccion> seccionesCoordina = user.getSeccionesCoordina(semester);
            for(Seccion seccion : seccionesCoordina){
                if(seccion.getCursoSeccion().getCodCurso().equals(courseCode)){
                    coordinates = true;
                    curso = seccion.getCursoSeccion();
                    break;
                }
            }
            if(curso == null){
                Set<Seccion> seccionesDicta = user.getSeccionesDicta(semester);
                for(Seccion seccion : seccionesDicta){
                    if(seccion.getCursoSeccion().getCodCurso().equals(courseCode)){
                        curso = seccion.getCursoSeccion();
                        break;
                    }
                }
            }

        }else{
            curso = courseService.findOneByCodCurso(courseCode);
        }

        List<Rubric> response = new ArrayList<>();

        Set<RubricaBase> rubricaBaseSet = curso.getRubricasBase();
        for(RubricaBase rubricaBase : rubricaBaseSet) {
            Rubrica rubrica = rubricaBase.getRubrica(semester);
            if(rubrica != null){
                Rubric rubric = new Rubric(
                        rubricaBase.getCodRubrica(),
                        rubrica.getEstado(),
                        rubricaBase.getEvaluacion(),
                        rubrica.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        "Semana " + rubricaBase.getSemana(),
                        rubricaBase.getEvidencia(),
                        rubrica.getActividad()
                );
                if(rubric.getState().equals("Sin asignar"))
                    rubric.setCanEdit(coordinates);
                else
                    rubric.setCanEdit(!rubric.getState().equals("Aprobacion pendiente"));
                rubric.setStudents("0");
                rubric.setLevel(String.valueOf(rubricaBase.getNivel()));

                response.add(rubric);
            }
        }



/*
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
                if(rubric.getState().equals("Sin asignar"))
                    rubric.setCanEdit(coordinates);
                else
                    rubric.setCanEdit(!rubric.getState().equals("Aprobacion pendiente"));
                rubric.setStudents("0");
                response.add(rubric);
            }
        }
*/
        response.sort((c1, c2) -> {
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(c1.getDate());
                date2 = new SimpleDateFormat("dd/MM/yyyy").parse(c2.getDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date1 != null;
            if(c1.getState().equals("Sin asignar") || c2.getState().equals("Sin asignar")){
                if(c1.getState().equals("Sin asignar") && c2.getState().equals("Sin asignar"))
                    return date1.compareTo(date2);
                else
                    return (c1.getState().equals("Sin asignar")) ? -1 : 1;
            }else if(c1.getState().equals("Fuera de fecha") || c2.getState().equals("Fuera de fecha")){
                if(c1.getState().equals("Fuera de fecha") && c2.getState().equals("Fuera de fecha"))
                    return date1.compareTo(date2);
                else
                    return (c1.getState().equals("Fuera de fecha")) ? -1 : 1;
            }else if(c1.getState().equals("Cumplidos") || c2.getState().equals("Cumplidos")){
                if(c1.getState().equals("Cumplidos") && c2.getState().equals("Cumplidos"))
                    return date1.compareTo(date2);
                else
                    return (c1.getState().equals("Cumplidos")) ? 1 : -1;
            }else return date1.compareTo(date2);
        });

        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}
