package ua.kpi.tef.essd.service;

import ua.kpi.tef.essd.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    User getUser(Integer id);

    List<User> getAllUsers();

    String getUserInfo(Integer userId);

    User updateUser(User user);

    void deleteUser(User user);
}
