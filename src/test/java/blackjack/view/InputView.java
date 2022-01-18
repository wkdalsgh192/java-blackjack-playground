package blackjack.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String START_MESSAGE = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)";
    private static final String BET_MESSAGE = "의 배팅 금액은?";
    private static final String DEAL_MESSAGE = "에게 2장의 카드를 나누었습니다.";

    public static void start() {
        System.out.println(START_MESSAGE);
        Players players = enroll(new Scanner(System.in).next());
        for (Player player : players.getBetPlayers()) {
            System.out.println(player.getName()+BET_MESSAGE);
            player.bet(Integer.valueOf(new Scanner(System.in).next()));
        }
        System.out.println(players + DEAL_MESSAGE);
        players.getAllPlayers().stream().forEach(System.out::println);
    }

    static Players enroll(String input) {
        Players players = new Players();
        // TODO 20220118 장민호 특수문자 제거 코드 포함
        for (String name : input.split(",")) {
            players.add(new Player(name));
        }
        return players;
    }

    public static class Players {

        private Player dealer;
        private List<Player> players;

        public Players() {
            this.dealer = new Player("딜러");
            this.players = new ArrayList<>();
        }

        public void add(Player player) {
            players.add(player);
        }

        public List<Player> getBetPlayers() { return players;}

        public List<Player> getAllPlayers() {
            List<Player> players = new ArrayList<>();
            players.add(dealer);
            players.addAll(players);
            return players;
        }

        public int size() {
            return players.size();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(dealer.getName()+"와 ");
            for (Player player : players) { sb.append(player.getName()+", "); }
            return sb.toString();
        }
    }
}
