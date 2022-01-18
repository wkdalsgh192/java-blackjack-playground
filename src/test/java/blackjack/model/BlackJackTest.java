package blackjack.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static blackjack.model.CardNumber.ACE;
import static blackjack.model.CardType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BlackJackTest {
    @Test
    @DisplayName("두 장의 카드가 주어졌을 때, 합을 계산한다.")
    void GivenTwoCards_WhenTheyAreNotOverBlackJack_SumAll() {
        Card card1 = new Card(CardNumber.FIVE, HEART);
        Card card2 = new Card(CardNumber.TEN, SPADE);
        Cards cards = Cards.of(card1, card2);
        assertThat(cards.sumAll()).isEqualTo(15);
    }

    @Test
    @DisplayName("두 장의 카드가 주어졌을 때, 21이 넘으면 예외를 발생시킨다.")
    void GivenTwoCards_WhenOverBlackJack_SumALl() {
        Card card1 = new Card(CardNumber.TEN, CLOVER);
        Card card2 = new Card(CardNumber.NINE, SPADE);
        Card card3 = new Card(CardNumber.SIX, DIAMOND);

        Cards cards = Cards.of(card1, card2);
        cards.add(card3);
        assertThatThrownBy(cards::sumAll).isInstanceOf(IllegalStateException.class).hasMessageContaining("블랙잭을 넘었습니다.");
    }

    @Test
    @DisplayName("두 장의 카드 중 1장이 ACE일 때, 21이 넘지 않는 최대값을 계산한다.")
    void GivenAceCard_WhenNotOverBlackJack_SumAll() {
        Card card1 = new Card(ACE, CLOVER);
        Card card2 = new Card(CardNumber.KING, HEART);
        Cards cards = Cards.of(card1, card2);
        assertThat(cards.sumAll()).isEqualTo(20);
        cards.add(new Card(ACE, HEART));
        assertThat(cards.sumAll()).isEqualTo(21);
    }
}
