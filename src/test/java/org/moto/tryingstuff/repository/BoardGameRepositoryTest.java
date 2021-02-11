package org.moto.tryingstuff.repository;

import org.junit.jupiter.api.Test;
import org.moto.tryingstuff.dto.BoardGameDto;
import org.moto.tryingstuff.dto.BoardGameFullDto;
import org.moto.tryingstuff.dto.BoardGameMinDto;
import org.moto.tryingstuff.dto.ThemeDto;
import org.moto.tryingstuff.model.BoardGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.jdbc.Sql;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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
        Instant start = Instant.now();
        List<BoardGame> games = jpaRepository.findAll();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.debug("Time elapsed : " + timeElapsed + "ms");
        logger.debug(games.toString());
        assertEquals(2, games.size());
    }

    @Test
    void findDtoSuccessiveRequests_ListShouldNotBeEmpty() {
        List<BoardGameMinDto> games = criteriaRepository.findAllDto();
        ArrayList<BoardGameFullDto> gamesFull = new ArrayList<>();

        for (BoardGameMinDto gameMin : games) {
            List<ThemeDto> themes = criteriaRepository.findAssociatedTheme(gameMin.getId());

            gamesFull.add(new BoardGameFullDto(gameMin.getId(), gameMin.getName(), gameMin.getPublisherName(), themes));
        }

        gamesFull.forEach(value -> logger.debug("-----> NEW GAME FOUND : " + value.toString()));
    }

    @Test
    void findGameSuccessiveRequests_ListShouldNotBeEmpty() {
        List<BoardGameFullDto> games = criteriaRepository.findAllGames();
        ArrayList<BoardGameFullDto> gamesFull = new ArrayList<>();

        for (BoardGameFullDto game : games) {
            List<ThemeDto> themes = criteriaRepository.findAssociatedTheme(game.getId());
            game.setThemeDtos(themes);
            gamesFull.add(game);
        }

        gamesFull.forEach(value -> logger.debug("-----> NEW GAME FOUND : " + value.toString()));
    }

    @Test
    void findDtosById_ListShouldNotBeEmpty() {
        List<BoardGameDto> games = criteriaRepository.findDtosById(2L);
        for (BoardGameDto game : games) {
            logger.debug("-----> NEW GAME FOUND : " + game.toString());
        }
        assertFalse(games.isEmpty());
    }

    @Test
    void findDtosByIdWithJoin_ListShouldNotBeEmpty() {
        List<BoardGameDto> games = criteriaRepository.findDtosByIdWithJoin(2L);
        for (BoardGameDto game : games) {
            logger.debug("-----> NEW GAME FOUND : " + game.toString());
        }
        assertFalse(games.isEmpty());
    }

    @Test
    void findDtosWithRTNoMaps_shouldLogThings() {
        Instant start = Instant.now();
        List<BoardGameFullDto> games = criteriaRepository.findDtosWithRTNoMaps(2L);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.debug("Time elapsed : " + timeElapsed + "ms");
        assertFalse(games.isEmpty());
    }

    @Test
    void findDtosWithRT_shouldLogThings() {

        Instant start = Instant.now();
        List<BoardGameFullDto> games = criteriaRepository.findDtosWithRT(2L);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        logger.debug("Time elapsed : " + timeElapsed + "ms");
        assertFalse(games.isEmpty());
    }
}