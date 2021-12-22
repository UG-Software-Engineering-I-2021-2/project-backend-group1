package controller;

import business.CareerService;
import business.CompetenceService;
import business.EvaluationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import config.endpoint_classes.career.Career;
import config.endpoint_classes.competence.Competence;
import config.endpoint_classes.evaluation.Evaluation;
import config.enums.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

class StatisticResponse{
    String criteria;
    String criteriaCode;
    Map<Integer, Double> score;
    Map<Integer, String> state;

    public StatisticResponse(String criteria, Map<Integer, Double> score, String criteriaCode, Map<Integer, String> state){
        this.criteria = criteria;
        this.score = score;
        this.criteriaCode = criteriaCode;
        this.state = state;
    }
    public String getCriteria() { return  criteria; }
}

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

    private static final String TOKEN_NOT_VERIFIED_STR = "token not verified";

    @GetMapping("/statistics_get_careers")
    public ResponseEntity<String> getCareersController(@RequestHeader(value = "Authorization") String authorization,
                                                        @RequestParam Map<String, String> requestParam) {
        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, TOKEN_NOT_VERIFIED_STR);
        List<Career> response = careerService.getAll();
        response.sort(Comparator.comparing(Career::getName));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    @GetMapping("/statistics_get_competences_by_career")
    public ResponseEntity<String> getCompetencesByCareerController(@RequestHeader(value = "Authorization") String authorization,
                                                       @RequestParam Map<String, String> requestParam) {
        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, TOKEN_NOT_VERIFIED_STR);
        String careerId = requestParam.get("careerId");
        if(careerId == null || careerId.isEmpty())
            return msgReturn.callError(404, "careerId empty");

        List<Competence> response = competenceService.getAllByCareer(Integer.valueOf(careerId));
        response.sort(Comparator.comparing(Competence::getCode));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }

    String getCriteriaCode(String raw){
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
        return criteriaCode.toString();
    }

    String getCriteriaCodeWithLevel(String raw, Integer level){
        String criteriaCode = getCriteriaCode(raw);
        criteriaCode += level;
        return criteriaCode;
    }

    @GetMapping("/statistics1_get")
    public ResponseEntity<String> statistics1Controller(@RequestHeader(value = "Authorization") String authorization,
                                                         @RequestParam Map<String, String> requestParam) {
        GoogleIdToken.Payload payload = tokenValidator.validateTokenAndGetPayload(authorization);
        if (payload == null)
            return msgReturn.callError(404, TOKEN_NOT_VERIFIED_STR);

        String semester = requestParam.get("semester");
        String competenceCode = requestParam.get("competenceCode");

        List<Evaluation> evaluationList = evaluationService.getEvaluationsForStatistics(semester, competenceCode);

        HashMap<String, Integer> criteriaCodeGood = new HashMap<>();
        HashMap<String, Integer> criteriaCodeTotal = new HashMap<>();
        HashMap<String, String> criteriaCodeMap = new HashMap<>();
        HashMap<String, String> criteriaCodeState = new HashMap<>();

        for(Evaluation evaluation : evaluationList){
            String criteriaCode = this.getCriteriaCodeWithLevel(evaluation.getCriteria(), evaluation.getLevel());

            if(!criteriaCodeMap.containsKey(criteriaCode)){
                criteriaCodeMap.put(criteriaCode, evaluation.getCriteria());
                criteriaCodeGood.put(criteriaCode, 0);
                criteriaCodeTotal.put(criteriaCode, 0);
                criteriaCodeState.put(criteriaCode, "Procesado");
            }

            Type empMapType = new TypeToken<Map<String, Double>>() {}.getType();
            Map<String, Double> competenceGrade = gson.fromJson(evaluation.getCompetenceGrade(), empMapType);

            String competenceLeft = "competenceLeft";

            criteriaCodeTotal.put(criteriaCode, criteriaCodeTotal.get(criteriaCode)+1);

            if(evaluation.getState().compareTo(State.Cumplidos) != 0)
                criteriaCodeState.put(criteriaCode, "En Proceso");
            if(evaluation.getState().compareTo(State.Cumplidos) == 0 && Boolean.FALSE.equals(evaluation.getTotalEvaluation()))
                criteriaCodeTotal.put(criteriaCode, criteriaCodeTotal.get(criteriaCode)-1);
            if(competenceGrade == null)
                continue;
            if(competenceGrade.containsKey(competenceLeft) && competenceGrade.get(competenceLeft) >= 75.0)
                criteriaCodeGood.put(criteriaCode, criteriaCodeGood.get(criteriaCode)+1);
        }
        HashMap<String, HashMap<Integer, Double>> statisticMap = new HashMap<>();
        HashMap<String, HashMap<Integer, String>> statisticMapState = new HashMap<>();

        for (String criteriaCodeLevel : criteriaCodeMap.keySet()) {
            Integer criteriaLevel = Integer.valueOf(criteriaCodeLevel.substring(criteriaCodeLevel.length()-1));
            if(!statisticMap.containsKey(criteriaCodeMap.get(criteriaCodeLevel))){
                HashMap<Integer, Double> map = new HashMap<>();
                statisticMap.put(criteriaCodeMap.get(criteriaCodeLevel), map);
                HashMap<Integer, String> mapState = new HashMap<>();
                statisticMapState.put(criteriaCodeMap.get(criteriaCodeLevel), mapState);
            }
            HashMap<Integer, Double> mapCompetence = statisticMap.get(criteriaCodeMap.get(criteriaCodeLevel));
            HashMap<Integer, String> mapCompetenceState = statisticMapState.get(criteriaCodeMap.get(criteriaCodeLevel));
            if(criteriaCodeTotal.get(criteriaCodeLevel) != 0){
                double value = (double) (criteriaCodeGood.get(criteriaCodeLevel))*100.0/criteriaCodeTotal.get(criteriaCodeLevel);
                Double valueTruncate = BigDecimal.valueOf(value)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue();
                mapCompetence.put(criteriaLevel, valueTruncate);
            }
            else
                mapCompetence.put(criteriaLevel, 0.00);
            mapCompetenceState.put(criteriaLevel, criteriaCodeState.get(criteriaCodeLevel));
            statisticMap.put(criteriaCodeMap.get(criteriaCodeLevel), mapCompetence);
            statisticMapState.put(criteriaCodeMap.get(criteriaCodeLevel), mapCompetenceState);
        }
        List<StatisticResponse> response = new ArrayList<>();
        for(String responseKey : statisticMap.keySet()){
            HashMap<Integer, Double> mapCompetence = statisticMap.get(responseKey);
            HashMap<Integer, String> mapCompetenceState = statisticMapState.get(responseKey);
            for(int i=1; i<=3; i++){
                if(!mapCompetence.containsKey(i))
                    mapCompetence.put(i,0.0);
                if(!mapCompetenceState.containsKey(i))
                    mapCompetenceState.put(i,"En Proceso");
            }
            StatisticResponse statisticResponse = new StatisticResponse(responseKey, mapCompetence, getCriteriaCode(responseKey), mapCompetenceState);
            response.add(statisticResponse);
        }
        response.sort(Comparator.comparing(StatisticResponse::getCriteria));
        return ResponseEntity.status(200).body(gson.toJson(response));
    }
}
