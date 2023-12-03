package org.ioanntar.webproject.modules;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @GetMapping("/game")
    public String game() {
        return "game";
    }

    @GetMapping("/")
    public String start() {
        return "index";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "registration";
    }

    @GetMapping("/registration")
    public String registr(@RequestParam String email, @RequestParam String name, @RequestParam short weight, @RequestParam String password) {
        return null;
    }
}
