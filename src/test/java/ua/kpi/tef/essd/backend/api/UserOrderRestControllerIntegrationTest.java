package ua.kpi.tef.essd.backend.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.backend.entity.Clothing;
import ua.kpi.tef.essd.backend.entity.Size;
import ua.kpi.tef.essd.backend.entity.Type;
import ua.kpi.tef.essd.backend.entity.User;
import ua.kpi.tef.essd.config.ApplicationTestConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationTestConfiguration.class)
@Transactional
class UserOrderRestControllerIntegrationTest {

    @Autowired
    UserRestController userController;

    @Autowired
    OrderRestController orderController;

    @Autowired
    ClothingRestController clothingController;

    @Autowired
    AdminRestController adminController;

    @Test
    void When_createOrder_Expect_Increase() {
        User user = new User("Tester", 10, "nothing interesting", null, null, null);
        userController.createUser(user);
        Clothing clothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        clothingController.createClothing(user.getId(), clothing);

        int beforeSize = adminController.getAllOrders().size();
        orderController.createOrder(user.getId(), clothing.getId(), 10);
        int afterSize = adminController.getAllOrders().size();

        assertEquals(1, afterSize - beforeSize);
    }

    @Test
    void When_deleteOrder_Expect_Decreased() {
        User user = new User("Tester", 10, "nothing interesting", null, null, null);
        userController.createUser(user);
        Clothing clothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        clothingController.createClothing(user.getId(), clothing);
        orderController.createOrder(user.getId(), clothing.getId(), 10);

        int beforeSize = adminController.getAllOrders().size();
        orderController.deleteOrder(beforeSize - 1);
        int afterSize = adminController.getAllOrders().size();

        assertEquals(1, beforeSize - afterSize);
    }

}
