package blackjack.view;

import blackjack.model.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static blackjack.model.CardNumber.KING;
import static blackjack.model.CardNumber.THREE;
import static blackjack.model.CardType.CLOVER;
import static blackjack.model.CardType.HEART;
import static org.assertj.core.api.Assertions.assertThat;

public class ViewTest {

    @Nested
    @DisplayName("InputView 테스트")
    class InputViewTest {

        @Test
        @DisplayName("사람 이름을 입력하면 쉼표를 기준으로 분리한다.")
        void WhenNamesProvided_Expect_SplitByComma() {
            String input = "pobi,jason";
            assertThat(InputView.enroll(input).size()).isEqualTo(2);
        }

        @Test
        @DisplayName("베팅 금액을 받으면 참가자로 등록한다.")
        void GivenNameProvided_WhenParticipantBets_AddBet() {
            Player participant = new Player("pobi");
            participant.bet(1000);
            assertThat(participant).isEqualTo(new Player("pobi", 1000));
        }

        @Test
        @DisplayName("참가자의 수만큼 카드를 두 장씩 배분한다.")
        void GivenPlayers_WhenDealerDeals_DistributePairOfCardsRandomly() {
            List<Card> cardList = Arrays.asList(new Card(KING, HEART), new Card(THREE, CLOVER));
            Player player = new Player("pobi", 1000);
            player.receive(cardList);
            assertThat(player).isEqualTo(new Player("pobi", 1000, cardList));
        }

    }

    @Nested
    @DisplayName("Output View Test")
    class OutputViewTest {

    }

}
