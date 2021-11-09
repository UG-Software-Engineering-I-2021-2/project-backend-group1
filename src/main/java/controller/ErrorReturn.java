package controller;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ErrorReturn {
    private final Gson gson = new Gson();

    ResponseEntity<String> callError(Integer status, String errorMsg) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("error", errorMsg);
        return ResponseEntity.status(status).body(gson.toJson(errorMap));
    }
}
