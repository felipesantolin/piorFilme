package com.gra.worstmovies.resources;

import com.google.gson.Gson;
import com.gra.worstmovies.Server;
import com.gra.worstmovies.dto.MinMaxWinnerIntervalDTO;
import com.gra.worstmovies.services.DataInitializatorService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.List;

public class MovieProducerResourceTest {

  private static HttpServer httpServer;

  @BeforeAll
  public static void beforeAll() {
    httpServer = new Server().initServer();
    initData();
  }

  @AfterAll
  public static void afterAll() {
    if (httpServer != null) {
      httpServer.stop();
    }
  }

  @Test
  @DisplayName("Deve verificar se servidor iniciou")
  public void testServerConnectionStarted() {
    Assert.assertTrue(httpServer.isStarted());
  }

  @Test
  @DisplayName("Deve acessar a url do servidor e retornar uma Lista e que não pode estar vazia")
  public void testMovieGetRequest() {
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8080");
    String response = target.path("/movies").request().get(String.class);
    List<?> result = new Gson().fromJson(response, List.class);
    Assert.assertTrue(!result.isEmpty());
  }

  @Test
  @DisplayName("Deve acessar a url do servidor e retornar o menor e Maior Intervalo")
  public void testProducersMinMaxWinnerInterval() {
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://localhost:8080");
    String response = target.path("/movieproducers/minmaxwinnerinterval").request().get(String.class);
    MinMaxWinnerIntervalDTO result = new Gson().fromJson(response, MinMaxWinnerIntervalDTO.class);
    Assert.assertEquals(Integer.valueOf(1), result.getMin().get(0).getInterval());
    Assert.assertEquals(Integer.valueOf(13), result.getMax().get(0).getInterval());
    Assert.assertEquals("Joel Silver", result.getMin().get(0).getProducer());
    Assert.assertEquals("Matthew Vaughn", result.getMax().get(0).getProducer());
  }

  private static void initData() {
    new DataInitializatorService().init();
  }


}
