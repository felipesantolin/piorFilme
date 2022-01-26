package com.gra.worstmovies.services;

import com.gra.worstmovies.common.MovieProducersBean;
import com.gra.worstmovies.dto.MinMaxWinnerIntervalDTO;
import com.gra.worstmovies.entities.Movie;
import com.gra.worstmovies.entities.MovieProducer;
import com.gra.worstmovies.dto.MovieProducerDTO;
import com.gra.worstmovies.repositories.MovieProducerRepository;
import com.gra.worstmovies.repositories.MovieRepository;

import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Transactional
public class MovieService {

  private MovieRepository movieRepository;
  private MovieProducerRepository movieProducerRepository;

  private List<MovieProducerDTO> minIntervalWinnerMovieList;
  private List<MovieProducerDTO> maxIntervalWinnerMovieList;

  public MovieService() {
    this.movieRepository = new MovieRepository();
    this.movieProducerRepository = new MovieProducerRepository();
  }

  public MinMaxWinnerIntervalDTO getWorstMovieWinners() {
    minIntervalWinnerMovieList = new ArrayList<>();
    minIntervalWinnerMovieList = new ArrayList<>();

    List<MovieProducersBean> moviesProducers = movieProducerRepository.findWinnersProducers();
//    List<MovieProducersBean> movieProducersBeanListOrdered = moviesProducers.stream().sorted(Comparator.comparing(MovieProducersBean::getProducer)).collect(Collectors.toList());
    MinMaxWinnerIntervalDTO minMaxWinnerIntervalDTO = getProducersMinAndMaxInterval(moviesProducers);
    return minMaxWinnerIntervalDTO;
  }

  private void createAndAddMovieProducerBean(Movie movie, MovieProducer movieProducer, List<MovieProducersBean> movieProducers) {
    MovieProducersBean movieProducerBean = null;
    movieProducerBean = new MovieProducersBean();
    movieProducerBean.setProducer(movieProducer.getProducer());
    movieProducerBean.setYear(movie.getYear());
    movieProducers.add(movieProducerBean);
  }

  private MinMaxWinnerIntervalDTO getProducersMinAndMaxInterval(List<MovieProducersBean> worstMovieWinners) {
    MinMaxWinnerIntervalDTO minMaxWinnerIntervalDTO = new MinMaxWinnerIntervalDTO();
    minIntervalWinnerMovieList = new ArrayList<>();
    maxIntervalWinnerMovieList = new ArrayList<>();

    worstMovieWinners.stream().findFirst().orElseThrow(RuntimeException::new);

    Map<String, List<MovieProducersBean>> producers = worstMovieWinners.stream().collect(Collectors.groupingBy(MovieProducersBean::getProducer));

    final List<MovieProducerDTO> movieProducerDTOs = new ArrayList<>();

    producers.forEach((producer,movieProducers) ->{
      if(movieProducers.size() > 1){
        movieProducerDTOs.add(compareAndCreateInterval(producer, movieProducers));
      }
    });

    int minIntervalActual = 0;
    int maxIntervalActual = 0;
    for (MovieProducerDTO movieProducerDTO : movieProducerDTOs) {
      if(minIntervalActual == 0 || maxIntervalActual == 0) {
        minIntervalActual = movieProducerDTO.getInterval();
      }
    }
    return minMaxWinnerIntervalDTO;
  }

  private MovieProducerDTO compareAndCreateInterval(String producer, List<MovieProducersBean> moviesProducer) {
    MovieProducersBean firstMovieProducersBean = moviesProducer.stream().findFirst().get();

      MovieProducersBean lastMovieProducer = moviesProducer.stream()
              .filter(mp -> !mp.getYear().equals(firstMovieProducersBean.getYear()))
              .max(Comparator.comparingInt(yIni -> yIni.getYear() - firstMovieProducersBean.getYear())).get();

    MovieProducerDTO movieProducerDTO = new MovieProducerDTO();
    movieProducerDTO.setProducer(producer);
    movieProducerDTO.setPreviousWin(firstMovieProducersBean.getYear());
    movieProducerDTO.setFollowingWin(lastMovieProducer.getYear());
    movieProducerDTO.setInterval(lastMovieProducer.getYear()-firstMovieProducersBean.getYear());
    return movieProducerDTO;
  }

  private boolean isMinIntervalLessOrEqualThenActual(List<MovieProducerDTO> list, int interval) {
    for (MovieProducerDTO movieProducerDTO : list) {
      if(movieProducerDTO.getInterval() > interval){
        return true;
      }
    }
    return false;
  }


  private boolean isMaxIntervalGreaterOrEqualThenActual(List<MovieProducerDTO> list, int interval) {
    for (MovieProducerDTO movieProducerDTO : list) {
      if(movieProducerDTO.getInterval() <= interval){
        return true;
      }
    }
    return false;
  }

  private MovieProducerDTO createMovieProducerDTO(String producer, Integer previousWin, Integer followingWin) {
    MovieProducerDTO movieProducerDTO = new MovieProducerDTO();
    movieProducerDTO.setProducer(producer);
    movieProducerDTO.setPreviousWin(previousWin);
    movieProducerDTO.setFollowingWin(followingWin);
    return movieProducerDTO;
  }

  public <E> Boolean isListNotNullOrEmpty(Collection<E> collection) {
    return collection != null && !collection.isEmpty();
  }

  public Movie create(Integer year, String title, Boolean winner) {
    Movie movie = new Movie();
    movie.setYear(year);
    movie.setTitle(title);
    movie.setWinner(winner);
    movieRepository.persist(movie);
    return movie;
  }

  public void save(Movie movie) {
    movieRepository.persist(movie);
  }

  public void update(Movie movie) {
    movieRepository.merge(movie);
  }
}
