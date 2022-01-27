package com.gra.worstmovies.services;

import com.gra.worstmovies.entities.Movie;
import com.gra.worstmovies.repositories.MovieRepository;

import javax.transaction.Transactional;

@Transactional
public class MovieService {

  private MovieRepository movieRepository;

  public MovieService() {
    this.movieRepository = new MovieRepository();
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
