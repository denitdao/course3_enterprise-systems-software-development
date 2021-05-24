package ua.kpi.tef.essd.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.backend.entity.Order;
import ua.kpi.tef.essd.backend.entity.OrderStatus;
import ua.kpi.tef.essd.backend.service.OrderService;

import java.util.List;

/**
 * Use this controller to operate with Orders and their status.
 */
@RestController
@RequestMapping(value = "/api/order")
public class AdminRestController {

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
