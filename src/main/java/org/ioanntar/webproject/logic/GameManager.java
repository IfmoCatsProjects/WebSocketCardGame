package org.ioanntar.webproject.logic;

import org.ioanntar.webproject.database.entities.*;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.modules.Response;
import org.json.JSONArray;
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
    private final Database database = new Database();

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

        new Response(game, template).sendStart(card.getCard());
        database.commit();
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

        if (playerPut == 3) {
            put = playerPut;
            int commonSize = game.getGameDecks().stream().filter(g -> g.getType() == DeckType.COMMON).toList().size();
            game.getGameDecks().add(new GameCard(game, DeckType.COMMON, card, commonSize));
        } else {
            put = (game.getCurrent() + playerPut) % game.getCount();
            PlayerProps player = game.getPlayerProps().stream().filter(p -> p.getPosition() == put).findFirst().get();

            long openedPosition = player.getPlayersDeck().stream().filter(p -> p.getType() == DeckType.OPENED).count();
            player.getPlayersDeck().add(new PlayerCard(player, card, DeckType.OPENED, (int) openedPosition));
        }

        if (playerPut != 0 & isPlayerWins(game)) return;
        new Response(game, template).sendToPlayers("put", new JSONObject().put("gamePos", put));

        if (playerPut == 0)
            game.setCurrent((game.getCurrent() + 1) % (game.getCount()));
        database.commit();
    }

    public void take() {

        long id = (long) sha.getSessionAttributes().get("playerId");
        PlayerProps player = database.get(PlayerProps.class, id);

        List<PlayerCard> deck = new ArrayList<>(player.getPlayersDeck().stream().filter(c -> c.getType() == DeckType.OPENED).toList());
        deck.sort(Comparator.comparing(PlayerCard::getPosition));
        PlayerCard card = deck.get(deck.size() - 1);
        player.getPlayersDeck().remove(card);
        String subCard = deck.size() != 1 ? deck.get(deck.size() - 2).getCard() : "none";

        new Response(player.getGame(), template).sendToPlayers("take", new JSONObject().put("card", card.getCard()).put("subCard", subCard));
        database.commit();
    }

    public void turn() {

        long id = (long) sha.getSessionAttributes().get("playerId");
        PlayerProps player = database.get(PlayerProps.class, id);
        player.getPlayersDeck().forEach(p -> p.setType(DeckType.CLOSED));

        new Response(player.getGame(), template).sendToPlayers("turn", new JSONObject());
        database.commit();
    }

    public void clickOnPlayerDeck() {

        long id = (long) sha.getSessionAttributes().get("playerId");
        PlayerProps player = database.get(PlayerProps.class, id);

        List<PlayerCard> closeCards = new ArrayList<>(player.getPlayersDeck().stream().filter(p -> p.getType() == DeckType.CLOSED).toList());
        PlayerCard card = closeCards.stream().min(Comparator.comparing(PlayerCard::getPosition)).get();
        player.getPlayersDeck().remove(card);

        JSONObject jsonObject = new JSONObject().put("card", card.getCard()).put("last", closeCards.size() - 1 == 0);

        new Response(player.getGame(), template).sendToPlayers("clickOnPlayerDeck", jsonObject);
        database.commit();
    }

    private JSONArray changeRating(Game game) {

        List<PlayerProps> playerPropsList = game.getPlayerProps().stream()
                .sorted(Comparator.comparing(e -> e.getPlayersDeck().size())).toList();

        List<JSONObject> objects = new LinkedList<>();
        for (PlayerProps player: playerPropsList) {
            JSONObject jsonObject = new JSONObject().put("position", player.getPosition());

            if (playerPropsList.indexOf(player) == playerPropsList.size() - 1)
                player.getPlayer().setRating(player.getPlayer().getRating() > 10 ? player.getPlayer().getRating() - 10 : 0);
            else
                player.getPlayer().setRating(player.getPlayer().getRating() + 16 - 16 * playerPropsList.indexOf(player));

            jsonObject.put("currentRating", player.getPlayer().getRating());
            objects.add(jsonObject);
        }
        return new JSONArray(objects);
    }

    private boolean isPlayerWins(Game game) {
        long playerId = (long) sha.getSessionAttributes().get("playerId");
        PlayerProps player = game.getPlayerProps().stream().filter(p -> p.getPlayerId() == playerId).findFirst().get();
        if (player.getPlayersDeck().size() == 0 & game.getGameDecks().stream().noneMatch(c -> c.getType() == DeckType.DISTRIBUTION)) {
            JSONArray playerStat = changeRating(game);

            game.getGameDecks().clear();
            game.getPlayerProps().forEach(e -> e.getPlayersDeck().clear());
            game.getPlayerProps().forEach(e -> e.setReady(false));

            new Response(game, template).sendToFinish(playerStat);
            return true;
        }
        return false;
    }

    public void clickOnPig() {

        long id = (long) sha.getSessionAttributes().get("playerId");
        PlayerProps player = database.get(PlayerProps.class, id);
        new Response(player.getGame(), template).sendToPlayers("pigSound", new JSONObject().put("position", player.getPosition()));
        database.commit();
    }

    public void checkReadyPlayers() {

        long id = (long) sha.getSessionAttributes().get("playerId");
        PlayerProps player = database.get(PlayerProps.class, id);
        player.setReady(true);

        if (player.getGame().getPlayerProps().stream().allMatch(PlayerProps::isReady))
            new Response(player.getGame(), template).sendToPlayers("punishEnough", new JSONObject());
        database.commit();
    }

    public void reload() {

        long id = (long) sha.getSessionAttributes().get("gameId");
        Game game = database.get(Game.class, id);

        if (game.getPlayerProps().stream().allMatch(PlayerProps::isReady))
            new Response(game, template).sendToPlayers("reload", new JSONObject());
        database.commit();
    }
}