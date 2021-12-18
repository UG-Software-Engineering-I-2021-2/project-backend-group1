package business;

import config.endpointClasses.career.Career;
import config.endpointClasses.career.CareerInterface;
import data.repositories.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CareerService {
    @Autowired
    private CareerRepository careerRepository;

    public List<Career> getAll(){
        List<CareerInterface> careerInterfaceList = careerRepository.getAll();
        List<Career> response = new ArrayList<>();
        for(CareerInterface careerInterface : careerInterfaceList){
            Career career = new Career(careerInterface);
            response.add(career);
        }
        return response;
    }

}
