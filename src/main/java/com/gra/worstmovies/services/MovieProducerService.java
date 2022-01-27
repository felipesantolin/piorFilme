package com.gra.worstmovies.services;

import javax.transaction.Transactional;

import com.gra.worstmovies.common.MovieProducersBean;
import com.gra.worstmovies.dto.MovieProducerDTO;
import com.gra.worstmovies.dto.MinMaxWinnerIntervalDTO;
import com.gra.worstmovies.entities.Movie;
import com.gra.worstmovies.entities.MovieProducer;
import com.gra.worstmovies.repositories.MovieProducerRepository;
import com.gra.worstmovies.repositories.MovieRepository;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
public class MovieProducerService {

  private MovieProducerRepository movieProducerRepository;
  private MovieRepository movieRepository;

  private List<MovieProducerDTO> minIntervalWinnerMovieList;
  private List<MovieProducerDTO> maxIntervalWinnerMovieList;

  public MovieProducerService(){
    this.movieProducerRepository = new MovieProducerRepository();
    this.movieRepository = new MovieRepository();
  }

  public MovieProducer create(Movie movie, String producer){
    MovieProducer movieProducer = new MovieProducer();
    movieProducer.setMovie(movie);
    movieProducer.setProducer(producer);
    movieProducerRepository.persist(movieProducer);
    return movieProducer;
  }

  public MinMaxWinnerIntervalDTO getWorstMovieWinners() {
    List<MovieProducersBean> moviesProducers = movieProducerRepository.findWinnersProducers();
    MinMaxWinnerIntervalDTO minMaxWinnerIntervalDTO = getProducersMinAndMaxInterval(moviesProducers);
    return minMaxWinnerIntervalDTO;
  }

  protected MinMaxWinnerIntervalDTO getProducersMinAndMaxInterval(List<MovieProducersBean> worstMovieWinners) {
    MinMaxWinnerIntervalDTO minMaxWinnerIntervalDTO = new MinMaxWinnerIntervalDTO();
    minIntervalWinnerMovieList = new ArrayList<>();
    maxIntervalWinnerMovieList = new ArrayList<>();

    worstMovieWinners.stream().findFirst().orElseThrow(RuntimeException::new);

    final List<MovieProducerDTO> movieProducerDTOs = groupByProducerAndFilter(worstMovieWinners);

    findMinAndMaxToAdd(movieProducerDTOs);

    minMaxWinnerIntervalDTO.setMin(minIntervalWinnerMovieList);
    minMaxWinnerIntervalDTO.setMax(maxIntervalWinnerMovieList);

    return minMaxWinnerIntervalDTO;
  }

  private void findMinAndMaxToAdd(List<MovieProducerDTO> movieProducerDTOs) {
    MovieProducerDTO maxMovieProducerDTO = movieProducerDTOs.stream().max(Comparator.comparing(MovieProducerDTO::getInterval)).get();
    MovieProducerDTO minMovieProducerDTO = movieProducerDTOs.stream().min(Comparator.comparing(MovieProducerDTO::getInterval)).get();

//    minIntervalWinnerMovieList.add(minMovieProducerDTO);
//    maxIntervalWinnerMovieList.add(maxMovieProducerDTO);
    for (MovieProducerDTO movieProducerDTO : movieProducerDTOs) {
      if(movieProducerDTO.getInterval().equals(minMovieProducerDTO.getInterval())){
        minIntervalWinnerMovieList.add(movieProducerDTO);
      }
      if(movieProducerDTO.getInterval().equals(maxMovieProducerDTO.getInterval())){
        maxIntervalWinnerMovieList.add(movieProducerDTO);
      }
    }
  }

  protected List<MovieProducerDTO> groupByProducerAndFilter(List<MovieProducersBean> worstMovieWinners) {
    Map<String, List<MovieProducersBean>> producers = worstMovieWinners.stream().collect(Collectors.groupingBy(MovieProducersBean::getProducer));

    final List<MovieProducerDTO> movieProducerDTOs = new ArrayList<>();

    producers.forEach((producer,movieProducers) ->{
      if(movieProducers.size() > 1){
        movieProducerDTOs.addAll(compareAndCreateInterval(producer, movieProducers));
      }
    });
    return movieProducerDTOs;
  }

  protected List<MovieProducerDTO> compareAndCreateInterval(String producer, List<MovieProducersBean> moviesProducer) {
//    MovieProducersBean firstMovieProducersBean = moviesProducer.stream().findFirst().get();

    List<MovieProducerDTO> moviesProducersDTO = new ArrayList<>();
    for (int i = 0; i < moviesProducer.size() -1; i++) {
      MovieProducerDTO movieProducerDTO = new MovieProducerDTO();
      movieProducerDTO.setProducer(producer);
      movieProducerDTO.setPreviousWin(moviesProducer.get(i).getYear());
      movieProducerDTO.setFollowingWin(moviesProducer.get(i+1).getYear());
      movieProducerDTO.setInterval(movieProducerDTO.getFollowingWin()-movieProducerDTO.getPreviousWin());
      moviesProducersDTO.add(movieProducerDTO);
    }
//    MovieProducersBean lastMovieProducer = moviesProducer.stream()
//            .filter(mp -> !mp.getYear().equals(firstMovieProducersBean.getYear()))
//            .max(Comparator.comparingInt(yIni -> yIni.getYear() - firstMovieProducersBean.getYear())).get();

    return moviesProducersDTO;
  }

}
