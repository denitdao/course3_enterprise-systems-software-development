package ua.kpi.tef.essd.service;

import ua.kpi.tef.essd.entity.Order;
import ua.kpi.tef.essd.entity.OrderStatus;

import java.util.List;

public interface OrderService {

    void saveOrder(Integer userId, Integer clothingId, Integer amount);

    Order getOrder(Integer id);

    List<Order> getAllOrders();

    List<Order> getOrdersOfUser(Integer userId);

    Order changeOrderStatus(Integer orderId, OrderStatus status);

    void deleteOrder(Integer id);

}
