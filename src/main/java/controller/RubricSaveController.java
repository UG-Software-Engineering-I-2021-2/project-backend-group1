package controller;

import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

class RubricSaveBody {
    // TODO: add remaining body elements
    private boolean onlySave;

    public boolean isOnlySave() {
        return onlySave;
    }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricSaveController {
    private final TokenValidator tokenValidator = new TokenValidator();
    private final ErrorReturn errorReturn = new ErrorReturn();

    @Autowired
    private RubricService rubricService;

    @PostMapping("/rubric_save")
    public ResponseEntity<String> rubricSaveController(@RequestHeader(value="Authorization") String authorization, @RequestBody RubricSaveBody rubricSaveBody) {
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null)
            return errorReturn.callError(404, "token not verified");

        // TODO: save rubric

        String msg;
        if (!rubricSaveBody.isOnlySave()) {
            // TODO: request approval
            msg = "Solicitud enviada correctamente.";
        } else {
            msg = "Rúbrica guardada correctamente.";
        }

        return ResponseEntity.status(200).body(msg);
    }
}
