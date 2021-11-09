package business;

import data.entities.Rubrica;
import data.repositories.RubricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RubricService {
    @Autowired
    private RubricRepository rubricRepository;

    public List<Rubrica> getRubricByCodRubricBaseAndSemester(String codRubricBase, String semester) {
        return rubricRepository.getRubricByCodRubricBaseAndSemester(codRubricBase, semester);
    }
}