package org.moto.tryingstuff.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public Theme(String name, String shortDesc) {
        this.name = name;
        this.shortDesc = shortDesc;
    }
}
