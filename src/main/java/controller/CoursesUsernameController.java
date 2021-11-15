package controller;

import business.CourseService;
import config.endpointClasses.courseEndpoint.Course;
import config.enums.Role;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

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

        List<Course> response;
        if(role.equals(Role.Docente))
            response = courseService.docenteCourseEndpoint(semester, username);
        else
            response = courseService.calidadCourseEndpoint(semester);


        System.out.println(gson.toJson(response));

        System.out.println("RETURN");
        return ResponseEntity.status(200).body(gson.toJson(response));

        /*

        List<Course> response = new ArrayList<>();

        List<Curso> cursos = courseService.findCursoBySemesterAndUsername(semester,
                username,
                role == Role.Docente);

        System.out.println("Start for course");

        for(Curso curso : cursos){
            System.out.println("Course " + curso.getNombre());
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
                System.out.println("\tIn Course " + curso.getNombre() + " rubricBase " + rubricaBase.getCodRubrica());
                Rubrica rubric = rubricaBase.getRubrica(semester);
                if(rubric != null){
                    State state = rubric.getEstado();
                    System.out.println("\tRubric not null in state " + rubric.getEstado().toString());
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
            System.out.println("Course " + curso.getNombre() + " before carreras");
            Set<Carrera> carreras = curso.getCarrerasPertenece();
            for(Carrera carrera : carreras)
                career.add(carrera.getNombre());
            System.out.println("Course " + curso.getNombre() + " before add to response");
            response.add(new Course(curso.getNombre(), curso.getCodCurso(),career, state.toString(), nState));
            System.out.println("Course " + curso.getNombre() + " after add to response");
        }
        System.out.println("Before order");
        response.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        System.out.println("Finish order");
        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
        */
    }
}