package sample.cafekiosk.spring.api.product.model;

import lombok.Builder;
import lombok.Getter;
import sample.cafekiosk.spring.domain.entity.product.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

@Getter
public class ProductRs {

    private Long id;
    private String productNumber;
    private ProductType productType;
    private ProductSellingType productSellingType;
    private String name;
    private Integer price;

    @Builder
    private ProductRs(Long id, String productNumber, ProductType productType, ProductSellingType productSellingType,
                      String name, Integer price) {
        this.id = id;
        this.productNumber = productNumber;
        this.productType = productType;
        this.productSellingType = productSellingType;
        this.name = name;
        this.price = price;
    }

    public static ProductRs of(Product product) {
        return ProductRs.builder()
                .id(product.getId())
                .productNumber(product.getProductNumber())
                .productType(product.getProductType())
                .productSellingType(product.getProductSellingType())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}
