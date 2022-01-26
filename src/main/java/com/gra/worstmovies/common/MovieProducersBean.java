package com.gra.worstmovies.common;

import java.io.Serializable;

public class MovieProducersBean implements Serializable {

  private String producer;

  private Integer year;

  public String getProducer() {
    return producer;
  }

  public void setProducer(String producer) {
    this.producer = producer;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }
}
