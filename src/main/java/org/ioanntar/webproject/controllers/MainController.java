package org.ioanntar.webproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
}
