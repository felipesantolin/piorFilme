package com.gra.worstmovies.repositories;

import com.gra.worstmovies.util.GenericRepository;
import com.gra.worstmovies.entities.Movie;

import java.util.List;

import javax.persistence.TypedQuery;

public class MovieRepository extends GenericRepository<Movie> {

  public List<Movie> getWorstMovieWinners() {
    StringBuilder sb = new StringBuilder();
    sb.append(" select m from Movie m ");
    sb.append(" where m.winner = :winner ");
    sb.append(" order by m.year ");

    TypedQuery<Movie> typedQuery = createTypedQuery(sb.toString());
    typedQuery.setParameter("winner", Boolean.TRUE);
    return typedQuery.getResultList();
  }

}
