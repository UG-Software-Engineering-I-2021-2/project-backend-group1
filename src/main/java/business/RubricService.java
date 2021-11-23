package business;

import config.endpointClasses.rubric.Rubric;
import config.endpointClasses.rubric.RubricInterface;
import config.endpointClasses.rubric.RubricUpdate;
import config.endpointClasses.rubricCreation.RubricCreation;
import config.endpointClasses.rubricCreation.RubricCreationInterface;
import data.entities.Rubrica;
import data.repositories.RubricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RubricService {
    @Autowired
    private RubricRepository rubricRepository;

    public RubricCreation getRubricCreation(String codRubrica, String semester, String codCourse){
        List<RubricCreationInterface> rubricInterfaceList = rubricRepository.getRubricCreation(codRubrica, semester, codCourse);
        RubricCreation response = new RubricCreation(rubricInterfaceList.get(0));
        response.setCycles(rubricInterfaceList);
        return response;
    }

    public List<Rubric> getRubric(String semester, String courseCode, String username, String role){
        List<RubricInterface> rubricInterfaceList = rubricRepository.getRubric(semester, courseCode, username);
        List<Rubric> response = new ArrayList<>();
        for(RubricInterface rubricInterface : rubricInterfaceList){
            Rubric rubric = new Rubric(rubricInterface, role);
            response.add(rubric);
        }
        return response;
    }

    public void updateRubric(String rubricCode, String semester, RubricUpdate rubricUpdate){
        Rubrica rubrica = rubricRepository.getRubricByRubricCodeAndSemester(rubricCode, semester);
        rubrica.setActividad(rubricUpdate.getActividad());
        rubrica.setDimensiones(rubricUpdate.getDimensiones());
        rubrica.setDescriptores(rubricUpdate.getDescriptores());
        rubrica.setTitulo(rubricUpdate.getTitle());
        rubrica.setEstado(rubricUpdate.getState());
        rubricRepository.save(rubrica);
    }
}