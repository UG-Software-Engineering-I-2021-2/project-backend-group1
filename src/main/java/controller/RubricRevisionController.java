package controller;

import business.MailSenderService;
import business.RubricService;
import business.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import config.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

class RubricRevisionBody {
    private String rubricCode;
    private String semester;
    private String courseCode;
    private String courseName;
    private String title;
    private String comment;
    private boolean accepted;

    public String getRubricCode() {
        return rubricCode;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public boolean isAccepted() {
        return accepted;
    }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RubricRevisionController {
    private final TokenValidator tokenValidator = new TokenValidator();
    private final MsgReturn msgReturn = new MsgReturn();

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private RubricService rubricService;

    @Autowired
    private UserService userService;

    @PostMapping("/rubric_revision")
    public ResponseEntity<String> rubricRevisionController(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestBody RubricRevisionBody rubricRevisionBody
    ) {
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = rubricRevisionBody.getSemester();
        String rubricCode = rubricRevisionBody.getRubricCode();
        String courseCode = rubricRevisionBody.getCourseCode();
        String courseName = rubricRevisionBody.getCourseName();
        String title = rubricRevisionBody.getTitle();
        String comment = rubricRevisionBody.getComment();

        String[] to = userService.getCourseCoordinators(semester, courseCode).toArray(new String[0]);
        String subject = "Resultado de revisión. Rúbrica: " + title + " del curso " + courseCode + " " + courseName;
        String body;
        String msg;

        if (rubricRevisionBody.isAccepted()) {
            body = "Buen día,\n\n" +
                "Una rúbrica asignada para su revisión ha sido aceptada.\n\n";
            msg = "La rúbrica fue aceptada";
            rubricService.updateRubricState(rubricCode, semester, State.DisponibleParaCalificar);
        } else {
            body = "Buen día,\n\n" +
                "Una rúbrica asignada para su revisión ha sido rechazada con el " +
                "siguiente comentario:\n\n \"" + comment + "\"\n\n";
            msg = "La rúbrica fue rechazada";
            rubricService.updateRubricState(rubricCode, semester, State.SinAsignar);
        }

        body += "Curso: " + courseCode + " " + courseName + "\n" +
            "Código de rúbrica: " + rubricCode + "\n" +
            "Título de rúbrica: " + title + "\n\n" +
            "Puede visualizar esta información ingresando al sistema https://group1-ingsort1.herokuapp.com/login\n\n" +
            "Atentamente.\n" +
            "Sistema de gestión de rúbricas";

        mailSenderService.sendEmail(to, subject, body);
        return msgReturn.callMsg(200, "msg", msg);
    }
}
