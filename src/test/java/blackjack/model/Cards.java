package blackjack.model;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class Cards {

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
