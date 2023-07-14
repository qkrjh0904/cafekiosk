package sample.cafekiosk.spring.api.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sample.cafekiosk.spring.api.ApiResponse;
import sample.cafekiosk.spring.api.product.model.CreateProductRq;
import sample.cafekiosk.spring.api.product.model.ProductRs;
import sample.cafekiosk.spring.api.product.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/api/v1/products/new")
    public ApiResponse<ProductRs> createProduct(@RequestBody @Validated CreateProductRq rq) {
        return ApiResponse.ok(productService.createProduct(rq));
    }

    @GetMapping("/api/v1/products/selling")
    public List<ProductRs> getSellingProducts() {
        return productService.getSellingProducts();
    }

}
