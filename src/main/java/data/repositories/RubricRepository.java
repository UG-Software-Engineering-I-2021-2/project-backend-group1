package data.repositories;

import config.endpointClasses.rubricCreationEndpoint.RubricInterface;
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

    @Query(
            value = "SELECT  " +
                    "R1.curso AS course, " +
                    "R1.actividad AS activity, " +
                    "R1.semana AS week, " +
                    "R1.competencia AS competence, " +
                    "R1.criterio AS criteria, " +
                    "R1.criterio_nivel AS criteriaLevel, " +
                    "R1.fecha AS date, " +
                    "R1.dimensiones AS dimensions, " +
                    "R1.descriptores AS descriptors, " +
                    "R1.evaluacion AS evaluation, " +
                    "R1.evidencia AS evidence, " +
                    "R2.ciclo AS cycle " +
                    "FROM " +
                    "( " +
                    "SELECT  " +
                    " C.nombre AS curso, " +
                    " R.actividad AS actividad, " +
                    " RB.semana AS semana, " +
                    " Co.descripcion AS competencia, " +
                    " RB.criterio_desempeno AS criterio, " +
                    " RB.nivel AS criterio_nivel, " +
                    " R.fecha AS fecha, " +
                    " R.dimensiones AS dimensiones, " +
                    " R.descriptores AS descriptores, " +
                    " RB.evaluacion AS evaluacion, " +
                    " RB.evidencia AS evidencia " +
                    "FROM rubrica R " +
                    "INNER JOIN rubrica_base RB " +
                    "ON R.cod_rubrica = RB.cod_rubrica  " +
                    "AND R.cod_rubrica = :#{#codRubrica} " +
                    "AND R.semestre = :#{#semester} " +
                    "INNER JOIN curso C " +
                    "ON C.cod_curso = :#{#codCourse} " +
                    "INNER JOIN competencia Co " +
                    "ON R.cod_competencia = Co.cod_competencia " +
                    " ) AS R1, " +
                    "( " +
                    "select distinct ciclo from lleva_alumno_seccion " +
                    "where semestre = :#{#semester} " +
                    "and cod_curso = :#{#codCourse} " +
                    " ) AS R2 ",
            nativeQuery = true
    )
    List<RubricInterface> getRubricCreation(
            @Param("codRubrica") String codRubrica,
            @Param("semester") String semester,
            @Param("codCourse") String codCourse
    );
}
