package sample.cafekiosk.spring.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.spring.domain.enums.OrderStatus;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.HOLD;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.STOP_SELLING;
import static sample.cafekiosk.spring.domain.enums.ProductType.BAKERY;
import static sample.cafekiosk.spring.domain.enums.ProductType.BOTTLE;
import static sample.cafekiosk.spring.domain.enums.ProductType.HANDMADE;

class OrderTest {
    @Test
    @DisplayName("" +
            "1. 주문 생성시 상품 리스트에서 주문의 총 금액을 계산한다." +
            "2. 새로 주문한 주문 상태는 INIT 이다." +
            "3. 주문 등록 시간을 기록한다."
    )
    void calculateTotalPrice() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", BOTTLE, HOLD, "사과주스", 5000);
        Product product3 = createProduct("003", BAKERY, STOP_SELLING, "치즈빵", 7000);
        List<Product> productList = List.of(product1, product2, product3);
        LocalDateTime now = LocalDateTime.now();

        // when
        Order order = Order.create(productList, now);

        // then
        assertThat(order.getTotalPrice()).isEqualTo(16000);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.INIT);
        assertThat(order.getRegisteredDateTime()).isEqualTo(now);
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