package blackjack.view;

import blackjack.model.Card;
import blackjack.model.Cards;

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
            String input = new Scanner(System.in).nextLine();
            player.bet(Integer.valueOf(input));
        }
        System.out.println(players + DEAL_MESSAGE);
        // 2장씩 카드를 나누기
        int i = 0;
        List<Card> cardList = Cards.shuffle(() -> 2);
        for (Player player : players.getAllPlayers()) {
            player.receive(cardList.subList(i, i+2));
            System.out.println(player);
            i+=2;
        }

    }

    static Players enroll(String input) {
        Players players = new Players();
        // TODO 20220118 장민호 특수문자 제거 코드 포함
        input = input.replace(" ", "");
        for (String name : input.split(",")) {
            players.add(new Player(name));
        }
        return players;
    }

}
