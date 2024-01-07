package org.ioanntar.webproject.logic;

import jakarta.servlet.http.HttpSession;
import org.ioanntar.webproject.database.entities.Game;
import org.ioanntar.webproject.database.utils.Database;

public class GameManager {

    public void create(HttpSession session, int count) {
        Database database = new Database();
        Game game = database.merge(new Game(count));
        database.commit();
        database.close();
        session.setAttribute("gameId", game.getId());
    }

    public void bind() {

    }
}
