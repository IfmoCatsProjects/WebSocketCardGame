package org.ioanntar.webproject.modules;

import org.ioanntar.webproject.logic.Cards;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @MessageMapping("/start")
    @SendTo("/room/game")
    public Response generate() {
        Cards cards = Cards.getCards();
        cards.generateRandomDeck();
        Response response = new Response(cards.get("card35"));
        cards.remove("card35");
        return response;
    }

    @MessageMapping("/click")
    @SendTo("/room/game")
    public Response action(Request data) {
        Cards card = Cards.getCards();
        Response response = new Response(card.get(data.getData()));
        card.remove(data.getData());
        return response;
    }
}
