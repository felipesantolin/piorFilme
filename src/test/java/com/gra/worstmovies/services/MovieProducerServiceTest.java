package com.gra.worstmovies.services;

import com.gra.worstmovies.common.MovieProducersBean;
import com.gra.worstmovies.dto.MovieProducerDTO;
import com.gra.worstmovies.dto.MinMaxWinnerIntervalDTO;
import com.gra.worstmovies.fixture.MovieProducerBeanFixture;
import org.junit.Assert;
import org.junit.Test;

import junit.framework.TestCase;
import org.junit.jupiter.api.DisplayName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MovieProducerServiceTest extends TestCase {

  private List<MovieProducersBean> movieProducersBeans;

  @Test
  @DisplayName("Deve verificar se criou o Intervalo do Producer 1")
  public void testDeveVerificarSeCriouIntervaloCorretamente(){
    String producer = "Producer 1";
    List<MovieProducersBean> movieProducersFromProducer1 = Arrays.asList(
            MovieProducerBeanFixture.movieProducer1980WinnerProducer1(),
            MovieProducerBeanFixture.movieProducer2022WinnerProducer1());
    MovieProducerService movieProducerService = new MovieProducerService();
    MovieProducerDTO movieProducerDTO = movieProducerService.compareAndCreateInterval(producer, movieProducersFromProducer1);
    Assert.assertEquals(Integer.valueOf(42), movieProducerDTO.getInterval());
    Assert.assertEquals(Integer.valueOf(1980), movieProducerDTO.getPreviousWin());
    Assert.assertEquals(Integer.valueOf(2022), movieProducerDTO.getFollowingWin());
  }

  @Test
  @DisplayName("Deve verificar se criou o Intervalo do Producer 1")
  public void testDeveAgruparPorProducerEIgnorarProducerComApenas(){
    adicionarMovieProducers();
    MovieProducerService movieProducerService = new MovieProducerService();
    List<MovieProducerDTO> movieProducerDTOS = movieProducerService.groupByProducerAndFilter(movieProducersBeans);
    Assert.assertEquals(3, movieProducerDTOS.size());
    Assert.assertEquals(Optional.empty(), movieProducerDTOS.stream().filter(m -> m.getProducer().equals("Producer 4")).findAny());
  }

  @Test
  @DisplayName("Deve encontrar o Menor e Maior Intervalo do Producer")
  public void testDeveEncontrarOMaiorEMenorIntervaloEAdicionarNaLista(){
    adicionarMovieProducers();
    MovieProducerService movieProducerService = new MovieProducerService();
    MinMaxWinnerIntervalDTO minMaxWinnerIntervalDTO = movieProducerService.getProducersMinAndMaxInterval(movieProducersBeans);
    Assert.assertEquals(1, minMaxWinnerIntervalDTO.getMax().size());
    Assert.assertEquals(1, minMaxWinnerIntervalDTO.getMin().size());
    assertEquals("Producer 1", minMaxWinnerIntervalDTO.getMax().stream().findFirst().get().getProducer());
    assertEquals("Producer 2", minMaxWinnerIntervalDTO.getMin().stream().findFirst().get().getProducer());
  }

  @Test
  @DisplayName("Deve encontrar o Menor e Maior Intervalo do Producer, com 2 Producers com Máximo")
  public void testDeveEncontrarOMaiorEMenorIntervaloEAdicionarCom2VencedoresComMaiorIntervalo(){
    adicionarMovieProducers();
    movieProducersBeans.add(MovieProducerBeanFixture.movieProducer1979WinnerProducer5());
    movieProducersBeans.add(MovieProducerBeanFixture.movieProducer2021WinnerProducer5());
    MovieProducerService movieProducerService = new MovieProducerService();
    MinMaxWinnerIntervalDTO minMaxWinnerIntervalDTO = movieProducerService.getProducersMinAndMaxInterval(movieProducersBeans);
    Assert.assertEquals(2, minMaxWinnerIntervalDTO.getMax().size());
  }

  private void adicionarMovieProducers(){
    movieProducersBeans = new ArrayList<>();
    movieProducersBeans.add(MovieProducerBeanFixture.movieProducer1980WinnerProducer1());
    movieProducersBeans.add(MovieProducerBeanFixture.movieProducer2022WinnerProducer1());
    movieProducersBeans.add(MovieProducerBeanFixture.movieProducer2018WinnerProducer2());
    movieProducersBeans.add(MovieProducerBeanFixture.movieProducer2019WinnerProducer2());
    movieProducersBeans.add(MovieProducerBeanFixture.movieProducer2016WinnerProducer3());
    movieProducersBeans.add(MovieProducerBeanFixture.movieProducer2020WinnerProducer3());
    movieProducersBeans.add(MovieProducerBeanFixture.movieProducer2000WinnerProducer4());
  }

}