package org.ioanntar.webproject.database.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "player_decks")
public class PlayerCard {

    @ManyToOne()
    @JoinColumn(name = "player_id")
    private Player player;

    @Id
    @Column(name = "card")
    private String card;

    @Column(name = "position")
    private int position;

    public PlayerCard() {
    }

    public PlayerCard(Player player, String card, int position) {
        this.player = player;
        this.card = card;
        this.position = position;
    }
}
