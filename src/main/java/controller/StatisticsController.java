package controller;

import business.CareerService;
import business.CompetenceService;
import business.EvaluationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import config.endpointClasses.career.Career;
import config.endpointClasses.competence.Competence;
import config.endpointClasses.evaluation.Evaluation;
import config.enums.State;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class StatisticsController {
    private final TokenValidator tokenValidator = new TokenValidator();

    private final Gson gson = new Gson();

    private final MsgReturn msgReturn = new MsgReturn();

    @Autowired
    private CareerService careerService;

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/statistics_get_careers")
    public ResponseEntity<String> getCareersController(@RequestHeader(value = "Authorization") String authorization,
                                                        @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST GET CAREERS");
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");
        List<Career> response = careerService.getAll();
        System.out.println(gson.toJson(response));
        System.out.println("RETURN");
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @GetMapping("/statistics_get_competences_by_career")
    public ResponseEntity<String> getCompetencesByCareerController(@RequestHeader(value = "Authorization") String authorization,
                                                       @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST GET COMPETENCE");
        GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, "token not verified");
        String careerId = requestParam.get("careerId");
        if(careerId == null || careerId.isEmpty())
            return msgReturn.callError(404, "careerId empty");

        System.out.println("\ncareerId " + careerId);

        List<Competence> response = competenceService.getAllByCareer(Integer.valueOf(careerId));
        System.out.println(gson.toJson(response));
        System.out.println("RETURN");
        return ResponseEntity.status(200).body(gson.toJson(response));

    }

    String getCriteriaCode(String raw, Integer level){
        //raw = N.N. abc
        StringBuilder criteriaCode = new StringBuilder();
        int i = 0;
        while(raw.charAt(i) != '.'){
            criteriaCode.append(raw.charAt(i));
            i++;
        }
        criteriaCode.append('.');
        i++;
        while(raw.charAt(i) - '0' >= 0 && raw.charAt(i) - '0' <= 9){
            criteriaCode.append(raw.charAt(i));
            i++;
        }
        criteriaCode.append('.');
        criteriaCode.append(level);
        return criteriaCode.toString();
    }

    @GetMapping("/statistics1_get")
    public ResponseEntity<String> statistics1Controller(@RequestHeader(value = "Authorization") String authorization,
                                                         @RequestParam Map<String, String> requestParam)
            throws JSONException, GeneralSecurityException, IOException {
        System.out.println("\nTEST STATISTICS 1 GET");
        //GoogleIdToken.Payload payload = tokenValidator.ValidateTokenAndGetPayload(authorization);
        //if (payload == null)
            //return msgReturn.callError(404, "token not verified");

        String role = requestParam.get("role");
        String semester = requestParam.get("semester");
        String competenceCode = requestParam.get("competenceCode");

        List<Evaluation> evaluationList = evaluationService.getEvaluationsForStatistics(semester, competenceCode);
        System.out.println("\nEvaluationList:");
        System.out.println(gson.toJson(evaluationList));

        HashMap<String, Integer> criteriaCodeGood = new HashMap<>();
        HashMap<String, Integer> criteriaCodeTotal = new HashMap<>();
        HashMap<String, String> criteriaCodeMap = new HashMap<>();
        for(Evaluation evaluation : evaluationList){
            String criteriaCode = this.getCriteriaCode(evaluation.getCriteria(), evaluation.getLevel());

            if(!criteriaCodeMap.containsKey(criteriaCode)){
                criteriaCodeMap.put(criteriaCode, evaluation.getCriteria());
                criteriaCodeGood.put(criteriaCode, 0);
                criteriaCodeTotal.put(criteriaCode, 0);
            }

            Type empMapType = new TypeToken<Map<String, Integer>>() {}.getType();
            Map<String, Integer> competenceGrade = gson.fromJson(evaluation.getCompetenceGrade(), empMapType);

            String competenceLeft = "competenceLeft";

            criteriaCodeTotal.put(criteriaCode, criteriaCodeTotal.get(criteriaCode)+1);

            if(evaluation.getState().compareTo(State.Cumplidos) == 0 && Boolean.TRUE.equals(evaluation.getTotalEvaluation()))
                criteriaCodeTotal.put(criteriaCode, criteriaCodeTotal.get(criteriaCode)-1);
            if(competenceGrade == null)
                continue;
            if(competenceGrade.containsKey(competenceLeft) && competenceGrade.get(competenceLeft) > 70)
                criteriaCodeGood.put(criteriaCode, criteriaCodeGood.get(criteriaCode)+1);
        }
        System.out.println("\ncriteriaCodeGood:");
        System.out.println(gson.toJson(criteriaCodeGood));
        System.out.println("\ncriteriaCodeTotal:");
        System.out.println(gson.toJson(criteriaCodeTotal));
        System.out.println("\ncriteriaCodeMap:");
        System.out.println(gson.toJson(criteriaCodeMap));

        HashMap<String, HashMap<Integer, Double>> response = new HashMap<>();

        for (String criteriaCodeLevel : criteriaCodeMap.keySet()) {
            String criteriaCode = criteriaCodeLevel.substring(0, criteriaCodeLevel.length()-1);
            Integer criteriaLevel = Integer.valueOf(criteriaCodeLevel.substring(criteriaCodeLevel.length()-1));
            if(!response.containsKey(criteriaCodeMap.get(criteriaCodeLevel))){
                HashMap<Integer, Double> map = new HashMap<>();
                response.put(criteriaCodeMap.get(criteriaCodeLevel), map);
            }
            HashMap<Integer, Double> mapCompetence = response.get(criteriaCodeMap.get(criteriaCodeLevel));
            if(criteriaCodeTotal.get(criteriaCodeLevel) != 0)
                mapCompetence.put(criteriaLevel, (double) (criteriaCodeGood.get(criteriaCodeLevel))*100.0/criteriaCodeTotal.get(criteriaCodeLevel));
            else
                mapCompetence.put(criteriaLevel, 0.0);
            response.put(criteriaCodeMap.get(criteriaCodeLevel), mapCompetence);
        }

        System.out.println(gson.toJson(response));
        System.out.println("RETURN");
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}
