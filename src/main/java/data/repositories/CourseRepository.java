package data.repositories;

import config.endpoint_classes.course.CourseInterface;
import data.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Curso, String> {
    Optional<Curso> findByCodCurso(String codCurso);

    @Query(
            value = "SELECT  " +
                    "R2.name as name, " +
                    "R2.code AS code, " +
                    "R2.count1 AS count1, " +
                    "R2.count2 AS count2, " +
                    "R2.count3 AS count3, " +
                    "R2.count4 AS count4, " +
                    "R2.count5 AS count5, " +
                    "STRING_AGG(Ca.nombre, '|') AS careers " +
                    "FROM( " +
                    "SELECT  " +
                    "R1.nombre AS name,  " +
                    "R1.cod_curso AS code,  " +
                    "COUNT(CASE WHEN estado = 'SinAsignar' THEN 1 END) AS count1,  " +
                    "COUNT(CASE WHEN estado = 'AprobacionPendiente' THEN 1 END) AS count2,  " +
                    "COUNT(CASE WHEN estado = 'DisponibleParaCalificar' THEN 1 END) AS count3,  " +
                    "COUNT(CASE WHEN estado = 'FueraDeFecha' THEN 1 END) AS count4,  " +
                    "COUNT(CASE WHEN estado = 'Cumplidos' THEN 1 END) AS count5 " +
                    "FROM (  " +
                    " SELECT DISTINCT  " +
                    " C.cod_curso,  " +
                    " C.nombre  " +
                    " FROM curso C  " +
                    " INNER JOIN seccion S  " +
                    " ON C.cod_curso = S.cod_curso  " +
                    " WHERE S.semestre = :#{#semester} " +
                    " ) AS R1  " +
                    "LEFT JOIN rubrica R  " +
                    "ON R1.cod_curso = R.cod_curso  " +
                    "GROUP BY R1.cod_curso, R1.nombre " +
                    " )AS R2 " +
                    "LEFT JOIN pertenece_curso_carrera PCC " +
                    "ON R2.code = PCC.cod_curso " +
                    "LEFT JOIN carrera Ca " +
                    "ON Ca.carrera_id = PCC.carrera_id " +
                    "GROUP BY R2.name, R2.code, R2.count1, R2.count2, R2.count3, R2.count4, R2.count5 ",
            nativeQuery = true
    )
    List<CourseInterface> getCourseCalidad(@Param("semester") String semester);

    @Query(
            value = "SELECT  " +
                    "R3.name as name, " +
                    "R3.code AS code, " +
                    "R3.count1 AS count1, " +
                    "R3.count2 AS count2, " +
                    "R3.count3 AS count3, " +
                    "R3.count4 AS count4, " +
                    "R3.count5 AS count5, " +
                    "STRING_AGG(Ca.nombre, '|') AS careers " +
                    "FROM( " +
                    "SELECT " +
                    "R2.nombre AS NAME, " +
                    "R2.cod_curso AS code, " +
                    "COUNT(CASE WHEN estado = 'SinAsignar' THEN 1 END) AS count1, " +
                    "COUNT(CASE WHEN estado = 'AprobacionPendiente' THEN 1 END) AS count2, " +
                    "COUNT(CASE WHEN estado = 'DisponibleParaCalificar' THEN 1 END) AS count3, " +
                    "COUNT(CASE WHEN estado = 'FueraDeFecha' THEN 1 END) AS count4, " +
                    "COUNT(CASE WHEN estado = 'Cumplidos' THEN 1 END) AS count5 " +
                    "FROM ( " +
                    " SELECT curso.cod_curso, curso.nombre " +
                    " FROM( " +
                    " SELECT cod_curso FROM dicta_docente_seccion D " +
                    "INNER JOIN usuario U " +
                    " ON D.usuario_id = U.usuario_id " +
                    " WHERE D.semestre = :#{#semester} " +
                    " AND U.username = :#{#username} " +
                    " UNION " +
                    " SELECT cod_curso FROM coordina_docente_seccion C " +
                    " INNER JOIN usuario U " +
                    " ON C.usuario_id = U.usuario_id " +
                    " WHERE C.semestre = :#{#semester} " +
                    " AND U.username = :#{#username} " +
                    " ) AS R1 " +
                    " LEFT JOIN curso " +
                    " ON R1.cod_curso = curso.cod_curso " +
                    " ) AS R2 " +
                    "LEFT JOIN rubrica R " +
                    "ON R2.cod_curso = R.cod_curso " +
                    "GROUP BY R2.cod_curso, R2.nombre " +
                    " )AS R3 " +
                    "LEFT JOIN pertenece_curso_carrera PCC " +
                    "ON R3.code = PCC.cod_curso " +
                    "LEFT JOIN carrera Ca " +
                    "ON Ca.carrera_id = PCC.carrera_id " +
                    "GROUP BY R3.name, R3.code, R3.count1, R3.count2, R3.count3, R3.count4, R3.count5 ",
            nativeQuery = true
    )
    List<CourseInterface> getCourseDocente(@Param("semester") String semester, @Param("username") String username);
}
