package business;

import business.custom_exceptions.CustomNotFoundException;
import config.endpointClasses.course.Course;
import config.endpointClasses.course.CourseInterface;
import config.endpointClasses.course.CourseInterface2;
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

    public Curso findOneByCodCurso(String codCurso) {
        Optional<Curso> cursoOptional = courseRepository.findByCodCurso(codCurso);
        if (cursoOptional.isPresent()) return cursoOptional.get();
        else throw new CustomNotFoundException("No exite un curso con el codigo: " + codCurso + ".\n");
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
