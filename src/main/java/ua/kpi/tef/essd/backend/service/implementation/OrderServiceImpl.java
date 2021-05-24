package ua.kpi.tef.essd.backend.service.implementation;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.backend.entity.*;
import ua.kpi.tef.essd.backend.exception.ResourceNotFoundException;
import ua.kpi.tef.essd.backend.exception.WrongValueException;
import ua.kpi.tef.essd.backend.repository.OrderRepository;
import ua.kpi.tef.essd.backend.service.ClothingService;
import ua.kpi.tef.essd.backend.service.OrderService;
import ua.kpi.tef.essd.backend.service.UserService;
import ua.kpi.tef.essd.backend.util.EntityNames;

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
        List<Order> orders = orderRepository.findAll();
        orders.forEach(Hibernate::initialize);
        return orders;
    }

    @Override
    public List<Order> getOrdersOfUser(Integer userId) {
        List<Order> orders = userService.getUser(userId).getOrders();
        orders.forEach(Hibernate::initialize);
        return orders;
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
        if (amount <= 0)
            throw new WrongValueException("Amount", amount);
        Clothing clothing = clothingService.getClothing(clothingId);
        User user = userService.getUser(userId);
        Order order = new Order(user, clothing, amount, OrderStatus.PENDING);
        orderRepository.save(order);
    }

    @Override
    public Order getOrder(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EntityNames.ORDER, id));
        Hibernate.initialize(order);
        return order;
    }

    @Override
    public void deleteOrder(Integer orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (DataAccessException ex) {
            throw new ResourceNotFoundException(EntityNames.ORDER, orderId);
        }
    }

}
