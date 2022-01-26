package com.gra.worstmovies;

import com.google.gson.Gson;
import com.gra.worstmovies.entities.Movie;
import com.gra.worstmovies.services.MovieService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.List;

public class IntegrationTest {

    private HttpServer httpServer;

    @Before
    public void before(){
        httpServer = new Server().initServer();
    }

    @After
    public void after(){
        if(httpServer != null) {
            httpServer.stop();
        }
    }

    @Test
    @DisplayName("Deve verifica se servidor iniciou")
    public void testServerConnection(){
        Assert.assertTrue(httpServer.isStarted());
    }

    @Test
    @DisplayName("Deve acessar a url do servidor e retornar um array de Movie com tamanho 1")
    public void testMovieGetRequest(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        new MovieService().create(2022, "Movie", Boolean.TRUE);
        String response = target.path("/movies").request().get(String.class);
        List<?> result = new Gson().fromJson(response, List.class);
        Assert.assertEquals(1, result.size());
    }


}
