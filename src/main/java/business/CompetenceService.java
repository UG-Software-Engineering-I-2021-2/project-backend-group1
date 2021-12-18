package business;

import config.endpointClasses.competence.Competence;
import config.endpointClasses.competence.CompetenceInterface;
import data.repositories.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompetenceService {
    @Autowired
    private CompetenceRepository competenceRepository;

    public List<Competence> getAllByCareer(Integer careerId){
        List<CompetenceInterface> competenceInterfaceList = competenceRepository.getAllByCareer(careerId);
        List<Competence> response = new ArrayList<>();
        for(CompetenceInterface competenceInterface : competenceInterfaceList){
            Competence competence = new Competence(competenceInterface);
            response.add(competence);
        }
        return response;
    }
}
