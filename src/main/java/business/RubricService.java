package business;

import config.endpointClasses.courseEndpoint.Course;
import config.endpointClasses.courseEndpoint.CourseInterface;
import config.endpointClasses.courseEndpoint.CourseInterface2;
import config.endpointClasses.rubricCreationEndpoint.Rubric;
import config.endpointClasses.rubricCreationEndpoint.RubricInterface;
import data.entities.Rubrica;
import data.repositories.RubricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RubricService {
    @Autowired
    private RubricRepository rubricRepository;

    public List<Rubrica> getRubricByCodRubricBaseAndSemester(String codRubricBase, String semester) {
        return rubricRepository.getRubricByCodRubricBaseAndSemester(codRubricBase, semester);
    }

    public Rubric getRubricCreation(String codRubrica, String semester, String codCourse){
        List<RubricInterface> rubricInterfaceList = rubricRepository.getRubricCreation(codRubrica, semester, codCourse);
        Rubric response = new Rubric(rubricInterfaceList.get(0));
        response.setCycles(rubricInterfaceList);
        return response;
    }
}