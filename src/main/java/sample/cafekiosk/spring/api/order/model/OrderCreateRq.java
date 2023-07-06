package sample.cafekiosk.spring.api.order.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class OrderCreateRq {

    private List<String> productNumberList = new ArrayList<>();

    public static OrderCreateRq of(List<String> productNumberList) {
        OrderCreateRq rq = new OrderCreateRq();
        rq.productNumberList = productNumberList;
        return rq;
    }
}
