package com.gra.worstmovies;

import com.google.gson.Gson;
import com.gra.worstmovies.entities.Movie;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

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
    @Ignore
    public void testServerConnection(){
        Assert.assertTrue(httpServer.isStarted());
    }

    @Test
    @Ignore
    public void testMovieGetRequest(){
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080");
        String response = target.path("/movies").request().get(String.class);
        Movie movie = new Gson().fromJson(response, Movie.class);
        Assert.assertNotNull(movie);
    }


}
