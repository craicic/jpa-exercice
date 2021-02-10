package org.moto.tryingstuff.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardGameFullDto {

    private long id;

    private String name;

    private String publisherName;

    private List<ThemeDto> themeDtos;
}
