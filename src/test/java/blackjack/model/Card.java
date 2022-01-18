package blackjack.model;

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
}
