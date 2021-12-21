package business;

import config.endpoint_classes.evaluation.Evaluation;
import config.endpoint_classes.evaluation.EvaluationInterface;
import data.entities.Evalua;
import data.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class EvaluationService {
    @Autowired
    private EvaluationRepository evaluationRepository;

    public void updateEvaluation(String rubricCode, String semester, String courseCode, String studentCode, String studentGrade, String competenceGrade, Boolean finished) {
        Evalua evalua = evaluationRepository.getEvalua(rubricCode, semester, courseCode, studentCode);
        evalua.setCalificacionAlumno(studentGrade);
        evalua.setCalificacionCompetencia(competenceGrade);
        evalua.setEvaluacionTotal(finished);
        evaluationRepository.save(evalua);
    }

    public List<Evaluation> getEvaluationsForStatistics(String semester, String competenceCode){
        List<EvaluationInterface> evaluationInterfaceList = evaluationRepository.getEvaluationsForStatistics(semester, competenceCode);
        List<Evaluation> response = new ArrayList<>();
        for(EvaluationInterface evaluationInterface: evaluationInterfaceList){
            Evaluation evaluation = new Evaluation(evaluationInterface);
            response.add(evaluation);
        }
        return response;
    }

}
