package org.ioanntar.webproject.database.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "principal")
    private String principal;

    @Column(name = "position")
    private int position;

    @ManyToOne()
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerCard> playersDeck = new LinkedList<>();

    public Player() {}

    public Player(String name) {
        this.name = name;
    }
}
