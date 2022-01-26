package blackjack.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class Players {

    private Player dealer;
    private List<Player> players;

    public Players() {
        this.dealer = new Player("딜러");
        this.players = new ArrayList<>();
    }

    public void add(Player player) {
        players.add(player);
    }

    public List<Player> getBetPlayers() {
        return players;
    }

    public List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(dealer);
        playerList.addAll(players);
        return playerList;
    }

    public int size() {
        return players.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dealer.getName()).append("와 ");
        for (Player player : players) {
            sb.append(player.getName()).append(", ");
        }
        return sb.toString();
    }

    public Player getDealer() { return dealer; }

    public List<Player> findLoser() {
        int losingNumber = getLosingNumber();
        return players.stream().filter(player -> player.getResult() == losingNumber).collect(Collectors.toList());
    }

    private int getLosingNumber() {
        OptionalInt opt = getAllPlayers().stream().mapToInt(Player::getResult).sorted().findFirst();
        if (opt.isPresent()) return opt.getAsInt();
        throw new IllegalStateException();
    }
}
