package ua.kpi.tef.essd.backend.service;

import ua.kpi.tef.essd.backend.entity.Order;
import ua.kpi.tef.essd.backend.entity.OrderStatus;

import java.util.List;

public interface OrderService {

    void saveOrder(Integer userId, Integer clothingId, Integer amount);

    Order getOrder(Integer id);

    List<Order> getAllOrders();

    List<Order> getOrdersOfUser(Integer userId);

    Order changeOrderStatus(Integer orderId, OrderStatus status);

    void deleteOrder(Integer id);

}
