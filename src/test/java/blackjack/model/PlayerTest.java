package blackjack.model;

import blackjack.view.Player;
import blackjack.view.Players;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import static blackjack.model.CardNumber.*;
import static blackjack.model.CardType.*;
import static blackjack.model.CardType.하트;
import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    Player dealer;
    Player player;

    @BeforeEach
    void init() {
        dealer = new Player("딜러");
        player = new Player("pobi");
    }

    @Test
    @DisplayName("해당 플레이어가 딜러인 경우, 카드의 합계가 16이 넘는 지 확인한다.")
    void GivenDealerCards_WhenSumOverSixteen_Expect_true() {
        dealer.receive(Arrays.asList(new Card(A, 하트), new Card(TWO, 스페이드)));
        assertThat(player.AbleToGetMoreCard()).isEqualTo(true);
    }

    @Test
    @DisplayName("해당 플레이어가 딜러가 아닌 경우, 카드의 합계가 21이 넘는 지 확인한다.")
    void GivenPlayerCards_WhenSumNotOverBlackJack_Expect_True() {
        player.receive(Arrays.asList(new Card(A, 하트), new Card(TEN, 스페이드)));
        assertThat(player.AbleToGetMoreCard()).isEqualTo(true);
    }

    @Nested
    @DisplayName("베팅 결과를 출력하는 테스트 코드들")
    class BetResult {

        private Players players;
        private Player dealer;
        private Player pobi;
        private Player jason;

        @BeforeEach
        void init() {
            players = new Players();
            dealer = players.getDealer();
            pobi = new Player("pobi");
            jason = new Player("jason");
        }

        @Test
        @DisplayName("어느 한 쪽도 BlackJack을 넘어가지 않는 경우, 합계가 가장 낮은 사람을 찾는다.")
        void GivenPlayers_WhenNoOneOverBlackJack_Expect_FindSmallestSumPlayer() {
            dealer.receive(Arrays.asList(new Card(A, 하트), new Card(K, 스페이드)));
            pobi.receive(Arrays.asList(new Card(FIVE, 다이아몬드), new Card(EIGHT, 클로버)));
            jason.receive(Arrays.asList(new Card(J, 스페이드), new Card(K, 하트)));
            players.add(pobi);
            players.add(jason);

            Optional<Player> opt = players.findLoser();
            opt.ifPresent((p) -> assertThat(p).isEqualTo(pobi));
        }


    }


}
