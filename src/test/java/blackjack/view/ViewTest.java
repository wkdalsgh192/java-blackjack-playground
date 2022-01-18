package blackjack.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class ViewTest {

    @Nested
    @DisplayName("InputView 테스트")
    class InputViewTest {

        @Test
        @DisplayName("사람 이름을 입력하면 쉼표를 기준으로 분리한다.")
        void WhenNamesProvided_Expect_SplitByComma() {
            String input = "pobi,jason";
            assertThat(InputView.from(input).length).isEqualTo(2);
        }

        @Test
        @DisplayName("베팅 금액을 받으면 참가자로 등록한다.")
        void GivenNameProvided_WhenParticipantBets_AddBet() {
            Persons participant = new Persons("pobi");
            participant.bet(1000);
            assertThat(participant).isEqualTo(new Persons("pobi", 1000));
        }

    }

    @Nested
    @DisplayName("Output View Test")
    class OutputViewTest {

    }

    private static class InputView {

        static String[] from(String input) {
            return input.split(",");
        }
    }

    private class Persons {

        private String name;
        private Integer money;

        public Persons(String name) {
            this.name = name;
        }

        public Persons(String name, Integer money) {
            this.name = name;
            this.money = money;
        }

        public void bet(Integer money) {
            this.money = money;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Persons persons = (Persons) o;
            return Objects.equals(name, persons.name) && Objects.equals(money, persons.money);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, money);
        }
    }
}
