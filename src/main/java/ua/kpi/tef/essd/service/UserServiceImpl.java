package ua.kpi.tef.essd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.dao.ClothesSetDao;
import ua.kpi.tef.essd.dao.ClothingDao;
import ua.kpi.tef.essd.dao.UserDao;
import ua.kpi.tef.essd.entity.User;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public String getUserInfo(Integer userId) {
        return userDao.findById(userId).toString();
    }

//  ---- Simple CRUD methods ----

    @Override
    @Transactional(readOnly = false)
    public void saveUser(User user) {
        userDao.save(user);
    }

    @Override
    public User getUser(Integer id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public User updateUser(User user) {
        return userDao.update(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteUser(User user) {
        userDao.delete(user);
    }
}
