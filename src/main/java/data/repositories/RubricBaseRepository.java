package data.repositories;

import data.entities.RubricaBase;
import data.entities.composite_keys.RubricaBasePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RubricBaseRepository extends JpaRepository<RubricaBase, RubricaBasePK>{
    @Query(
            value = "SELECT * FROM rubrica_base " +
                    "WHERE cod_curso = :#{#codCourse} ",
            nativeQuery = true
    )
    List<RubricaBase> getRubricBaseByCodCourse(@Param("codCourse") String codCourse);
}