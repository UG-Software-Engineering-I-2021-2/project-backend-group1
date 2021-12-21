package controller;

import business.UserService;
import data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;


@CrossOrigin(origins = "https://group1-ingsort1.herokuapp.com/*", allowedHeaders = "*")
@RestController
public class LoginController {
    private final TokenValidator tokenValidator = new TokenValidator();

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> loginVerifier(@RequestHeader(value="Authorization") String authorization) {

        Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if(payload == null){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "token not verified");
            return  ResponseEntity.status(404).body(errorMap);
        }
        HashMap<String, String> map = new HashMap<>();
        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));

        if(!userService.isUser(username)){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "user is not valid");
            return  ResponseEntity.status(404).body(errorMap);
        }

        User user = userService.findByUsername(username);

        map.put("name", payload.get("name").toString());
        map.put("role", user.getRol().toString());

        return ResponseEntity.status(200).body(map);
    }


}

