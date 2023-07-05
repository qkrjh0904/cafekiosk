package sample.cafekiosk.spring.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.cafekiosk.spring.domain.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
