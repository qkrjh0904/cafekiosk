package sample.cafekiosk.spring.api.product.model;

import lombok.Getter;
import sample.cafekiosk.spring.domain.entity.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

import javax.validation.constraints.NotNull;

@Getter
public class CreateProductRq {

    private String productNumber;

    @NotNull
    private ProductType productType;

    @NotNull
    private ProductSellingType productSellingType;

    @NotNull
    private String name;

    @NotNull
    private Integer price;

    public static CreateProductRq of(ProductType productType, ProductSellingType productSellingType, String name, Integer price) {
        CreateProductRq rq = new CreateProductRq();
        rq.productType = productType;
        rq.productSellingType = productSellingType;
        rq.name = name;
        rq.price = price;
        return rq;
    }

    public Product toEntity(String productNumber) {
        return Product.builder()
                .productNumber(productNumber)
                .price(this.price)
                .productType(this.productType)
                .productSellingType(this.productSellingType)
                .name(this.name)
                .build();
    }
}
