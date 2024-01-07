package org.ioanntar.webproject.modules;

import lombok.ToString;
import org.json.JSONObject;

@ToString
public class Request {
    private final JSONObject jsonObject;

    public Request(String json) {
        jsonObject = new JSONObject(json);
    }

    public String getString(String key) {
        return jsonObject.getString(key);
    }
}
