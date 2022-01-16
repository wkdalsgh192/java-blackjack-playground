package blackjack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static blackjack.BlackJackTest.CardType.*;
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
        Card card1 = new Card(CardNumber.ACE, CLOVER);
        Card card2 = new Card(CardNumber.KING, HEART);
        Cards cards = Cards.of(card1, card2);
        assertThat(cards.sumAll()).isEqualTo(20);
    }

    private class Card {

        private CardNumber number;
        private CardType type;

        public Card(CardNumber number, CardType type) {
            this.number = number;
            this.type = type;
        }

        public int getNumber() { return number.getNumericValue(); }
    }

    enum CardType {
        HEART,
        SPADE,
        CLOVER,
        DIAMOND
    }

    private static class Cards {
        Set<Card> cardSet = new HashSet<>();

        public static Cards of(Card c1, Card c2) {
            Cards cards = new Cards();
            cards.add(c1);
            cards.add(c2);
            return cards;
        }

        public void add(Card card) {
            this.cardSet.add(card);
        }

        public int sumAll() {
            int total  = 0;
            for (Card card : cardSet) {
            }
            Optional<Integer> optionalInt = Optional.of(cardSet.stream().mapToInt(Card::getNumber).sum());
            return optionalInt.filter(x -> x <= 21).orElseThrow(() -> new IllegalStateException("블랙잭을 넘었습니다."));
        }
    }

    private enum CardNumber {
        ACE(1),
        TWO(2),
        THREE(3),
        FOUR(4),
        FIVE(5),
        SIX(6),
        SEVEN(7),
        EIGHT(8),
        NINE(9),
        TEN(10),
        JACK(10),
        QUEEN(10),
        KING(10);

        private int value;

        CardNumber(int value) {
            this.value = value;
        }

        public int getNumericValue() { return value; }
    }
}
