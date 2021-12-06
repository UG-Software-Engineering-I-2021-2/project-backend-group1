package business;

import config.endpointClasses.rubric.Rubric;
import config.endpointClasses.rubric.RubricInterface;
import config.endpointClasses.rubric.RubricUpdate;
import config.endpointClasses.rubricCreation.RubricCreation;
import config.endpointClasses.rubricCreation.RubricCreationInterface;
import config.endpointClasses.rubricImport.RubricImport;
import config.endpointClasses.rubricImport.RubricImportInterface;
import config.endpointClasses.rubricSection.RubricSection;
import config.endpointClasses.rubricStudents.RubricStudents;
import config.endpointClasses.rubricStudents.RubricStudentsInterface;
import config.enums.State;
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
        RubricCreationInterface rubricCreationInterface = rubricRepository.getRubricCreation(codRubrica, semester, codCourse, "");
        return new RubricCreation(rubricCreationInterface);
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

    public List<RubricImport> getRubricImport(String username, String semester, String courseCode, String rubricCode){
        String semesterPrev = "";
        if(semester.charAt(semester.length()-1) == '2'){
            semesterPrev = semester.substring(0, semester.length() - 1);
            semesterPrev += '1';
        }else {
            String[] parts = semester.split("-");
            semesterPrev = String.valueOf(Integer.parseInt(parts[0].trim()) - 1) + " - 2";
        }
        System.out.println("Semester: " + semester);
        System.out.println("SemesterPrev: " + semesterPrev);
        List<RubricImportInterface> rubricImportInterfaceList = rubricRepository.getRubricImport(username, semester, semesterPrev, courseCode, rubricCode);
        List<RubricImport> response = new ArrayList<>();
        for(RubricImportInterface rubricImportInterface : rubricImportInterfaceList){
            RubricImport rubricImport = new RubricImport(rubricImportInterface);
            if(!rubricImport.getContent().equals(""))
                response.add(rubricImport);
        }
        return response;
    }

    public void updateRubricState(String rubricCode, String semester, State newState) {
        Rubrica rubrica = rubricRepository.getRubricByRubricCodeAndSemester(rubricCode, semester);
        rubrica.setEstado(newState);
        rubricRepository.save(rubrica);
    }

    public RubricSection getRubricSection(String rubricCode, String semester, String courseCode, String username, String role){
        RubricCreationInterface rubricCreationInterface = rubricRepository.getRubricCreation(rubricCode, semester, courseCode, username);
        return new RubricSection(rubricCreationInterface, role);
    }

    public List<RubricStudents> getRubricStudents(String rubricCode, String semester, String courseCode, String section){
        List<RubricStudentsInterface> rubricStudentsInterfaceList = rubricRepository.getRubricStudents(rubricCode, semester, courseCode, section);
        List<RubricStudents> response = new ArrayList<>();
        for(RubricStudentsInterface rubricStudentsInterface : rubricStudentsInterfaceList){
            RubricStudents rubricStudents = new RubricStudents(rubricStudentsInterface);
            response.add(rubricStudents);
        }
        return response;
    }
}