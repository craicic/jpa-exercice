package org.moto.tryingstuff.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Publisher {
    @Id
    private long id;

    @Column(length = 50)
    private String name;

    private String website;

    @ToString.Exclude
    @OneToMany(mappedBy = "publisher")
    private Set<BoardGame> boardGames = new HashSet<>();
}
