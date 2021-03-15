package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ua.kpi.tef.essd.entity.Order;
import ua.kpi.tef.essd.service.OrderService;
import ua.kpi.tef.essd.service.Validator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Use this controller to get or create Orders.
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Validator validator;

    public void createOrder(Integer userId, Integer clothingId, Integer amount) {
        if (!validator.validateUser(userId))
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
        if (!validator.validateClothing(clothingId))
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
        orderService.saveOrder(userId, clothingId, amount);
    }

    public Order getOrder(Integer orderId) {
        if (!validator.validateUser(orderId))
            throw new NoSuchElementException("No order with specified id=" + orderId + " found");
        return orderService.getOrder(orderId);
    }

    public List<Order> getAllOrders(Integer userId) {
        if (!validator.validateUser(userId))
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
        return orderService.getOrdersOfUser(userId);
    }

    public void deleteOrder(Integer orderId) {
        if (!validator.validateOrder(orderId))
            throw new NoSuchElementException("No order with specified id=" + orderId + " found");
        orderService.deleteOrder(orderId);
    }

}
