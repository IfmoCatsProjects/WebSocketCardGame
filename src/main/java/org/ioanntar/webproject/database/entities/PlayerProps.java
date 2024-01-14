package org.ioanntar.webproject.database.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "player_props")
@Data
@NoArgsConstructor
@ToString(exclude = {"player", "game"})
public class PlayerProps {

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "position")
    private int position;

    @Column(name = "deck_pointer")
    private int deckPointer;

    @OneToMany(mappedBy = "playerProps", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<PlayerCard> playersDeck = new LinkedList<>();

    public PlayerProps(Player player, Game game) {
        this.player = player;
        this.game = game;
    }
}
