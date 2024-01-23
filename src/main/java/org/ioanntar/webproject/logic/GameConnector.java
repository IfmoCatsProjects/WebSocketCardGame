package org.ioanntar.webproject.logic;

import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.ioanntar.webproject.database.entities.Game;
import org.ioanntar.webproject.database.entities.Player;
import org.ioanntar.webproject.database.entities.PlayerProps;
import org.ioanntar.webproject.database.utils.Database;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class GameConnector {

    private final Database database = new Database();

    public void create(HttpSession session, int count) {
        database.openTransaction();

        Game game = database.merge(new Game(count));
        Player player = database.get(Player.class, (long) session.getAttribute("playerId"));
        database.merge(new PlayerProps(player.getId(), game, 0));

        session.setAttribute("gameId", game.getId());
        database.commit();
    }

    public Game exit(SimpMessageHeaderAccessor sha) {
        database.openTransaction();

        long playerId = (long) sha.getSessionAttributes().get("playerId");
        Game game = database.get(Game.class, (long) sha.getSessionAttributes().get("gameId"));
        Player player = database.get(Player.class, playerId);

        game.getPlayerProps().remove(player.getPlayerProps());
        database.delete(player.getPlayerProps());
        if (game.getPlayerProps().size() == 0) {
            database.delete(game);
        }

        database.commit();
        return game;
    }

    public JSONObject bind(Game game) {
        database.openTransaction();

        List<JSONObject> playersList = new LinkedList<>();
        List<PlayerProps> playerPropsList = game.getPlayerProps();
        playerPropsList.sort(Comparator.comparing(PlayerProps::getPosition));

        for (PlayerProps playerProps: playerPropsList) {
            JSONObject jsonPlayers = new JSONObject();
            jsonPlayers.put("name", playerProps.getPlayer().getName()).put("playerId", playerProps.getPlayerId());
            playersList.add(jsonPlayers);
        }
        for(int i = 0; i < game.getCount() - playerPropsList.size(); i++)
            playersList.add(null);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("players", new JSONArray(playersList)).put("gameId", game.getId());
        database.commit();
        return jsonObject;
    }

    public String join(HttpSession session, long gameId) {
        database.openTransaction();

        Player player = database.get(Player.class, (long) session.getAttribute("playerId"));
        Game game = database.get(Game.class, gameId);

        JSONObject jsonObject = new JSONObject();
        if (game == null) {
            jsonObject.put("status", "not found");
            return jsonObject.toString();
        } else if (game.getPlayerProps().size() == game.getCount()) {
            jsonObject.put("status", "full");
            return jsonObject.toString();
        }

        database.merge(new PlayerProps(player.getId(), game, game.getPlayerProps().size()));
        database.commit();
        session.setAttribute("gameId", gameId);
        jsonObject.put("status", "ok");
        return jsonObject.toString();
    }

    public Game connectToGame(String data, SimpMessageHeaderAccessor sha) {
        database.openTransaction();

        JSONObject jsonObject = new JSONObject(data);
        PlayerProps player = database.get(PlayerProps.class, jsonObject.getLong("playerId"));

        sha.getSessionAttributes().put("playerId", player.getPlayerId());
        sha.getSessionAttributes().put("gameId", jsonObject.getLong("gameId"));

        return player.getGame();
    }
}