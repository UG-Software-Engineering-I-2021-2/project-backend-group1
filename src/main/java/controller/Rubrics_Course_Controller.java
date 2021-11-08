package controller;

import business.CourseService;
import business.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import data.entities.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

class Rubrics_Course_Body {
    private String semestre;
    private String course_code;
    public String getSemestre() {return semestre;}
    public String getCourse_code() {return course_code;}
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Rubrics_Course_Controller {
    private TokenValidator tokenValidator = new TokenValidator();

    @Autowired
    private CourseService courseService;

    @PostMapping("/rubrics_course")
    public ResponseEntity<HashMap<String, String>> rubrics_course_controller(@RequestHeader(value="Authorization") String authorization, @RequestBody Rubrics_Course_Body rubrics_course_body) throws JSONException, GeneralSecurityException, IOException {
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null){
            //JSONObject errorJson = new JSONObject();
            //errorJson.put("error", "token not verified");
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "token not verified");
            return  ResponseEntity.status(404).body(errorMap);
        }

        HashMap<String, String> map = new HashMap<>();

        String course_code = rubrics_course_body.getCourse_code();
        String semester = rubrics_course_body.getSemestre();
        Curso course = courseService.findOneByCodCurso(course_code);
        Set<RubricaBase> set_rubrics_base = course.getRubricasBase();
        RubricaBase[] rubrics_base = set_rubrics_base.toArray(new RubricaBase[set_rubrics_base.size()]);
        int cont = 1;
        for (int i = 0; i < rubrics_base.length; i++) {
            Set<Rubrica> set_rubrics = rubrics_base[i].getRubricas();
            Rubrica[] rubrics = set_rubrics.toArray(new Rubrica[set_rubrics.size()]);
            for (int j = 0; j < rubrics.length; j++) {
                if (rubrics[j].getSemestre().equals(semester)) {
                    map.put(String.valueOf(cont),
                            rubrics_base[i].getCodRubrica() + "|" + rubrics[j].getEstado() + "|" + rubrics_base[i].getEvaluacion() + "|" +  rubrics[j].getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "|Semana " + String.valueOf(rubrics_base[i].getSemana()));
                    cont = cont + 1;
                }
            }
        }
        //map.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v));
        return ResponseEntity.status(200).body(map);
    }
}

