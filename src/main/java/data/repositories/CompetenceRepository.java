package data.repositories;

import config.endpointClasses.competence.CompetenceInterface;
import data.entities.Competencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompetenceRepository extends JpaRepository<Competencia, String> {
    @Query(
            value = "SELECT cod_competencia AS code, descripcion AS description " +
                    "FROM competencia WHERE carrera_id = CAST(:#{#carreraId} AS INTEGER)",
            nativeQuery = true
    )
    List<CompetenceInterface> getAllByCareer(@Param("carreraId") int carreraId);
}
