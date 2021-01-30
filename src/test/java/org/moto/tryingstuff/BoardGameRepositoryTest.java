package org.moto.tryingstuff;

import org.junit.jupiter.api.Test;
import org.moto.tryingstuff.dto.BoardGameDto;
import org.moto.tryingstuff.model.BoardGame;
import org.moto.tryingstuff.repository.BoardGameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql(scripts = "/sql/data.sql")
class BoardGameRepositoryTest {

    private static final Logger logger = LoggerFactory.getLogger(BoardGameRepositoryTest.class);


    @Autowired
    private BoardGameRepository repository;

    @Test
    void findAll_ShouldReturnTwoObjects() {
       List<BoardGame> games = repository.findAll();
       logger.debug(games.toString());
       assertEquals(2, games.size());
    }

    @Test
    void test_things() {
        BoardGameDto game = repository.findGameDto(2L);
        logger.debug(game.toString());
        assertEquals("Munchkin", game.getName());
    }
}