package sample.cafekiosk.spring.api.order.model;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderCreateRq {

    private List<String> productNumberList = new ArrayList<>();

    @Builder
    private OrderCreateRq(List<String> productNumberList) {
        this.productNumberList = productNumberList;
    }
}
