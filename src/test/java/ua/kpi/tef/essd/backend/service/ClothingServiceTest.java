package ua.kpi.tef.essd.backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.kpi.tef.essd.config.ApplicationTestConfiguration;
import ua.kpi.tef.essd.backend.entity.*;
import ua.kpi.tef.essd.backend.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.backend.repository.ClothingRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationTestConfiguration.class)
class ClothingServiceTest {

    @MockBean
    private ClothingRepository clothingRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private ClothesSetService clothesSetService;

    @Autowired
    private ClothingService clothingService;

    ClothesSet clothesSet;
    User user;
    Clothing clothing;
    Clothing clothing1;
    Clothing clothing2;

    @BeforeEach
    void setup() {
        clothesSet = new ClothesSet("clothesSet", null, null);
        user = new User("Tester", 10, "nothing interesting", null, null, null);
        clothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        clothing1 = new Clothing("clothing 1", Type.MAN, Size.XL, null, null);
        clothing2 = new Clothing("clothing 2", Type.MAN, Size.XL, null, null);
    }

    @Test
    void When_saveClothingOfUser_Expect_Persisted() {
        int userId = 1;
        when(userService.getUser(userId)).thenReturn(user);

        clothingService.saveClothingOfUser(userId, clothing);

        verify(clothingRepository).save(clothing);
        assertEquals(clothing, user.getClothes().get(0));
    }

    @Test
    void When_getClothingById_Expect_Entity() {
        when(clothingRepository.findById(1)).thenReturn(Optional.of(clothing));

        Clothing returned = clothingService.getClothing(1);

        assertNotNull(returned, "Clothes set was not found");
        assertSame(returned, clothing, "The service returned was not the same as the mock");
    }

    @Test
    void When_getClothingByWrongId_Expect_ResourceNotFoundException() {
        when(clothingRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> clothingService.getClothing(1));
    }

    @Test
    void When_getClothesOfUser_Expect_ListOfEntities() {
        user.setClothes(List.of(clothing1, clothing2));
        when(userService.getUser(1)).thenReturn(user);
        List<Clothing> expected = user.getClothes();

        List<Clothing> returned = clothingService.getClothesOfUser(1);

        assertNotNull(returned, "Sets were not found");
        assertSame(expected, returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_addClothingToSet_Expect_Added() {
        int setId = 1;
        int clothingId = 1;
        when(clothingRepository.findById(clothingId)).thenReturn(Optional.of(clothing));
        when(clothesSetService.getClothesSet(setId)).thenReturn(clothesSet);

        clothingService.addClothingToSet(setId, clothingId);

        assertSame(clothing.getClothesSet(), clothesSet, "The service returned was not the same as the mock");
        verify(clothingRepository).save(clothing);
    }

    @Test
    void When_updateClothing_Expect_Updated() {
        when(clothingRepository.save(clothing)).thenReturn(clothing);

        Clothing returned = clothingService.updateClothing(clothing);

        verify(clothingRepository).save(clothing);
        assertSame(clothing, returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_deleteClothingById_Expect_Removed() {
        clothingService.deleteClothing(1);

        verify(clothingRepository).deleteById(1);
    }

    @Test
    void When_deleteClothingByWrongId_Expect_ResourceNotFoundException() {
        int clothingId = -1;
        doThrow(new EmptyResultDataAccessException(1)).when(clothingRepository).deleteById(clothingId);

        assertThrows(ResourceNotFoundException.class, () -> clothingService.deleteClothing(clothingId));
    }

}
