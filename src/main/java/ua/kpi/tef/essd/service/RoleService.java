package ua.kpi.tef.essd.service;

public interface RoleService {

    void addRoleToUser(Integer userId, Integer roleId);

    void removeRoleFromUser(Integer userId, Integer roleId);

}
