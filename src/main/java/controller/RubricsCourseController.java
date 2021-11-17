package controller;

import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpointClasses.rubric.Rubric;
import config.enums.State;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private RubricService rubricService;


    @PostMapping("/rubrics_course")
    public ResponseEntity<String> rubricsCourseController(@RequestHeader(value="Authorization") String authorization, @RequestBody RubricsCourseBody rubricsCourseBody) throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC");
        System.out.println(authorization);
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
        System.out.println("\nUsername " + username);

        if(semester == null || semester.isEmpty())
            return errorReturn.callError(404, "semester empty");
        if(courseCode == null || courseCode.isEmpty())
            return errorReturn.callError(404, "course code empty");
        if(role == null || role.isEmpty())
            return errorReturn.callError(404, "role empty");

        List<Rubric> response = rubricService.getRubric(semester, courseCode, username, role);

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
            if(c1.getState().equals(State.SinAsignar.toString()) || c2.getState().equals(State.SinAsignar.toString())){
                if(c1.getState().equals(State.SinAsignar.toString()) && c2.getState().equals(State.SinAsignar.toString()))
                    return date1.compareTo(date2);
                else
                    return (c1.getState().equals(State.SinAsignar.toString())) ? -1 : 1;
            }else if(c1.getState().equals(State.FueraDeFecha.toString()) || c2.getState().equals(State.FueraDeFecha.toString())){
                if(c1.getState().equals(State.FueraDeFecha.toString()) && c2.getState().equals(State.FueraDeFecha.toString()))
                    return date1.compareTo(date2);
                else
                    return (c1.getState().equals(State.FueraDeFecha.toString())) ? -1 : 1;
            }else if(c1.getState().equals(State.Cumplidos.toString()) || c2.getState().equals(State.Cumplidos.toString())){
                if(c1.getState().equals(State.Cumplidos.toString()) && c2.getState().equals(State.Cumplidos.toString()))
                    return date1.compareTo(date2);
                else
                    return (c1.getState().equals(State.Cumplidos.toString())) ? 1 : -1;
            }else return date1.compareTo(date2);
        });

        System.out.println(gson.toJson(response));
        System.out.println("RETURN");
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}
