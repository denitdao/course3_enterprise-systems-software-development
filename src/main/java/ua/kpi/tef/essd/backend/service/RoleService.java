package ua.kpi.tef.essd.backend.service;

import ua.kpi.tef.essd.backend.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> getRolesOfUser(Integer userId);

    void addRoleToUser(Integer userId, Integer roleId);

    void removeRoleFromUser(Integer userId, Integer roleId);

}
