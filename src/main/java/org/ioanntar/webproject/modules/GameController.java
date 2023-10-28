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

    @MessageMapping("/connect")
    @SendTo("/room/game")
    public Response connect(SimpMessageHeaderAccessor headerAccessor) {
        long id = new IdGenerator(current.keySet()).generate();
        headerAccessor.getSessionAttributes().put("gameId", id);
        LinkedHashMap<String, LinkedList<String>> decks = new LinkedHashMap<>();
        //временный код
        decks.put(headerAccessor.getSessionId(), new LinkedList<>());
        decks.put("b", new LinkedList<>());
        decks.put("c", new LinkedList<>());
        decks.put("common", new LinkedList<>());

        games.put(id, decks);
//        games.computeIfAbsent(id, k -> new LinkedHashMap<>());
//        games.get(id).put(headerAccessor.getSessionId(), new LinkedList<>());
        return new Response();
    }

    @MessageMapping("/start")
    @SendTo("/room/game")
    public Response start(SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        LinkedHashMap<String, LinkedList<String>> decks = games.get(id);
        current.put(id, (int) (Math.random() * (games.get(id).size() - 2)));
        System.out.println(current.get(id));

        Cards cards = new Cards();
        decks.put("deck", cards.getDeck());
        decks.get("common").add(cards.get(35));
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
        int size = games.get(id).size() - 1;
        int currentPlayer = current.get(id);
        String[] players = games.get(id).keySet().toArray(new String[size]);
        games.get(id).get(players[data.getNumber()]).add(data.getData()); //TODO изменить на (currentPlayer + data.getNumber()) % size

        if (data.getNumber() == currentPlayer) { //TODO поменять правое выражение на 0, когда буду интегрировать несколько игроков
            current.replace(id, (currentPlayer + 1) % (size - 1));
        }
        System.out.println(current.get(id) + "\n" + games.get(id));
        return new Response();
    }
}
