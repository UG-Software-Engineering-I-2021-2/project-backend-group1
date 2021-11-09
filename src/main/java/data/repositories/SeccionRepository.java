package data.repositories;

import data.entities.Curso;
import data.entities.Seccion;
import data.entities.composite_keys.SeccionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeccionRepository extends JpaRepository<Seccion, SeccionPK> {
}
