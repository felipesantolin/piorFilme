package com.gra.worstmovies.services;

import javax.transaction.Transactional;

import com.gra.worstmovies.entities.Movie;
import com.gra.worstmovies.entities.MovieProducer;
import com.gra.worstmovies.repositories.MovieProducerRepository;

@Transactional
public class MovieProducerService {

    private MovieProducerRepository movieProducerRepository;

    public MovieProducerService(){
        this.movieProducerRepository = new MovieProducerRepository();
    }

    public MovieProducer create(Movie movie, String producer){
        MovieProducer movieProducer = new MovieProducer();
        movieProducer.setMovie(movie);
        movieProducer.setProducer(producer);
        movieProducerRepository.persist(movieProducer);
        return movieProducer;
    }
    
}
