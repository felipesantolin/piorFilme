package com.gra.worstmovies.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.gra.worstmovies.common.MovieProducersBean;
import com.gra.worstmovies.util.GenericRepository;
import com.gra.worstmovies.entities.MovieProducer;

public class MovieProducerRepository extends GenericRepository<MovieProducer> {

  public List<MovieProducersBean> findWinnersProducers() {
    List<MovieProducersBean> movieProducersBeans = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    sb.append(" select ");
    sb.append(" mp.producer, ");
    sb.append(" m.year ");
    sb.append(" from movieproducer mp ");
    sb.append(" inner join movie m on m.id = mp.id_movie ");
    sb.append(" where m.winner = 1 ");
    sb.append(" group by mp.producer, m.year ");
    sb.append(" order by mp.producer, m.year ");

    Query query = createNativeQuery(sb.toString());
    List<Object[]> resultList = query.getResultList();
    movieProducersBeans = convertToMovieProducersBean(resultList);
    return movieProducersBeans;
  }

  private List<MovieProducersBean> convertToMovieProducersBean(List<Object[]> resultList) {
    List<MovieProducersBean> movieProducersBeans = new ArrayList<>();
    MovieProducersBean movieProducersBean;
    for (Object[] object : resultList) {
      movieProducersBean = new MovieProducersBean();
      movieProducersBean.setProducer((String) object[0]);
      movieProducersBean.setYear((Integer) object[1]);
      movieProducersBeans.add(movieProducersBean);
    }
    return movieProducersBeans;
  }

}
