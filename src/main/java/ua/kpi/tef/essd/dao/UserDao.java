package ua.kpi.tef.essd.dao;

import org.springframework.stereotype.Repository;
import ua.kpi.tef.essd.entity.User;

@Repository
public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }

}
