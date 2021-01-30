package org.moto.tryingstuff.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Theme {

    @Id
    private long id;

    @Column(length = 50)
    private String name;

    @Column(name = "short_desc")
    private String shortDesc;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "themes")
    private Set<BoardGame> boardGames = new HashSet<>();

}
