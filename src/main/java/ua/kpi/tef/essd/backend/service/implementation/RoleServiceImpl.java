package ua.kpi.tef.essd.backend.service.implementation;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.tef.essd.backend.entity.Role;
import ua.kpi.tef.essd.backend.entity.User;
import ua.kpi.tef.essd.backend.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.backend.repository.RoleRepository;
import ua.kpi.tef.essd.backend.service.RoleService;
import ua.kpi.tef.essd.backend.service.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public List<Role> getRolesOfUser(Integer userId) {
        User user = userService.getUser(userId);
        List<Role> roles = user.getRoles();
        roles.forEach(Hibernate::initialize);
        return roles;
    }

    @Override
    public void addRoleToUser(Integer userId, Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("role", roleId));
        User user = userService.getUser(userId);
        if (!user.getRoles().contains(role))
            role.addUser(user);
        roleRepository.save(role);
    }

    @Override
    public void removeRoleFromUser(Integer userId, Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("role", roleId));
        role.removeUser(userService.getUser(userId));
        roleRepository.save(role);
    }

}
