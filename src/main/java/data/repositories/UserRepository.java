package data.repositories;

import config.endpoint_classes.user.CoordinatorInterface;
import data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query(
            value = "SELECT username FROM (" +
                    "SELECT DISTINCT usuario_id " +
                    "FROM coordina_docente_seccion " +
                    "WHERE semestre = :#{#semester} " +
                    "AND cod_curso = :#{#courseCode} " +
                    ") u1 " +
                    "JOIN usuario u2 " +
                    "ON u1.usuario_id = u2.usuario_id;",
            nativeQuery = true
    )
    List<CoordinatorInterface> findCourseCoordinatorsUsername(
            @Param("semester") String semester,
            @Param("courseCode") String courseCode
    );
}
