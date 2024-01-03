package org.ioanntar.webproject.modules;

import jakarta.servlet.http.HttpSession;
import org.ioanntar.webproject.database.entities.Player;
import org.ioanntar.webproject.database.utils.Database;
import org.ioanntar.webproject.utils.PasswordHash;
import org.json.JSONObject;

import java.util.List;

public class HttpRequest {
    private final JSONObject object;
    private Database database = new Database();

    public HttpRequest(JSONObject object) {
        this.object = object;
    }

    private JSONObject getClientData(Player player) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "ok");
        jsonObject.put("name", player.getName());
        jsonObject.put("rating", player.getRating());
        jsonObject.put("weight", player.getWeight());

        return jsonObject;
    }

    public String clientEnter(HttpSession session) {
        String sha = new PasswordHash(object.getString("password")).hash("SHA-224");
        List<Player> players = database.getAll(Player.class);

        String email = object.getString("email");
        Player player = players.stream().filter(p -> p.getEmail().equals(email)).findFirst().orElse(null);
        boolean pass = player != null & players.stream().anyMatch(p -> p.getPassword().equals(sha));

        if(player == null || !pass) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "not found");
            jsonObject.put("text", "Неверный логин или пароль!");
            return jsonObject.toString();
        }

        session.setAttribute("id", player.getId());
        return getClientData(player).toString();
    }

    public String regClient(HttpSession session) {
        String sha = new PasswordHash(object.getString("password")).hash("SHA-224");
        String email = object.getString("email");

        List<Player> players = database.getAll(Player.class);
        if(players.stream().anyMatch(p -> p.getEmail().equals(email))) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "account exists");
            jsonObject.put("text", "Аккаунт с такой почтой уже есть!");
            return jsonObject.toString();
        }
        Player player = new Player(object.getString("name"), email, sha, (int) object.getNumber("weight"));
        player = database.merge(player);
        database.commit();

        session.setAttribute("id", player.getId());
        return getClientData(player).toString();
    }
}
