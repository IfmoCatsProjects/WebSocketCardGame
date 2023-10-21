package org.ioanntar.webproject.modules;

import org.ioanntar.webproject.logic.Cards;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @MessageMapping("/action")
    @SendTo("/room/game")
    public Response greeting(Request data) {
        return new Response("Hello, " + data.getData());
    }

    @MessageMapping("/start")
    @SendTo("/room/game")
    public Response generate() {
        Cards cards = Cards.getCards();
        cards.generateRandomDeck();
        Response response = new Response(cards.getDeck().get("card0"));
        cards.remove("card0");
        return response;
    }
}
