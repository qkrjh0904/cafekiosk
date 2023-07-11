package sample.cafekiosk.spring.api.product.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.domain.entity.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.HOLD;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.STOP_SELLING;
import static sample.cafekiosk.spring.domain.enums.ProductType.BAKERY;
import static sample.cafekiosk.spring.domain.enums.ProductType.BOTTLE;
import static sample.cafekiosk.spring.domain.enums.ProductType.HANDMADE;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("원하는 판매 상태를 가진 상품들을 조회한다.")
    void findAllByProductSellingTypeIn() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", BOTTLE, HOLD, "사과주스", 5000);
        Product product3 = createProduct("003", BAKERY, STOP_SELLING, "치즈빵", 7000);
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

    @Test
    @DisplayName("원하는 판매 상태를 가진 상품들을 조회한다.")
    void findAllByProductNumberIn() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", BOTTLE, HOLD, "사과주스", 5000);
        Product product3 = createProduct("003", BAKERY, STOP_SELLING, "치즈빵", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> list = productRepository.findAllByProductNumberIn(List.of("002", "003"));

        // then
        assertThat(list).hasSize(2)
                .extracting("productNumber", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("002", "사과주스", 5000),
                        tuple("003", "치즈빵", 7000)
                );
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품 번호를 읽어온다.")
    void findLastProductNumber() {
        // given
        Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        Product product2 = createProduct("002", BOTTLE, HOLD, "사과주스", 5000);
        Product product3 = createProduct("003", BAKERY, STOP_SELLING, "치즈빵", 7000);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String lastProductNumber = productRepository.findLastProductNumber();

        // then
        assertThat(lastProductNumber).isEqualTo("003");
    }

    @Test
    @DisplayName("가장 마지막으로 저장한 상품의 상품 번호를 읽어올 때, 상품이 없는 경우에 null 을 반환한다.")
    void findLastProductNumberWhenProductsIsEmpty() {
        // given
        // when
        String lastProductNumber = productRepository.findLastProductNumber();

        // then
        assertThat(lastProductNumber).isEqualTo(null);
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