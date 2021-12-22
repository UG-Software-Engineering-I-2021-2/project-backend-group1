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
import java.util.List;

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
        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = rubricRevisionBody.getSemester();
        String rubricCode = rubricRevisionBody.getRubricCode();
        String courseCode = rubricRevisionBody.getCourseCode();
        String courseName = rubricRevisionBody.getCourseName();
        String title = rubricRevisionBody.getTitle();
        String comment = rubricRevisionBody.getComment();
        String link = rubricRevisionBody.getLink();

        List<String> coordinators = userService.getCourseCoordinators(semester, courseCode);
        coordinators.add("luis.carbajal@utec.edu.pe");
        String[] to = coordinators.toArray(new String[0]);

        String subject = "Resultado de revisión. Rúbrica: " + title + " del curso " + courseCode + " " + courseName;
        String body;
        String msg;

        String divStr = "<div>";
        String divCloseStr = "</div>";
        String brStr = "<br />";

        if (rubricRevisionBody.isAccepted()) {
            body = "<div style=\"display: flex; justify-content: center; width: 100%; font-family: SYSTEM-UI;\">\n" +
                    "    <div style=\"width: 50%;\">\n" +
                    divStr +
                    "            <p>Buen día,</p>\n" +
                    divCloseStr +
                    divStr +
                    "            Una rúbrica asignada para su revisión ha sido aceptada.\n" +
                    divCloseStr;
            msg = "La rúbrica fue aceptada";
            rubricService.updateRubricState(rubricCode, semester, State.DisponibleParaCalificar);
        } else {
            body = "<div style=\"display: flex; justify-content: center; width: 100%; font-family: SYSTEM-UI;\">\n" +
                    "    <div style=\"width: 50%;\">\n" +
                    divStr +
                    "            <p>Buen día,</p>\n" +
                    divCloseStr +
                    divStr +
                    "            Una rúbrica asignada para su revisión ha sido rechazada con el siguiente\n" +
                    "            comentario:\n" +
                    divCloseStr +
                    brStr +
                    "        <div\n" +
                    "            style=\"padding: 20px;color: black; background-color:aliceblue; border-radius:16px; display:flex; justify-content: center;\">\n" +
                    "            <b>" + comment + "</b>\n" +
                    divCloseStr;
            msg = "La rúbrica fue rechazada";
            rubricService.updateRubricState(rubricCode, semester, State.SinAsignar);
        }
        body += brStr +
                divStr +
                "            <b>Curso:</b> " + courseCode + " " + courseName + "\n" +
                brStr +
                "            <b>Código de rúbrica:</b> " + rubricCode + "\n" +
                brStr +
                "            <b>Título de rúbrica: </b> " + title + "\n" +
                divCloseStr +
                brStr +
                divStr + "Puede visualizar esta información dandole click al siguiente boton</div>\n" +
                brStr +
                "        <div style=\"display: flex; justify-content: center; padding: 15px;\">\n" +
                "            <a href=\"" + link + "\"\n" +
                "                style=\"padding:15px; border: 2px solid black; text-decoration:none; color:black; border-radius: 16px\">\n" +
                "                Ingresar al sistema </a>\n" +
                divCloseStr +
                "        <div style=\"display: flex; justify-content: flex-end;\">\n" +
                "            <p>Atentamente.</p>\n" +
                divCloseStr +
                "        <div style=\"display: flex; justify-content: flex-end;\">\n" +
                "            <p>Sistema de gestión de rúbricas</p>\n" +
                divCloseStr +
                divCloseStr +
                divCloseStr;
        mailSenderService.sendEmail(to, subject, body);
        return msgReturn.callMsg(200, "msg", msg);
    }
}
