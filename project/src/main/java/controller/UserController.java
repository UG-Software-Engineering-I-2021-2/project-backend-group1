package controller;

import business.UserService;
import data.dtos.UserDTO;
import data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User postUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.findOneById(id);
    }

    @GetMapping("/codEmpleado/{cod}")
    public List<User> getUsersByCodEmpleado(@PathVariable Long cod) {
        return userService.findByCodEmpleado(cod);
    }

    @GetMapping("/username/{username}")
    public List<User> getUsersByCodEmpleado(@PathVariable String username) {
        return userService.findByUsername(username);
    }
}


