package org.ioanntar.webproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.ioanntar.webproject.logic.GameManager;
import org.ioanntar.webproject.modules.HttpRequest;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @PostMapping("/registration")
    public String registration(@RequestParam String data, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        HttpRequest request = new HttpRequest(new JSONObject(data));
        return request.regClient(session);
    }

    @PostMapping("/enter")
    public String enter(@RequestParam String data, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        HttpRequest request = new HttpRequest(new JSONObject(data));
        return request.clientEnter(session);
    }

    @GetMapping("/get_client")
    public String getClient(HttpServletRequest request) {
        HttpSession session = request.getSession();

        return new HttpRequest().getClientData((long) session.getAttribute("playerId")).toString();
    }

    @GetMapping("/exit/{attribute}")
    public void exit(HttpServletRequest request, @PathVariable("attribute") String attribute) {
        HttpSession session = request.getSession();
        session.removeAttribute(attribute);
    }

    @PostMapping("/createGame")
    public String createGame(@RequestParam String data, HttpServletRequest request) {
        JSONObject object = new JSONObject(data);
        int count = object.getInt("count");
        new GameManager().create(request.getSession(), count);
        return null;
    }
}
