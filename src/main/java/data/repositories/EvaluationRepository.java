package data.repositories;

import data.entities.Evalua;
import data.entities.composite_keys.EvaluaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EvaluationRepository extends JpaRepository<Evalua, EvaluaPK> {
    @Query(
            value = "SELECT * FROM evalua_alumno_rubrica " +
                    "WHERE cod_alumno = :#{#studentCode} AND cod_curso = :#{#courseCode} AND cod_rubrica = :#{#rubricCode} AND semestre = :#{#semester}",
            nativeQuery = true
    )
    Evalua getEvalua(@Param("rubricCode") String rubricCode,
                     @Param("semester") String semester,
                     @Param("courseCode") String courseCode,
                     @Param("studentCode") String studentCode);
}
