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
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public CreateOrderRs createOrder(OrderCreateRq rq, LocalDateTime registeredDateTime) {
        List<String> productNumberList = rq.getProductNumberList();
        List<Product> productList = findProductsBy(productNumberList);

        Order order = Order.create(productList, registeredDateTime);
        orderRepository.save(order);
        return CreateOrderRs.of(order);
    }

    private List<Product> findProductsBy(List<String> productNumberList) {
        List<Product> productList = productRepository.findAllByProductNumberIn(productNumberList);
        Map<String, Product> productMap = productList.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumberList.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }
}
