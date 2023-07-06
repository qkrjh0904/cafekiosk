package sample.cafekiosk.spring.api.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.cafekiosk.spring.domain.entity.Stock;

import java.util.List;

public interface StockRepository extends JpaRepository<Stock, Long> {
    List<Stock> findAllByProductNumberIn(List<String> productNumberList);
}
