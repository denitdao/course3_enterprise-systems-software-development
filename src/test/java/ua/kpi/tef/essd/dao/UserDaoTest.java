package ua.kpi.tef.essd.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.ApplicationConfiguration;
import ua.kpi.tef.essd.entity.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void whenPersist_thenNoExceptionIsThrown() {
        User user = new User("Tester", 10, "nothing interesting");

        userDao.save(user);
        System.out.println(userDao.findAll());
        assertEquals(user, userDao.findById(2));
    }

}