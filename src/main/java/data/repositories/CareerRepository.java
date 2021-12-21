package data.repositories;

import config.endpoint_classes.career.CareerInterface;
import data.entities.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CareerRepository extends JpaRepository<Carrera, Integer> {
    @Query(
            value = "SELECT carrera_id as id, nombre as name FROM carrera",
            nativeQuery = true
    )
    List<CareerInterface> getAll();
}
