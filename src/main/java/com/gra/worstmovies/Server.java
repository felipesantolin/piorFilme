package com.gra.worstmovies;

import com.gra.worstmovies.services.DataInitializatorService;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Server {


    public static void main(String[] args) throws IOException {
        Server server = new Server();
        HttpServer httpServer = server.initServer();
        new DataInitializatorService().init();
        System.out.println("#### SERVER Started ####");
        System.out.println("#### http://localhost:8080/producers/minmaxwinnerinterval ####");
        System.in.read();
        httpServer.stop();
    }


    public HttpServer initServer() {
        ResourceConfig rc = new ResourceConfig();
        rc.packages("com.gra.worstmovies.resources");
        URI uri = URI.create("http://localhost:8080/");
        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(uri, rc);
        return httpServer;
    }

}
