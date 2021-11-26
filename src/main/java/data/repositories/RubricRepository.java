package data.repositories;

import config.endpointClasses.rubric.RubricInterface;
import config.endpointClasses.rubricCreation.RubricCreationInterface;
import config.endpointClasses.rubricImport.RubricImportInterface;
import data.entities.Rubrica;
import data.entities.composite_keys.RubricaPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RubricRepository extends JpaRepository<Rubrica, RubricaPK> {
    @Query(
            value = "SELECT " +
                    "R.cod_rubrica AS code, " +
                    "R.estado AS state, " +
                    "RB.evaluacion AS evaluation, " +
                    "R.fecha AS ddate, " +
                    "RB.semana AS week, " +
                    "RB.evidencia AS evidence, " +
                    "R.actividad AS activity, " +
                    "CASE WHEN :#{#courseCode} IN (SELECT DISTINCT " +
                    " cod_curso " +
                    " FROM( " +
                    " SELECT " +
                    " usuario_id  " +
                    " FROM usuario " +
                    " WHERE username = :#{#username} " +
                    "  ) AS user_id " +
                    " INNER JOIN coordina_docente_seccion AS CDS " +
                    " ON user_id.usuario_id = CDS.usuario_id " +
                    ") THEN 1 ELSE 0 END AS coordinates, " +
                    "(SELECT COUNT(DISTINCT cod_alumno) FROM lleva_alumno_seccion " +
                    "WHERE cod_curso = :#{#courseCode} " +
                    "AND semestre = :#{#semester}) AS students, " +
                    "RB.nivel AS dlevel, " +
                    "R.titulo AS title," +
                    "R.cod_competencia AS competenceCode, " +
                    "RB.criterio_desempeno AS criteriaCode " +
                    "FROM " +
                    "rubrica AS R " +
                    "LEFT JOIN rubrica_base AS RB " +
                    "ON R.cod_rubrica = RB.cod_rubrica " +
                    "WHERE R.semestre = :#{#semester} " +
                    "AND R.cod_curso = :#{#courseCode} ",
            nativeQuery = true
    )
    List<RubricInterface> getRubric(
            @Param("semester") String semester,
            @Param("courseCode") String courseCode,
            @Param("username") String username
    );

    @Query(
            value = "SELECT  " +
                    "R1.curso AS course, " +
                    "R1.actividad AS activity, " +
                    "R1.semana AS week, " +
                    "R1.cod_competencia AS codCompetence, " +
                    "R1.competencia AS competence, " +
                    "R1.criterio AS criteria, " +
                    "R1.criterio_nivel AS criteriaLevel, " +
                    "R1.fecha AS date, " +
                    "R1.descriptores AS content, " +
                    "R1.evaluacion AS evaluation, " +
                    "R1.evidencia AS evidence, " +
                    "R2.ciclo AS cycle, " +
                    "R1.titulo AS title, " +
                    "R1.estado AS state " +
                    "FROM " +
                    "( " +
                    "SELECT  " +
                    " C.nombre AS curso, " +
                    " R.actividad AS actividad, " +
                    " RB.semana AS semana, " +
                    " Co.cod_competencia AS cod_competencia, " +
                    " Co.descripcion AS competencia, " +
                    " RB.criterio_desempeno AS criterio, " +
                    " RB.nivel AS criterio_nivel, " +
                    " R.fecha AS fecha, " +
                    " R.descriptores AS descriptores, " +
                    " RB.evaluacion AS evaluacion, " +
                    " RB.evidencia AS evidencia, " +
                    " R.titulo AS titulo, " +
                    " R.estado AS estado " +
                    "FROM rubrica R " +
                    "INNER JOIN rubrica_base RB " +
                    "ON R.cod_rubrica = RB.cod_rubrica " +
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
    List<RubricCreationInterface> getRubricCreation(
            @Param("codRubrica") String codRubrica,
            @Param("semester") String semester,
            @Param("codCourse") String codCourse
    );

    @Query(
            value = "SELECT * FROM rubrica WHERE cod_rubrica = :#{#rubricCode} AND semestre = :#{#semester} ",
            nativeQuery = true
    )
    Rubrica getRubricByRubricCodeAndSemester(@Param("rubricCode") String rubricCode, @Param("semester") String semester);

    @Query(
            value = "SELECT  " +
                    "C.nombre AS course, " +
                    "R.cod_curso AS codCourse, " +
                    "R.titulo AS title, " +
                    "R.descriptores AS content, " +
                    "R.semestre AS semester " +
                    "FROM rubrica R LEFT JOIN curso C ON R.cod_curso = C.cod_curso WHERE R.cod_curso IN( " +
                    " SELECT cod_curso FROM coordina_docente_seccion CDS LEFT JOIN usuario U ON CDS.usuario_id = U.usuario_id " +
                    " WHERE U.username = :#{#username} AND CDS.semestre = :#{#semester} " +
                    ") AND R.semestre = :#{#semester} " +
                    "UNION " +
                    "SELECT  " +
                    "C.nombre AS course, " +
                    "R.cod_curso AS codCourse, " +
                    "R.titulo AS title, " +
                    "R.descriptores AS content, " +
                    "R.semestre AS semester " +
                    "FROM rubrica R LEFT JOIN curso C ON R.cod_curso = C.cod_curso WHERE R.cod_curso IN( " +
                    " SELECT cod_curso FROM coordina_docente_seccion CDS LEFT JOIN usuario U ON CDS.usuario_id = U.usuario_id " +
                    " WHERE U.username = :#{#username} AND CDS.semestre = :#{#semesterPrev} " +
                    ") AND R.semestre = :#{#semesterPrev} " +
                    "UNION " +
                    "SELECT  " +
                    "C.nombre AS course, " +
                    "R.cod_curso AS codCourse, " +
                    "R.titulo AS title, " +
                    "R.descriptores AS content , " +
                    "R.semestre AS semester " +
                    "FROM rubrica R LEFT JOIN curso C ON R.cod_curso = C.cod_curso WHERE R.cod_rubrica = :#{#rubricCode} " +
                    "AND R.cod_curso = :#{#courseCode} AND R.semestre = :#{#semesterPrev} ",
            nativeQuery = true
    )
    List<RubricImportInterface> getRubricImport(
            @Param("username") String username,
            @Param("semester") String semester,
            @Param("semesterPrev") String semesterPrev,
            @Param("courseCode") String courseCode,
            @Param("rubricCode") String rubricCode
    );




}
