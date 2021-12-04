package controller;

import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpointClasses.rubricImport.RubricImport;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricImportController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final MsgReturn msgReturn = new MsgReturn();

    @Autowired
    private RubricService rubricService;

    @GetMapping("/rubric_import")
    public ResponseEntity<String> rubricImportController(@RequestHeader(value = "Authorization") String authorization,
                                                           @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC IMPORT GET");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));
        String semester = requestParam.get("semester");
        String courseCode = requestParam.get("courseCode");
        String rubricCode = requestParam.get("rubricCode");

        if (semester == null || semester.isEmpty())
            return msgReturn.callError(404, "semester empty");
        if (courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, "course code empty");
        if (rubricCode == null || rubricCode.isEmpty())
            return msgReturn.callError(404, "rubric code empty");

        System.out.println("username " + username);
        System.out.println("Semester " + semester);
        System.out.println("CourseCode " + courseCode);
        System.out.println("RubricCode " + rubricCode);

        List<RubricImport> response = rubricService.getRubricImport(username, semester, courseCode, rubricCode);
        response.sort((c1, c2) -> -c1.getSemester().compareTo(c2.getSemester()));
        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}
