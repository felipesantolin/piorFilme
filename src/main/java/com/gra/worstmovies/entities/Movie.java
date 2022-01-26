package com.gra.worstmovies.entities;

import com.gra.worstmovies.util.AbstractEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Movie extends AbstractEntity {

  @Id
  @GeneratedValue
  private Long id;

  private Integer year;

  private String title;

  private Boolean winner;
  
  @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
  private Set<MovieStudio> studios;

  @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY)
  private List<MovieProducer> producers;

  public Movie() {
  }

  @Override
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<MovieStudio> getStudios() {
    return studios;
  }

  public void setStudios(Set<MovieStudio> studios) {
    this.studios = studios;
  }

  public List<MovieProducer> getProducers() {
    return producers;
  }

  public void setProducers(List<MovieProducer> producers) {
    this.producers = producers;
  }

  public Boolean getWinner() {
    return winner;
  }

  public void setWinner(Boolean winner) {
    this.winner = winner;
  }
}
