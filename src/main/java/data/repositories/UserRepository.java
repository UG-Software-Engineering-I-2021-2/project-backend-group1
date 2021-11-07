package data.repositories;

import data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCodEmpleado(Long codEmpleado);
    Optional<User> findByUsername(String username);

    @Query(
            value = "SELECT * FROM usuario WHERE rol = 'Docente' and username = :#{#username}",
            nativeQuery = true
    )
    Optional<User> findTeacherUsername(@Param("username") String username);

    @Query(
            value = "SELECT * FROM usuario",
            nativeQuery = true
    )
    List<User> findAll();
}
