package sample.cafekiosk.spring.api.product.service.rq;

import lombok.Getter;
import sample.cafekiosk.spring.domain.entity.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CreateProductServiceRq {

    private String productNumber;

    private ProductType productType;

    private ProductSellingType productSellingType;

    private String name;

    private Integer price;

    public static CreateProductServiceRq of(ProductType productType, ProductSellingType productSellingType, String name, Integer price) {
        CreateProductServiceRq rq = new CreateProductServiceRq();
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
