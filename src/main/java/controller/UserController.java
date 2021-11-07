package controller;

import business.UserService;
import data.dtos.UserDTO;
import data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
    public User getUsersByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/isUser/{username}")
    public boolean isUser(@PathVariable String username) {
        return userService.isUser(username);
    }
}


