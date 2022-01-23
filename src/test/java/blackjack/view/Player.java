package blackjack.view;

import blackjack.model.Card;
import blackjack.model.Cards;

import java.util.List;
import java.util.Objects;

public class Player {

    private String name;
    private Integer money;
    private Cards cards = new Cards();

    private final int INVALID_DEALER_NUMBER = 16;
    private final int INVALID_PLAYER_NUMBER = 21;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, Integer money) {
        this.name = name;
        this.money = money;
    }

    public Player(String name, Integer money, List<Card> cardList) {
        this.name = name;
        this.money = money;
        this.cards = Cards.of(cardList.get(0), cardList.get(1));
    }

    public String getName() {
        return this.name;
    }

    public void bet(Integer money) {
        this.money = money;
    }

    public void receive(Card card) {
        this.cards.add(card);
    }

    public void receive(List<Card> cardList) {
        cardList.forEach(cards::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player other = (Player) o;
        return Objects.equals(name, other.name) && Objects.equals(money, other.money) && Objects.equals(cards, other.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, money);
    }

    @Override
    public String toString() {
        return name + ": " + cards;
    }

    public boolean AbleToGetMoreCard() {
        if (isDealer()) return cards.isValid(INVALID_DEALER_NUMBER);
        return cards.isValid(INVALID_PLAYER_NUMBER);
    }

    private boolean isDealer() {
        return name.equals("딜러");
    }

    public int getResult() { return cards.sumAll(); }
}
