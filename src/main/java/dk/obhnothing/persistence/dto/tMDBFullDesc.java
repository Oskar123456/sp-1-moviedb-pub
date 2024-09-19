package dk.obhnothing.persistence.dto;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;

/**
 * MDetailsDTO
 */
@EqualsAndHashCode
public class tMDBFullDesc
{
    public Boolean adult;
    public String backdrop_path;
    public Double budget;
    public String homepage;
    public Integer id;
    public String imdb_id;
    public String[] origin_country;
    public String original_language;
    public String original_title;
    public String overview;
    public Double popularity;
    public String poster_path;
    public LocalDate release_date;
    public Double revenue;
    public Double runtime;
    public String status;
    public String tagline;
    public String title;
    public Boolean video;
    public Double vote_average;
    public Integer vote_count;

    public tMDBGenre[] genres;
    public tMDBCmp[] production_companies;
    public tMDBCountry[] production_countries;
    public tMDBLang[] spoken_languages;
    public tMDBColl belongs_to_collection;
    public KeywordList keywords;
    public Credits credits;

    @EqualsAndHashCode
    public static class Credits {
        public tMDBCast[] cast;
        public tMDBCrew[] crew;
    }

    @EqualsAndHashCode
    public static class KeywordList {
        public tMDBKeyword[] keywords;
    }
}

















