package controller;

import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpointClasses.rubricCreation.RubricCreation;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricCreationController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final ErrorReturn errorReturn = new ErrorReturn();

    @Autowired
    private RubricService rubricService;

    @GetMapping("/rubric_creation")
    public ResponseEntity<String> rubricCreationController(@RequestHeader(value="Authorization") String authorization, @RequestParam Map<String, String> requestParam) throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC CREATION");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null)
            return errorReturn.callError(404, "token not verified");

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));

        String semester = requestParam.get("semester");
        String courseCode = requestParam.get("courseCode");
        String rubricCode = requestParam.get("rubricCode");


        if(semester == null || semester.isEmpty())
            return errorReturn.callError(404, "semester empty");
        if(courseCode == null || courseCode.isEmpty())
            return errorReturn.callError(404, "course code empty");
        if(rubricCode == null || rubricCode.isEmpty())
            return errorReturn.callError(404, "rubric code empty");

        System.out.println("Semester " + semester);
        System.out.println("CourseCode " + courseCode);
        System.out.println("RubricCode " + rubricCode);

        List<RubricCreation> response = new ArrayList<>();
        response.add(rubricService.getRubricCreation(rubricCode, semester, courseCode));
        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}
