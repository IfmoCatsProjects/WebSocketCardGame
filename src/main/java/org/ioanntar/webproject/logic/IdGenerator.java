package org.ioanntar.webproject.logic;

import java.util.Set;
import java.util.SortedMap;

public class IdGenerator {
    private final Set<?> keys;

    public IdGenerator(Set<?> keys) {
        this.keys = keys;
    }

    public long generate() {
        long id = (long) ((Math.random() * (9*Math.pow(10, 7) - 1)) + Math.pow(10, 7));
        if (keys.contains(id)) {
            generate();
        }
        return id;
    }
}
