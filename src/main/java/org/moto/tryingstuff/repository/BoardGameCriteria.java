package org.moto.tryingstuff.repository;

import org.moto.tryingstuff.dto.BoardGameDto;
import org.moto.tryingstuff.dto.BoardGameFullDto;
import org.moto.tryingstuff.dto.BoardGameMinDto;
import org.moto.tryingstuff.dto.ThemeDto;

import java.util.List;

public interface BoardGameCriteria {

    List<BoardGameMinDto> findAllDto();

    List<BoardGameFullDto> findAllGames();

    List<ThemeDto> findAssociatedTheme(long gameId);

    List<BoardGameDto> findDtosById(long id);

    List<BoardGameDto> findDtosByIdWithJoin(long id);

    List<BoardGameFullDto> findDtosWithRT(long id);

    List<BoardGameFullDto> findDtosWithRTNoMaps(long id);
}
