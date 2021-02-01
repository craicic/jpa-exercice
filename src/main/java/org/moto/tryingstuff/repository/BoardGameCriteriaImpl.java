package org.moto.tryingstuff.repository;

import org.moto.tryingstuff.dto.BoardGameDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class BoardGameCriteriaImpl implements BoardGameCriteria {

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
}
