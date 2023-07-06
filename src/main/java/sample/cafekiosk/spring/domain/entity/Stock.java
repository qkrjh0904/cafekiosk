package sample.cafekiosk.spring.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNumber;

    private int quantity;

    public static Stock create(String productNumber, int quantity) {
        Stock stock = new Stock();
        stock.productNumber = productNumber;
        stock.quantity = quantity;
        return stock;
    }

    public void reduce() {
        this.quantity -= 1;
    }
}
