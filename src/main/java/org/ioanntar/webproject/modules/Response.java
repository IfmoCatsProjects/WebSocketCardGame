package org.ioanntar.webproject.modules;

import lombok.ToString;
import org.ioanntar.webproject.database.entities.Game;
import org.ioanntar.webproject.database.entities.Player;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.*;
import java.util.stream.Collectors;

@ToString
public class Response {

    private Set<Long> players;

    public Response() {}

    public Response(Game game) {
        players = game.getPlayers().stream().map(Player::getId).collect(Collectors.toSet());
    }

    public void sendToPlayers(SimpMessagingTemplate template, String destination, String response) {
        for (long player : players)
            template.convertAndSendToUser(String.valueOf(player), "/game/" + destination, response);

    }

    public void sendToPlayers(SimpMessagingTemplate template, String destination, List<String> responses) {
        Iterator<String> iterator = responses.iterator();
        for (long player : players)
            template.convertAndSendToUser(String.valueOf(player), "/game/" + destination, iterator.next());
    }

    //TODO Внедрить этот метод после реализации поддержки нескольких игроков
    public List<String> generateResponses(int current, String resp, String anotherResp) {
        List<String> list = new LinkedList<>();
        for (int i = 0; i < players.size() - 2; i++) {
            if (i == current) {
                list.add(resp);
            } else {
                list.add(anotherResp);
            }
        }
        return list;
    }
}
