package ua.kpi.tef.essd.dao;

import org.springframework.stereotype.Component;
import ua.kpi.tef.essd.entity.User;

@Component
public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }

}
