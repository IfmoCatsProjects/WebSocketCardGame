package org.ioanntar.webproject.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String start(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("id") != null)
            return "redirect:/templates/game.html";
        return "redirect:/templates/index.html";
    }

    @GetMapping("/game")
    public String game() {
        return "redirect:/templates/game.html";
    } //TODO впоследствии уберу, так как делаю SPA
}
