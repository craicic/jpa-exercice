package org.moto.tryingstuff.repository;

import org.moto.tryingstuff.dto.BoardGameDto;

import java.util.List;

public interface BoardGameCriteria {

    List<BoardGameDto> findDtosById(long id);

    List<BoardGameDto> findDtosByIdWithJoin(long id);
}
