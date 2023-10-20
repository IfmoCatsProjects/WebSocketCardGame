package org.ioanntar.webproject.modules;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    @MessageMapping("/game")
    @SendTo("/room/game")
    public Greeting greeting(HelloMessage message) {
        System.out.println(message);
        return new Greeting("Hello, " + message.getName());
    }

}
