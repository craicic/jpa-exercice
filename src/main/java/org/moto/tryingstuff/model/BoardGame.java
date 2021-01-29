package org.moto.tryingstuff.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "board_game")
@Data
public class BoardGame {

    @Id
    private long id;

    @Column(length = 50)
    private String name;

    @Column(name = "min_age")
    private short minAge;

    @Column(length = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_publisher")
    private Publisher publisher;

    @ManyToMany
    @JoinTable(
            name = "board_game_theme",
            joinColumns = {@JoinColumn(name = "fk_game")},
            inverseJoinColumns = {@JoinColumn(name = "fk_theme")})
    private Set<Theme> themes = new HashSet<>();
}
