package org.moto.tryingstuff.repository;

import org.junit.jupiter.api.Test;
import org.moto.tryingstuff.dto.BoardGameDto;
import org.moto.tryingstuff.dto.BoardGameFullDto;
import org.moto.tryingstuff.model.BoardGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@Sql(scripts = "/sql/data.sql")
class BoardGameRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(BoardGameRepositoryTest.class);

    @Autowired
    private BoardGameRepository jpaRepository;

    @Autowired
    private BoardGameCriteria criteriaRepository;

    @Test
    void findAll_ShouldReturnTwoObjects() {
       List<BoardGame> games = jpaRepository.findAll();
       logger.debug(games.toString());
       assertEquals(2, games.size());
    }

    @Test
    void findDtosById_ListShouldNotBeEmpty() {
        List<BoardGameDto> games = criteriaRepository.findDtosById(2L);
        for (BoardGameDto game: games) {
            logger.debug("-----> NEW GAME FOUND : " + game.toString());
        }
        assertFalse(games.isEmpty());
    }

    @Test
    void findDtosByIdWithJoin_ListShouldNotBeEmpty() {
        List<BoardGameDto> games = criteriaRepository.findDtosByIdWithJoin(2L);
        for (BoardGameDto game: games) {
            logger.debug("-----> NEW GAME FOUND : " + game.toString());
        }
        assertFalse(games.isEmpty());
    }

    @Test
    void findDtosWithRT_shouldAtLeastLogStuff() {
        List<BoardGameFullDto> games = criteriaRepository.findDtosWithRT(2L);
        assertFalse(games.isEmpty());
    }
}