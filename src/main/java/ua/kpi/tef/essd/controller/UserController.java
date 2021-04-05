package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.User;
import ua.kpi.tef.essd.service.UserService;
import ua.kpi.tef.essd.service.implementation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.saveUser(user);
    }

    @GetMapping(value = "/{userId}")
    public User getUserById(@PathVariable Integer userId) throws NoSuchElementException {
        if (validator.validateUser(userId))
            return userService.getUser(userId);
        else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/info/{userId}")
    public String getUserInfo(@PathVariable Integer userId) {
        return userService.getUserInfo(userId);
    }

    @PutMapping
    public void updateUser(@RequestBody User user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        if (validator.validateUser(userId))
            userService.deleteUser(userId);
        else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

}
