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

class Courses_Username {
    private String email;
    private String semestre;

    public String getEmail() {
        return email;
    }
    public String getSemestre() {
        return semestre;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Courses_Username_Controller {
    private TokenValidator tokenValidator = new TokenValidator();

    @Autowired
    private UserService userService;

    @PostMapping("/courses_username")
    public ResponseEntity<JSONObject> courses_username_controller(@RequestHeader(value="Authorization") String authorization, @RequestBody Courses_Username courses_username) throws JSONException, GeneralSecurityException, IOException {
        JSONObject json = new JSONObject();

        String email = courses_username.getEmail();
        String semestre = courses_username.getSemestre();
        String username = email.substring(0,email.indexOf('@'));

        Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null){
            JSONObject errorJson = new JSONObject();
            errorJson.put("error", "token not verified");
            return  ResponseEntity.status(404).body(errorJson);
        }

        User user = userService.findByUsername(username);

        Set<Seccion> set_secciones = user.getSeccionesDocente();
        Seccion [] secciones = set_secciones.toArray(new Seccion[set_secciones.size()]);
        ArrayList<String> courses = new ArrayList<String>();

        for(int i = 0; i < secciones.length; i++){
            courses.add(secciones[i].getCursoSeccion().getNombre());
            /*System.out.println("Código curso: " + secciones[i].getCursoSeccion().getCodCurso());
            System.out.println("Curso: " + secciones[i].getCursoSeccion().getNombre());
            System.out.println("Código: " + secciones[i].getSeccionPK());*/
        }
        json.put("email", courses_username.getEmail());
        json.put("rol", user.getRol().toString());
        json.put("cursos", courses);
        /*map.put("name", payload.get("name").toString());
        map.put("email", login.getEmail());
        map.put("rol", user.getRol().toString());
        */
        return ResponseEntity.status(200).body(json);
    }


}

