package org.ioanntar.webproject.logic;

import org.ioanntar.webproject.database.entities.*;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.modules.Response;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

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
        //-------------------------------------------------------------------
        Game prevGame = database.get(Game.class, id - 1);//TODO временная очистка всех игр
        try {
            for (Player player: prevGame.getPlayers()) {
                player.getPlayersDeck().clear();
            }
            database.delete(prevGame);
        } catch (NullPointerException ignored) {}
        //-------------------------------------------------------------------
        database.commit();
        database = new Database();
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
        GameCard card = game.getGameDecks().stream().filter(c -> c.getPosition() == cardPosition).findFirst().get();

        new Response(game, template).sendToPlayers("click", new JSONObject().put("card", card.getCard()).put("cardPos",
                "card" + cardPosition));
        game.getGameDecks().remove(card);
        database.commit();
    }

    public void put(int playerPut, String card) {
        long id = (long) sha.getSessionAttributes().get("gameId");
        Game game = database.get(Game.class, id);
        int put;

        if (playerPut == 3) {
            put = playerPut;
            int commonSize = game.getGameDecks().stream().filter(g -> g.getType() == DeckType.COMMON).toList().size();
            game.getGameDecks().add(new GameCard(game, DeckType.COMMON, card, commonSize));
        } else {
            put = (game.getCurrent() + playerPut) % game.getCount();
            Player player = game.getPlayers().stream().filter(p -> p.getPosition() == put).findFirst().get();
            player.getPlayersDeck().add(new PlayerCard(player, card, player.getPlayersDeck().size()));
        }
        new Response(game, template).sendToPlayers("put", new JSONObject().put("gamePos", put));

        if (playerPut == 0)
            game.setCurrent((game.getCurrent() + 1) % (game.getCount()));
        database.commit();
    }

    public void take() {
        long id = (long) sha.getSessionAttributes().get("playerId");
        Player player = database.get(Player.class, id);

        List<PlayerCard> deck = player.getPlayersDeck();
        String card = deck.get(deck.size() - 1).getCard();
        deck.remove(deck.size() - 1);
        String subCard = deck.size() != 0 ? deck.get(deck.size() - 1).getCard() : "none";

        database.commit();
        new Response(player.getGame(), template).sendToPlayers("take", new JSONObject().put("card", card).put("subCard", subCard));
    }
}
