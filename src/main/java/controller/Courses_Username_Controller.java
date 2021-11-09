package controller;

import business.CourseService;
import business.UserService;
import data.entities.Seccion;
import data.entities.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import data.entities.Curso;
import data.entities.Seccion;
import data.entities.User;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

import javax.persistence.criteria.CriteriaBuilder;

class Course{
    private String name;
    private String code;
    Course(String _name, String _code){
        name = _name;
        code = _code;
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

class Courses_Username_Body {
    private String semestre;
    public String getSemestre() {return semestre;}
    public void setSemestre(String semestre) {this.semestre = semestre;}
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Courses_Username_Controller {
    private TokenValidator tokenValidator = new TokenValidator();

    private Gson gson = new Gson();

    @Autowired
    private UserService userService;

    @PostMapping(value = "/courses_username")
    public ResponseEntity<String> courses_username_controller(@RequestHeader(value="Authorization") String authorization, @RequestBody Courses_Username_Body courses_username_body) throws JSONException, GeneralSecurityException, IOException {
        System.out.println("TEST COURSE");

        Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);

        if(payload == null){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "token not verified");
            return  ResponseEntity.status(404).body(gson.toJson(errorMap));
        }

        ArrayList<Course> cursos = new ArrayList<>();

        String email = payload.getEmail();
        String semestre = courses_username_body.getSemestre();
        String username = email.substring(0,email.indexOf('@'));

        User user = userService.findByUsername(username);

        if(user.getRol().toString().equals("Docente")){
            Set<Seccion> set_secciones = user.getSeccionesDicta(semestre);
            for(Seccion seccion : set_secciones){
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

