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
import ua.kpi.tef.essd.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.repository.ClothesSetRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationTestConfiguration.class)
class ClothesSetServiceTest {

    @MockBean
    private ClothesSetRepository clothesSetRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private ClothingService clothingService;

    @Autowired
    private ClothesSetService clothesSetService;

    ClothesSet clothesSet;
    ClothesSet clothesSet1;
    ClothesSet clothesSet2;
    User user;
    Clothing clothing;

    @BeforeEach
    void setup() {
        clothesSet = new ClothesSet("clothesSet", null, null);
        clothesSet1 = new ClothesSet("clothesSet1", null, null);
        clothesSet2 = new ClothesSet("clothesSet2", null, null);
        user = new User("Tester", 10, "nothing interesting", null, null, null);
        clothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
    }

    @Test
    void When_saveClothesSetOfUser_Expect_Persisted() {
        int userId = 1;
        when(userService.getUser(userId)).thenReturn(user);

        clothesSetService.saveClothesSetOfUser(1, clothesSet);

        verify(clothesSetRepository).save(clothesSet);
        assertEquals(clothesSet, user.getClothesSets().get(0));
    }

    @Test
    void When_getClothesSetById_Expect_Entity() {
        when(clothesSetRepository.findById(1)).thenReturn(Optional.of(clothesSet));

        ClothesSet returnedSet = clothesSetService.getClothesSet(1);

        assertNotNull(returnedSet, "Clothes set was not found");
        assertSame(returnedSet, clothesSet, "The service returned was not the same as the mock");
    }

    @Test
    void When_getClothesSetByWrongId_Expect_ResourceNotFoundException() {
        int setId = 1;
        when(clothesSetRepository.findById(setId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clothesSetService.getClothesSet(setId));
    }

    @Test
    void When_getClothesSetsOfUser_Expect_ListOfEntities() {
        user.setClothesSets(List.of(clothesSet1, clothesSet2));
        when(userService.getUser(1)).thenReturn(user);
        List<ClothesSet> expected = user.getClothesSets();

        List<ClothesSet> returned = clothesSetService.getClothesSetsOfUser(1);

        assertNotNull(returned, "Sets were not found");
        assertSame(expected, returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_getClothesSetOfClothing_Expect_ListOfEntities() {
        clothing.setClothesSet(clothesSet);
        when(clothingService.getClothing(1)).thenReturn(clothing);
        ClothesSet expected = clothing.getClothesSet();

        ClothesSet returned = clothesSetService.getClothesSetOfClothing(1);

        assertNotNull(returned, "Sets were not found");
        assertSame(expected, returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_updateClothesSet_Expect_Updated() {
        when(clothesSetRepository.save(clothesSet)).thenReturn(clothesSet);

        ClothesSet returned = clothesSetService.updateClothesSet(clothesSet);

        verify(clothesSetRepository).save(clothesSet);
        assertSame(clothesSet, returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_deleteClothesSet_Expect_Removed() {
        when(clothesSetRepository.findById(1)).thenReturn(Optional.of(clothesSet));

        clothesSetService.deleteClothesSet(1);

        verify(clothesSetRepository).delete(clothesSet);
    }

    @Test
    void When_deleteClothesSetWithConnections_Expect_DeletedConnections() {
        int setId = 1;
        clothesSet.addClothing(clothing);
        // everything is connected
        assertNotNull(clothing.getClothesSet());
        assertNotNull(clothesSet.getSetOfClothes().get(0));
        // Setup our mock
        when(clothesSetRepository.findById(setId)).thenReturn(Optional.of(clothesSet));
        // run action
        clothesSetService.deleteClothesSet(setId);

        assertNull(clothing.getClothesSet());
        assertTrue(clothesSet.getSetOfClothes().isEmpty());
        verify(clothesSetRepository).delete(clothesSet);
    }

    @Test
    void When_deleteClothesSetByWrongId_Expect_ResourceNotFoundException() {
        int setId = 1;
        when(clothesSetRepository.findById(setId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clothesSetService.deleteClothesSet(setId));
    }

}