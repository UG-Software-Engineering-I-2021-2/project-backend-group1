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
        String body;
        String msg;

        if (rubricRevisionBody.isAccepted()) {
            System.out.println("\nAceptar");
            body = "<div style=\"display: flex; justify-content: center; width: 100%; font-family: SYSTEM-UI;\">\n" +
                    "    <div style=\"width: 50%;\">\n" +
                    "        <div>\n" +
                    "            <p>Buen día,</p>\n" +
                    "        </div>\n" +
                    "        <div>\n" +
                    "            Una rúbrica asignada para su revisión ha sido aceptada.\n" +
                    "        </div>\n";
            msg = "La rúbrica fue aceptada";
            rubricService.updateRubricState(rubricCode, semester, State.DisponibleParaCalificar);
        } else {
            System.out.println("\nRechazar");
            body = "<div style=\"display: flex; justify-content: center; width: 100%; font-family: SYSTEM-UI;\">\n" +
                    "    <div style=\"width: 50%;\">\n" +
                    "        <div>\n" +
                    "            <p>Buen día,</p>\n" +
                    "        </div>\n" +
                    "        <div>\n" +
                    "            Una rúbrica asignada para su revisión ha sido rechazada con el siguiente\n" +
                    "            comentario:\n" +
                    "        </div>\n" +
                    "        <br />\n" +
                    "        <div\n" +
                    "            style=\"padding: 20px;color: black; background-color:aliceblue; border-radius:16px; display:flex; justify-content: center;\">\n" +
                    "            <b>" + comment + "</b>\n" +
                    "        </div>";
            msg = "La rúbrica fue rechazada";
            rubricService.updateRubricState(rubricCode, semester, State.SinAsignar);
        }
        body += "<br />\n" +
                "        <div>\n" +
                "            <b>Curso:</b> " + courseCode + " " + courseName + "\n" +
                "            <br />\n" +
                "            <b>Código de rúbrica:</b> " + rubricCode + "\n" +
                "            <br />\n" +
                "            <b>Título de rúbrica: </b> " + title + "\n" +
                "        </div>\n" +
                "        <br />\n" +
                "        <div>Puede visualizar esta información dandole click al siguiente boton</div>\n" +
                "        <br />\n" +
                "        <div style=\"display: flex; justify-content: center; padding: 15px;\">\n" +
                "            <a href=\"" + link + "\"\n" +
                "                style=\"padding:15px; border: 1px solid black; text-decoration:none; color:black; border-radius: 16px\">\n" +
                "                Click aqui </a>\n" +
                "        </div>\n" +
                "        <div style=\"display: flex; justify-content: flex-end;\">\n" +
                "            <p>Atentamente.</p>\n" +
                "        </div>\n" +
                "        <div style=\"display: flex; justify-content: flex-end;\">\n" +
                "            <p>Sistema de gestión de rúbricas</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>";
        System.out.println("\nAntes de enviar email");
        mailSenderService.sendEmail(to, subject, body);
        System.out.println("\nRETURN");
        return msgReturn.callMsg(200, "msg", msg);
    }
}
