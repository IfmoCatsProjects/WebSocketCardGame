package org.ioanntar.webproject.controllers;

import org.ioanntar.webproject.modules.HttpRequest;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @PostMapping("/registration")
    public String registration(@RequestParam String data) {
        HttpRequest request = new HttpRequest(new JSONObject(data));
        return request.regClient();
    }

    @PostMapping("/enter")
    public String enter(@RequestParam String data) {
        HttpRequest request = new HttpRequest(new JSONObject(data));
        return request.clientEnter();
    }
}
