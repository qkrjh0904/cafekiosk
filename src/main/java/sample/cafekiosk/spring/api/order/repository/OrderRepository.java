package sample.cafekiosk.spring.api.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sample.cafekiosk.spring.domain.entity.Order;
import sample.cafekiosk.spring.domain.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "select * from orders o where o.registered_date_time >= :startDateTime and o.registered_date_time < :endDateTime and o.order_status = :orderStatus", nativeQuery = true)
    List<Order> findOrdersBy(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDateTime") LocalDateTime endDateTime, @Param("orderStatus") String orderStatus);
}
