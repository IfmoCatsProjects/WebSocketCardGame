package org.ioanntar.webproject.controllers;

import org.ioanntar.webproject.database.entities.*;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.logic.GenerateDeck;
import org.ioanntar.webproject.modules.Request;
import org.ioanntar.webproject.modules.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate template;
    private final Database database = new Database();

    @MessageMapping("/hello")
    public void send(@Payload String data, SimpMessageHeaderAccessor sha) {
        JSONObject jsonObject = new JSONObject(data);
        String message = "Hello from " + sha.getUser().getName();
        template.convertAndSendToUser(jsonObject.getString("id"), "/game/test", message);
    }

    @MessageMapping("/connect")
    public void connect(@Payload String data, SimpMessageHeaderAccessor sha) {
        Game game = database.get(Game.class, 22);//TODO временная очистка всех игр
        try {
            game.getGameDecks().clear();
            for (Player player: game.getPlayers()) {
                player.getPlayersDeck().clear();
            }
        } catch (NullPointerException ignored) {}
        database.commit();
        //-------------------------------------------------------------------
        JSONObject jsonObject = new JSONObject(data);
        Game game1 = database.get(Game.class, 22);

        Player player = database.get(Player.class, jsonObject.getLong("id"));
        player.setGame(game1);
        database.commit();
        sha.getSessionAttributes().put("playerId", player.getId());
        sha.getSessionAttributes().put("gameId", game.getId());

        template.convertAndSendToUser(sha.getUser().getName(), "/game/connect", "");
    }

    @MessageMapping("/start")
    public void start(SimpMessageHeaderAccessor sha) {
        long id = (long) sha.getSessionAttributes().get("gameId");
        Game game = database.get(Game.class, id);

        List<GameCard> gameDeck = GenerateDeck.generate(game);
        GameCard card = gameDeck.get(35);
        gameDeck.remove(35);
        card.setType(DeckType.COMMON);
        card.setPosition(0);
        game.getGameDecks().addAll(gameDeck);
        game.getGameDecks().add(card);

        new Response(game).sendToPlayers(template, "start", card.getCard());
        database.commit();
    }

    @MessageMapping("/click")
    public void click(@Payload String data, SimpMessageHeaderAccessor headerAccessor) {
        JSONObject jsonObject = new JSONObject(data);
        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        Game game = database.get(Game.class, id);
        GameCard card = game.getGameDecks().stream().filter(c -> c.getPosition() == jsonObject.getInt("card")).findFirst().get();

        new Response(game).sendToPlayers(template, "click", card.getCard());
        game.getGameDecks().remove(card);
        database.commit();
    }

    @MessageMapping("/put")
    public void put(@Payload String data, SimpMessageHeaderAccessor headerAccessor) {
        JSONObject jsonObject = new JSONObject(data);
        int playerPut = jsonObject.getInt("player");
        String card = jsonObject.getString("card");

        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        Game game = database.get(Game.class, id);

        if (playerPut == 3) {
            int commonSize = game.getGameDecks().stream().filter(g -> g.getType() == DeckType.COMMON).toList().size();
            game.getGameDecks().add(new GameCard(game, DeckType.COMMON, card, commonSize));
        } else {
            Player player = game.getPlayers().stream().filter(p -> p.getPosition() == playerPut).findFirst().get();
            player.getPlayersDeck().add(new PlayerCard(player, card, player.getPlayersDeck().size())); //TODO изменить на (currentPlayer + data.getNumber()) % size

            if (playerPut == game.getCurrent()) { //TODO поменять правое выражение на 0, когда буду внедрять несколько игроков
                game.setCurrent((game.getCurrent() + 1) % (game.getCount()));
            }
        }
        new Response(game).sendToPlayers(template, "put", String.valueOf(playerPut));
        database.commit();
    }

    @MessageMapping("/take")
    public void take(SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("playerId");
        Player player = database.get(Player.class, id);

        List<PlayerCard> deck = player.getPlayersDeck();
        String card = deck.get(deck.size() - 1).getCard();
        deck.remove(deck.size() - 1);
        String subCard = deck.size() != 0 ? deck.get(deck.size() - 1).getCard() : "none";

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("card", card);
        jsonObject.put("subCard", subCard);
        new Response(player.getGame()).sendToPlayers(template, "take", jsonObject.toString());

        database.commit();
    }

    @MessageMapping("/close")
    public void closeDataBase() {
        database.close();
    }
}
