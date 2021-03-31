package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.kpi.tef.essd.entity.User;
import ua.kpi.tef.essd.service.UserService;
import ua.kpi.tef.essd.service.implementation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    public void createUser(User user) {
        userService.saveUser(user);
    }

    public User getUserById(Integer userId) throws RuntimeException {
        if (validator.validateUser(userId))
            return userService.getUser(userId);
        else
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public String getUserInfo(Integer userId) {
        return userService.getUserInfo(userId);
    }

    public void updateUser(User user) {
        userService.updateUser(user);
    }

}
