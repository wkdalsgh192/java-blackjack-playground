package blackjack.view;

import java.util.*;
import java.util.stream.Collectors;

public class Players {

    private List<Player> players;

    public Players() {
        this.players = new ArrayList<>();
    }

    public static Players initialize() {
        Players players = new Players();
        Player dealer = new Player("딜러");
        players.add(dealer);
        return players;
    }

    public static Players of(List<Player> playerList) {
        Players players = new Players();
        playerList.stream().forEach(players::add);
        return players;
    }

    public void add(Player player) {
        players.add(player);
    }

    public List<Player> getBetPlayers() {
        return players;
    }

    public List<Player> getAllPlayers() { return players; }

    public int size() {
        return players.size();
    }

    public Player getDealer() { return players.get(0); }

    public Players findLoser() {
        int losingNumber = getLosingNumber();
        return Players.of(players.stream().filter(player -> player.getResult() == losingNumber).collect(Collectors.toList()));
    }

    private int getLosingNumber() {
        OptionalInt opt = getAllPlayers().stream().mapToInt(Player::getResult).sorted().findFirst();
        if (opt.isPresent()) return opt.getAsInt();
        throw new IllegalStateException();
    }

    public Players findWinner() {
        int losingNumber = getLosingNumber();
        return Players.of(players.stream().filter(player -> player.getResult() != losingNumber).collect(Collectors.toList()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Players players1 = (Players) o;
        return Objects.equals(players, players1.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append(dealer.getName()).append("와 ");
        for (Player player : players) {
            sb.append(player.getName()).append(", ");
        }
        return sb.toString();
    }

    public void earn(Integer money) {
        players.forEach(player -> player.accept(money));
    }
}
