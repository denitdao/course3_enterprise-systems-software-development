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
import ua.kpi.tef.essd.dao.PartDao;
import ua.kpi.tef.essd.entity.*;

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
    private PartDao partDao;

    @Test
    void When_getPart_Expect_Returned() {
        // Setup our mock dao
        //User user = new User("Tester", 10, "nothing interesting");

        // Execute the service call
        assertEquals("", partService.getPartInfo(1));

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
        Clothing clothing = new Clothing("Tester Shirt", Type.CHILDREN, Size.S);
        entityManager.persist(clothing);

        Part part = partDao.findById(1);

        partService.addPartToClothing(clothing.getId(), part.getId(), 4);

        assertEquals("", partService.getClothingParts(clothing.getId()));
    }

}