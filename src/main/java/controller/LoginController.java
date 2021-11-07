package controller;

import business.UserService;
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

@CrossOrigin(origins = "*")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<HashMap<String, String>> isUser(@RequestHeader(value="Authorization") String authorization, @RequestBody Login login) throws JSONException, GeneralSecurityException, IOException {
        HashMap<String, String> map = new HashMap<>();

        System.out.println(login.getEmail() + "  " + authorization);
        if(!userService.isUser(login.getEmail())){
            HashMap<String, String> errorMap = new HashMap<>();
            errorMap.put("error", "user is not valid");
            return  ResponseEntity.status(404).body(errorMap);
        }
Payload payload = this.ValidateTokenAndGetPayload(authorization);
            if(payload == null){
                HashMap<String, String> errorMap = new HashMap<>();
                errorMap.put("error", "token not verified");
                return  ResponseEntity.status(404).body(errorMap);
            }


        map.put("name", payload.get("name").toString());
        map.put("email", login.getEmail());
        map.put("roll", "profesor");

        return ResponseEntity.status(200).body(map);
    }

    public Payload ValidateTokenAndGetPayload(String idTokenString)  {

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList("854441781361-k1cg7207b002frst5mirrhfko7tbj602.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            System.out.println(idToken);

            if (idToken != null) {
                Payload payload = idToken.getPayload();

                return payload;
            }
        }catch(Exception er){
            System.out.println(er);
            return null;

        }
        System.out.println("Cant get token info");

        return null;
    }
}

