package org.ioanntar.webproject.modules;

import org.ioanntar.webproject.logic.Cards;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate template;

    private static SortedMap<Long, LinkedHashMap<String, LinkedList<String>>> games = new TreeMap<>();
    private static SortedMap<Long, Integer> current = new TreeMap<>();
    private static long id = 1;

    @MessageMapping("/connect")
    public void connect(SimpMessageHeaderAccessor sha) {
        sha.getSessionAttributes().put("gameId", id);
        LinkedHashMap<String, LinkedList<String>> decks = new LinkedHashMap<>();
        //временный код
        decks.put(sha.getUser().getName(), new LinkedList<>());
        decks.put("b", new LinkedList<>());
        decks.put("c", new LinkedList<>());
        decks.put("common", new LinkedList<>());

        games.put(id++, decks);
        template.convertAndSendToUser(sha.getUser().getName(), "/game", "ready");
    }

    @MessageMapping("/start")
    public void start(SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        LinkedHashMap<String, LinkedList<String>> decks = games.get(id);
        current.put(id, (int) (Math.random() * (games.get(id).size() - 2)));

        Cards cards = new Cards();
        decks.put("deck", cards.getDeck());
        decks.get("common").add(cards.get(35));
        games.put(id, decks);

        Response response = new Response(games.get(id).keySet());
        response.sendToPlayers(template, cards.get(35));
        cards.remove(35);
    }

    @MessageMapping("/click")
    public void click(Request data, SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        LinkedList<String> cards = games.get(id).get("deck");
        Response response = new Response(games.get(id).keySet());
        response.sendToPlayers(template, cards.get(data.getNumber()));
        cards.set(data.getNumber(), null);
    }

    @MessageMapping("/put")
    public void put(Request data, SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        int size = games.get(id).size() - 1;
        int currentPlayer = current.get(id);
        String[] players = games.get(id).keySet().toArray(new String[size]);
        games.get(id).get(players[data.getNumber()]).add(data.getData()); //TODO изменить на (currentPlayer + data.getNumber()) % size

        if (data.getNumber() == currentPlayer) { //TODO поменять правое выражение на 0, когда буду интегрировать несколько игроков
            current.replace(id, (currentPlayer + 1) % (size - 1));
        }
        new Response(games.get(id).keySet()).sendToPlayers(template, "");
        System.out.println(games.get(id) + "\n");
    }

    @MessageMapping("/take")
    public void take(SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        LinkedList<String> deck = games.get(id).get(headerAccessor.getUser().getName());
        String card = deck.getLast();
        deck.removeLast();
        String subCard = deck.size() != 0 ? deck.getLast() : "none";
        new Response(games.get(id).keySet()).sendToPlayers(template, card + " " + subCard);
        System.out.println(games.get(id) + "\n");
    }

}
