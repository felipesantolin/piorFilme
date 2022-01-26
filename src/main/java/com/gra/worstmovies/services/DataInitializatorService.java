package com.gra.worstmovies.services;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.gra.worstmovies.entities.Movie;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


public class DataInitializatorService {


  private MovieService movieService;
  private MovieStudioService movieStudioService;
  private MovieProducerService movieProducerService;


  public DataInitializatorService() {
    this.movieService = new MovieService();
    this.movieProducerService = new MovieProducerService();
    this.movieStudioService = new MovieStudioService();
  }

  public void init() {
    createMoviesFromFile();
  }

  private void createMoviesFromFile() {
    try {
      CSVReader reader = createCSVReader();
      List<String[]> lines = reader.readAll();
      for (String[] line : lines) {
        createMovieFromLine(line);
      }
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }

  private CSVReader createCSVReader() throws IOException {
    String file = getClass().getClassLoader().getResource("movielist.csv").getFile();
    CSVParser csvParser = new CSVParserBuilder()
            .withSeparator(';')
            .withIgnoreQuotations(true)
            .build();
    CSVReader csvReader = new CSVReaderBuilder(new FileReader(file))
            .withSkipLines(1)
            .withCSVParser(csvParser)
            .build();
    return csvReader;
  }

  private void createMovieFromLine(String[] line) {
    Boolean winner = line.length == 5 && line[4].equalsIgnoreCase("yes");
    Movie movie = movieService.create(Integer.parseInt(line[0]), line[1], winner);

    createStudiosFromLine(movie, line[2]);
    createProducersFromLine(movie, line[3]);
    movie.setWinner(winner);
  }

  private void createStudiosFromLine(Movie movie, String studiosFromLine) {
    studiosFromLine = studiosFromLine.replaceAll("and", ",");
    String[] splitStudios = studiosFromLine.split(",");
    for (int i = 0; i < splitStudios.length; i++) {
      String studio = splitStudios[i];
      movieStudioService.create(movie, studio);
    }
  }

  private void createProducersFromLine(Movie movie, String producersFromLine) {
    producersFromLine = producersFromLine.replaceAll("\\sand\\s", ",");
    String[] splitProducers = producersFromLine.split(",");
    for (int i = 0; i < splitProducers.length; i++) {
      String producer = splitProducers[i];
      if(!producer.trim().isEmpty()) {
        movieProducerService.create(movie, producer.trim());
      }
    }
  }

}
