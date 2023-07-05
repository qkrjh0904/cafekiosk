package sample.cafekiosk.spring.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.domain.enums.OrderStatus;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    public static Order create(List<Product> productList, LocalDateTime registeredDateTime) {
        Order order = new Order();
        order.orderStatus = OrderStatus.INIT;
        order.totalPrice = calculateTotalPrice(productList);
        order.registeredDateTime = registeredDateTime;
        order.orderProductList = productList.stream()
                .map(product -> OrderProduct.create(order, product))
                .collect(Collectors.toList());
        return order;
    }

    private static int calculateTotalPrice(List<Product> productList) {
        return productList.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }
}
