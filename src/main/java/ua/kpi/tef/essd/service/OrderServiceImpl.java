package ua.kpi.tef.essd.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kpi.tef.essd.dao.OrderDao;
import ua.kpi.tef.essd.entity.*;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    private ClothingService clothingService;

    @Autowired
    private UserService userService;

    @Override
    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    @Override
    public List<Order> getOrdersOfUser(Integer userId) {
        return userService.getUser(userId).getOrders();
    }

    @Override
    @Transactional(readOnly = false)
    public Order changeOrderStatus(Integer orderId, OrderStatus status) {
        Order order = orderDao.findById(orderId);
        order.setStatus(status);
        return orderDao.update(order);
    }

//  ---- Simple CRUD methods ----

    @Override
    @Transactional(readOnly = false)
    public void saveOrder(Integer userId, Integer clothingId, Integer amount) {
        Clothing clothing = clothingService.getClothing(clothingId);
        User user = userService.getUser(userId);
        Order order = new Order(user, clothing, amount, OrderStatus.Pending);
        orderDao.save(order);
    }

    @Override
    public Order getOrder(Integer id) {
        return orderDao.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteOrder(Integer orderId) {
        orderDao.deleteById(orderId);
    }

}
