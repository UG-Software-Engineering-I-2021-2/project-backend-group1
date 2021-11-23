package controller;

import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpointClasses.rubric.RubricUpdate;
import config.endpointClasses.rubricCreation.RubricCreation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class RubricCreationBody {
    private List<HashMap<String, HashMap<String, String>>> content;
    private String activity;
    private String rubricCode;
    private String semester;
    private String title;
    private boolean onlySave;

    public List<HashMap<String, HashMap<String, String>>> getContent() {
        return content;
    }

    public String getActivity() {
        return activity;
    }

    public String getRubricCode() {
        return rubricCode;
    }

    public String getSemester() {
        return semester;
    }

    public String getTitle() {
        return title;
    }

    public boolean isOnlySave() {
        return onlySave;
    }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricCreationController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final MsgReturn msgReturn = new MsgReturn();

    @Autowired
    private RubricService rubricService;

    @GetMapping("/rubric_creation")
    public ResponseEntity<String> rubricCreationController(@RequestHeader(value="Authorization") String authorization, @RequestParam Map<String, String> requestParam) throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC CREATION GET");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = requestParam.get("semester");
        String courseCode = requestParam.get("courseCode");
        String rubricCode = requestParam.get("rubricCode");


        if(semester == null || semester.isEmpty())
            return msgReturn.callError(404, "semester empty");
        if(courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, "course code empty");
        if(rubricCode == null || rubricCode.isEmpty())
            return msgReturn.callError(404, "rubric code empty");

        System.out.println("Semester " + semester);
        System.out.println("CourseCode " + courseCode);
        System.out.println("RubricCode " + rubricCode);

        List<RubricCreation> response = new ArrayList<>();
        response.add(rubricService.getRubricCreation(rubricCode, semester, courseCode));
        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @PostMapping("/rubric_creation")
    public ResponseEntity<String> rubricCreationControllerPost(@RequestHeader(value="Authorization") String authorization, @RequestBody RubricCreationBody rubricCreationBody) throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC CREATION POST");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = rubricCreationBody.getSemester();
        String rubricCode = rubricCreationBody.getRubricCode();
        String activity = rubricCreationBody.getActivity();
        String title = rubricCreationBody.getTitle();
        List<HashMap<String, HashMap<String, String>>> content = rubricCreationBody.getContent();

        System.out.println("\nsemester: " + semester);
        System.out.println("\nrubricCode: " + rubricCode);
        System.out.println("\nactivity: " + activity);
        System.out.println("\ncontent: " + gson.toJson(content));

        rubricService.updateRubric(rubricCode, semester, new RubricUpdate((short) content.size(), gson.toJson(content), activity, title));
        System.out.println("\nRETURN");
        if(rubricCreationBody.isOnlySave()){
            return msgReturn.callMsg(200, "msg", "Rúbrica guardada correctamente");
        }else{
            //TODO: Función para enviar correo
            return msgReturn.callMsg(200, "msg", "Solicitud enviada correctamente");
        }
    }
}
