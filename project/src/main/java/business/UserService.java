package business;

import business.custom_exceptions.CustomNotFoundException;
import data.dtos.UserDTO;
import data.entities.User;
import data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User save (UserDTO userDTO) {
        var user = new User();
        user.setCodEmpleado(userDTO.getCodEmpleado());
        user.setUsername(userDTO.getUsername());
        return userRepository.save(user);
    }

    public List<User> findAll() {
        List<User> usersList = userRepository.findAll();
        if (usersList.size() > 0) return usersList;
        else throw new CustomNotFoundException("No existe ningun usuario.\n");
    }

    public User findOneById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) return userOptional.get();
        else throw new CustomNotFoundException("No existe un usuario con el id: " + id + ".\n");
    }

    public List<User> findByCodEmpleado(Long cod) {
        List<User> usersList = userRepository.findByCodEmpleado(cod);
        if (usersList.size() > 0) return usersList;
        else throw new CustomNotFoundException("No existe usuarios con el codigo: " + cod + ".\n");
    }

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) return userOptional.get();
        else throw new CustomNotFoundException("No existe un usuario con el id: " + username + ".\n");
    }

    public boolean isUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.isPresent();
    }
}
