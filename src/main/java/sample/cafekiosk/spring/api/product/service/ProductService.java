package sample.cafekiosk.spring.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sample.cafekiosk.spring.api.product.model.ProductRs;
import sample.cafekiosk.spring.api.product.repository.ProductRepository;
import sample.cafekiosk.spring.domain.entity.product.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductRs> getSellingProducts() {
        List<Product> productList = productRepository.findAllByProductSellingTypeIn(ProductSellingType.forDisplay());

        return productList.stream()
                .map(ProductRs::of)
                .collect(Collectors.toList());
    }

}
