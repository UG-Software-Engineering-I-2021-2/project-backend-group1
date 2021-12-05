package business;

import com.google.gson.Gson;
import config.enums.State;
import data.entities.Evalua;
import data.entities.Rubrica;
import data.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    public void updateEvaluation(String rubricCode, String semester, String courseCode, List<HashMap<String, HashMap<String, String>>> content) {
        Gson gson = new Gson();

        for(HashMap<String, HashMap<String, String>> student : content){
            String studentCode = student.get("student").get("code");
            String finished = student.get("student").get("finished");
            Evalua evalua = evaluationRepository.getEvalua(rubricCode, semester, courseCode, studentCode);
            evalua.setCalificacionAlumno(gson.toJson(student.get("studentGrade")));
            evalua.setCalificacionCompetencia(gson.toJson(student.get("competenceGrade")));
            evalua.setEvaluacionTotal(Objects.equals(finished, "1"));
            evaluationRepository.save(evalua);
        }
    }

}
