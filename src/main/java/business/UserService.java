package business;

import business.custom_exceptions.CustomNotFoundException;
import config.endpoint_classes.user.CoordinatorInterface;
import data.entities.User;
import data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static config.GlobalConstants.DOMINIO;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) return userOptional.get();
        else throw new CustomNotFoundException("No existe un usuario con el id: " + username + ".\n");
    }

    public boolean isUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userOptional.isPresent();
    }

    public List<String> getCourseCoordinators(String semester, String courseCode) {
        List<CoordinatorInterface> coordinatorList = userRepository.findCourseCoordinatorsUsername(semester, courseCode);
        if (coordinatorList.isEmpty())
            throw new CustomNotFoundException("No existen coordinadores para el curso con el código: " + courseCode + ".\n");
        List<String> response = new ArrayList<>();
        for (CoordinatorInterface ci : coordinatorList) {
            String username = ci.getUsername() + DOMINIO;
            response.add(username);
        }
        return response;
    }
}
