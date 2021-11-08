package controller;

import business.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import data.entities.Seccion;
import data.entities.User;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

class Courses_Username_Body {
    private String semestre;
    public String getSemestre() {return semestre;}
    public void setSemestre(String semestre) {this.semestre = semestre;}
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Courses_Username_Controller {
    private TokenValidator tokenValidator = new TokenValidator();

    @Autowired
    private UserService userService;

    @PostMapping("/courses_username")
    public ResponseEntity<JSONObject> courses_username_controller(@RequestHeader(value="Authorization") String authorization, @RequestBody Courses_Username_Body courses_username_body) throws JSONException, GeneralSecurityException, IOException {
        Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null){
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", "token not verified");
            return  ResponseEntity.status(404).body(errorJson);
        }

        JSONObject json = new JSONObject();

        String email = payload.getEmail();
        String semestre = courses_username_body.getSemestre();
        String username = email.substring(0,email.indexOf('@'));

        User user = userService.findByUsername(username);

        Set<Seccion> set_secciones = user.getSeccionesDocente();
        Seccion [] secciones = set_secciones.toArray(new Seccion[set_secciones.size()]);
        ArrayList<String> courses = new ArrayList<String>();
        ArrayList<String> courses_codes = new ArrayList<String>();

        for(int i = 0; i < secciones.length; i++){
            courses.add(secciones[i].getCursoSeccion().getNombre());
            courses_codes.add(secciones[i].getCursoSeccion().getCodCurso());
        }

        json.put("courses", courses);
        json.put("code_courses", courses_codes);

        return ResponseEntity.status(200).body(json);
    }
}

