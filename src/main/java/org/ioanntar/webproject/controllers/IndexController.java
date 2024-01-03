package org.ioanntar.webproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String start() {
        return "redirect:/templates/index.html";
    }

    @GetMapping("/game")
    public String game() {
        return "redirect:/templates/game.html";
    } //TODO впоследствии уберу, так как делаю SPA
}
