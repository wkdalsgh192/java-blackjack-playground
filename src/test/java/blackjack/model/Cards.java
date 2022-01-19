package blackjack.model;

import java.util.*;
import java.util.stream.Collectors;

public class Cards {

    private final int BLACKJACK_NUMBER = 21;
    private final Set<Card> cardSet = new HashSet<>();

    public static Cards of(Card c1, Card c2) {
        Cards cards = new Cards();
        cards.add(c1);
        cards.add(c2);
        return cards;
    }

    public static List<Card> shuffle(CardStrategy strategy) {
        List<Card> deck = new ArrayList<>();
        for (CardNumber number : CardNumber.values()) {
            for (CardType type : CardType.values()) {
                deck.add(new Card(number, type));
            }
        }
        if (strategy.isRandom()) Collections.shuffle(deck);
        return deck;
    }

    public void add(Card card) {
        this.cardSet.add(card);
    }

    public int sumAll() {
        List<Card> list = cardSet.stream().sorted(Comparator.comparing(Card::getNumericValue, Comparator.reverseOrder())).collect(Collectors.toList());
        int total = 0;
        for (Card card : list) {
            total = card.getCardNumber().apply(total);
        }
        if (total > BLACKJACK_NUMBER) throw new IllegalStateException("블랙잭을 넘었습니다.");
        else return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cards cards = (Cards) o;
        return Objects.equals(cardSet, cards.cardSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardSet);
    }

    @Override
    public String toString() {
        return "Cards{" +
                "cardSet=" + cardSet +
                '}';
    }
}
