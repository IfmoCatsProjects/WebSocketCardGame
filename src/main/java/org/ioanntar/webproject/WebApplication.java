package org.ioanntar.webproject;

import jakarta.transaction.Transactional;
import org.ioanntar.webproject.database.entities.Game;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.database.utils.HibernateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        HibernateUtils.init();
        Database database = new Database();

        List<Game> games = database.getAll(Game.class);
        for (Game game : games) {
            game.getGameDecks().forEach(database::delete);
            database.delete(game);
        }
        database.commit();

        SpringApplication.run(WebApplication.class, args);
    }
}
