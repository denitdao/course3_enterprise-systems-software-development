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
import ua.kpi.tef.essd.backend.exception.WrongValueException;
import ua.kpi.tef.essd.backend.repository.PartRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationTestConfiguration.class)
class PartServiceTest {

    @MockBean
    private PartRepository partRepository;

    @MockBean
    private ClothingService clothingService;

    @Autowired
    private PartService partService;

    Part part;
    Part part1;
    Part part2;
    Clothing clothing;
    Clothing clothing1;

    @BeforeEach
    void setup() {
        // prepare data for our tests
        part = new Part("part", null);
        part1 = new Part("part1", null);
        part2 = new Part("part2", null);
        clothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        clothing1 = new Clothing("clothing1", Type.MAN, Size.XL, null, null);
        clothing1.addPart(part1, 1);
        clothing1.addPart(part2, 10);
    }

    @Test
    void When_addPartToClothing_Expect_Added() {
        int clothingId = 1;
        int partId = 1;
        int amount = 10;
        when(partRepository.findById(partId)).thenReturn(Optional.of(part));
        when(clothingService.getClothing(clothingId)).thenReturn(clothing);

        partService.addPartToClothing(clothingId, partId, amount);

        verify(partRepository).save(part);
        assertEquals(part, clothing.getParts().get(0).getPart());
        assertEquals(amount, clothing.getParts().get(0).getAmount());
    }

    @Test
    void When_addPartToClothingWrongAmount_Expect_Exception() {
        int amount = -2;

        assertThrows(WrongValueException.class, () -> partService.addPartToClothing(1, 1, amount));
    }

    @Test
    void When_getPartById_Expect_Entity() {
        // Setup our mock repository
        when(partRepository.findById(1)).thenReturn(Optional.of(part));

        // Execute the service call
        Part returnedUser = partService.getPart(1);

        // verify that mock called and data is correct
        assertNotNull(returnedUser, "Part was not found");
        assertSame(returnedUser, part, "The service returned was not the same as the mock");
    }

    @Test
    void When_getPartByWrongId_Expect_ResourceNotFoundException() {
        // Setup our mock
        int partId = 1;
        when(partRepository.findById(partId)).thenReturn(Optional.empty());

        // verify that correct exception thrown
        assertThrows(ResourceNotFoundException.class, () -> partService.getPart(partId));
    }

    @Test
    void When_getAllParts_Expect_ListOfEntities() {
        List<Part> expected = List.of(part1, part2);
        when(partRepository.findAll()).thenReturn(expected);

        List<Part> returned = partService.getAllParts();

        assertNotNull(returned, "Parts were not found");
        assertSame(expected, returned, "The service returned was not the same as the mock");
    }

   @Test
    void When_getClothingParts_Expect_ListOfEntities() {
        int clothingId = 1;
        when(clothingService.getClothing(clothingId)).thenReturn(clothing1);

        List<ClothingPart> returned = partService.getClothingParts(clothingId);

        assertNotNull(returned, "Parts were not found");
        assertSame(clothing1.getParts(), returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_changePartInClothing_Expect_ChangeAmount() {
        int clothingId = 1;
        int partId = 1;
        int amount = 5;
        when(partRepository.findById(partId)).thenReturn(Optional.of(part1));
        when(clothingService.getClothing(clothingId)).thenReturn(clothing1);

        partService.changePartInClothingAmount(clothingId, partId, amount);

        verify(partRepository).save(part1);
        assertThat(clothing1.getParts()
                .stream()
                .map(ClothingPart::getPart)
                .collect(Collectors.toList()), hasItems(part1, part2));
        assertEquals(amount, clothing1.getParts().stream()
                .filter(cp -> cp.getPart().equals(part1))
                .findFirst()
                .orElseGet(() -> new ClothingPart(clothing1, part1, -1))
                .getAmount());
    }

    @Test
    void When_changePartInClothingAmountToZero_Expect_Removed() {
        int clothingId = 1;
        int partId = 1;
        int amount = 0;
        when(partRepository.findById(partId)).thenReturn(Optional.of(part1));
        when(clothingService.getClothing(clothingId)).thenReturn(clothing1);

        partService.changePartInClothingAmount(clothingId, partId, amount);

        verify(partRepository).save(part1);
        assertThat(clothing1.getParts()
                .stream()
                .map(ClothingPart::getPart)
                .collect(Collectors.toList()), hasItem(part2));
        assertThat(clothing1.getParts()
                .stream()
                .map(ClothingPart::getPart)
                .collect(Collectors.toList()), not(hasItem(part1)));
    }

}