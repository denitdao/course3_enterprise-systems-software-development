package ua.kpi.tef.essd.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.entity.User;

import static org.junit.jupiter.api.Assertions.*;

// UserControllerIntegrationTest (integration test) - where we use controller to insert and check using Service
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = ApplicationConfiguration.class)
@Transactional
@AutoConfigureTestEntityManager
class UserControllerIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserController userController;

    @Test
    void When_createUser_Expect_UserPersisted() {
        User user = new User("Tester", 10, "nothing interesting", null, null, null);

        userController.createUser(user);
        User found = entityManager.find(User.class, user.getId());

        assertEquals(user, found);
    }

    @Test
    void When_updateUser_Expect_UserUpdated() {
        User user = new User("Tester", 10, "nothing interesting", null, null, null);
        entityManager.persist(user);

        user.setName("Changed");

        userController.updateUser(user);
        User found = entityManager.find(User.class, user.getId());

        assertEquals(user, found);
    }

}