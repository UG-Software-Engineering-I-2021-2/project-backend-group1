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

    public void updateEvaluation(String rubricCode, String semester, String courseCode, String studentCode, List<HashMap<String, HashMap<String, String>>> content) {
        Gson gson = new Gson();
        HashMap<String, HashMap<String, String>> student = content.get(0);
        Evalua evalua = evaluationRepository.getEvalua(rubricCode, semester, courseCode, studentCode);
        evalua.setCalificacionAlumno(gson.toJson(student.get("studentGrade")));
        evalua.setCalificacionCompetencia(gson.toJson(student.get("competenceGrade")));
        evalua.setEvaluacionTotal(Objects.equals(student.get("student").get("finished"), "1"));
        evaluationRepository.save(evalua);
    }

}
