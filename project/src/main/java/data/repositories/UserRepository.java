package data.repositories;

import data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCodEmpleado(Long codEmpleado);
    Optional<User> findByUsername(String username);
}
