package dk.obhnothing.persistence.dto;

import java.time.LocalDate;

/**
 * MDetailsDTO
 */
public class MDetailsDTO
{

  Boolean adult;
  String backdrop_path;
  Collection belongs_to_collection; // class
  static class Collection {
      String id;
      String name;
      String poster_path;
      String backdrop_path;
  }
  Double budget;
  GenreDTO[] genres;
  String homepage;
  Integer id;
  String imdb_id;
  String[] origin_country;
  String original_language;
  String original_title;
  String overview;
  Double popularity;
  String poster_path;
  ProductionCompany[] production_companies; //class
  static class ProductionCompany {
      String id;
      String logo_path;
      String name;
      String origin_country;
  }
  ProductionCountry[] production_countries; //class
  static class ProductionCountry {
      String iso_3166_1;
      String name;
  }
  LocalDate release_date;
  Double revenue;
  Double runtime;
  String spoken_languages; //class
      String english_name;
      String iso_639_1;
      String name;
  String status;
  String tagline;
  String title;
  Boolean video;
  Double vote_average;
  Integer vote_count;

}
