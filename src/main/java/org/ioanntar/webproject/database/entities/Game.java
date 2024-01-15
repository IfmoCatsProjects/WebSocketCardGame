package org.ioanntar.webproject.database.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Data
@ToString(exclude = {"gameDecks"})
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "count")
    private int count;

    @Column(name = "current")
    private int current;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GameCard> gameDecks = new LinkedList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PlayerProps> playerProps = new LinkedList<>();

    public Game() {}

    public Game(int count) {
        this.count = count;
    }
}
