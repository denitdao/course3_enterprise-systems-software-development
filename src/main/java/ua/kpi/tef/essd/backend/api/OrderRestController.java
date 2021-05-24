package ua.kpi.tef.essd.backend.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.kpi.tef.essd.backend.entity.Order;
import ua.kpi.tef.essd.backend.service.OrderService;

import java.util.List;

/**
 * Use this controller to get or create Orders.
 */
@RestController
@RequestMapping(value = "/api/order")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public void createOrder(@RequestParam Integer userId, @RequestParam Integer clothingId, @RequestParam Integer amount) {
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
