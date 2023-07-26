package sample.cafekiosk.spring.api.product.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.product.model.CreateProductRq;
import sample.cafekiosk.spring.api.product.model.ProductRs;
import sample.cafekiosk.spring.api.product.repository.ProductRepository;
import sample.cafekiosk.spring.api.product.service.rq.CreateProductServiceRq;
import sample.cafekiosk.spring.domain.entity.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.domain.enums.ProductSellingType.SELLING;
import static sample.cafekiosk.spring.domain.enums.ProductType.HANDMADE;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    static void beforeAll() {
        // before class
    }

    @BeforeEach
    void setUp() {
        // before method

        // 각 테스트 입장에서 봤을 때 setUp 메서드를 몰라도 테스트 내용을 이해하는데 문제가 없는가?
        // 수정해도 모든 테스트에 영향을 주지 않는가?
    }

    @Test
    @DisplayName("신규 상품을 등록한다. 상품 번호는 가장 최근 상품의 상품 번호에서 1 증가한 값이다.")
    void createProduct() {
        // given
        Product product = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
        productRepository.save(product);

        // when
        CreateProductServiceRq rq = CreateProductServiceRq.of(HANDMADE, SELLING, "카푸치노", 5000);
        ProductRs rs = productService.createProduct(rq);

        // then
        assertThat(rs)
                .extracting("productNumber", "productType", "productSellingType", "name", "price")
                .contains("002", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> list = productRepository.findAll();
        assertThat(list).hasSize(2)
                .extracting("productNumber", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", 4000),
                        tuple("002", "카푸치노", 5000)
                );
    }

    @Test
    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면 상품 번호는 001이다.")
    void createProductWhenProductsIsEmpty() {
        // given
        CreateProductServiceRq rq = CreateProductServiceRq.of(HANDMADE, SELLING, "카푸치노", 5000);

        // when
        ProductRs rs = productService.createProduct(rq);

        // then
        assertThat(rs)
                .extracting("productNumber", "productType", "productSellingType", "name", "price")
                .contains("001", HANDMADE, SELLING, "카푸치노", 5000);

        List<Product> list = productRepository.findAll();
        assertThat(list).hasSize(1)
                .extracting("productNumber", "name", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", "카푸치노", 5000)
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