package org.ioanntar.webproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.ioanntar.webproject.logic.GameConnector;
import org.ioanntar.webproject.logic.GameManager;
import org.ioanntar.webproject.modules.GetDataRequest;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @PostMapping("/registration")
    public String registration(@RequestParam String data, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        GetDataRequest request = new GetDataRequest(new JSONObject(data));
        String response = request.regClient(session).toString();

        return response;
    }

    @PostMapping("/enter")
    public String enter(@RequestParam String data, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        GetDataRequest request = new GetDataRequest(new JSONObject(data));
        JSONObject response = request.clientEnter(session);

        return response.toString();
    }

    @GetMapping("/get_client")
    public String getClient(HttpServletRequest request) {
        HttpSession session = request.getSession();
        GetDataRequest getDataRequest = new GetDataRequest();
        JSONObject response = getDataRequest.getClientData((long) session.getAttribute("playerId"));

        return response.toString();
    }

    @GetMapping("/exit_home")
    public void exitHome(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("playerId");
    }

    @PostMapping("/createGame")
    public void createGame(@RequestParam String data, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject(data);
        GameConnector gameConnector = new GameConnector();
        gameConnector.create(request.getSession(), jsonObject.getInt("count"));
    }

    @GetMapping("/exit_game")
    public void exitGame(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("gameId");
    }

    @PostMapping("/joinGame")
    public String joinGame(@RequestParam String data, HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject(data);
        GameConnector gameConnector = new GameConnector();
        return gameConnector.join(request.getSession(), jsonObject.getLong("gameId"));
    }
}
