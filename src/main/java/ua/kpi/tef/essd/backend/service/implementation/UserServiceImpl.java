package ua.kpi.tef.essd.backend.service.implementation;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.backend.entity.User;
import ua.kpi.tef.essd.backend.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.backend.repository.UserRepository;
import ua.kpi.tef.essd.backend.service.RoleService;
import ua.kpi.tef.essd.backend.service.UserService;
import ua.kpi.tef.essd.backend.util.EntityNames;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userRepository.findAll();
        userList.forEach(Hibernate::initialize);
        return userList;
    }

//  ---- Simple CRUD methods ----

    @Override
    public void saveUser(User user) {
        user = userRepository.save(user);
        roleService.addRoleToUser(user.getId(), 2);
    }

    @Override
    public User getUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.USER, id));
        Hibernate.initialize(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        User user = this.getUser(id);
        user.getClothes()
                .forEach(clothing -> clothing.setUser(null));
        user.getClothesSets()
                .forEach(clothesSet -> clothesSet.setUser(null));
        userRepository.delete(user);
    }
}
