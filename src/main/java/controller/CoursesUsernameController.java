package controller;

import business.CourseService;
import config.enums.Role;
import config.enums.State;
import data.entities.Carrera;
import data.entities.Curso;
import com.google.gson.Gson;
import data.entities.Rubrica;
import data.entities.RubricaBase;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

class Course{
    private String name;
    private String code;
    private List<String> career;
    private String state;
    private List<Integer> nState;
    public Course(String name, String code, List<String> career, String state, List<Integer> nState) {
        this.name = name;
        this.code = code;
        this.career = career;
        this.state = state;
        this.nState = nState;
    }
    public String getName() {
        return name;
    }
}

class CoursesUsernameBody {
    private String semester;
    private String role;
    public String getSemester() { return semester; }
    public String getRole() { return role; }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CoursesUsernameController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final ErrorReturn errorReturn = new ErrorReturn();

    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/courses_username")
    public ResponseEntity<String> coursesUsernameController(@RequestHeader(value="Authorization") String authorization, @RequestBody CoursesUsernameBody coursesUsernameBody) throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST COURSE");

        Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null)
            return errorReturn.callError(404, "token not verified");

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));
        String semester = coursesUsernameBody.getSemester();
        String roleString = coursesUsernameBody.getRole();

        System.out.println("\nSemester " + semester);
        System.out.println("\nRole " + roleString);

        if(semester == null || semester.isEmpty())
            return errorReturn.callError(404, "semester empty");
        if(roleString == null || roleString.isEmpty())
            return errorReturn.callError(404, "role empty");
        Role role = Role.valueOf(roleString);

        List<Course> response = new ArrayList<>();
        List<Curso> cursos = courseService.findCursoBySemesterAndUsername(semester,
                username,
                role == Role.Docente);
        Set<String> codes = new HashSet<>();
        codes.add("EG0005");
        codes.add("EN0022");
        codes.add("EN0023");
        codes.add("EN0032");
        codes.add("EN0038");
        codes.add("EN1011");
        codes.add("EN2012");
        codes.add("EN3004");
        codes.add("EN4003");
        codes.add("EN5001");
        codes.add("EN5002");

        for(Curso curso : cursos){
            if(codes.contains(curso.getCodCurso())){
                System.out.println("\nCurso " + curso.getNombre() + "\n");
            }
            List<String> career = new ArrayList<>();
            List<Integer> nState = new ArrayList<>();
            nState.add(0);
            nState.add(0);
            nState.add(0);
            nState.add(0);
            nState.add(0);
            Integer stateMaxNumber = 4;
            Set<RubricaBase> rubricaBases = curso.getRubricasBase();
            for(RubricaBase rubricaBase: rubricaBases){
                Rubrica rubric = rubricaBase.getRubrica(semester);
                if(codes.contains(curso.getCodCurso())){
                    System.out.println("\nRúbrica Base " + rubricaBase.getCodRubrica() + "\n");
                }
                if(rubric != null){
                    String state_2 = rubric.getEstado();
                    if(codes.contains(curso.getCodCurso())){
                        System.out.println("\nRubric not null");
                        System.out.println("state: " + state_2 + "\n");
                    }
                    State state = State.valueOf(state_2);
                    if(codes.contains(curso.getCodCurso())){
                        System.out.println("\nAfter if\n");
                    }
                    switch (state){
                        case SinAsignar:
                            nState.set(0, nState.get(0) + 1);
                            if(stateMaxNumber > 0)
                                stateMaxNumber = 0;
                            break;
                        case AprobacionPendiente:
                            nState.set(1, nState.get(1) + 1);
                            if(stateMaxNumber > 1)
                                stateMaxNumber = 1;
                            break;
                        case DisponibleParaCalificar:
                            nState.set(2, nState.get(2) + 1);
                            if(stateMaxNumber > 2)
                                stateMaxNumber = 2;
                            break;
                        case FueraDeFecha:
                            nState.set(3, nState.get(3) + 1);
                            if(stateMaxNumber > 3)
                                stateMaxNumber = 3;
                            break;
                        case Cumplidos:
                            nState.set(4, nState.get(4) + 1);
                            break;
                    }
                }
            }
            if(codes.contains(curso.getCodCurso())){
                System.out.println(gson.toJson(nState));
            }
            State state = State.SinAsignar;
            switch (stateMaxNumber) {
                case 0:
                    state = State.SinAsignar;
                    break;
                case 1:
                    state = State.AprobacionPendiente;
                    break;
                case 2:
                    state = State.DisponibleParaCalificar;
                    break;
                case 3:
                    state = State.FueraDeFecha;
                    break;
                case 4:
                    state = State.Cumplidos;
                    break;
            }
            Set<Carrera> carreras = curso.getCarrerasPertenece();
            for(Carrera carrera : carreras)
                career.add(carrera.getNombre());
            response.add(new Course(curso.getNombre(), curso.getCodCurso(),career, state.toString(), nState));
        }

        response.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}