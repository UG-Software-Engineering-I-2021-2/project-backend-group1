package business;

import business.custom_exceptions.CustomNotFoundException;
import config.endpointClasses.courseEndpoint.Course;
import config.endpointClasses.courseEndpoint.CourseInterface;
import config.endpointClasses.courseEndpoint.CourseInterface2;
import data.dtos.CourseDTO;
import data.entities.Curso;
import data.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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

    public Curso findOneByCodCurso(String codCurso) {
        Optional<Curso> cursoOptional = courseRepository.findByCodCurso(codCurso);
        if (cursoOptional.isPresent()) return cursoOptional.get();
        else throw new CustomNotFoundException("No exite un curso con el codigo: " + codCurso + ".\n");
    }

    public List<Curso> findCursoBySemester(String semester) {
        List<Curso> courseList = courseRepository.findCursoBySemester(semester);
        if (!courseList.isEmpty()) return courseList;
        else throw new CustomNotFoundException("No existe ningun curso con el semestre: " + semester + ".\n");
    }

    public List<Curso> findCursoBySemesterAndUsername(String semester, String username, Boolean isDocent) {
        if(Boolean.TRUE.equals(isDocent))
            return courseRepository.findCursoBySemesterAndUsernameDocente(semester, username);
        else
            return courseRepository.findCursoBySemester(semester);
    }

    public List<Curso> findCursoCoordinedByUsername(String semester, String username) {
        return courseRepository.findCursoCoordinedByUsername(semester, username);
    }

    public List<Course> calidadCourseEndpoint(String semester){
        List<CourseInterface> courseInterfaces = courseRepository.calidadCourseEndpoint(semester);
        List<CourseInterface2> courseInterface2s = courseRepository.calidadCourseEndpointCareers(semester);
        Map<String, List<String>> cursosCarrera = new HashMap<>();
        for(CourseInterface2 courseInterface2 : courseInterface2s) {
            if (cursosCarrera.containsKey(courseInterface2.getCode())) {
                cursosCarrera.get(courseInterface2.getCode()).add(courseInterface2.getName());
            } else {
                List<String> list = new ArrayList<>();
                list.add(courseInterface2.getName());
                cursosCarrera.put(courseInterface2.getCode(), list);
            }
        }
        List<Course> response = new ArrayList<>();
        for(CourseInterface courseInterface : courseInterfaces){
            Course course = new Course(courseInterface);
            course.setCaeers(cursosCarrera.get(courseInterface.getCode()));
            response.add(course);
        }
        return response;
    }
    public List<Course> docenteCourseEndpoint(String semester, String username){
        List<CourseInterface> courseInterfaces = courseRepository.docenteCourseEndpoint(semester, username);
        List<CourseInterface2> courseInterface2s = courseRepository.docenteCourseEndpointCareers(semester, username);
        Map<String, List<String>> cursosCarrera = new HashMap<>();
        for(CourseInterface2 courseInterface2 : courseInterface2s){
            if(cursosCarrera.containsKey(courseInterface2.getCode())){
                cursosCarrera.get(courseInterface2.getCode()).add(courseInterface2.getName());
            }else{
                List<String> list = new ArrayList<>();
                list.add(courseInterface2.getName());
                cursosCarrera.put(courseInterface2.getCode(), list);
            }
        }
        List<Course> response = new ArrayList<>();
        for(CourseInterface courseInterface : courseInterfaces){
            Course course = new Course(courseInterface);
            course.setCaeers(cursosCarrera.get(courseInterface.getCode()));
            response.add(course);
        }
        return response;
    }


}
