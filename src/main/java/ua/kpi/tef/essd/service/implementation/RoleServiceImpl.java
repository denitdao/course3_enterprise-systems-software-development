package ua.kpi.tef.essd.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kpi.tef.essd.entity.Role;
import ua.kpi.tef.essd.entity.User;
import ua.kpi.tef.essd.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.repository.RoleRepository;
import ua.kpi.tef.essd.service.RoleService;
import ua.kpi.tef.essd.service.UserService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

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
