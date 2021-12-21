package controller;

import business.MailSenderService;
import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpoint_classes.rubric.RubricUpdate;
import config.endpoint_classes.rubric_creation.RubricCreation;
import config.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;

class RubricCreationBody {
    private List<HashMap<String, HashMap<String, String>>> content;
    private String activity;
    private String rubricCode;
    private String semester;
    private String title;
    private boolean onlySave;
    private String courseCode;
    private String courseName;
    private String link;

    public List<HashMap<String, HashMap<String, String>>> getContent() {
        return content;
    }

    public String getActivity() {
        return activity;
    }

    public String getRubricCode() {
        return rubricCode;
    }

    public String getSemester() {
        return semester;
    }

    public String getTitle() {
        return title;
    }

    public boolean isOnlySave() {
        return onlySave;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getLink() { return link; }
}

@CrossOrigin(origins = "https://group1-ingsort1.herokuapp.com/*", allowedHeaders = "Authorization")
@RestController
public class RubricCreationController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final MsgReturn msgReturn = new MsgReturn();

    @Autowired
    private RubricService rubricService;

    @Autowired
    private MailSenderService mailSenderService;

    @GetMapping("/rubric_creation")
    public ResponseEntity<String> rubricCreationController(@RequestHeader(value = "Authorization") String authorization,
            @RequestParam Map<String, String> requestParam) {
        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = requestParam.get("semester");
        String courseCode = requestParam.get("courseCode");
        String rubricCode = requestParam.get("rubricCode");

        if (semester == null || semester.isEmpty())
            return msgReturn.callError(404, "semester empty");
        if (courseCode == null || courseCode.isEmpty())
            return msgReturn.callError(404, "course code empty");
        if (rubricCode == null || rubricCode.isEmpty())
            return msgReturn.callError(404, "rubric code empty");

        List<RubricCreation> response = new ArrayList<>();
        response.add(rubricService.getRubricCreation(rubricCode, semester, courseCode));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @PostMapping("/rubric_creation")
    public ResponseEntity<String> rubricCreationControllerPost(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestBody RubricCreationBody rubricCreationBody)
            throws MessagingException {

        String divStr = "<div>";
        String divCloseStr = "</div>";
        String brStr = "<br />";

        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");

        String semester = rubricCreationBody.getSemester();
        String rubricCode = rubricCreationBody.getRubricCode();
        String activity = rubricCreationBody.getActivity();
        String title = rubricCreationBody.getTitle();
        List<HashMap<String, HashMap<String, String>>> content = rubricCreationBody.getContent();
        String courseCode = rubricCreationBody.getCourseCode();
        String courseName = rubricCreationBody.getCourseName();
        String link = rubricCreationBody.getLink();

        if (rubricCreationBody.isOnlySave()) {
            rubricService.updateRubric(rubricCode, semester,
                    new RubricUpdate((short) content.size(), gson.toJson(content), activity, title, State.SinAsignar));
            return msgReturn.callMsg(200, "msg", "Rúbrica guardada correctamente");
        } else {
            String[] to = new String[1];
            to[0] = "jorge.neira@utec.edu.pe";
            String subject = "Nueva rúbrica creada requiere revisión. Rúbrica: " + title + " del curso " + courseCode + " " + courseName;
            String body = "<div style=\"display: flex; justify-content: center; width: 100%; font-family: SYSTEM-UI;\">\n" +
                    "    <div style=\"width: 50%;\">\n" +
                    divStr +
                    "            <p>Buen día,</p>\n" +
                    divCloseStr +
                    divStr +
                    "            El profesor " + payload.getEmail() + " acaba de crear una nueva rúbrica y requiere de una aprobación.\n" +
                    divCloseStr +
                    brStr +
                    divStr +
                    "            <b>Curso:</b> " + courseCode + " " + courseName + "\n" +
                    "            <br />\n" +
                    "            <b>Código de rúbrica:</b> " + rubricCode + "\n" +
                    "            <br />\n" +
                    "            <b>Título de rúbrica: </b> " + title + "\n" +
                    divCloseStr +
                    brStr +
                    "        <div>Para aprobar o rechazar la solicitud, ingrese al sistema haciendo click en el siguiente botón.</div>\n" +
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
            rubricService.updateRubric(rubricCode, semester,
                    new RubricUpdate((short) content.size(), gson.toJson(content), activity, title, State.AprobacionPendiente));
            return msgReturn.callMsg(200, "msg", "Solicitud enviada correctamente");
        }
    }
}
