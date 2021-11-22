package controller;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class MsgReturn {
    private final Gson gson = new Gson();

    ResponseEntity<String> callError(Integer status, String errorMsg) {
        HashMap<String, String> errorMap = new HashMap<>();
        errorMap.put("error", errorMsg);
        return ResponseEntity.status(status).body(gson.toJson(errorMap));
    }
    ResponseEntity<String> callMsg(Integer status, String key, String msg) {
        HashMap<String, String> msgMap = new HashMap<>();
        msgMap.put(key, msg);
        return ResponseEntity.status(status).body(gson.toJson(msgMap));
    }
}
