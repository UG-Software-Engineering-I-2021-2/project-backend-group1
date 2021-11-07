package controller;

import business.UserService;
import data.entities.User;
import org.checkerframework.checker.units.qual.A;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

class Login {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LoginController {
    private TokenValidator tokenValidator = new TokenValidator();

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> login_verifier(@RequestHeader(value="Authorization") String authorization, @RequestBody Login login) throws JSONException, GeneralSecurityException, IOException {
        HashMap<String, String> map = new HashMap<>();
        String email = login.getEmail();
        String username = email.substring(0,email.indexOf('@'));

        if(!userService.isUser(username)){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "user is not valid");
            return  ResponseEntity.status(404).body(errorMap);
        }
        Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if(payload == null){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "token not verified");
            return  ResponseEntity.status(404).body(errorMap);
        }

        User user = userService.findByUsername(username);

        map.put("name", payload.get("name").toString());
        map.put("email", login.getEmail());
        map.put("rol", user.getRol().toString());

        return ResponseEntity.status(200).body(map);
    }


}

