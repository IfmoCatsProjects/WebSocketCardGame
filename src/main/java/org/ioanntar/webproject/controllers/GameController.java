package org.ioanntar.webproject.controllers;

import org.ioanntar.webproject.database.entities.*;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.logic.GameConnector;
import org.ioanntar.webproject.logic.GameManager;
import org.ioanntar.webproject.modules.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @Autowired
    private SimpMessagingTemplate template;

    @MessageMapping("/connect")
    public void bind(@Payload String data, SimpMessageHeaderAccessor sha) {
        GameConnector gameConnector = new GameConnector();
        Game game = gameConnector.connectToGame(data, sha);
        JSONObject players = gameConnector.bind(game);
        new Response(game, template).sendToPlayers("connectedPlayers", players);
    }

    @MessageMapping("/disconnect")
    public void disconnect(SimpMessageHeaderAccessor sha) {
        GameConnector gameConnector = new GameConnector();
        Game game = gameConnector.exit(sha);
        JSONObject players = gameConnector.bind(game);
        new Response(game, template).sendToPlayers("connectedPlayers", players);
    }

    @MessageMapping("/start")
    public void start(SimpMessageHeaderAccessor sha) {
        new GameManager(sha, template).start();
    }

    @MessageMapping("/click")
    public void click(@Payload String data, SimpMessageHeaderAccessor sha) {
        JSONObject jsonObject = new JSONObject(data);
        new GameManager(sha, template).click(jsonObject.getInt("cardPos"));
    }

    @MessageMapping("/put")
    public void put(@Payload String data, SimpMessageHeaderAccessor sha) {
        JSONObject jsonObject = new JSONObject(data);
        new GameManager(sha, template).put(jsonObject.getInt("player"), jsonObject.getString("card"));
    }

    @MessageMapping("/take")
    public void take(SimpMessageHeaderAccessor sha) {
        new GameManager(sha, template).take();
    }

    @MessageMapping("/turn")
    public void turn(SimpMessageHeaderAccessor sha) {
        new GameManager(sha, template).turn();
    }
}
