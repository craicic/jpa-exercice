package org.moto.tryingstuff.repository;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.moto.tryingstuff.dto.BoardGameDto;
import org.moto.tryingstuff.dto.BoardGameFullDto;
import org.moto.tryingstuff.dto.ThemeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class BoardGameCriteriaImpl implements BoardGameCriteria {

    private static final Logger logger = LoggerFactory.getLogger(BoardGameCriteriaImpl.class);

    final EntityManager em;

    @Autowired
    public BoardGameCriteriaImpl(EntityManager em) {
        this.em = em;
    }

    public List<BoardGameDto> findDtosById(long id) {
        TypedQuery<BoardGameDto> q = em.createQuery(
                "SELECT new org.moto.tryingstuff.dto.BoardGameDto(bg.id, bg.name , p.name, t.name, t.shortDesc) " +
                        " FROM BoardGame as bg, Publisher as p, Theme as t " +
                        " WHERE bg.id = :id", BoardGameDto.class);
        return q.setParameter("id", id).getResultList();
    }

    public List<BoardGameDto> findDtosByIdWithJoin(long id) {
        TypedQuery<BoardGameDto> q = em.createQuery(
                "SELECT new org.moto.tryingstuff.dto.BoardGameDto(bg.id, bg.name , p.name, t.name, t.shortDesc) " +
                        " FROM BoardGame as bg" +
                        " JOIN bg.publisher p JOIN bg.themes t " +
                        " WHERE bg.id = :id", BoardGameDto.class);
        return q.setParameter("id", id).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<BoardGameFullDto> findDtosWithRTNoMaps(long id) {

        List<BoardGameFullDto> games = this.em.unwrap(Session.class)
                .createNativeQuery("select bg.id as bg_id , bg.name, p2.name as publisher_name, t.name as theme_name, t.short_desc  from board_game bg" +
                        " join board_game_theme bgt on bg.id = bgt.fk_game " +
                        " join theme t on t.id = bgt.fk_theme " +
                        " join publisher p2 on bg.fk_publisher = p2.id ")
                .addScalar("bg_id", StandardBasicTypes.LONG)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("publisher_name", StandardBasicTypes.STRING)
                .addScalar("theme_name", StandardBasicTypes.STRING)
                .addScalar("short_desc", StandardBasicTypes.STRING)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] tuples, String[] aliases) {

                        logger.debug("Tuples : " + aliases[0] + " =" + tuples[0]);
                        logger.debug("Tuples : " + aliases[1] + " =" + tuples[1]);
                        logger.debug("Tuples : " + aliases[2] + " =" + tuples[2]);
                        logger.debug("Tuples : " + aliases[3] + " =" + tuples[3]);
                        logger.debug("Tuples : " + aliases[4] + " =" + tuples[4]);

                        BoardGameFullDto boardGameFullDto = new BoardGameFullDto();
                        boardGameFullDto.setId((long) tuples[0]);
                        boardGameFullDto.setName((String) tuples[1]);
                        boardGameFullDto.setPublisherName((String) tuples[2]);

                        ThemeDto theme = new ThemeDto((String) tuples[3], (String) tuples[4]);
                        List<ThemeDto> themes = List.of(theme);

                        boardGameFullDto.setThemeDtos(themes);

                        return (BoardGameFullDto) boardGameFullDto;
                    }

                    @Override
                    public List transformList(List list) {
                        List<ThemeDto> themesRelatedToAGame = new ArrayList<>();
                        List<BoardGameFullDto> filteredList = new ArrayList<>();
                        List<Long> ids = new ArrayList<>();
                        int distinctRows = -1;
                        int i = 0;

                        for (Object o : list) {
                            BoardGameFullDto currentGame = (BoardGameFullDto) o;
                            logger.debug("obj contains " + o.toString());
                            if (!ids.contains(currentGame.getId())) {
                                distinctRows++;
                                logger.debug("Its a new BoardGame row... Adding a entry in filtered list");
                                filteredList.add(currentGame);
                                ids.add(currentGame.getId());
                                logger.debug("id stored : " + currentGame.getId());
                                themesRelatedToAGame.clear();
                                logger.debug("Cleared themesRelatedToAGame...");
                                themesRelatedToAGame.add(currentGame.getThemeDtos().get(0));
                                logger.debug("...And populate themesRelatedToAGame with the current theme");

                            } else {
                                logger.debug("Another row of id " + currentGame.getId() + " found... Adding current theme to the list");
                                themesRelatedToAGame.add(currentGame.getThemeDtos().get(0));
                                logger.debug("then adding this theme list to the filtered game list");
                                filteredList.get(distinctRows).setThemeDtos(themesRelatedToAGame);
                            }
                            i++;
                        }
                        for (BoardGameFullDto game : filteredList) {
                            logger.debug(game.toString());
                        }

                        return filteredList;
                    }
                })
                .list();
        return games;
    }

    @SuppressWarnings("unchecked")
    public List<BoardGameFullDto> findDtosWithRT(long id) {

        List<BoardGameFullDto> games = this.em.unwrap(Session.class)
                .createNativeQuery("select bg.id as bg_id , bg.name, p2.name as publisher_name, t.name as theme_name, t.short_desc  from board_game bg" +
                        " join board_game_theme bgt on bg.id = bgt.fk_game " +
                        " join theme t on t.id = bgt.fk_theme " +
                        " join publisher p2 on bg.fk_publisher = p2.id ")
                .addScalar("bg_id", StandardBasicTypes.LONG)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("publisher_name", StandardBasicTypes.STRING)
                .addScalar("theme_name", StandardBasicTypes.STRING)
                .addScalar("short_desc", StandardBasicTypes.STRING)
                .unwrap(Query.class)
                .setResultTransformer(new ResultTransformer() {
                    @Override
                    public Object transformTuple(Object[] tuples, String[] aliases) {

                        logger.debug("Tuples : " + aliases[0] + " =" + tuples[0]);
                        logger.debug("Tuples : " + aliases[1] + " =" + tuples[1]);
                        logger.debug("Tuples : " + aliases[2] + " =" + tuples[2]);
                        logger.debug("Tuples : " + aliases[3] + " =" + tuples[3]);
                        logger.debug("Tuples : " + aliases[4] + " =" + tuples[4]);

                        BoardGameFullDto boardGameFullDto = new BoardGameFullDto();
                        boardGameFullDto.setId((long) tuples[0]);
                        boardGameFullDto.setName((String) tuples[1]);
                        boardGameFullDto.setPublisherName((String) tuples[2]);

                        ThemeDto theme = new ThemeDto((String) tuples[3], (String) tuples[4]);
                        List<ThemeDto> themes = List.of(theme);

                        boardGameFullDto.setThemeDtos(themes);

                        return (BoardGameFullDto) boardGameFullDto;
                    }

                    @Override
                    public List transformList(List list) {

                        HashMap<Long, List<ThemeDto>> map = new HashMap<>();
                        ArrayList<BoardGameFullDto> filteredList = new ArrayList<>();
                        Long id;
                        BoardGameFullDto game;

                        Instant startLoop = Instant.now();
                        for (Object o : list) {
                            game = (BoardGameFullDto) o;
                            id = game.getId();
                            if (!map.containsKey(id)) {
                                map.put(id, game.getThemeDtos());
                                filteredList.add(game);
                            } else {
                                ArrayList<ThemeDto> tmp = new ArrayList<>(map.get(id));
                                tmp.add(game.getThemeDtos().get(0));
                                map.replace(id, tmp);
                            }
                        }
                        Instant finishLoop = Instant.now();
                        long timeElapsedLoop = Duration.between(startLoop, finishLoop).toMillis();
                        logger.debug("Time elapsed for loop : " + timeElapsedLoop + "ms");
                        AtomicInteger i = new AtomicInteger();

                        Instant start = Instant.now();
                        map.forEach((k, v) -> filteredList.get(i.getAndIncrement()).setThemeDtos(v));
                        filteredList.forEach(bg -> logger.debug(bg.toString()));
                        Instant finish = Instant.now();
                        long timeElapsed = Duration.between(start, finish).toMillis();
                        logger.debug("Time elapsed for stream operations : " + timeElapsed + "ms");

                        return filteredList;
                    }
                })
                .list();
        return games;
    }
}
