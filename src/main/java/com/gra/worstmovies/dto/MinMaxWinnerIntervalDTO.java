package com.gra.worstmovies.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class MinMaxWinnerIntervalDTO implements Serializable {

  private List<MovieProducerDTO> max;

  private List<MovieProducerDTO> min;

  public List<MovieProducerDTO> getMax() {
    return max;
  }

  public void setMax(List<MovieProducerDTO> max) {
    this.max = max;
  }

  public List<MovieProducerDTO> getMin() {
    return min;
  }

  public void setMin(List<MovieProducerDTO> min) {
    this.min = min;
  }

}
