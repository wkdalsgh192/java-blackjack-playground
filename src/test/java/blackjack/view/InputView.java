package blackjack.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private static final String START_MESSAGE = "게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)";

    public static void start() {
        System.out.println(START_MESSAGE);
        List<Player> players = enroll(new Scanner(System.in).next());
    }

    static List<Player> enroll(String input) {

        List<Player> playerList = new ArrayList();
        for (String name : input.split(",")) {
            playerList.add(new Player(name));
        }
        return playerList;
    }
}
