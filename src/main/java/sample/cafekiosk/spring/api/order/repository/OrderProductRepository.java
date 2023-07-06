package sample.cafekiosk.spring.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.cafekiosk.spring.domain.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
