package ua.kpi.tef.essd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.dao.ClothesSetDao;
import ua.kpi.tef.essd.dao.ClothingDao;
import ua.kpi.tef.essd.dao.UserDao;
import ua.kpi.tef.essd.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@AutoConfigureTestEntityManager
class UserServiceTest {

    @MockBean
    private UserDao userDao;

    @MockBean
    private ClothingDao clothingDao;

    @MockBean
    private ClothesSetDao clothesSetDao;

    @Autowired
    private UserService userService;

    @Test
    void When_saveUser_Expect_Persisted() {
        // Setup our mock dao
        User user = new User("Tester", 10, "nothing interesting");

        // Execute the service call
        userService.saveUser(user);

        // verify that mock called
        verify(userDao).save(user);
    }

    @Test
    void When_getUserById_Expect_Entity() {
        User user = new User("Tester", 10, "nothing interesting");
        doReturn(user).when(userDao).findById(1);

        User returnedUser = userService.getUser(1);

        assertNotNull(returnedUser, "User was not found");
        assertSame(returnedUser, user, "The service returned was not the same as the mock");
    }

    @Test
    void When_getAllUsers_Expect_ListOfEntities() {
        User user1 = new User("Tester 1", 10, "nothing interesting");
        User user2 = new User("Tester 2", 20, "nothing interesting too");
        List<User> expected = List.of(user1, user2);
        doReturn(expected).when(userDao).findAll();

        List<User> returned = userService.getAllUsers();

        assertNotNull(returned, "User was not found");
        assertSame(expected, returned, "The service returned was not the same as the mock");
    }

    @Autowired
    private TestEntityManager entityManager;
    @Test
    @Transactional
    void When_getUserInfo_Expect_String() {
        User user = entityManager.find(User.class, 1);
        doReturn(user).when(userDao).findById(user.getId());

        String returned = userService.getUserInfo(user.getId());

        verify(userDao).findById(user.getId());
        assertEquals(user.toString(), returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_updateUser_Expect_Updated() {
        User user = new User("Tester", 10, "nothing interesting");

        userService.updateUser(user);

        verify(userDao).update(user);
    }

    @Test
    void When_deleteUser_Expect_Removed() {
        User user = new User("Tester", 10, "nothing interesting");

        userService.deleteUser(user);

        verify(userDao).delete(user);
    }

}