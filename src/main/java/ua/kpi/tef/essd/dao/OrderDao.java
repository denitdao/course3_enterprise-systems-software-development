package ua.kpi.tef.essd.dao;

import org.springframework.stereotype.Repository;
import ua.kpi.tef.essd.entity.Order;
import ua.kpi.tef.essd.entity.OrderStatus;

import java.util.List;

@Repository
public class OrderDao extends GenericDao<Order> {

    public OrderDao() {
        super(Order.class);
    }

    public List<Order> findByStatus(OrderStatus status) {
        return entityManager
                .createQuery("select o from Order o where o.status = :status", Order.class)
                .setParameter("status", status)
                .getResultList();
    }

}
