package ua.kpi.tef.essd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.dao.UserDao;
import ua.kpi.tef.essd.entity.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@Transactional
class UserServiceTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserDao dao;

    @Test
    void When__Expect_1() {
        // Setup our mock repository
        User user = new User("Tester", 10, "nothing interesting");
        doReturn(user).when(dao).findById(1);

        // Execute the service call
        User returnedUser = service.getUser(1);

        // Assert the response
        assertNotNull(returnedUser, "User was not found");
        assertSame(returnedUser, user, "The widget returned was not the same as the mock");
    }

    @Test
    void When__Expect_2() {

    }

    @Test
    void When__Expect_3() {

    }
}