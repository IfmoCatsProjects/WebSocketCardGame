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
    private Integer position;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "weight")
    private int weight;

    @Column(name = "rating")
    private int rating;

    @ManyToOne()
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerCard> playersDeck = new LinkedList<>();

    public Player() {}

    public Player(String name, String email, String password, int weight) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.weight = weight;
    }
}
