package data.repositories;

import data.entities.Rubrica;
import data.entities.composite_keys.RubricaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RubricRepository extends JpaRepository<Rubrica, RubricaPK> {
    @Query(
            value = "SELECT * FROM rubrica " +
                    "WHERE cod_rubrica = :#{#codRubricBase} " +
                    "AND semestre = :#{#semester} ",
            nativeQuery = true
    )
    List<Rubrica> getRubricByCodRubricBaseAndSemester(
            @Param("codRubricBase") String codRubricBase,
            @Param("semester") String semester
            );
}
