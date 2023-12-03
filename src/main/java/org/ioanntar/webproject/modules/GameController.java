package org.ioanntar.webproject.modules;

import org.ioanntar.webproject.database.entities.*;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.logic.GenerateDeck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/connect")
    public void connect(SimpMessageHeaderAccessor sha, Request request) {
        Database database = new Database();
        Game game = database.get(Game.class, 22);

        Player player = database.get(Player.class, request.getNumber());
        player.setPrincipal(sha.getUser().getName());
        player.setGame(game);
        database.commit();
        sha.getSessionAttributes().put("playerId", player.getId());
        sha.getSessionAttributes().put("gameId", game.getId());

        template.convertAndSendToUser(sha.getUser().getName(), "/game", "ready");
    }

    @MessageMapping("/start")
    public void start(SimpMessageHeaderAccessor sha) {
        Database database = new Database();
        long id = (long) sha.getSessionAttributes().get("gameId");
        Game game = database.get(Game.class, id);

        List<GameCard> gameDeck = GenerateDeck.generate(game);
        GameCard card = gameDeck.get(35);
        gameDeck.remove(35);
        card.setType(DeckType.COMMON);
        card.setPosition(0);
        game.getGameDecks().addAll(gameDeck);
        game.getGameDecks().add(card);

        new Response(game).sendToPlayers(template, card.getCard());
        database.commit();
    }

    @MessageMapping("/click")
    public void click(Request data, SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        Database database = new Database();
        Game game = database.get(Game.class, id);
        GameCard card = game.getGameDecks().stream().filter(c -> c.getPosition() == data.getNumber()).findFirst().get();

        new Response(game).sendToPlayers(template, card.getCard());
        game.getGameDecks().remove(card);
        database.commit();
    }

    @MessageMapping("/put")
    public void put(Request data, SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("gameId");
        Database database = new Database();
        Game game = database.get(Game.class, id);

        if (data.getNumber() == 3) {
            int commonSize = game.getGameDecks().stream().filter(g -> g.getType() == DeckType.COMMON).toList().size();
            game.getGameDecks().add(new GameCard(game, DeckType.COMMON, data.getData(), commonSize));
        } else {
            Player player = game.getPlayers().stream().filter(p -> p.getPosition() == data.getNumber()).findFirst().get();
            player.getPlayersDeck().add(new PlayerCard(player, data.getData(), player.getPlayersDeck().size())); //TODO изменить на (currentPlayer + data.getNumber()) % size

            if (data.getNumber() == game.getCurrent()) { //TODO поменять правое выражение на 0, когда буду внедрять несколько игроков
                game.setCurrent((game.getCurrent() + 1) % (game.getCount()));
            }
        }
        new Response(game).sendToPlayers(template, "");
        database.commit();
    }

    @MessageMapping("/take")
    public void take(SimpMessageHeaderAccessor headerAccessor) {
        long id = (long) headerAccessor.getSessionAttributes().get("playerId");
        Database database = new Database();
        Player player = database.get(Player.class, id);

        List<PlayerCard> deck = player.getPlayersDeck();
        String card = deck.get(deck.size() - 1).getCard();
        deck.remove(deck.size() - 1);
        String subCard = deck.size() != 0 ? deck.get(deck.size() - 1).getCard() : "none";
        new Response(player.getGame()).sendToPlayers(template, card + " " + subCard);
        database.commit();
    }
}
