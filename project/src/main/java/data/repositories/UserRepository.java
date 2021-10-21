package data.repositories;

import data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByCodEmpleado(Long codEmpleado);
    List<User> findByUsername(String username);
}
