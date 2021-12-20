package controller;

import business.MailSenderService;
import business.RubricService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.Gson;
import config.endpointClasses.rubric.RubricUpdate;
import config.endpointClasses.rubricCreation.RubricCreation;
import config.enums.State;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
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

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
            @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST RUBRIC CREATION GET");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
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

        System.out.println("Semester " + semester);
        System.out.println("CourseCode " + courseCode);
        System.out.println("RubricCode " + rubricCode);

        List<RubricCreation> response = new ArrayList<>();
        response.add(rubricService.getRubricCreation(rubricCode, semester, courseCode));
        System.out.println(gson.toJson(response));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @PostMapping("/rubric_creation")
    public ResponseEntity<String> rubricCreationControllerPost(
            @RequestHeader(value = "Authorization") String authorization,
            @RequestBody RubricCreationBody rubricCreationBody)
            throws JSONException, GeneralSecurityException, IOException, MessagingException {
        System.out.println("\nTEST RUBRIC CREATION POST");

        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
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

        System.out.println("\nsemester: " + semester);
        System.out.println("\nrubricCode: " + rubricCode);
        System.out.println("\nactivity: " + activity);
        System.out.println("\ncontent: " + gson.toJson(content));
        System.out.println("\ncourseCode: " + courseCode);
        System.out.println("\ncourseName: " + courseName);

        if (rubricCreationBody.isOnlySave()) {
            rubricService.updateRubric(rubricCode, semester,
                    new RubricUpdate((short) content.size(), gson.toJson(content), activity, title, State.SinAsignar));
            System.out.println("\nRETURN");
            return msgReturn.callMsg(200, "msg", "Rúbrica guardada correctamente");
        } else {
            String[] to = new String[1];
            to[0] = "jorge.neira@utec.edu.pe";
            String subject = "Nueva rúbrica creada requiere revisión. Rúbrica: " + title + " del curso " + courseCode + " " + courseName;
            String body = "<div style=\"display:-moz-inline-grid; justify-content: center; width: 100%; font-family: SYSTEM-UI;margin: 100px\">" +
                    " <div>" +
                    "   <p>Buen día,</p>" +
                    " </div>" +
                    " <div> " +
                    " El profesor " + payload.getEmail() + " acaba de crear una nueva rúbrica y requiere de una aprobación." +
                    " </div> " +
                    "    <br />" +
                    "    <div>" +
                    "        <b>Curso:</b> " + courseCode + " " + courseName +
                    "        <br />" +
                    "        <b>Código de rúbrica:</b> " + rubricCode +
                    "        <br />" +
                    "        <b>Título de rúbrica: </b> " + title +
                    "    </div>" +
                    "    <br />" +
                    "    <div>Para aprobar o rechazar la solicitud, ingrese al sistema haciendo click en el siguiente botón.</div>" +
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
            mailSenderService.sendEmail(to, subject, body);
            rubricService.updateRubric(rubricCode, semester,
                    new RubricUpdate((short) content.size(), gson.toJson(content), activity, title, State.AprobacionPendiente));
            System.out.println("\nRETURN");
            return msgReturn.callMsg(200, "msg", "Solicitud enviada correctamente");
        }
    }
}
