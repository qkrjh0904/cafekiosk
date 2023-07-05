package sample.cafekiosk.spring.api.order.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.api.order.model.CreateOrderRs;
import sample.cafekiosk.spring.api.order.model.OrderCreateRq;
import sample.cafekiosk.spring.api.product.repository.ProductRepository;
import sample.cafekiosk.spring.domain.entity.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.HOLD;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.STOP_SELLING;
import static sample.cafekiosk.spring.domain.enums.ProductType.BAKERY;
import static sample.cafekiosk.spring.domain.enums.ProductType.BOTTLE;
import static sample.cafekiosk.spring.domain.enums.ProductType.HANDMADE;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("주문 번호 리스트를 받아 주문을 생성한다.")
    void createOrder() {
        // given
        Product product1 = createProduct("001", BAKERY, STOP_SELLING, "치즈빵", 3000);
        Product product2 = createProduct("002", BOTTLE, HOLD, "사과주스", 4500);
        Product product3 = createProduct("003", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRq rq = OrderCreateRq.builder()
                .productNumberList(List.of("001", "002"))
                .build();

        LocalDateTime now = LocalDateTime.now();

        // when
        CreateOrderRs rs = orderService.createOrder(rq, now);

        // then
        assertThat(rs.getId()).isNotNull();
        assertThat(rs)
                .extracting("registeredDateTime", "totalPrice")
                .contains(now, 7500);
        assertThat(rs.getProductList()).hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 3000),
                        tuple("002", 4500)
                );

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