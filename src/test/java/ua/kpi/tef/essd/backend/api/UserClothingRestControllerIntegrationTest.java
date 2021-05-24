package ua.kpi.tef.essd.backend.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.backend.entity.*;
import ua.kpi.tef.essd.backend.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.config.ApplicationTestConfiguration;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationTestConfiguration.class)
@Transactional
class UserClothingRestControllerIntegrationTest {

    @Autowired
    UserRestController userController;

    @Autowired
    ClothingRestController clothingController;

    @Autowired
    ClothesSetRestController clothesSetRestController;

    @Test
    void When_createUser_Expect_UserPersisted() {
        User createdUser = new User("Tester", 10, "nothing interesting", null, null, null);

        userController.createUser(createdUser);
        User foundUser = userController.getUserById(createdUser.getId());

        assertEquals(createdUser, foundUser);
    }

    @Test
    void When_updateUser_Expect_UserUpdated() {
        User createdUser = new User("Tester", 10, "nothing interesting", null, null, null);
        userController.createUser(createdUser);
        createdUser.setAge(14);
        createdUser.setName("Not tester");

        userController.updateUser(createdUser);
        User foundUser = userController.getUserById(createdUser.getId());

        assertSame("Not tester", foundUser.getName());
        assertSame(14, foundUser.getAge());
    }

    @Test
    void When_addClothingToUser_Expect_UserContains() {
        User createdUser = new User("Tester", 10, "nothing interesting", null, null, null);
        userController.createUser(createdUser);

        Clothing createdClothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        clothingController.createClothing(createdUser.getId(), createdClothing);

        List<Clothing> clothesOfUser = clothingController.getClothesOfUser(createdUser.getId());

        assertThat(clothesOfUser, hasItem(createdClothing));
    }

    @Test
    void When_addClothingToSet_Expect_SetContains() {
        User createdUser = new User("Tester", 10, "nothing interesting", null, null, null);
        userController.createUser(createdUser);
        Clothing createdClothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        clothingController.createClothing(createdUser.getId(), createdClothing);
        ClothesSet clothesSet = new ClothesSet("clothesSet", null, null);
        clothesSetRestController.createClothesSet(createdUser.getId(), clothesSet);

        clothingController.addClothingToSet(clothesSet.getId(), createdClothing.getId());
        ClothesSet foundSet = clothesSetRestController.getClothesSetWithClothing(createdClothing.getId());

        assertEquals(clothesSet, foundSet);
    }

    @Test
    void When_addClothingToWrongUserId_Expect_Throw() {
        Clothing createdClothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        assertThrows(ResourceNotFoundException.class,
                () -> clothingController.createClothing(-1, createdClothing));
    }

    @Test
    void When_addClothingToWrongSetId_Expect_Throw() {
        User createdUser = new User("Tester", 10, "nothing interesting", null, null, null);
        userController.createUser(createdUser);
        Clothing createdClothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        clothingController.createClothing(createdUser.getId(), createdClothing);

        Integer clId = createdClothing.getId();
        assertThrows(ResourceNotFoundException.class,
                () -> clothingController.addClothingToSet(-1, clId));
    }

}