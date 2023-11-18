package org.ioanntar.webproject.database.entities;

import lombok.Getter;

@Getter
public enum DeckType {
    DISTRIBUTION("DISTRIBUTION"), COMMON("COMMON");

    private final String type;

    DeckType(String type) {
        this.type = type;
    }


}
