package sample.cafekiosk.learning;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GuavaLearningTest {

    @Test
    @DisplayName("주어진 개수만큼 List 를 파티셔닝 한다.")
    void partitionLearningTestWithSize3() {
        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 3);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                                List.of(1, 2, 3), List.of(4, 5)
                        )
                );
    }

    @Test
    @DisplayName("주어진 개수만큼 List 를 파티셔닝 한다.")
    void partitionLearningTestWithSize4() {
        // given
        List<Integer> integers = List.of(1, 2, 3, 4, 5);

        // when
        List<List<Integer>> partition = Lists.partition(integers, 4);

        // then
        assertThat(partition).hasSize(2)
                .isEqualTo(List.of(
                                List.of(1, 2, 3, 4), List.of(5)
                        )
                );
    }

    @Test
    @DisplayName("멀티맵 기능 확인")
    void multiMapLearningTest() {
        // given
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "식빵");
        multimap.put("베이커리", "크루아상");

        // when
        Collection<String> coffee = multimap.get("커피");
        Collection<String> bakery = multimap.get("베이커리");

        // then
        assertThat(coffee).hasSize(3)
                .isEqualTo(List.of("아메리카노", "카페라떼", "카푸치노"));
        assertThat(bakery).hasSize(2)
                .isEqualTo(List.of("식빵", "크루아상"));
    }

    @TestFactory
    @DisplayName("멀티맵 기능 확인")
    Collection<DynamicTest> multiMapLearningTest2() {
        // given
        Multimap<String, String> multimap = ArrayListMultimap.create();
        multimap.put("커피", "아메리카노");
        multimap.put("커피", "카페라떼");
        multimap.put("커피", "카푸치노");
        multimap.put("베이커리", "식빵");
        multimap.put("베이커리", "크루아상");


        return List.of(
                DynamicTest.dynamicTest("1개 value 삭제", () -> {
                    // given
                    multimap.remove("커피", "카푸치노");

                    // when
                    Collection<String> strings = multimap.get("커피");

                    // then
                    assertThat(strings).hasSize(2)
                            .isEqualTo(List.of("아메리카노", "카페라떼"));
                }),
                DynamicTest.dynamicTest("1개 key 삭제", () -> {
                    // given
                    multimap.removeAll("베이커리");

                    // when
                    Collection<String> strings = multimap.get("베이커리");

                    // then
                    assertThat(strings).hasSize(0);
                })
        );
    }
}
