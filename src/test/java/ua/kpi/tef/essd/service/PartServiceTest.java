package ua.kpi.tef.essd.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.config.ApplicationConfiguration;
import ua.kpi.tef.essd.entity.*;
import ua.kpi.tef.essd.repository.PartRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ApplicationConfiguration.class)
@AutoConfigureTestEntityManager
class PartServiceTest {

    @Autowired
    private PartService partService;

    @Autowired
    private PartRepository partRepository;

    @Test
    void When_getPart_Expect_Returned() {
        // Setup our mock dao
        //User user = new User("Tester", 10, "nothing interesting");

        // Execute the service call
        assertEquals("Part{id=1, name='Sleeve', properties=[Property{id=1, name='Color', value='Red'}, " +
                        "Property{id=6, name='Material', value='Cotton'}, Property{id=11, name='Fit', value='Regular'}], " +
                        "clothes={Basic Trousers}}", partService.getPartInfo(1));

        // verify that mock called
        //verify(userDao).save(user);
    }

    @Autowired
    private TestEntityManager entityManager;
    @Test
    @Transactional
    void When_getUserInfo_Expect_String() {
        /*User user = entityManager.find(User.class, 1);
        doReturn(user).when(userDao).findById(user.getId());

        String returned = userService.getUserInfo(user.getId());

        verify(userDao).findById(user.getId());
        assertEquals(user.toString(), returned, "The service returned was not the same as the mock");*/
        Clothing clothing = new Clothing("Tester Shirt", Type.CHILDREN, Size.S, null, null);
        entityManager.persist(clothing);


        Optional<Part> partOptional = partRepository.findById(1);
        assertTrue(partOptional.isPresent());
        Part part = partOptional.get();

        partService.addPartToClothing(clothing.getId(), part.getId(), 4);

        assertEquals("[ClothingPart{id=ClothingPartKey(clothingId=6, partId=1), " +
                "clothing=Tester Shirt, part=Sleeve, amount=4}]", partService.getClothingParts(clothing.getId()).toString());
    }

}