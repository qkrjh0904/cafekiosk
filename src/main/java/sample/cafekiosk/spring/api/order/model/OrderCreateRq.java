package sample.cafekiosk.spring.api.order.model;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderCreateRq {

    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumberList = new ArrayList<>();

    public static OrderCreateRq of(List<String> productNumberList) {
        OrderCreateRq rq = new OrderCreateRq();
        rq.productNumberList = productNumberList;
        return rq;
    }
}
