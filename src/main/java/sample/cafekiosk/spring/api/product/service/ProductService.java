package sample.cafekiosk.spring.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.api.product.model.CreateProductRq;
import sample.cafekiosk.spring.api.product.model.ProductRs;
import sample.cafekiosk.spring.api.product.repository.ProductRepository;
import sample.cafekiosk.spring.api.product.service.rq.CreateProductServiceRq;
import sample.cafekiosk.spring.domain.entity.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductRs> getSellingProducts() {
        List<Product> productList = productRepository.findAllByProductSellingTypeIn(ProductSellingType.forDisplay());

        return productList.stream()
                .map(ProductRs::of)
                .collect(Collectors.toList());
    }

    public ProductRs createProduct(CreateProductServiceRq rq) {
        // productNumber
        // 001 002 003 004
        // db 에서 마지막 저장된 Product의 상품 번호를 읽어서 +1
        // 009 -> 010
        String productNumber = createNextProductNumber();
        Product product = rq.toEntity(productNumber);
        productRepository.save(product);
        return ProductRs.of(product);
    }

    private String createNextProductNumber() {
        String lastProductNumber = productRepository.findLastProductNumber();
        if (Objects.isNull(lastProductNumber)) {
            return "001";
        }

        int productNumber = Integer.parseInt(lastProductNumber) + 1;
        return String.format("%03d", productNumber);
    }
}
