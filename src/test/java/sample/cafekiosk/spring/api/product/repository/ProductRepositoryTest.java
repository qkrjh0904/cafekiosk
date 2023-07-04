package sample.cafekiosk.spring.api.product.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.domain.entity.product.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@ActiveProfiles("test")
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 판매 상태를 가진 상품들을 조회한다.")
    void findAllByProductSellingTypeIn() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .productType(ProductType.HANDMADE)
                .productSellingType(ProductSellingType.SELLING)
                .name("아메리카노")
                .price(4000)
                .build();

        Product product2 = Product.builder()
                .productNumber("002")
                .productType(ProductType.BOTTLE)
                .productSellingType(ProductSellingType.HOLD)
                .name("사과주스")
                .price(5000)
                .build();

        Product product3 = Product.builder()
                .productNumber("003")
                .productType(ProductType.BAKERY)
                .productSellingType(ProductSellingType.STOP_SELLING)
                .name("치즈빵")
                .price(7000)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> list = productRepository.findAllByProductSellingTypeIn(ProductSellingType.forDisplay());

        // then
        assertThat(list).hasSize(2)
                .extracting("productNumber", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", 4000),
                        tuple("002", "사과주스", 5000)
                );
    }
}