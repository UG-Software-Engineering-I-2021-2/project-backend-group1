package business;

import config.endpoint_classes.rubric.Rubric;
import config.endpoint_classes.rubric.RubricInterface;
import config.endpoint_classes.rubric.RubricUpdate;
import config.endpoint_classes.rubric_creation.RubricCreation;
import config.endpoint_classes.rubric_creation.RubricCreationInterface;
import config.endpoint_classes.rubric_import.RubricImport;
import config.endpoint_classes.rubric_import.RubricImportInterface;
import config.endpoint_classes.rubric_section.RubricSection;
import config.endpoint_classes.rubric_students.RubricStudent;
import config.endpoint_classes.rubric_students.RubricStudentInterface;
import config.endpoint_classes.student.StudentInterface;
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

    public List<StudentInterface> getStudents(String rubricCode, String semester, String courseCode, String section){
        return rubricRepository.getStudents(rubricCode, semester, courseCode, section);
    }

    public RubricStudent getRubricStudent(String rubricCode, String semester, String courseCode, String studentCode){
        RubricStudentInterface rubricStudentInterface = rubricRepository.getRubricStudent(rubricCode, semester, courseCode, studentCode);
        return new RubricStudent(rubricStudentInterface);
    }
}