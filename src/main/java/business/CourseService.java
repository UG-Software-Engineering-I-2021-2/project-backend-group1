package business;

import config.endpoint_classes.course.Course;
import config.endpoint_classes.course.CourseInterface;
import config.enums.Role;
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

    public List<Course> getCourse(String semester, String username, String role){
        List<CourseInterface> courseInterfaces = null;
        if(role.equals(Role.Docente.toString()))
            courseInterfaces = courseRepository.getCourseDocente(semester, username);
        else
            courseInterfaces = courseRepository.getCourseCalidad(semester);
        List<Course> response = new ArrayList<>();
        for(CourseInterface courseInterface : courseInterfaces){
            Course course = new Course(courseInterface);
            response.add(course);
        }
        return response;
    }
}
