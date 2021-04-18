package ua.kpi.tef.essd.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.entity.User;
import ua.kpi.tef.essd.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.repository.UserRepository;
import ua.kpi.tef.essd.service.RoleService;
import ua.kpi.tef.essd.service.UserService;
import ua.kpi.tef.essd.util.EntityNames;

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
        return userRepository.findAll();
    }

//  ---- Simple CRUD methods ----

    @Override
    public void saveUser(User user) {
        user = userRepository.save(user);
        roleService.addRoleToUser(user.getId(), 2);
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.USER, id));
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
