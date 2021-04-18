package ua.kpi.tef.essd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.kpi.tef.essd.config.ApplicationTestConfiguration;
import ua.kpi.tef.essd.entity.*;

import ua.kpi.tef.essd.repository.RoleRepository;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationTestConfiguration.class)
class RoleServiceTest {

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private RoleService roleService;

    User user;
    Role role;

    @BeforeEach
    void setup() {
        user = new User("Tester", 10, "nothing interesting", null, null, null);
        role = new Role();
    }

    @Test
    void When_addRoleToUser_Expect_Added() {
        int roleId = 1;
        int userId = 1;
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(userService.getUser(userId)).thenReturn(user);

        roleService.addRoleToUser(userId, roleId);

        verify(roleRepository).save(role);
        assertThat(role.getUsers(), hasItem(user));
    }

    @Test
    void When_removeRoleFromUser_Expect_Removed() {
        int roleId = 1;
        int userId = 1;
        user.addRole(role);
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(userService.getUser(userId)).thenReturn(user);

        roleService.removeRoleFromUser(userId, roleId);

        verify(roleRepository).save(role);
        assertThat(role.getUsers(), not(hasItem(user)));
    }

    @Test
    void When_removeRoleFromUserWithoutRole_Expect_NotRemoved() {
        int roleId = 1;
        int userId = 1;
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));
        when(userService.getUser(userId)).thenReturn(user);

        roleService.removeRoleFromUser(userId, roleId);

        verify(roleRepository).save(role);
        assertThat(role.getUsers(), not(hasItem(user)));
    }
}
