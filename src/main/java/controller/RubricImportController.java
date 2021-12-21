package controller;

import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpoint_classes.rubric_import.RubricImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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
                                                           @RequestParam Map<String, String> requestParam) {

        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
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

        List<RubricImport> response = rubricService.getRubricImport(username, semester, courseCode, rubricCode);
        response.sort(Comparator.comparing(RubricImport::getSemester));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}
