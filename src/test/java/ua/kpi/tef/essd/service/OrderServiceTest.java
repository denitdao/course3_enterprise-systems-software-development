package ua.kpi.tef.essd.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.kpi.tef.essd.config.ApplicationTestConfiguration;
import ua.kpi.tef.essd.entity.*;
import ua.kpi.tef.essd.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.exception.WrongValueException;
import ua.kpi.tef.essd.repository.ClothingRepository;
import ua.kpi.tef.essd.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ApplicationTestConfiguration.class)
class OrderServiceTest {

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ClothingService clothingService;

    @MockBean
    private UserService userService;

    @Autowired
    private OrderService orderService;

    Order order;
    Order order1;
    Order order2;
    User user;
    Clothing clothing;
    Integer amount = 10;

    @BeforeEach
    void setup() {
        user = new User("Tester", 10, "nothing interesting", null, null, null);
        clothing = new Clothing("clothing", Type.MAN, Size.XL, null, null);
        order = new Order(user, clothing, amount, OrderStatus.PENDING);
        order1 = new Order(user, null, amount, OrderStatus.CANCELLED);
        order2 = new Order(user, null, amount, OrderStatus.CONFIRMED);
    }

    @Test
    void When_saveOrder_Expect_Persisted() {
        int userId = 1;
        int clothingId = 1;
        when(userService.getUser(userId)).thenReturn(user);
        when(clothingService.getClothing(clothingId)).thenReturn(clothing);

        orderService.saveOrder(userId, clothingId, amount);

        verify(orderRepository).save(order);
    }

    @Test
    void When_saveOrderWrongAmount_Expect_WrongValueException() {
        assertThrows(WrongValueException.class, () -> orderService.saveOrder(1, 2, -1));
    }

    @Test
    void When_getOrderById_Expect_Entity() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));

        Order returned = orderService.getOrder(1);

        assertNotNull(returned, "Order was not found");
        assertSame(returned, order, "The service returned was not the same as the mock");
    }

    @Test
    void When_getOrderByWrongId_Expect_ResourceNotFoundException() {
        when(orderRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrder(1));
    }

    @Test
    void When_getAllOrders_Expect_ListOfEntities() {
        List<Order> expected = List.of(order1, order2);
        when(orderRepository.findAll()).thenReturn(expected);

        List<Order> returned = orderService.getAllOrders();

        assertNotNull(returned, "Orders were not found");
        assertSame(expected, returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_getOrdersOfUser_Expect_ListOfEntities() {
        when(userService.getUser(1)).thenReturn(user);
        List<Order> expected = user.getOrders();

        List<Order> returned = orderService.getOrdersOfUser(1);

        assertNotNull(returned, "Orders were not found");
        assertSame(expected, returned, "The service returned was not the same as the mock");
    }

    @Test
    void When_changeOrderStatus_Expect_Updated() {
        when(orderRepository.findById(1)).thenReturn(Optional.of(order));
        OrderStatus before = order.getStatus();

        orderService.changeOrderStatus(1, OrderStatus.SHIPPED);

        verify(orderRepository).save(order);
        assertNotEquals(before, order.getStatus(), "Order status was not updated");
    }

    @Test
    void When_deleteOrderById_Expect_Removed() {
        orderService.deleteOrder(1);

        verify(orderRepository).deleteById(1);
    }

    @Test
    void When_deleteOrderByWrongId_Expect_ResourceNotFoundException() {
        int orderId = -1;
        doThrow(new EmptyResultDataAccessException(1)).when(orderRepository).deleteById(orderId);

        assertThrows(ResourceNotFoundException.class, () -> orderService.deleteOrder(orderId));
    }

}
