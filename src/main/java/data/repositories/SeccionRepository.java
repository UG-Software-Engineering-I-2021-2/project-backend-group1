package data.repositories;

import data.entities.Seccion;
import data.entities.composite_keys.SeccionPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeccionRepository extends JpaRepository<Seccion, SeccionPK> {
}
