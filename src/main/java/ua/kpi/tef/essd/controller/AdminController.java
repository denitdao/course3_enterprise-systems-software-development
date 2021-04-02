package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.Order;
import ua.kpi.tef.essd.entity.OrderStatus;
import ua.kpi.tef.essd.service.OrderService;
import ua.kpi.tef.essd.service.implementation.Validator;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Use this controller to operate with Orders and their status.
 */
@RestController
@RequestMapping(value = "/order")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private Validator validator;

    @PutMapping
    public void changeOrderStatus(@RequestBody Integer orderId, @RequestBody OrderStatus status) {
        if (!validator.validateOrder(orderId))
            throw new NoSuchElementException("No order with specified id=" + orderId + " found");
        orderService.changeOrderStatus(orderId, status);
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/all/{userId}")
    public List<Order> getUserOrders(@PathVariable Integer userId) {
        if (!validator.validateUser(userId))
            throw new NoSuchElementException("No user with specified id=" + userId + " found");
        return orderService.getOrdersOfUser(userId);
    }

}
