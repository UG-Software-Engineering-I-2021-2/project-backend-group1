package controller;

import business.CourseService;
import business.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import data.entities.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

class Rubric{
    private String codRubrica;
    private String estado;
    private String evaluacion;
    private String fecha;
    private String semana;
    private String evidencia;
    private String actividad;

    public Rubric(String codRubrica, String estado, String evaluacion, String fecha, String semana, String evidencia, String actividad) {
        this.codRubrica = codRubrica;
        this.estado = estado;
        this.evaluacion = evaluacion;
        this.fecha = fecha;
        this.semana = semana;
        this.evidencia = evidencia;
        this.actividad = actividad;
    }

    public String getCodRubrica() {
        return codRubrica;
    }

    public void setCodRubrica(String codRubrica) {
        this.codRubrica = codRubrica;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(String evaluacion) {
        this.evaluacion = evaluacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSemana() {
        return semana;
    }

    public void setSemana(String semana) {
        this.semana = semana;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }
}

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

    private Gson gson = new Gson();

    @Autowired
    private CourseService courseService;

    @PostMapping("/rubrics_course")
    public ResponseEntity<String> rubrics_course_controller(@RequestHeader(value="Authorization") String authorization, @RequestBody Rubrics_Course_Body rubrics_course_body) throws JSONException, GeneralSecurityException, IOException {
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);

        if(payload == null){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "token not verified");
            return  ResponseEntity.status(404).body(gson.toJson(errorMap));
        }

        ArrayList<Rubric> response = new ArrayList<>();

        String course_code = rubrics_course_body.getCourse_code();
        String semester = rubrics_course_body.getSemestre();
        Curso course = courseService.findOneByCodCurso(course_code);
        Set<RubricaBase> set_rubrics_base = course.getRubricasBase();
        RubricaBase[] rubrics_base = set_rubrics_base.toArray(new RubricaBase[set_rubrics_base.size()]);
        //int cont = 1;
        for (int i = 0; i < rubrics_base.length; i++) {
            Set<Rubrica> set_rubrics = rubrics_base[i].getRubricas();
            Rubrica[] rubrics = set_rubrics.toArray(new Rubrica[set_rubrics.size()]);
            for (int j = 0; j < rubrics.length; j++) {
                if (rubrics[j].getSemestre().equals(semester)) {
                    Rubric rubric = new Rubric(
                            rubrics_base[i].getCodRubrica(),
                            rubrics[j].getEstado(),
                            rubrics_base[i].getEvaluacion(),
                            rubrics[j].getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            "Semana " + String.valueOf(rubrics_base[i].getSemana()),
                            rubrics_base[i].getEvidencia(),
                            rubrics_base[i].getActividadBase()
                    );
                    response.add(rubric);
                }
            }
        }
        //map.forEach((k,v) -> System.out.println("Key: " + k + ": Value: " + v));

        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}

