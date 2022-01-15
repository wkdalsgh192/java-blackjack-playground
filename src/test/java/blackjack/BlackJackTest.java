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
        Card card1 = new Card(5, HEART);
        Card card2 = new Card(10, SPADE);
        Cards cards = Cards.of(card1, card2);
        assertThat(cards.sumAll()).isEqualTo(15);
    }

    @Test
    @DisplayName("두 장의 카드가 주어졌을 때, 21이 넘으면 예외를 발생시킨다.")
    void GivenTwoCards_WhenOverBlackJack_SumALl() {
        Card card1 = new Card(10, CLOVER);
        Card card2 = new Card(9, SPADE);
        Card card3 = new Card(6, DIAMOND);

        Cards cards = Cards.of(card1, card2);
        cards.add(card3);
        assertThatThrownBy(cards::sumAll).isInstanceOf(IllegalStateException.class).hasMessageContaining("블랙잭을 넘었습니다.");
    }

    private class Card {

        private int number;
        private CardType type;

        public Card(int number, CardType type) {
            this.number = number;
            this.type = type;
        }

        public int getNumber() { return number; }
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
            Optional<Integer> optionalInt = Optional.of(cardSet.stream().mapToInt(Card::getNumber).sum());
            return optionalInt.filter(x -> x <= 21).orElseThrow(() -> new IllegalStateException("블랙잭을 넘었습니다."));
        }
    }
}
