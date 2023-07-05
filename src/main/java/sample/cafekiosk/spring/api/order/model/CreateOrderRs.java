package sample.cafekiosk.spring.api.order.model;

import lombok.Getter;
import sample.cafekiosk.spring.api.product.model.ProductRs;
import sample.cafekiosk.spring.domain.entity.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CreateOrderRs {
    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductRs> productList;

    public static CreateOrderRs of(Order order) {
        CreateOrderRs rs = new CreateOrderRs();
        rs.id = order.getId();
        rs.totalPrice = order.getTotalPrice();
        rs.registeredDateTime = order.getRegisteredDateTime();
        rs.productList = order.getOrderProductList().stream()
                .map(orderProduct -> ProductRs.of(orderProduct.getProduct()))
                .collect(Collectors.toList());
        return rs;
    }
}
