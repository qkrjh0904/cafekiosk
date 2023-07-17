package sample.cafekiosk.spring.api.order.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.mail.repository.MailSendHistoryRepository;
import sample.cafekiosk.spring.api.order.repository.OrderRepository;
import sample.cafekiosk.spring.api.product.repository.ProductRepository;
import sample.cafekiosk.spring.domain.entity.MailSendHistory;
import sample.cafekiosk.spring.domain.entity.Order;
import sample.cafekiosk.spring.domain.entity.Product;
import sample.cafekiosk.spring.domain.enums.OrderStatus;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.enums.ProductType.HANDMADE;


@SpringBootTest
@ActiveProfiles("test")
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @Test
    @DisplayName("결제 완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
    void test() {
        // given
        LocalDateTime orderDateTime1 = LocalDateTime.of(2023, 7, 17, 21, 30);
        LocalDateTime orderDateTime2 = LocalDateTime.of(2023, 7, 16, 21, 30);
        LocalDateTime orderDateTime3 = LocalDateTime.of(2023, 7, 15, 21, 30);

        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 1000);
        Product product2 = createProduct("002", HANDMADE, SELLING, "사과주스", 2000);
        Product product3 = createProduct("003", HANDMADE, SELLING, "치즈빵", 3000);
        List<Product> productList = List.of(product1, product2, product3);
        productRepository.saveAll(productList);

        Order order1 = Order.create(productList, orderDateTime1);
        Order order2 = Order.create(productList, orderDateTime2);
        Order order3 = Order.create(productList, orderDateTime3);
        order1.updateStatus(OrderStatus.PAYMENT_COMPLETED);
        order2.updateStatus(OrderStatus.PAYMENT_COMPLETED);
        order3.updateStatus(OrderStatus.PAYMENT_COMPLETED);
        orderRepository.saveAll(List.of(order1, order2, order3));

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2023, 7, 17), "qkrjh0904@gmail.com");
        List<MailSendHistory> mailSendHistories = mailSendHistoryRepository.findAll();

        // then
        assertThat(result).isTrue();
        assertThat(mailSendHistories).hasSize(1)
                .extracting("contents")
                .contains("총 매출 합계는 6000원 입니다.");
    }

    private Product createProduct(String productNumber, ProductType productType, ProductSellingType productSellingType,
                                  String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .productType(productType)
                .productSellingType(productSellingType)
                .name(name)
                .price(price)
                .build();
    }

}