package com.gra.worstmovies.resources;

import com.google.gson.Gson;
import com.gra.worstmovies.entities.Movie;
import com.gra.worstmovies.repositories.MovieRepository;
import com.gra.worstmovies.services.MovieService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

  @GET
  public String getMovies() {
    List<Movie> all = new MovieRepository().getAll();
    return new Gson().toJson(all);
  }

  @GET
  @Path("/{id}")
  public String getMovies(@PathParam("id") Long id) {
    Movie movie = new MovieRepository().find(id);
    return new Gson().toJson(movie);
  }


  @POST
  public String save(Movie movie) {
    new MovieService().save(movie);
    return new Gson().toJson(movie);
  }

  @PUT
  @Path("/{id}")
  public String update(Movie movie, @PathParam("id") Long id) {
    new MovieService().update(movie);
    return new Gson().toJson(movie);
  }

}
