package blackjack.view;

import blackjack.model.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static blackjack.model.CardNumber.*;
import static blackjack.model.CardType.*;
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
            List<Card> cardList = Arrays.asList(new Card(K, 하트), new Card(THREE, 클로버));
            Player player = new Player("pobi", 1000);
            player.receive(cardList);
            assertThat(player).isEqualTo(new Player("pobi", 1000, cardList));
        }

        @RepeatedTest(5)
        @DisplayName("참가자에게 2장씩 나누고, 해당 결과를 출력한다.")
        void GivenDealerDeals_WhenDistributeTwoCards_PrintOutTheResult() {
            List<Card> cardList = Arrays.asList(new Card(K, 하트), new Card(THREE, 클로버));
            Player player = new Player("pobi", 1000);
            player.receive(cardList);
            assertThat(player.toString()).isEqualTo("pobi: K하트, 3클로버");
        }

    }

    @Nested
    @DisplayName("Output View Test")
    class OutputViewTest {

        @Test
        @DisplayName("카드 분배가 모두 끝나면 각자의 카드와 합을 출력한다.")
        void whenDealEnds_Expect_PrintOutSumOfCardNumbers() {
            Players players = Players.initialize();
            Player dealer = players.getDealer();
            dealer.receive(Arrays.asList(new Card(K, 하트), new Card(THREE, 클로버)));
            Player player = new Player("pobi");
            player.receive(Arrays.asList(new Card(TWO, 스페이드), new Card(THREE, 클로버)));
            players.add(player);

            OutputView outputView = new OutputView();
            String expected = "딜러: K하트, 3클로버 - 결과: 13\npobi: 2스페이드, 3클로버 - 결과: 5\n";
            assertThat(outputView.printFinalCardSet(players)).isEqualTo(expected);
        }

        public class OutputView {

            public String printFinalCardSet(Players players) {
                StringBuilder sb = new StringBuilder();
                String message = " - 결과: ";
                for(Player p : players.getAllPlayers()) { sb.append(p).append(message).append(p.getResult()+"\n"); }
                return sb.toString();
            }

            public String printBetResult(Players players) {
                StringBuilder sb = new StringBuilder();
                String message = "";
                return "";
            }
        }

        @Test
        @DisplayName("최종 수익을 계산해 분배한 결과를 출력한다.")
        void whenDealEnds_Expect_PrintOutBetResult() {
            Players players = Players.initialize();
            Player dealer = players.getDealer();
            dealer.receive(Arrays.asList(new Card(K, 하트), new Card(THREE, 클로버)));
            Player player = new Player("pobi");
            player.receive(Arrays.asList(new Card(TWO, 스페이드), new Card(THREE, 클로버)));
            players.add(player);

            OutputView outputView = new OutputView();
            String betResult = outputView.printBetResult(players);

            String expected = "## 최종 수익\n딜러: \npobi: \njason: ";
            assertThat(betResult).isEqualTo(expected);
        }

    }

}
