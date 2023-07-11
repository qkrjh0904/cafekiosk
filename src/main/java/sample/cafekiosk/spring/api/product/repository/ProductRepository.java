package sample.cafekiosk.spring.api.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sample.cafekiosk.spring.domain.entity.Product;
import sample.cafekiosk.spring.domain.enums.ProductSellingType;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * select *
     * from product
     * where product_selling_type in ('SELLING', 'HOLD')
     */
    List<Product> findAllByProductSellingTypeIn(List<ProductSellingType> sellingTypeList);

    List<Product> findAllByProductNumberIn(List<String> productNumberList);

    @Query(value = "select p.product_number from product p order by id desc limit 1", nativeQuery = true)
    String findLastProductNumber();
}
