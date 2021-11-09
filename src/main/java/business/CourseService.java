package business;

import business.custom_exceptions.CustomNotFoundException;
import data.dtos.CourseDTO;
import data.entities.Curso;
import data.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Curso save(CourseDTO courseDTO) {
        var curso = new Curso();
        curso.setNombre(courseDTO.getNombre());
        return courseRepository.save(curso);
    }

    public List<Curso> findAll() {
        List<Curso> courseList = courseRepository.findAll();
        if (!courseList.isEmpty()) return courseList;
        else throw new CustomNotFoundException("No existe ningun curso.\n");
    }

    public Curso findOneByCodCurso(String codCurso) {
        Optional<Curso> cursoOptional = courseRepository.findByCodCurso(codCurso);
        if (cursoOptional.isPresent()) return cursoOptional.get();
        else throw new CustomNotFoundException("No exite un curso con el codigo: " + codCurso + ".\n");
    }

    public List<Curso> findCursoBySemestre(String semestre) {
        List<Curso> courseList = courseRepository.findCursoBySemestre(semestre);
        if (!courseList.isEmpty()) return courseList;
        else throw new CustomNotFoundException("No existe ningun curso con el semestre: " + semestre + ".\n");
    }

    public List<Curso> findCursoBySemestreAndUsername(String semestre, String username, Boolean isDocent) {
        if(Boolean.TRUE.equals(isDocent))
            return courseRepository.findCursoBySemestreAndUsernameDocente(semestre, username);
        else
            return courseRepository.findCursoBySemestre(semestre);
    }
}
