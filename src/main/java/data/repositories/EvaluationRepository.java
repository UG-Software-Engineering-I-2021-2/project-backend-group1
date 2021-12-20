package data.repositories;

import config.endpointClasses.evaluation.EvaluationInterface;
import data.entities.Evalua;
import data.entities.composite_keys.EvaluaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    @Query(
            value = "SELECT  " +
                    "EAR.cod_rubrica AS rubricCode, " +
                    "EAR.calificacion_alumno AS studentGrade, " +
                    "EAR.calificacion_competencia AS competenceGrade, " +
                    "EAR.evaluacion_total AS totalEvaluation, " +
                    "R.estado AS state, " +
                    "RB.nivel AS level," +
                    "RB.criterio_desempeno AS criteria " +
                    "FROM evalua_alumno_rubrica EAR " +
                    "INNER JOIN rubrica R " +
                    "ON R.cod_rubrica = EAR.cod_rubrica AND R.semestre = EAR.semestre " +
                    "INNER JOIN rubrica_base RB " +
                    "ON R.cod_rubrica = RB.cod_rubrica " +
                    "WHERE R.semestre = :#{#semester} " +
                    "AND R.cod_competencia = :#{#competenceCode} ",
            nativeQuery = true
    )
    List<EvaluationInterface> getEvaluationsForStatistics(@Param("semester") String semester,
                                                          @Param("competenceCode") String competenceCode);
}
