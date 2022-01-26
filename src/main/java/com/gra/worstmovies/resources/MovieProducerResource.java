package com.gra.worstmovies.resources;

import com.google.gson.Gson;
import com.gra.worstmovies.dto.MinMaxWinnerIntervalDTO;
import com.gra.worstmovies.services.MovieProducerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/movieproducers")
public class MovieProducerResource {

  @GET
  @Path("/minmaxwinnerinterval")
  @Produces(MediaType.APPLICATION_JSON)
  public String getMinMaxIntervalProducersWinners() {
    MinMaxWinnerIntervalDTO minMaxWinnerIntervalDTO = new MovieProducerService().getWorstMovieWinners();
    return new Gson().toJson(minMaxWinnerIntervalDTO);
  }

}
