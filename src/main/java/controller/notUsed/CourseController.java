package controller.notUsed;

import business.CourseService;
import business.UserService;
import data.entities.Curso;
import data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Curso> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{semestre}")
    public List<Curso> getCoursesBySemester(@PathVariable String semester) {
        return courseService.findCursoBySemester(semester);
    }
}
