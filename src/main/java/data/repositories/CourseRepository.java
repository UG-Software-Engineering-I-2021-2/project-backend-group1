package data.repositories;

import data.entities.Curso;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Curso, String> {
    Optional<Curso> findByCodCurso(String codCurso);
    /*
    @Query(
            value = "SELECT * FROM usuario WHERE rol = 'Docente' and username = :#{#username}",
            nativeQuery = true
    )
    Optional<User> findTeacherUsername(@Param("username") String username);*/
}
