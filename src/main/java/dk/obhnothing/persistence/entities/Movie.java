package dk.obhnothing.persistence.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;

/**
 * Movie
 */
@Entity
public class Movie
{

    public Integer id;
    public Boolean adult;
    public String backdrop_path;
    public String original_language;
    public String original_title;
    public String overview;
    public Double popularity;
    public String poster_path;
    public LocalDate release_date;
    public String title;
    public Boolean video;
    public Double vote_average;
    public Integer vote_count;
    public Double budget;
    public String homepage;
    public String imdb_id;
    public Double revenue;
    public Double runtime;
    public String status;
    public String tagline;
    // RELATIONS
    public Genre[] genres;
    public MKeyword[] keywords;
    public Country[] origin_country;
    public Language[] spoken_languages;
    public Company[] production_companies;
    public Country[] production_countries;
    public MCollection belongs_to_collection;

}













