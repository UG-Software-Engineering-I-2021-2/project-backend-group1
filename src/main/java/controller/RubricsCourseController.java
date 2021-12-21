package controller;

import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpoint_classes.rubric.Rubric;
import config.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final MsgReturn msgReturn = new MsgReturn();

    @Autowired
    private RubricService rubricService;

    private Boolean evaluationOr(Rubric c1, Rubric c2, State state){
        return c1.getState().equals(state.toString()) || c2.getState().equals(state.toString());
    }
    private Boolean evaluationAnd(Rubric c1, Rubric c2, State state){
        return c1.getState().equals(state.toString()) && c2.getState().equals(state.toString());
    }
    private Integer equalsState(Rubric c1, State state){
        return (c1.getState().equals(state.toString())) ? -1 : 1;
    }
    private List<Rubric> sortListOfRubrics(List<Rubric> list){
        list.sort((c1, c2) -> {
            Date date1 = null;
            Date date2 = null;
            try {
                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(c1.getDate());
                date2 = new SimpleDateFormat("dd/MM/yyyy").parse(c2.getDate());
            } catch (ParseException e) {
                //Do nothing
            }
            assert date1 != null;
            if(evaluationOr(c1, c2, State.SinAsignar)){
                return evaluationAnd(c1, c2, State.SinAsignar) ? date1.compareTo(date2) : equalsState(c1, State.SinAsignar);
            }else if(evaluationOr(c1, c2, State.FueraDeFecha)){
                return evaluationAnd(c1, c2, State.FueraDeFecha) ? date1.compareTo(date2) : equalsState(c1, State.FueraDeFecha);
            }else if(evaluationOr(c1, c2, State.Cumplidos)){
                return evaluationAnd(c1, c2, State.Cumplidos) ? date1.compareTo(date2) : equalsState(c1, State.Cumplidos);
            }
            return date1.compareTo(date2);
        });
        return list;
    }

    @PostMapping("/rubrics_course")
    public ResponseEntity<String> rubricsCourseController(@RequestHeader(value="Authorization") String authorization, @RequestBody RubricsCourseBody rubricsCourseBody) {
        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if(payload == null)
            return msgReturn.callError(404, "token not verified");

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));

        String semester = rubricsCourseBody.getSemester();
        String courseCode = rubricsCourseBody.getCourseCode();
        String role = rubricsCourseBody.getRole();

        if(semester == null || semester.isEmpty())
            return msgReturn.callError(404, "semester empty");
        if(courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, "course code empty");
        if(role == null || role.isEmpty())
            return msgReturn.callError(404, "role empty");

        List<Rubric> response = rubricService.getRubric(semester, courseCode, username, role);

        sortListOfRubrics(response);

        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}
