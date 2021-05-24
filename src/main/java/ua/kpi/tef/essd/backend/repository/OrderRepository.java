package ua.kpi.tef.essd.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.kpi.tef.essd.backend.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

}
