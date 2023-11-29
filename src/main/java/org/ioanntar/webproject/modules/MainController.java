package org.ioanntar.webproject.modules;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/game")
    public String game(Model model) {
        return "redirect:/templates/game.html";
    }

    @GetMapping("/")
    public String start(Model model) {
        return "redirect:/templates/index.html";
    }
}
