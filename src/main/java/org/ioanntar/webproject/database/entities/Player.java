package org.ioanntar.webproject.database.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "weight")
    private int weight;

    @Column(name = "rating")
    private int rating;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL)
    private PlayerProps playerProps;

    public Player() {}

    public Player(String name, String email, String password, int weight) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.weight = weight;
    }
}
