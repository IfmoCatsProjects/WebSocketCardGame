package org.ioanntar.webproject.modules;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.*;

public class Response {
    private Set<String> players;

    public Response() {
    }

    public Response(Set<String> players) {
        this.players = players;
    }

    public void sendToPlayers(SimpMessagingTemplate template, String response) {
        for (String player : players) {
            if (player.equals("common")) {
                break;
            }
            template.convertAndSendToUser(player, "/game", response);
        }
    }

    public void sendToPlayers(SimpMessagingTemplate template, List<String> responses) {
        Iterator<String> iterator = responses.iterator();
        for (String player : players) {
            if (player.equals("common")) {
                break;
            }
            template.convertAndSendToUser(player, "/game", iterator.next());
        }
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

    @Override
    public String toString() {
        return "Response{" +
                "players=" + players +
                '}';
    }
}
