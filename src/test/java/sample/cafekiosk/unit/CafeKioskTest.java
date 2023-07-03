package sample.cafekiosk.unit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CafeKioskTest {

    /**
     * 잘못된 테스트의 예.
     * 1. 사람이 확인해야함.
     * 2. 무조건 통과하는 테스트.
     * 3. 무엇을 테스트하는지 알기 힘듦.
     */
    @Test
    void addManualTest() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료의 수 : " + cafeKiosk.getBeverageList().size());
        System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverageList().get(0).getName());
    }

    @Test
    @DisplayName("음료 1개 추가하면 주문 목록에 담긴다.")
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverageList()).hasSize(1);
        assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);
        assertThat(cafeKiosk.getBeverageList().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverageList().get(1)).isEqualTo(americano);

        cafeKiosk.clear();
        Latte latte = new Latte();
        assertThatThrownBy(() -> cafeKiosk.add(latte, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
    }

    @Test
    void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverageList()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverageList()).isEmpty();
    }

    @Test
    void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverageList()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverageList()).isEmpty();
    }

    @Test
    @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
    void calculateTotalPriceWithTdd() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        // when
        int totalPrice = cafeKiosk.calculateTotalPriceWithTdd();

        // then
        assertThat(totalPrice).isEqualTo(8500);
    }

    @Test
    @DisplayName("영업 시간 중에 주문을 주문할 수 있다.")
    void createOrderAtOpenTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        // 시간을 인자로 받지 않으면 테스트 수행 시간에 따라 결과가 달라지는 테스트가 될 수 있다.
        LocalDateTime now = LocalDateTime.of(2023, 7, 3, 10, 0, 0, 1);
        Order order = cafeKiosk.createOrder(now);
        assertThat(order.getBeverageList()).hasSize(1);
        assertThat(order.getBeverageList().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    @DisplayName("영업 시작 시간 이전에는 주문을 생성할 수 없다.")
    void createOrderAtClosedTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        // 시간을 인자로 받지 않으면 테스트 수행 시간에 따라 결과가 달라지는 테스트가 될 수 있다.
        LocalDateTime now1 = LocalDateTime.of(2023, 7, 3, 22, 0);
        assertThatThrownBy(() -> cafeKiosk.createOrder(now1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다.");

        LocalDateTime now2 = LocalDateTime.of(2023, 7, 3, 10, 0);
        assertThatThrownBy(() -> cafeKiosk.createOrder(now2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다.");
    }

}