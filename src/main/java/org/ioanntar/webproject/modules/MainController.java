package org.ioanntar.webproject.modules;

import org.ioanntar.webproject.database.entities.Player;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.utils.PasswordHash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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

    @PostMapping("/registration")
    public String registration(@RequestParam String email, @RequestParam String name, @RequestParam short weight, @RequestParam String password, Model model) throws NoSuchAlgorithmException {
        String sha = new PasswordHash(password).hash("SHA-224");

        Database database = new Database();
        List<Player> players = database.getAll(Player.class);
        if(players.stream().anyMatch(p -> p.getEmail().equals(email))) {
            database.commit();
            model.addAttribute("emailError", "Аккаунт с такой почтой уже есть!");
            model.addAttribute("name", name);
            model.addAttribute("weight", weight);
            model.addAttribute("password", password);
            return "registration";
        }
        database.merge(new Player(name, email, sha, weight));
        database.commit();
        return null;
    }

    @PostMapping("/enter")
    public String enter(@RequestParam String email, @RequestParam String password, Model model) {
        String sha = new PasswordHash(password).hash("SHA-224");
        Database database = new Database();
        List<Player> players = database.getAll(Player.class);
        System.out.println(players);

        boolean checkEmail = players.stream().anyMatch(p -> p.getEmail().equals(email));
        boolean checkPass = players.stream().anyMatch(p -> p.getPassword().equals(sha));
        if(!checkEmail || !checkPass) {
            model.addAttribute("errorEnter", "Неверный логин или пароль!");
            return "index";
        }
        return null;
    }
}
