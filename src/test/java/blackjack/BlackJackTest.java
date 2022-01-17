package blackjack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static blackjack.BlackJackTest.CardNumber.ACE;
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
        Card card1 = new Card(ACE, CLOVER);
        Card card2 = new Card(CardNumber.KING, HEART);
        Cards cards = Cards.of(card1, card2);
        assertThat(cards.sumAll()).isEqualTo(20);
        cards.add(new Card(ACE, HEART));
        assertThat(cards.sumAll()).isEqualTo(21);
    }

    private class Card {

        private CardNumber number;
        private CardType type;

        public Card(CardNumber number, CardType type) {
            this.number = number;
            this.type = type;
        }

        public int getNumericValue() { return number.getNumericValue(); }

        public CardNumber getCardNumber() { return number; }
    }

    enum CardType {
        HEART,
        SPADE,
        CLOVER,
        DIAMOND
    }

    private static class Cards {

        private final int BLACKJACK_NUMBER = 21;
        private final Set<Card> cardSet = new HashSet<>();

        public static Cards of(Card c1, Card c2) {
            Cards cards = new Cards();
            cards.add(c1);
            cards.add(c2);
            return cards;
        }

        public void add(Card card) {
            this.cardSet.add(card);
        }

/*        public int sumAll() {
            Optional<Integer> optionalInt = Optional.of(cardSet.stream().mapToInt(Card::getNumericValue).sum());
            return optionalInt.filter(x -> x <= BLACKJACK_NUMBER).orElseThrow(() -> new IllegalStateException("블랙잭을 넘었습니다."));
        }*/

        public int sumAll() {
            List<Card> list = cardSet.stream().sorted(Comparator.comparing(Card::getNumericValue, Comparator.reverseOrder())).collect(Collectors.toList());
            int total = 0;
            for (Card card : list) {
                total = card.getCardNumber().apply(total);
            }
            if (total > BLACKJACK_NUMBER) throw new IllegalStateException("블랙잭을 넘었습니다.");
            else return total;
        }
    }

    enum CardNumber {
        ACE(1,t -> t + 10 > 21 ? t + 1 : t + 10),
        TWO(2, t -> t + 2),
        THREE(3, t -> t + 3),
        FOUR(4, t -> t + 4),
        FIVE(5,t -> t + 5),
        SIX(6, t -> t + 6),
        SEVEN(7, t -> t + 7),
        EIGHT(8, t -> t + 8),
        NINE(9, t -> t + 9),
        TEN(10, t -> t + 10),
        JACK(10, t -> t + 10),
        QUEEN(10, t -> t + 10),
        KING(10, t -> t + 10);

        private Integer value;
        private Function<Integer, Integer> function;

        CardNumber(Integer value, Function<Integer, Integer> function) {
            this.value = value;
            this.function = function;
        }

        public int apply(int currentSum) { return function.apply(currentSum);}

        public int getNumericValue() { return value; }
    }
}
