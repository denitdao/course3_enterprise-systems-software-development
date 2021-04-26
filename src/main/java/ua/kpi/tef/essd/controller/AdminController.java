package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.Order;
import ua.kpi.tef.essd.entity.OrderStatus;
import ua.kpi.tef.essd.service.OrderService;

import java.util.List;

/**
 * Use this controller to operate with Orders and their status.
 */
@RestController
@RequestMapping(value = "/order")
public class AdminController {

    @Autowired
    private OrderService orderService;

    @PutMapping
    public void changeOrderStatus(@RequestParam Integer orderId, @RequestParam OrderStatus status) {
        orderService.changeOrderStatus(orderId, status);
    }

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/all/{userId}")
    public List<Order> getUserOrders(@PathVariable Integer userId) {
        return orderService.getOrdersOfUser(userId);
    }

}
