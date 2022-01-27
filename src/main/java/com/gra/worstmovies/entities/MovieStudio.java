package com.gra.worstmovies.entities;

import com.gra.worstmovies.util.AbstractEntity;

import javax.persistence.*;

@Entity
public class MovieStudio extends AbstractEntity {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_movie")
  private Movie movie;

  private String studio;

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Movie getMovie() {
    return movie;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  public String getStudio() {
    return studio;
  }

  public void setStudio(String studio) {
    this.studio = studio;
  }
}
