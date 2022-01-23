package blackjack.view;

import java.util.ArrayList;
import java.util.List;

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
}
