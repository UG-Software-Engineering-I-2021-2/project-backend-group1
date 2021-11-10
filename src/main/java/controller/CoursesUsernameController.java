package controller;

import business.CourseService;
import data.entities.Curso;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

class Course{
    private String name;
    private String code;

    public Course(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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
        String role = coursesUsernameBody.getRole();

        if(semester == null || semester.isEmpty())
            return errorReturn.callError(404, "semester empty");
        if(role == null || role.isEmpty())
            return errorReturn.callError(404, "role empty");

        List<Course> response = new ArrayList<>();
        List<Curso> cursos = courseService.findCursoBySemesterAndUsername(semester,
                username,
                role.equals("Docente"));
        for(Curso curso : cursos)
            response.add(new Course(curso.getNombre(), curso.getCodCurso()));

        response.sort((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));

        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}

