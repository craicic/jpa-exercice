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
import java.util.ArrayList;
import java.util.List;

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

                        logger.warn("Tuples : " + aliases[0] + " =" + tuples[0]);
                        logger.warn("Tuples : " + aliases[1] + " =" + tuples[1]);
                        logger.warn("Tuples : " + aliases[2] + " =" + tuples[2]);
                        logger.warn("Tuples : " + aliases[3] + " =" + tuples[3]);
                        logger.warn("Tuples : " + aliases[4] + " =" + tuples[4]);

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

                        for (Object o : list) {
                            BoardGameFullDto currentGame = (BoardGameFullDto) o;
                            logger.warn("obj contains " + o.toString());
                            if (!ids.contains(currentGame.getId())) {
                                distinctRows++;
                                logger.warn("Its a new BoardGame row... Adding a entry in filtered list");
                                filteredList.add(currentGame);
                                ids.add(currentGame.getId());
                                logger.warn("id stored : " + currentGame.getId());
                                themesRelatedToAGame.clear();
                                logger.warn("Cleared themesRelatedToAGame...");
                                themesRelatedToAGame.add(currentGame.getThemeDtos().get(0));
                                logger.warn("...And populate themesRelatedToAGame with the current theme");

                            } else {
                                logger.warn("Another row of id " + currentGame.getId() + " found... Adding current theme to the list");
                                themesRelatedToAGame.add(currentGame.getThemeDtos().get(0));
                                logger.warn("then adding this theme list to the filtered game list");
                                filteredList.get(distinctRows).setThemeDtos(themesRelatedToAGame);
                            }

                        }
                        for (BoardGameFullDto game : filteredList) {
                            logger.warn(game.toString());
                        }

                        return filteredList;
                    }
                })
                .list();
        return games;

    }
}
