package ua.kpi.tef.essd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.entity.Order;
import ua.kpi.tef.essd.service.OrderService;

import java.util.List;

/**
 * Use this controller to get or create Orders.
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public void createOrder(@RequestBody Integer userId, @RequestBody Integer clothingId, @RequestBody Integer amount) {
        orderService.saveOrder(userId, clothingId, amount);
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Integer orderId) {
        return orderService.getOrder(orderId);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getAllOrders(@PathVariable Integer userId) {
        return orderService.getOrdersOfUser(userId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Integer orderId) {
        orderService.deleteOrder(orderId);
    }

}
