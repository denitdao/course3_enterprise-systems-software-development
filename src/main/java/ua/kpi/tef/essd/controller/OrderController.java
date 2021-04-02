package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.Order;
import ua.kpi.tef.essd.service.OrderService;
import ua.kpi.tef.essd.service.implementation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Use this controller to get or create Orders.
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Validator validator;

    @PostMapping
    public void createOrder(@RequestBody Integer userId, @RequestBody Integer clothingId, @RequestBody Integer amount) {
        if (!validator.validateUser(userId))
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
        if (!validator.validateClothing(clothingId))
            throw new NoSuchElementException("No clothing with specified id=" + clothingId + " found");
        orderService.saveOrder(userId, clothingId, amount);
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Integer orderId) {
        if (!validator.validateUser(orderId))
            throw new NoSuchElementException("No order with specified id=" + orderId + " found");
        return orderService.getOrder(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getAllOrders(@PathVariable Integer userId) {
        if (!validator.validateUser(userId))
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
        return orderService.getOrdersOfUser(userId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Integer orderId) {
        if (!validator.validateOrder(orderId))
            throw new NoSuchElementException("No order with specified id=" + orderId + " found");
        orderService.deleteOrder(orderId);
    }

}
