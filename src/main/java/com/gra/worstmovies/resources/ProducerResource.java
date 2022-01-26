package com.gra.worstmovies.resources;

import com.google.gson.Gson;
import com.gra.worstmovies.dto.MinMaxWinnerIntervalDTO;
import com.gra.worstmovies.services.MovieService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/producers")
public class ProducerResource {

  @GET
  @Path("/minmaxwinnerinterval")
  @Produces(MediaType.APPLICATION_JSON)
  public String getMinMaxIntervalProducersWinners() {
    MinMaxWinnerIntervalDTO minMaxWinnerIntervalDTO = new MovieService().getWorstMovieWinners();
    return new Gson().toJson(minMaxWinnerIntervalDTO);
  }

}
