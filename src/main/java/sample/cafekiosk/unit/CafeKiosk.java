package sample.cafekiosk.unit;

import lombok.Getter;
import sample.cafekiosk.unit.beverage.Beverage;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private static final LocalTime SHOP_OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime SHOP_CLOSE_TIME = LocalTime.of(22, 0);

    private final List<Beverage> beverageList = new ArrayList<>();

    public void add(Beverage beverage) {
        beverageList.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
        }

        for (int i = 0; i < count; ++i) {
            beverageList.add(beverage);
        }
    }

    public void remove(Beverage beverage) {
        beverageList.remove(beverage);
    }

    public void clear() {
        beverageList.clear();
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage beverage : beverageList) {
            totalPrice += beverage.getPrice();
        }
        return totalPrice;
    }

    public int calculateTotalPriceWithTdd() {
        return beverageList.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }

    public Order createOrder(LocalDateTime now) {
        LocalTime currentTime = now.toLocalTime();
        if (currentTime.isAfter(SHOP_OPEN_TIME) && currentTime.isBefore(SHOP_CLOSE_TIME)) {
            return new Order(now, beverageList);
        }

        throw new IllegalArgumentException("주문 시간이 아닙니다.");
    }

}
