package blackjack.model;

import blackjack.view.Player;
import blackjack.view.Players;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static blackjack.model.CardNumber.*;
import static blackjack.model.CardType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BlackJackTest {
    @Test
    @DisplayName("두 장의 카드가 주어졌을 때, 합을 계산한다.")
    void GivenTwoCards_WhenTheyAreNotOverBlackJack_SumAll() {
        Card card1 = new Card(CardNumber.FIVE, 하트);
        Card card2 = new Card(CardNumber.TEN, 스페이드);
        Cards cards = Cards.of(card1, card2);
        assertThat(cards.sumAll()).isEqualTo(15);
    }

    @Test
    @DisplayName("두 장의 카드가 주어졌을 때, 21이 넘으면 예외를 발생시킨다.")
    void GivenTwoCards_WhenOverBlackJack_SumALl() {
        Card card1 = new Card(CardNumber.TEN, 클로버);
        Card card2 = new Card(CardNumber.NINE, 스페이드);
        Card card3 = new Card(CardNumber.SIX, 다이아몬드);

        Cards cards = Cards.of(card1, card2);
        cards.add(card3);
        assertThatThrownBy(cards::sumAll).isInstanceOf(IllegalStateException.class).hasMessageContaining("블랙잭을 넘었습니다.");
    }

    @Test
    @DisplayName("두 장의 카드 중 1장이 ACE일 때, 21이 넘지 않는 최대값을 계산한다.")
    void GivenAceCard_WhenNotOverBlackJack_SumAll() {
        Card card1 = new Card(A, 클로버);
        Card card2 = new Card(K, 하트);
        Cards cards = Cards.of(card1, card2);
        assertThat(cards.sumAll()).isEqualTo(20);
        cards.add(new Card(A, 하트));
        assertThat(cards.sumAll()).isEqualTo(21);
    }

    @Test
    @DisplayName("두 장의 카드를 랜덤으로 섞을 때, 두 장의 카드는 가장 첫 두 장이 아니다.")
    void GivenTwoCards_WhenShuffleRandomly_Expect_NotEqualToFirstTwoCards() {
        List<Card> cardList;
        Cards expected = Cards.of(new Card(A, 하트), new Card(A, 스페이드));
        cardList = Cards.shuffle(() -> 1);
        assertThat(Cards.of(cardList.get(0), cardList.get(1))).isNotEqualTo(expected);
        cardList = Cards.shuffle(() -> 0);
        assertThat(Cards.of(cardList.get(0), cardList.get(1))).isEqualTo(expected); //NOTE: 어떻게 완전한 랜덥값을 보장할까?
    }

    @Test
    @DisplayName("해당 플레이어가 딜러인 경우, 카드의 합계가 16이 넘는 지 확인한다.")
    void GivenDealerCards_WhenSumOverSixteen_Expect_true() {
        Player player = new Player("딜러");
        player.receive(Arrays.asList(new Card(A, 하트), new Card(TWO, 스페이드)));
        assertThat(player.AbleToGetMoreCard()).isEqualTo(true);
    }

    @Test
    @DisplayName("해당 플레이어가 딜러가 아닌 경우, 카드의 합계가 21이 넘는 지 확인한다.")
    void GivenPlayerCards_WhenSumNotOverBlackJack_Expect_True() {
        Player player = new Player("pobi");
        player.receive(Arrays.asList(new Card(A, 하트), new Card(TEN, 스페이드)));
        assertThat(player.AbleToGetMoreCard()).isEqualTo(true);
    }

    @Test
    @DisplayName("어느 한 쪽도 BlackJack을 넘어가지 않는 경우, 합계가 가장 낮은 사람을 찾는다.")
    void GivenPlayers_WhenNoOneOverBlackJack_Expect_FindSmallestSumPlayer() {
        Players players = new Players();
        Player dealer = players.getDealer();
        dealer.receive(Arrays.asList(new Card(A, 하트), new Card(K, 스페이드)));
        Player pobi = new Player("pobi");
        pobi.receive(Arrays.asList(new Card(FIVE, 다이아몬드), new Card(EIGHT, 클로버)));
        players.add(pobi);
        Player jason = new Player("jason");
        jason.receive(Arrays.asList(new Card(J, 스페이드), new Card(K, 하트)));
        players.add(jason);

        Optional<Player> smallestSumPlayer = players.getAllPlayers().stream().sorted(Comparator.comparingInt(Player::getResult)).findFirst();
        smallestSumPlayer.ifPresent((p) -> assertThat(p).isEqualTo(pobi));
    }

}
