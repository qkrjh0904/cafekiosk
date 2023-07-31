package sample.cafekiosk.spring.api.product.model;

import lombok.Getter;
import sample.cafekiosk.spring.api.product.service.rq.CreateProductServiceRq;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;
import sample.cafekiosk.spring.domain.enums.ProductType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class CreateProductRq {

    @NotNull(message = "상품 타입은 필수입니다.")
    private ProductType productType;

    @NotNull(message = "상품 판매상태는 필수입니다.")
    private ProductSellingType productSellingType;

    @NotBlank(message = "상품 명은 필수입니다.")
    private String name;

    @Positive(message = "상품 가격은 양수여야합니다.")
    private Integer price;

    public static CreateProductRq of(ProductType productType, ProductSellingType productSellingType, String name, Integer price) {
        CreateProductRq rq = new CreateProductRq();
        rq.productType = productType;
        rq.productSellingType = productSellingType;
        rq.name = name;
        rq.price = price;
        return rq;
    }

    public CreateProductServiceRq toServiceRq() {
        return CreateProductServiceRq.of(productType, productSellingType, name, price);
    }
}
