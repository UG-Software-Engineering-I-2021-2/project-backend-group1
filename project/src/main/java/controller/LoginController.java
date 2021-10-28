package controller;

import business.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String isUser(@RequestBody JSONObject email) throws JSONException {
        System.out.println(email);
        return (String) email.get("email");
    }
}


