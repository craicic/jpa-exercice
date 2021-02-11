package org.moto.tryingstuff.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardGameMinDto {

    private long id;

    private String name;

    private String publisherName;

}
