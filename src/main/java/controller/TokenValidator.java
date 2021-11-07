package controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import java.util.Collections;

public class TokenValidator {
    public GoogleIdToken.Payload ValidateTokenAndGetPayload(String idTokenString)  {

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                    .setAudience(Collections.singletonList("854441781361-k1cg7207b002frst5mirrhfko7tbj602.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            System.out.println(idToken);

            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

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
