package org.ioanntar.webproject.logic;

import jakarta.servlet.http.HttpSession;
import lombok.NoArgsConstructor;
import org.ioanntar.webproject.database.entities.Game;
import org.ioanntar.webproject.database.entities.Player;
import org.ioanntar.webproject.database.utils.Database;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import java.util.LinkedList;
import java.util.List;

@NoArgsConstructor
public class GameConnector {

    private final Database database = new Database();

    public void create(HttpSession session, int count) {
        Game game = database.merge(new Game(count));
        Player player = database.get(Player.class, (long) session.getAttribute("playerId"));
        player.setGame(game);

        database.commit();

        session.setAttribute("gameId", game.getId());
    }

    public Game exit(SimpMessageHeaderAccessor sha) {
        long playerId = (long) sha.getSessionAttributes().get("playerId");
        Game game = database.get(Game.class, (long) sha.getSessionAttributes().get("gameId"));
        Player player = database.get(Player.class, playerId);
        if(game.getPlayers().size() == 1)
            database.delete(game);

        player.setGame(null);
        database.commit();
        game.getPlayers().removeIf(p -> p.getId() == playerId);
        return game;
    }

    public JSONObject bind(Game game) {
        List<JSONObject> playersList = new LinkedList<>();
        List<Player> players = game.getPlayers();
        for (Player player: players) {
            JSONObject jsonPlayers = new JSONObject();
            jsonPlayers.put("name", player.getName());
            jsonPlayers.put("playerId", player.getId());
            jsonPlayers.put("rating", player.getRating());
            jsonPlayers.put("weight", player.getWeight());

            playersList.add(jsonPlayers);
        }

        for(int i = 0; i < game.getCount() - players.size(); i++)
            playersList.add(null);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("players", new JSONArray(playersList));
        return jsonObject;
    }

    public String join(HttpSession session, long gameId) {
        Player player = database.get(Player.class, (long) session.getAttribute("playerId"));
        Game game = database.get(Game.class, gameId);

        JSONObject jsonObject = new JSONObject();
        if (game == null) {
            jsonObject.put("status", "not found");
            return jsonObject.toString();
        } else if (game.getPlayers().size() == game.getCount()) {
            jsonObject.put("status", "full");
            return jsonObject.toString();
        }

        player.setGame(game);
        database.commit();
        session.setAttribute("gameId", gameId);
        jsonObject.put("status", "ok");
        return jsonObject.toString();
    }

    public Game connectToGame(String data, SimpMessageHeaderAccessor sha) {
        JSONObject jsonObject = new JSONObject(data);
        Player player = database.get(Player.class, jsonObject.getLong("playerId"));
        Game game = player.getGame();
        player.setPosition(game.getPlayers().size() - 1);
        database.commit();

        sha.getSessionAttributes().put("playerId", player.getId());
        sha.getSessionAttributes().put("gameId", jsonObject.getLong("gameId"));

        return game;
    }
}
