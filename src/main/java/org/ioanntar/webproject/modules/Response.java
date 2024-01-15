package org.ioanntar.webproject.modules;

import lombok.ToString;
import org.ioanntar.webproject.database.entities.Game;
import org.ioanntar.webproject.database.entities.Player;
import org.ioanntar.webproject.database.entities.PlayerProps;
import org.ioanntar.webproject.logic.GameConnector;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@ToString
public class Response {

    private final SimpMessagingTemplate template;
    private final Game game;

    public Response(Game game, SimpMessagingTemplate template) {
        this.game = game;
        this.template = template;
    }

    public void sendToPlayers(String destination, JSONObject response) {
        for (PlayerProps player: game.getPlayerProps())
            template.convertAndSendToUser(String.valueOf(player.getPlayerId()), "/game/" + destination, response.toString());
    }

    public void sendStart(String card) {
        JSONArray players = new GameConnector().bind(game).getJSONArray("players");
        JSONObject response = new JSONObject().put("common", card).put("current", game.getCurrent()).put("players", players);
        for (PlayerProps player: game.getPlayerProps()) {
            response.put("position", player.getPosition());
            template.convertAndSendToUser(String.valueOf(player.getPlayerId()), "/game/start", response.toString());
        }
    }
}
