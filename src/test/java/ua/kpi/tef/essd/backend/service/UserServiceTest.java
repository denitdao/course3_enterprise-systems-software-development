package ua.kpi.tef.essd.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.kpi.tef.essd.config.ApplicationTestConfiguration;
import ua.kpi.tef.essd.backend.entity.*;
import ua.kpi.tef.essd.backend.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// UserServiceTest (unit test) - where we mock other used beans and check that logics are running is Ok
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationTestConfiguration.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @Autowired
    private UserService userService;

    User user;
    User user1;
    User user2;

    @BeforeEach
    void setup() {
        // prepare data for our tests
        user = new User("Tester", 10, "nothing interesting", null, null, null);
        user1 = new User("Tester 1", 11, "nothing interesting 1", null, null, null);
        user2 = new User("Tester 2", 22, "nothing interesting 2", null, null, null);
    }

    @Test
    void When_saveUser_Expect_Persisted() {
        // Execute the service call
        when(userRepository.save(user)).thenReturn(user);

        userService.saveUser(user);

        // verify that mock called
        verify(userRepository).save(user);
        verify(roleService).addRoleToUser(user.getId(), 2);
    }

    @Test
    void When_getUserById_Expect_Entity() {
        // Setup our mock repository
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Execute the service call
        User returnedUser = userService.getUser(1);

        // verify that mock called and data is correct
        assertNotNull(returnedUser, "User was not found");
        assertSame(returnedUser, user, "The service returned was not the same as the mock");
    }

    @Test
    void When_getUserByWrongId_Expect_ResourceNotFoundException() {
        // Setup our mock
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // verify that correct exception thrown
        assertThrows(ResourceNotFoundException.class, () -> userService.getUser(userId));
    }

    @Test
    void When_getAllUsers_Expect_ListOfEntities() {
        List<User> expected = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(expected);

        List<User> returned = userService.getAllUsers();

        assertNotNull(returned, "User was not found");
        assertSame(expected, returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_updateUser_Expect_Updated() {
        when(userRepository.save(user)).thenReturn(user);

        User returned = userService.updateUser(user);

        verify(userRepository).save(user);
        assertSame(user, returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_deleteUser_Expect_Removed() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteUser(1);

        verify(userRepository).delete(user);
    }

    @Test
    void When_deleteUserWithConnections_Expect_DeletedConnections() {
        int userId = 1;
        ClothesSet clothesSet = new ClothesSet("Set 1", null, null);
        Clothing clothing = new Clothing("Clothing 1", Type.MAN, Size.XL, null, clothesSet);
        User fullUser = new User("Tester", 10, "nothing interesting",
                List.of(clothing), List.of(clothesSet), null);
        // everything is connected
        assertNotNull(clothing.getUser());
        assertNotNull(clothesSet.getUser());
        // Setup our mock
        when(userRepository.findById(userId)).thenReturn(Optional.of(fullUser));
        // run action
        userService.deleteUser(userId);

        assertNull(clothing.getUser());
        assertNull(clothesSet.getUser());
        verify(userRepository).delete(fullUser);
    }

    @Test
    void When_deleteUserByWrongId_Expect_ResourceNotFoundException() {
        // Setup our mock
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // verify that correct exception thrown
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(userId));
    }

}