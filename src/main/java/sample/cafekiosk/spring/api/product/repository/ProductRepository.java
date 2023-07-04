package sample.cafekiosk.spring.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sample.cafekiosk.spring.domain.entity.product.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * select *
     * from product
     * where product_selling_type in ('SELLING', 'HOLD')
     */
    List<Product> findAllByProductSellingTypeIn(List<ProductSellingType> sellingTypeList);

}
