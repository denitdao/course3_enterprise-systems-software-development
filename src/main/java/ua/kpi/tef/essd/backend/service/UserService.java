package ua.kpi.tef.essd.backend.service;

import ua.kpi.tef.essd.backend.entity.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);

    User getUser(Integer id);

    List<User> getAllUsers();

    User updateUser(User user);

    void deleteUser(Integer id);

}
