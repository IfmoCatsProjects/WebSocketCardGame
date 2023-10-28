package org.ioanntar.webproject.modules;

import org.ioanntar.webproject.logic.Cards;
import org.ioanntar.webproject.logic.IdGenerator;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class GameController {

    private SortedMap<Long, LinkedHashMap<String, LinkedList<String>>> games = new TreeMap<>();
    private SortedMap<Long, Integer> current = new TreeMap<>();

    @MessageMapping("/start")
    @SendTo("/room/game")
    public Response start(Request players, SimpMessageHeaderAccessor headerAccessor) {
        long id = new IdGenerator(current.keySet()).generate();
        current.put(id, (int) (Math.random() * players.getNumber()));
        LinkedHashMap<String, LinkedList<String>> decks = new LinkedHashMap<>();
        headerAccessor.getSessionAttributes().put("gameId", id);
        System.out.println(current.get(id));
        Cards cards = new Cards();
        decks.put("deck", cards.getDeck());
        for (int i = 0; i <= players.getNumber(); i++) {
            decks.put("" + i, new LinkedList<>());
        }
        decks.get("3").add(cards.get(35));
        games.put(id, decks);

        Response response = new Response(cards.get(35));
        cards.remove(35);
        return response;
    }

    @MessageMapping("/click")
    @SendTo("/room/game")
    public Response click(Request data, SimpMessageHeaderAccessor headerAccessor) {
        LinkedList<String> cards = games.get(headerAccessor.getSessionAttributes().get("gameId")).get("deck");
        Response response = new Response(cards.get(data.getNumber()));
        cards.set(data.getNumber(), null);
        return response;
    }

    @MessageMapping("/put")
    @SendTo("/room/game")
    public Response put(Request data, SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        int size = games.get(id).size() - 2;
        int currentPlayer = current.get(id);
        LinkedList<String> playerDeck = games.get(id).get(String.valueOf((data.getNumber() + currentPlayer) % size));
        playerDeck.add(data.getData());

        if (data.getNumber() == currentPlayer) {
            current.replace(id, (currentPlayer + 1) % size);
            System.out.println((currentPlayer + 1) % size + " " + size);
        }
        System.out.println(current.get(id) + "\n" + games.get(id));
        return new Response();
    }
}
