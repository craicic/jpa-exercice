package org.moto.tryingstuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardGameFullDto {

    private long id;

    private String name;

    private String publisherName;

    private List<ThemeDto> themeDtos;

    public BoardGameFullDto(long id, String name, String publisherName) {
        this.id = id;
        this.name = name;
        this.publisherName = publisherName;
    }
}
