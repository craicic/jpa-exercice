package org.moto.tryingstuff.dto;

import lombok.Data;

@Data
public class BoardGameFlatDto {
    private long id;

    private String name;

    private String publisherName;

    private String themeName;

    private String themeShortDesc;
}
