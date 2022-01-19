package blackjack.model;

import java.util.Objects;

public class Card {

    private CardNumber number;
    private CardType type;

    public Card(CardNumber number, CardType type) {
        this.number = number;
        this.type = type;
    }

    public int getNumericValue() {
        return number.getNumericValue();
    }

    public CardNumber getCardNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return number == card.number && type == card.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, type);
    }
}
