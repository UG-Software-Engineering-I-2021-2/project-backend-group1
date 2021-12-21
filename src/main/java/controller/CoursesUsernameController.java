package controller;

import business.CourseService;
import config.endpoint_classes.course.Course;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;

class CoursesUsernameBody {
    private String semester;
    private String role;
    public String getSemester() { return semester; }
    public String getRole() { return role; }
}

@CrossOrigin(origins = "https://group1-ingsort1.herokuapp.com/*", allowedHeaders = "*")
@RestController
public class CoursesUsernameController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final MsgReturn msgReturn = new MsgReturn();

    @Autowired
    private CourseService courseService;

    @PostMapping(value = "/courses_username")
    public ResponseEntity<String> coursesUsernameController(@RequestHeader(value="Authorization") String authorization, @RequestBody CoursesUsernameBody coursesUsernameBody) {

        Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if(payload == null)
            return msgReturn.callError(404, "token not verified");

        String email = payload.getEmail();
        String username = email.substring(0,email.indexOf('@'));
        String semester = coursesUsernameBody.getSemester();
        String role = coursesUsernameBody.getRole();

        if(semester == null || semester.isEmpty())
            return msgReturn.callError(404, "semester empty");
        if(role == null || role.isEmpty())
            return msgReturn.callError(404, "role empty");

        List<Course> response = courseService.getCourse(semester, username, role);

        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}