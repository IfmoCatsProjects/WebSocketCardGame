package org.ioanntar.webproject.modules;

import lombok.ToString;
import org.ioanntar.webproject.database.entities.Game;
import org.ioanntar.webproject.database.entities.Player;
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
        for (Player player: game.getPlayers())
            template.convertAndSendToUser(String.valueOf(player.getId()), "/game/" + destination, response.toString());
    }

    public void sendStart(String card) {
        JSONObject response = new JSONObject().put("common", card).put("current", game.getCurrent()).put("count", game.getCount());
        for (Player player: game.getPlayers()) {
            response.put("position", player.getPosition());
            template.convertAndSendToUser(String.valueOf(player.getId()), "/game/start", response.toString());
        }
    }
}
