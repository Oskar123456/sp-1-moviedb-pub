package dk.obhnothing.persistence.entities;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/**
 * Movie
 */
@Entity
public class Movie
{

    @Id @GeneratedValue public Integer id;
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
    /* RELATIONS */
    @ManyToMany public Set<Genre> genres;
    @ManyToMany public Set<MKeyword> keywords;
    @OneToMany public Set<MCreditActor> actors;
    @OneToMany public Set<MCreditCrew> crew;
    @ManyToOne public Set<Country> origin_country;
    @ManyToOne public Set<Language> spoken_languages;
    @ManyToMany public Set<Company> production_companies;
    @ManyToMany public Set<Country> production_countries;
    @OneToOne public MCollection collection;

}













