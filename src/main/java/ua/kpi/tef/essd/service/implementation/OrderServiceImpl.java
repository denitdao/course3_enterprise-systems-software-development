package ua.kpi.tef.essd.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.entity.*;
import ua.kpi.tef.essd.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.repository.OrderRepository;
import ua.kpi.tef.essd.service.ClothingService;
import ua.kpi.tef.essd.service.OrderService;
import ua.kpi.tef.essd.service.UserService;
import ua.kpi.tef.essd.util.EntityNames;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClothingService clothingService;

    @Autowired
    private UserService userService;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersOfUser(Integer userId) {
        return userService.getUser(userId).getOrders();
    }

    @Override
    public Order changeOrderStatus(Integer orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.ORDER, orderId));
        order.setStatus(status);
        return orderRepository.save(order);
    }

//  ---- Simple CRUD methods ----

    @Override
    public void saveOrder(Integer userId, Integer clothingId, Integer amount) {
        Clothing clothing = clothingService.getClothing(clothingId);
        User user = userService.getUser(userId);
        Order order = new Order(user, clothing, amount, OrderStatus.Pending);
        orderRepository.save(order);
    }

    @Override
    public Order getOrder(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.ORDER, id));
    }

    @Override
    public void deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
    }

}
