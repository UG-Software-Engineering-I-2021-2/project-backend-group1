package controller;

import business.UserService;
import data.entities.Seccion;
import data.entities.User;
import com.google.gson.Gson;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

class Course{
    private String name;
    private String code;

    public Course(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() { return name; }

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
    public String getSemester() {return semester;}
    public void setSemestre(String semester) {this.semester = semester;}
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CoursesUsernameController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    @Autowired
    private UserService userService;

    @PostMapping(value = "/courses_username")
    public ResponseEntity<String> coursesUsernameController(@RequestHeader(value="Authorization") String authorization, @RequestBody CoursesUsernameBody coursesUsernameBody) throws JSONException, GeneralSecurityException, IOException {
        System.out.println("TEST COURSE");

        Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);

        if(payload == null){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "token not verified");
            return  ResponseEntity.status(404).body(gson.toJson(errorMap));
        }

        ArrayList<Course> cursos = new ArrayList<>();

        String email = payload.getEmail();
        String semester = coursesUsernameBody.getSemester();
        String username = email.substring(0,email.indexOf('@'));

        User user = userService.findByUsername(username);

        if(user.getRol().toString().equals("Docente")){
            Set<Seccion> setSecciones = user.getSeccionesDicta(semester);
            for(Seccion seccion : setSecciones){
                Course curso = new Course(seccion.getCursoSeccion().getNombre(), seccion.getCursoSeccion().getCodCurso());
                cursos.add(curso);
            }
        }else if(user.getRol().toString().equals("Calidad")){

            //Set<Seccion> set_secciones =
        }

        System.out.println(gson.toJson(cursos));
        return ResponseEntity.status(200).body(gson.toJson(cursos));
    }
}

