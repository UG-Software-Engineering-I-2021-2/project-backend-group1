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

    @Query(
            value = "SELECT * FROM curso " +
                    "WHERE curso.cod_curso IN (" +
                    "SELECT cod_curso FROM seccion " +
                    "WHERE semestre = :#{#semestre}" +
                    ")",
            nativeQuery = true
    )
    List<Curso> findCursoBySemestre(@Param("semestre") String semestre);

    @Query(
            value = "SELECT * FROM curso " +
                    "WHERE curso.cod_curso IN ( " +
                    "SELECT cod_curso FROM dicta_docente_seccion D " +
                    "INNER JOIN usuario U " +
                    "ON D.usuario_id = U.usuario_id " +
                    "WHERE D.semestre = :#{#semestre} " +
                    "AND U.username = :#{#username}) ",
            nativeQuery = true
    )
    List<Curso> findCursoBySemestreAndUsernameDocente(
            @Param("semestre") String semestre,
            @Param("username") String username);
}
