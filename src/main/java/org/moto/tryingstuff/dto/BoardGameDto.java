package org.moto.tryingstuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.moto.tryingstuff.model.Theme;

@Data
@AllArgsConstructor
public class BoardGameDto {

    private long id;

    private String name;

    private String publisherName;

    private Theme theme;

    public BoardGameDto(long id, String name, String publisherName, String themeName, String themeShortDesc) {
        this.id = id;
        this.name = name;
        this.publisherName = publisherName;
        this.theme = new Theme(themeName, themeShortDesc);
    }
}
