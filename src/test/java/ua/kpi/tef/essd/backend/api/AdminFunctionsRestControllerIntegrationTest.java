package ua.kpi.tef.essd.backend.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.backend.entity.*;
import ua.kpi.tef.essd.config.ApplicationTestConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationTestConfiguration.class)
@Transactional
class AdminFunctionsRestControllerIntegrationTest {

    @Autowired
    UserRestController userController;

    @Autowired
    OrderRestController orderController;

    @Autowired
    ClothingRestController clothingController;

    @Autowired
    AdminRestController adminController;

    @Test
    void When_createOrder_Expect_Persisted() {
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
    void When_updateOrderStatus_Expect_Change() {
        User user = new User("Tester", 10, "nothing interesting", null, null, null);
        userController.createUser(user);
        Clothing clothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        clothingController.createClothing(user.getId(), clothing);

        orderController.createOrder(user.getId(), clothing.getId(), 10);

        List<Order> orders = adminController.getAllOrders();
        adminController.changeOrderStatus(orders.get(orders.size() - 1).getId(), OrderStatus.SHIPPED);

        assertEquals(OrderStatus.SHIPPED, orderController.getOrder(orders.get(orders.size() - 1).getId()).getStatus());
    }

}
