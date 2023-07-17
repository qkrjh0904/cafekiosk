package sample.cafekiosk.spring.api.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.order.model.CreateOrderRs;
import sample.cafekiosk.spring.api.order.model.OrderCreateRq;
import sample.cafekiosk.spring.api.order.repository.OrderRepository;
import sample.cafekiosk.spring.api.product.repository.ProductRepository;
import sample.cafekiosk.spring.api.stock.StockRepository;
import sample.cafekiosk.spring.domain.entity.Order;
import sample.cafekiosk.spring.domain.entity.Product;
import sample.cafekiosk.spring.domain.entity.Stock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public CreateOrderRs createOrder(OrderCreateRq rq, LocalDateTime registeredDateTime) {
        List<String> productNumberList = rq.getProductNumberList();
        List<Product> productList = findProductsBy(productNumberList);

        Map<String, Stock> stockMap = stockRepository.findAllByProductNumberIn(productNumberList).stream()
                .collect(Collectors.toMap(Stock::getProductNumber, Function.identity()));
        for (Product product : productList) {
            if (stockMap.containsKey(product.getProductNumber())) {
                stockMap.get(product.getProductNumber()).reduce();
            }
        }

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
