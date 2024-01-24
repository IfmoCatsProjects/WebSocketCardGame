package org.ioanntar.webproject;

import jakarta.transaction.Transactional;
import org.ioanntar.webproject.controllers.MainController;
import org.ioanntar.webproject.database.entities.Game;
import org.ioanntar.webproject.database.entities.Player;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.database.utils.HibernateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        HibernateUtils.init();
        Database database = new Database();
        List<Game> games = database.getAll(Game.class);
        for (Game game : games) {
            game.getGameDecks().clear();
            game.getPlayerProps().clear();
            database.delete(game);
        }
        database.commit();

        SpringApplication.run(WebApplication.class, args);
    }
}
