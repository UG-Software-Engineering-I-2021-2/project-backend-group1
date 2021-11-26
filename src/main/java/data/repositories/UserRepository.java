package data.repositories;

import config.endpointClasses.user.CoordinatorInterface;
import data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(
            value = "SELECT cooremail FROM project.coordinador_cursos " +
                    "WHERE codcurso = :#{#courseCode};",
            nativeQuery = true
    )
    List<CoordinatorInterface> findCourseCoordinatorsUsername(@Param("courseCode") String courseCode);
}
