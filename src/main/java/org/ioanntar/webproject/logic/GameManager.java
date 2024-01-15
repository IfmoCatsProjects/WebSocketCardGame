package org.ioanntar.webproject.logic;

import org.ioanntar.webproject.database.entities.*;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.modules.Response;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class GameManager {
    private final SimpMessageHeaderAccessor sha;
    private final SimpMessagingTemplate template;
    private Database database = new Database();

    public GameManager(SimpMessageHeaderAccessor sha, SimpMessagingTemplate template) {
        this.sha = sha;
        this.template = template;
    }

    public void start() {
        long id = (long) sha.getSessionAttributes().get("gameId");
        Game game = database.get(Game.class, id);

        List<GameCard> gameDeck = GenerateDeck.generate(game);
        GameCard card = gameDeck.get(35);
        gameDeck.remove(35);
        card.setType(DeckType.COMMON);
        card.setPosition(0);
        game.getGameDecks().addAll(gameDeck);
        game.getGameDecks().add(card);

        database.commit();
        new Response(game, template).sendStart(card.getCard());
    }

    public void click(int cardPosition) {
        long id = (long) sha.getSessionAttributes().get("gameId");
        Game game = database.get(Game.class, id);
        GameCard card = game.getGameDecks().stream().filter(c -> c.getType() == DeckType.DISTRIBUTION && c.getPosition() == cardPosition)
                .findFirst().get();

        new Response(game, template).sendToPlayers("click", new JSONObject().put("card", card.getCard()).put("cardPos",
                "card" + cardPosition));
        game.getGameDecks().remove(card);
        database.commit();
    }

    public void put(int playerPut, String card) {
        long id = (long) sha.getSessionAttributes().get("gameId");
        Game game = database.get(Game.class, id);
        int put;
        long a;

        if (playerPut == 3) {
            put = playerPut;
            int commonSize = game.getGameDecks().stream().filter(g -> g.getType() == DeckType.COMMON).toList().size();
            a = commonSize;
            game.getGameDecks().add(new GameCard(game, DeckType.COMMON, card, commonSize));
        } else {
            put = (game.getCurrent() + playerPut) % game.getCount();
            PlayerProps player = game.getPlayerProps().stream().filter(p -> p.getPosition() == put).findFirst().get();

            long openedPosition = player.getPlayersDeck().stream().filter(p -> p.getType() == DeckType.OPENED).count();
            a = openedPosition;
            player.getPlayersDeck().add(new PlayerCard(player, card, DeckType.OPENED, (int) openedPosition));
        }
        new Response(game, template).sendToPlayers("put", new JSONObject().put("gamePos", put));

        if (playerPut == 0)
            game.setCurrent((game.getCurrent() + 1) % (game.getCount()));

        try {
            database.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void take() {
        long id = (long) sha.getSessionAttributes().get("playerId");
        PlayerProps player = database.get(PlayerProps.class, id);

        List<PlayerCard> deck = new ArrayList<>(player.getPlayersDeck().stream().filter(c -> c.getType() == DeckType.OPENED).toList());
        deck.sort(Comparator.comparing(PlayerCard::getPosition));
        PlayerCard card = deck.get(deck.size() - 1);
        player.getPlayersDeck().remove(card);
        String subCard = deck.size() != 1 ? deck.get(deck.size() - 2).getCard() : "none";

        database.commit();
        new Response(player.getGame(), template).sendToPlayers("take", new JSONObject().put("card", card.getCard()).put("subCard", subCard));
    }

    public void turn() {
        long id = (long) sha.getSessionAttributes().get("playerId");
        PlayerProps player = database.get(PlayerProps.class, id);
        player.getPlayersDeck().forEach(p -> p.setType(DeckType.CLOSED));
        database.commit();

        new Response(player.getGame(), template).sendToPlayers("turn", new JSONObject());
    }

    public void clickOnPlayerDeck() {
        long id = (long) sha.getSessionAttributes().get("playerId");
        PlayerProps player = database.get(PlayerProps.class, id);

        List<PlayerCard> closeCards = new ArrayList<>(player.getPlayersDeck().stream().filter(p -> p.getType() == DeckType.CLOSED).toList());
        PlayerCard card = closeCards.stream().min(Comparator.comparing(PlayerCard::getPosition)).get();
        player.getPlayersDeck().remove(card);

        JSONObject jsonObject = new JSONObject().put("card", card.getCard()).put("last", closeCards.size() - 1 == 0);
        database.commit();

        new Response(player.getGame(), template).sendToPlayers("clickOnPlayerDeck", jsonObject);
    }
}
