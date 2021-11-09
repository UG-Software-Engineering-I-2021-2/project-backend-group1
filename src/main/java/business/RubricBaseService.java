package business;

import data.entities.RubricaBase;
import data.repositories.RubricBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RubricBaseService {
    @Autowired
    private RubricBaseRepository rubricBaseRepository;

    public List<RubricaBase> getRubricBaseByCodCourse(String codCourse) {
        return rubricBaseRepository.getRubricBaseByCodCourse(codCourse);
    }

}
