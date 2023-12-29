package org.ioanntar.webproject;

import org.ioanntar.webproject.database.entities.*;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.database.utils.HibernateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {
    public static void main(String[] args) {
        HibernateUtils.init();
        Database database = new Database();
        Game game = database.get(Game.class, 22);//TODO временная очистка всех игр
        try {
            game.getGameDecks().clear();
            for (Player player: game.getPlayers()) {
                player.getPlayersDeck().clear();
            }
        } catch (NullPointerException ignored) {}
        database.commit();
        SpringApplication.run(WebApplication.class, args);
    }
}
