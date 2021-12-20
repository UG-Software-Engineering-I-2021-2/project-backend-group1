package controller;

import business.MailSenderService;
import business.RubricService;
import business.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import config.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

class RubricRevisionBody {
    private String rubricCode;
    private String semester;
    private String courseCode;
    private String courseName;
    private String title;
    private String comment;
    private boolean accepted;
    private String link;

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

    public String getLink() { return link; }
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
    ) throws MessagingException {
        System.out.println("\nTEST RUBRIC REVISION");
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = rubricRevisionBody.getSemester();
        String rubricCode = rubricRevisionBody.getRubricCode();
        String courseCode = rubricRevisionBody.getCourseCode();
        String courseName = rubricRevisionBody.getCourseName();
        String title = rubricRevisionBody.getTitle();
        String comment = rubricRevisionBody.getComment();
        String link = rubricRevisionBody.getLink();

        String[] to = userService.getCourseCoordinators(semester, courseCode).toArray(new String[0]);


        String subject = "Resultado de revisión. Rúbrica: " + title + " del curso " + courseCode + " " + courseName;
        String body = "<div style=\"display:-moz-inline-grid; justify-content: center; width: 100%; font-family: SYSTEM-UI;margin: 100px\">" +
                "    <div>" +
                "        <p>Buen día,</p>" +
                "    </div>";
        String msg;

        if (rubricRevisionBody.isAccepted()) {
            System.out.println("\nAceptar");
            body += "    <div>" +
                    "        Una rúbrica asignada para su revisión ha sido aceptada." +
                    "    </div>";
            msg = "La rúbrica fue aceptada";
            rubricService.updateRubricState(rubricCode, semester, State.DisponibleParaCalificar);
        } else {
            System.out.println("\nRechazar");
            body += "    <div>" +
                    "        Una rúbrica asignada para su revisión ha sido rechazada con el siguiente" +
                    "        comentario:" +
                    "    </div>" +
                    "    <br />" +
                    "    <div style=\"padding: 20px;color: black; background-color:aliceblue; border-radius:16px; display:flex; justify-content: center;\">" +
                    "        <b>" + comment + "</b>" +
                    "    </div>";
            msg = "La rúbrica fue rechazada";
            rubricService.updateRubricState(rubricCode, semester, State.SinAsignar);
        }

        body += "    <br />" +
                "    <div>" +
                "        <b>Curso:</b> " + courseCode + " " + courseName +
                "        <br />" +
                "        <b>Código de rúbrica:</b> " + rubricCode +
                "        <br />" +
                "        <b>Título de rúbrica: </b> " + title +
                "    </div>" +
                "    <br />" +
                "    <div>Puede visualizar esta información haciendo click en el siguiente botón.</div>" +
                "    <br/>" +
                "    <div style=\"display: flex; justify-content: center; padding: 15px;\">" +
                "        <a href=\""+ link + "\" style=\"padding:15px; border: 1px solid black; text-decoration:none; color:black; border-radius: 16px\"> Ingrese al sistema </a>" +
                "    </div>" +
                "    <br/>" +
                "    <div style=\"display: flex; justify-content: flex-end;\">" +
                "        <p>Atentamente.</p>" +
                "    <br/> " +
                "        <p>Sistema de gestión de rúbricas</p>" +
                "    </div>" +
                "</div>";
        System.out.println("\nAntes de enviar email");
        mailSenderService.sendEmail(to, subject, body);
        System.out.println("\nRETURN");
        return msgReturn.callMsg(200, "msg", msg);
    }
}
