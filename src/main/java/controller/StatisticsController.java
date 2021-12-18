package controller;

import business.CareerService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpointClasses.career.Career;
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
public class StatisticsController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final MsgReturn msgReturn = new MsgReturn();

    @Autowired
    private CareerService careerService;

    @GetMapping("/get_careers")
    public ResponseEntity<String> getCareersController(@RequestHeader(value = "Authorization") String authorization,
                                                        @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST GET CAREERS");
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");
        List<Career> response = careerService.getAll();
        System.out.println(gson.toJson(response));
        System.out.println("RETURN");
        return ResponseEntity.status(200).body(gson.toJson(response));

    }

    @GetMapping("/get_statistics1")
    public ResponseEntity<String> statistics1Controller(@RequestHeader(value = "Authorization") String authorization,
                                                         @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST STATISTICS 1 GET");
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));
        String role = requestParam.get("role");
        String semester = requestParam.get("2021-2");
        String careerId = requestParam.get("careerId");



        return ResponseEntity.status(200).body(gson.toJson(username));
    }
}