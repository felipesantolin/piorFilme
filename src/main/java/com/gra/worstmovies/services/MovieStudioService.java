package com.gra.worstmovies.services;

import javax.transaction.Transactional;

import com.gra.worstmovies.entities.Movie;
import com.gra.worstmovies.entities.MovieStudio;
import com.gra.worstmovies.repositories.MovieStudioRepository;

@Transactional
public class MovieStudioService {

    private MovieStudioRepository movieStudioRepository;

    public MovieStudioService(){
        this.movieStudioRepository = new MovieStudioRepository();
    }

    public MovieStudio create(Movie movie, String studio){
        MovieStudio movieStudio = new MovieStudio();
        movieStudio.setMovie(movie);
        movieStudio.setStudio(studio);
        movieStudioRepository.persist(movieStudio);
        return movieStudio;
    }
    
}
