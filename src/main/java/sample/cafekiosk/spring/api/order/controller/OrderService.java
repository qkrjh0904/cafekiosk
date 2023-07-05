package sample.cafekiosk.spring.api.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.order.model.CreateOrderRs;
import sample.cafekiosk.spring.api.order.model.OrderCreateRq;
import sample.cafekiosk.spring.api.order.repository.OrderRepository;
import sample.cafekiosk.spring.api.product.repository.ProductRepository;
import sample.cafekiosk.spring.domain.entity.Order;
import sample.cafekiosk.spring.domain.entity.Product;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CreateOrderRs createOrder(OrderCreateRq rq, LocalDateTime registeredDateTime) {
        List<String> productNumberList = rq.getProductNumberList();
        List<Product> productList = productRepository.findAllByProductNumberIn(productNumberList);
        Order order = Order.create(productList, registeredDateTime);
        orderRepository.save(order);
        return CreateOrderRs.of(order);
    }
}
