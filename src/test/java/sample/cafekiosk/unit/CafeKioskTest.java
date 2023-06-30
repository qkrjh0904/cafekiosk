package sample.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;

class CafeKioskTest {

    /**
     * 잘못된 테스트의 예.
     * 1. 사람이 확인해야함.
     * 2. 무조건 통과하는 테스트.
     * 3. 무엇을 테스트하는지 알기 힘듦.
     */
    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료의 수 : " + cafeKiosk.getBeverageList().size());
        System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverageList().get(0).getName());
    }

}