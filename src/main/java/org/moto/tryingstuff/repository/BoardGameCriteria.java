package org.moto.tryingstuff.repository;

import org.moto.tryingstuff.dto.BoardGameDto;
import org.moto.tryingstuff.dto.BoardGameFullDto;

import java.util.List;

public interface BoardGameCriteria {

    List<BoardGameDto> findDtosById(long id);

    List<BoardGameDto> findDtosByIdWithJoin(long id);

    List<BoardGameFullDto> findDtosWithRT(long id);

    List<BoardGameFullDto> findDtosWithRTNoMaps(long id);
}
