package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.entity.User;
import ua.kpi.tef.essd.service.ClothesSetService;
import ua.kpi.tef.essd.service.ClothingService;
import ua.kpi.tef.essd.service.UserService;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private ClothesSetService clothesSetService;

    @Autowired
    private ClothingService clothingService;

    @Autowired
    private UserService userService;

    public void createUser(User user) {
        userService.saveUser(user);
    }

    public User getUserById(Integer userId) {
        return userService.getUser(userId);
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
