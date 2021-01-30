package org.moto.tryingstuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class BoardGameDto {

    private String name;

    private short minAge;

    private String publisherName;

    private Set<String> themesNames;
}
