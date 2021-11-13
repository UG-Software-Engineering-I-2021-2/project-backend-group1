package data.repositories;

import config.endpointClasses.CourseInterface;
import config.endpointClasses.CourseInterface2;
import data.entities.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Curso, String> {
    Optional<Curso> findByCodCurso(String codCurso);

    @Query(
            value = "SELECT * FROM curso " +
                    "WHERE curso.cod_curso IN (" +
                    "SELECT cod_curso FROM seccion " +
                    "WHERE semestre = :#{#semester}" +
                    ")",
            nativeQuery = true
    )
    List<Curso> findCursoBySemester(@Param("semester") String semester);

    @Query(
            value = "SELECT * FROM curso " +
                    "WHERE cod_curso IN ( " +
                    "SELECT cod_curso FROM dicta_docente_seccion D " +
                    "INNER JOIN usuario U " +
                    "ON D.usuario_id = U.usuario_id " +
                    "WHERE D.semestre = :#{#semester} " +
                    "AND U.username = :#{#username} " +
                    "UNION " +
                    "SELECT cod_curso FROM coordina_docente_seccion C " +
                    "INNER JOIN usuario U " +
                    "ON C.usuario_id = U.usuario_id " +
                    "WHERE C.semestre = :#{#semester} " +
                    "AND U.username = :#{#username} )",
            nativeQuery = true
    )
    List<Curso> findCursoBySemesterAndUsernameDocente(
            @Param("semester") String semester,
            @Param("username") String username);

    @Query(
            value = "SELECT * FROM curso " +
                    "WHERE cod_curso IN ( " +
                    "SELECT cod_curso FROM coordina_docente_seccion C " +
                    "INNER JOIN usuario U " +
                    "ON C.usuario_id = U.usuario_id " +
                    "WHERE C.semestre = :#{#semester} " +
                    "AND U.username = :#{#username} ) ",
            nativeQuery = true
    )
    List<Curso> findCursoCoordinedByUsername(
            @Param("semester") String semester,
            @Param("username") String username);

    @Query(
            value = "select " +
                    "R1.nombre as name, " +
                    "R1.cod_curso as code, " +
                    "COUNT(CASE WHEN estado = 'SinAsignar' THEN 1 END) AS count1, " +
                    "COUNT(CASE WHEN estado = 'AprobacionPendiente' THEN 1 END) AS count2, " +
                    "COUNT(CASE WHEN estado = 'DisponibleParaCalificar' THEN 1 END) AS count3, " +
                    "COUNT(CASE WHEN estado = 'FueraDeFecha' THEN 1 END) AS count4, " +
                    "COUNT(CASE WHEN estado = 'Cumplidos' THEN 1 END) AS count5 " +
                    "from ( " +
                    " select distinct " +
                    " C.cod_curso, " +
                    " C.nombre " +
                    " from curso C " +
                    " inner join seccion S " +
                    " ON C.cod_curso = S.cod_curso " +
                    " where S.semestre = :#{#semester} " +
                    " ) as R1 " +
                    "left join rubrica R " +
                    "on R1.cod_curso = R.cod_curso " +
                    "group by R1.cod_curso, R1.nombre  ",
            nativeQuery = true
    )
    List<CourseInterface> calidadCourseEndpoint(@Param("semester") String semester);

    @Query(
            value = "select distinct " +
                    "C.nombre as name, " +
                    "P.cod_curso as code " +
                    "from pertenece_curso_carrera P " +
                    "inner join seccion S " +
                    "on S.cod_curso = P.cod_curso " +
                    "inner join carrera C " +
                    "on C.carrera_id = P.carrera_id " +
                    "where S.semestre = :#{#semester}  ",
            nativeQuery = true
    )
    List<CourseInterface2> calidadCourseEndpointCareers(@Param("semester") String semester);

    @Query(
            value = "select " +
                    "R2.nombre as name, " +
                    "R2.cod_curso as code, " +
                    "COUNT(CASE WHEN estado = 'SinAsignar' THEN 1 END) AS count1, " +
                    "COUNT(CASE WHEN estado = 'AprobacionPendiente' THEN 1 END) AS count2, " +
                    "COUNT(CASE WHEN estado = 'DisponibleParaCalificar' THEN 1 END) AS count3, " +
                    "COUNT(CASE WHEN estado = 'FueraDeFecha' THEN 1 END) AS count4, " +
                    "COUNT(CASE WHEN estado = 'Cumplidos' THEN 1 END) AS count5 " +
                    "from ( " +
                    " SELECT curso.cod_curso, curso.nombre  " +
                    " FROM( " +
                    " SELECT cod_curso FROM dicta_docente_seccion D  " +
                    " INNER JOIN usuario U  " +
                    " ON D.usuario_id = U.usuario_id  " +
                    " WHERE D.semestre = :#{#semester} " +
                    " AND U.username = :#{#username} " +
                    " UNION  " +
                    " SELECT cod_curso FROM coordina_docente_seccion C  " +
                    " INNER JOIN usuario U  " +
                    " ON C.usuario_id = U.usuario_id  " +
                    " WHERE C.semestre = :#{#semester} " +
                    " AND U.username = :#{#username} " +
                    " ) AS R1 " +
                    " LEFT JOIN curso " +
                    " ON R1.cod_curso = curso.cod_curso " +
                    " ) as R2 " +
                    "left join rubrica R " +
                    "on R2.cod_curso = R.cod_curso " +
                    "group by R2.cod_curso, R2.nombre  ",
            nativeQuery = true
    )
    List<CourseInterface> docenteCourseEndpoint(@Param("semester") String semester, @Param("username") String username);

    @Query(
            value = "SELECT  " +
                    "C.nombre as name, " +
                    "R.cod_curso as code " +
                    "FROM( " +
                    " SELECT cod_curso FROM dicta_docente_seccion D  " +
                    " INNER JOIN usuario U  " +
                    " ON D.usuario_id = U.usuario_id  " +
                    " WHERE D.semestre = :#{#semester} " +
                    " AND U.username = :#{#username} " +
                    " UNION  " +
                    " SELECT cod_curso FROM coordina_docente_seccion C  " +
                    " INNER JOIN usuario U  " +
                    " ON C.usuario_id = U.usuario_id  " +
                    " WHERE C.semestre = :#{#semester} " +
                    " AND U.username = :#{#username} " +
                    ") AS R " +
                    "INNER JOIN pertenece_curso_carrera P  " +
                    "ON P.cod_curso = R.cod_curso " +
                    "INNER JOIN carrera C " +
                    "on C.carrera_id = P.carrera_id   ",
            nativeQuery = true
    )
    List<CourseInterface2> docenteCourseEndpointCareers(@Param("semester") String semester, @Param("username") String username);

}
