package business;

import com.google.gson.Gson;
import data.entities.Evalua;
import data.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    public void updateEvaluation(String rubricCode, String semester, String courseCode, String studentCode, String studentGrade, String competenceGrade, Boolean finished) {
        Gson gson = new Gson();
        Evalua evalua = evaluationRepository.getEvalua(rubricCode, semester, courseCode, studentCode);
        evalua.setCalificacionAlumno(studentGrade);
        evalua.setCalificacionCompetencia(competenceGrade);
        evalua.setEvaluacionTotal(finished);
        evaluationRepository.save(evalua);
    }

}
