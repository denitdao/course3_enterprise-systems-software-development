package ua.kpi.tef.essd.dao;

import ua.kpi.tef.essd.entity.User;

public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }

}
