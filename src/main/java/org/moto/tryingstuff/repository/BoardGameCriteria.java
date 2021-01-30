package org.moto.tryingstuff.repository;

import org.moto.tryingstuff.dto.BoardGameDto;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardGameCriteria {

    BoardGameDto findOverviewById(long id);
}
