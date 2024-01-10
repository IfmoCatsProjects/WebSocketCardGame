package org.ioanntar.webproject.modules;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.ioanntar.webproject.database.entities.Player;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.utils.PasswordHash;
import org.json.JSONObject;

import java.util.List;

@NoArgsConstructor
public class HttpRequest {
    private JSONObject object;
    private final Database database = new Database();

    public HttpRequest(JSONObject object) {
        this.object = object;
    }

    public JSONObject getClientData(long id) {
        Player player = database.get(Player.class, id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("playerId", player.getId());
        jsonObject.put("gameId", player.getGame() != null ? player.getGame().getId() : null);
        jsonObject.put("email", player.getEmail());
        jsonObject.put("name", player.getName());
        jsonObject.put("rating", player.getRating());
        jsonObject.put("weight", player.getWeight());

        database.commit();
        return jsonObject;
    }

    public String clientEnter(HttpSession session) {
        String sha = new PasswordHash(object.getString("password")).hash("SHA-224");
        List<Player> players = database.getAll(Player.class);

        String email = object.getString("email");
        Player player = players.stream().filter(p -> p.getEmail().equals(email)).findFirst().orElse(null);
        boolean pass = player != null & players.stream().anyMatch(p -> p.getPassword().equals(sha));

        JSONObject jsonObject = new JSONObject();
        if(player == null || !pass) {
            jsonObject.put("status", "not found");
            jsonObject.put("text", "Неверный логин или пароль!");
            return jsonObject.toString();
        }

        session.setAttribute("playerId", player.getId());
        jsonObject.put("status", "ok");
        database.commit();
        return jsonObject.toString();
    }

    public String regClient(HttpSession session) {
        String sha = new PasswordHash(object.getString("password")).hash("SHA-224");
        String email = object.getString("email");

        List<Player> players = database.getAll(Player.class);
        JSONObject jsonObject = new JSONObject();
        if(players.stream().anyMatch(p -> p.getEmail().equals(email))) {
            jsonObject.put("status", "account exists");
            jsonObject.put("text", "Аккаунт с такой почтой уже есть!");
            return jsonObject.toString();
        }
        Player player = new Player(object.getString("name"), email, sha, (int) object.getNumber("weight"));
        player = database.merge(player);
        database.commit();

        session.setAttribute("playerId", player.getId());
        jsonObject.put("status", "ok");
        return jsonObject.toString();
    }
}
