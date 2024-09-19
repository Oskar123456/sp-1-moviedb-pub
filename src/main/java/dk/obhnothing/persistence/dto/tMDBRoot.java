package dk.obhnothing.persistence.dto;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;

/**
 * MDetailsDTO
 */
@EqualsAndHashCode
public class MDetailsDTO
{

    public Boolean adult;
    public String backdrop_path;
    public Collection belongs_to_collection;
    public Double budget;
    public GenreDTO[] genres;
    public String homepage;
    public Integer id;
    public String imdb_id;
    public String[] origin_country;
    public String original_language;
    public String original_title;
    public String overview;
    public Double popularity;
    public String poster_path;
    public ProductionCompany[] production_companies;
    public ProductionCountry[] production_countries;
    public LocalDate release_date;
    public Double revenue;
    public Double runtime;
    public SpokenLanguage[] spoken_languages;
    public String status;
    public String tagline;
    public String title;
    public Boolean video;
    public Double vote_average;
    public Integer vote_count;
    public KeywordList keywords;
    public Credits credits;

    @EqualsAndHashCode
    public static class ProductionCompany {
        public Integer id;
        public String logo_path;
        public String name;
        public String origin_country;
    }

    @EqualsAndHashCode
    public static class ProductionCountry {
        public String iso_3166_1;
        public String name;
    }

    @EqualsAndHashCode
    public static class SpokenLanguage {
        public String english_name;
        public String iso_639_1;
        public String name;
    }

    @EqualsAndHashCode
    public static class Collection {
        public Integer id;
        public String name;
        public String poster_path;
        public String backdrop_path;
    }

    @EqualsAndHashCode
    public static class Credits {
        public CreditActorDTO[] cast;
        public CreditCrewDTO[] crew;
    }

    @EqualsAndHashCode
    public static class KeywordList {
        public MKeywordDTO[] keywords;
    }

}

















