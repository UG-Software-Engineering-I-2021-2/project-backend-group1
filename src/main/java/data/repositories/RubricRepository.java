package data.repositories;

import config.endpointClasses.rubric.RubricInterface;
import config.endpointClasses.rubricCreation.RubricCreationInterface;
import config.endpointClasses.rubricImport.RubricImportInterface;
import config.endpointClasses.rubricStudents.RubricStudentInterface;
import config.endpointClasses.student.StudentInterface;
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
                    " WHERE semestre = :#{#semester} " +
                    ") THEN 1 ELSE 0 END AS coordinates, " +
                    "(SELECT COUNT(DISTINCT cod_alumno) FROM lleva_alumno_seccion " +
                    "WHERE cod_curso = :#{#courseCode} " +
                    "AND semestre = :#{#semester}) AS students, " +
                    "RB.nivel AS dlevel, " +
                    "R.titulo AS title," +
                    "R.cod_competencia AS competenceCode, " +
                    "RB.criterio_desempeno AS criteriaCode," +
                    "CASE WHEN :#{#courseCode} IN (SELECT DISTINCT  " +
                    " cod_curso  " +
                    " FROM(  " +
                    " SELECT  " +
                    " usuario_id   " +
                    " FROM usuario  " +
                    " WHERE username = :#{#username}  " +
                    "  ) AS user_id  " +
                    " INNER JOIN dicta_docente_seccion AS DDS  " +
                    " ON user_id.usuario_id = DDS.usuario_id " +
                    " WHERE semestre = :#{#semester} " +
                    ") THEN 1 ELSE 0 END AS grade " +
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
            value = "SELECT   " +
                    " C.nombre AS course,  " +
                    " R.actividad AS activity,  " +
                    " RB.semana AS week,  " +
                    " Co.cod_competencia AS codCompetence,  " +
                    " Co.descripcion AS competence,  " +
                    " RB.criterio_desempeno AS criteria,  " +
                    " RB.nivel AS criteriaLevel,  " +
                    " R.fecha AS date,  " +
                    " R.descriptores AS content,  " +
                    " RB.evaluacion AS evaluation,  " +
                    " RB.evidencia AS evidence,  " +
                    " (SELECT STRING_AGG(DISTINCT CAST(ciclo AS VARCHAR),'|') FROM lleva_alumno_seccion  " +
                    " WHERE semestre = :#{#semester} AND cod_curso = :#{#codCourse} ) AS cycle, " +
                    " R.titulo AS title,  " +
                    " R.estado AS state, " +
                    " (SELECT STRING_AGG(cod_seccion,'|') FROM dicta_docente_seccion WHERE semestre = :#{#semester}  " +
                    "  AND cod_curso = :#{#codCourse} AND usuario_id = (SELECT usuario_id FROM usuario WHERE username = :#{#username})) AS sections, " +
                    " (SELECT STRING_AGG(cod_seccion,'|') FROM ( " +
                    "   SELECT DISTINCT cod_seccion FROM dicta_docente_seccion WHERE semestre = :#{#semester} AND cod_curso = :#{#codCourse} UNION " +
                    "   SELECT DISTINCT cod_seccion FROM coordina_docente_seccion WHERE semestre = :#{#semester} AND cod_curso = :#{#codCourse}) AS sec " +
                    " ) AS allSections, " +
                    "CASE WHEN :#{#codCourse} IN (SELECT DISTINCT  " +
                    " cod_curso  " +
                    " FROM(  " +
                    " SELECT  " +
                    " usuario_id   " +
                    " FROM usuario  " +
                    " WHERE username = :#{#username}  " +
                    "  ) AS user_id  " +
                    " INNER JOIN dicta_docente_seccion AS DDS  " +
                    " ON user_id.usuario_id = DDS.usuario_id " +
                    " WHERE semestre = :#{#semester} " +
                    ") THEN 1 ELSE 0 END AS grade " +
                    "FROM rubrica R  " +
                    "INNER JOIN rubrica_base RB  " +
                    "ON R.cod_rubrica = RB.cod_rubrica  " +
                    "AND R.cod_rubrica = :#{#codRubrica}  " +
                    "AND R.semestre = :#{#semester}  " +
                    "INNER JOIN curso C  " +
                    "ON C.cod_curso = :#{#codCourse}  " +
                    "INNER JOIN competencia Co  " +
                    "ON R.cod_competencia = Co.cod_competencia  ",
            nativeQuery = true
    )
    RubricCreationInterface getRubricCreation(
            @Param("codRubrica") String codRubrica,
            @Param("semester") String semester,
            @Param("codCourse") String codCourse,
            @Param("username") String username
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

    @Query(
            value = "SELECT  " +
                    "EAR.cod_alumno AS studentCode,  " +
                    "A.nombre AS studentName  " +
                    "FROM evalua_alumno_rubrica EAR  " +
                    "LEFT JOIN alumno A ON EAR.cod_alumno = A.cod_alumno  " +
                    "WHERE EAR.semestre = :#{#semester} " +
                    "AND EAR.cod_curso = :#{#courseCode} " +
                    "AND EAR.cod_rubrica = :#{#rubricCode} " +
                    "AND EAR.cod_alumno IN ( " +
                    " SELECT cod_alumno " +
                    " FROM lleva_alumno_seccion " +
                    " WHERE cod_curso = :#{#courseCode} " +
                    " AND semestre = :#{#semester} " +
                    " AND cod_seccion = :#{#section} " +
                    ") ",
            nativeQuery = true
    )
    List<StudentInterface> getStudents(
            @Param("rubricCode") String rubricCode,
            @Param("semester") String semester,
            @Param("courseCode") String courseCode,
            @Param("section") String section
    );

    @Query(
            value = "SELECT  " +
                    "EAR.cod_alumno AS studentCode,  " +
                    "EAR.calificacion_alumno AS calificacionAlumno,  " +
                    "EAR.calificacion_competencia AS calificacionCompetencia,  " +
                    "EAR.evaluacion_total AS EvaluacionTotal  " +
                    "FROM evalua_alumno_rubrica EAR  " +
                    "WHERE EAR.semestre = :#{#semester} " +
                    "AND EAR.cod_curso = :#{#courseCode} " +
                    "AND EAR.cod_rubrica = :#{#rubricCode} " +
                    "AND EAR.cod_alumno = :#{#studentCode} ",
            nativeQuery = true
    )
    RubricStudentInterface getRubricStudent(
            @Param("rubricCode") String rubricCode,
            @Param("semester") String semester,
            @Param("courseCode") String courseCode,
            @Param("studentCode") String studentCode
    );


}
