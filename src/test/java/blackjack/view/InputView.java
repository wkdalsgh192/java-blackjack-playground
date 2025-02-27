package blackjack.view;

import blackjack.model.Card;
import blackjack.model.Cards;

import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String START_MESSAGE = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)";
    private static final String BET_MESSAGE = "의 배팅 금액은?";
    private static final String DEAL_MESSAGE = "에게 2장의 카드를 나누었습니다.";
    private static final String PLAYER_EXTRA_DISTRIBUTION_MESSAGE = "는 한 장의 카드를 더 받겠습니까? (예는 y, 아니오는 n)";
    private static final String DEALER_EXTRA_DISTRIBUTION_MESSAGE = "딜러는 16이하라 한 장의 카드를 더 받았습니다.";

    public static void start() {
        System.out.println(START_MESSAGE);
        Players players = enroll(new Scanner(System.in).next());
        for (Player player : players.getBetPlayers()) {
            System.out.println(player.getName()+BET_MESSAGE);
            String input = new Scanner(System.in).nextLine();
            player.bet(Integer.valueOf(input));
        }
        System.out.println(players + DEAL_MESSAGE);
        // TODO 장민호 2장씩 카드를 나누는 로직을 분리하기
        int i = 0;
        List<Card> cardList = Cards.shuffle(() -> 2);
        for (Player player : players.getAllPlayers()) {
            player.receive(cardList.subList(i, i+2));
            System.out.println(player);
            i+=2;
        }
        // 플레이어가 추가로 카드를 받는 지를 확인하는 부분
        for (Player player : players.getBetPlayers()) {
            if (player.AbleToGetMoreCard()) {
                System.out.println(player.getName()+ PLAYER_EXTRA_DISTRIBUTION_MESSAGE);
                String input = new Scanner(System.in).nextLine();
                if ("y".equals(input)) player.receive(cardList.get(i++));
                System.out.println(player);
            }
        }
        Player dealer = players.getDealer();
        if (dealer.AbleToGetMoreCard()) {
            System.out.println(DEALER_EXTRA_DISTRIBUTION_MESSAGE);
            dealer.receive(cardList.get(i++));
        }
    }

    static Players enroll(String input) {
        Players players = Players.initialize();
        // TODO 20220118 장민호 특수문자 제거 코드 포함
        input = input.replace(" ", "");
        for (String name : input.split(",")) {
            players.add(new Player(name));
        }
        return players;
    }

}
