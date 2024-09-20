package dk.obhnothing.persistence.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;

import dk.obhnothing.persistence.dto.tMDBCast;
import dk.obhnothing.persistence.dto.tMDBCmp;
import dk.obhnothing.persistence.dto.tMDBColl;
import dk.obhnothing.persistence.dto.tMDBCountry;
import dk.obhnothing.persistence.dto.tMDBCrew;
import dk.obhnothing.persistence.dto.tMDBFullDesc;
import dk.obhnothing.persistence.dto.tMDBGenre;
import dk.obhnothing.persistence.dto.tMDBKeyword;
import dk.obhnothing.persistence.dto.tMDBLang;
import dk.obhnothing.persistence.dto.tMDBPers;
import dk.obhnothing.persistence.entities.OurDBCast;
import dk.obhnothing.persistence.entities.OurDBCmp;
import dk.obhnothing.persistence.entities.OurDBColl;
import dk.obhnothing.persistence.entities.OurDBCountry;
import dk.obhnothing.persistence.entities.OurDBCrew;
import dk.obhnothing.persistence.entities.OurDBGenre;
import dk.obhnothing.persistence.entities.OurDBKeyword;
import dk.obhnothing.persistence.entities.OurDBLang;
import dk.obhnothing.persistence.entities.OurDBMovie;
import dk.obhnothing.persistence.entities.OurDBPers;
import dk.obhnothing.persistence.enums.OurDBStatus;

/**
 * Mapper
 */
public class Mapping
{
    /* null values are left for the database layer to fill in */
    public static OurDBCmp tMDBCmp_OurDBCmp(tMDBCmp cmpIn) {
        if (cmpIn == null) {
            return null;
        }
        return new OurDBCmp(null, cmpIn.logo_path, cmpIn.name, cmpIn.origin_country, null).withUId(cmpIn.id);
    }
    public static OurDBCountry tMDBCountry_OurDBCountry(tMDBCountry countryIn) {
        if (countryIn == null) {
            return null;
        }
        return new OurDBCountry(countryIn.iso_3166_1, countryIn.name, new HashSet<>(), new HashSet<>());
    }
    public static OurDBColl tMDBColl_OurDBColl(tMDBColl collIn) {
        if (collIn == null) {
            return null;
        }
        return new OurDBColl(null, collIn.name, collIn.poster_path, collIn.backdrop_path, new HashSet<>()).withUId(collIn.id);
    }
    public static OurDBLang tMDBLang_OurDBLang(tMDBLang langIn) {
        if (langIn == null) {
            return null;
        }
        return new OurDBLang(langIn.iso_639_1, langIn.english_name, langIn.name, new HashSet<>(), new HashSet<>());
    }
    public static OurDBGenre tMDBGenre_OurDBGenre(tMDBGenre genreIn) {
        if (genreIn == null) {
            return null;
        }
        return new OurDBGenre(null, genreIn.name, new HashSet<>());
    }
    public static OurDBKeyword tMDBKeyword_OurDBKeyword(tMDBKeyword keywordIn) {
        if (keywordIn == null) {
            return null;
        }
        return new OurDBKeyword(null, keywordIn.name, new HashSet<>());
    }
    public static OurDBPers tMDBPers_OurDBPers(tMDBPers persIn) {
        return new OurDBPers(null, persIn.adult, persIn.also_known_as, persIn.biography,
                persIn.birthday, persIn.deathday, persIn.gender, persIn.homepage, persIn.imdb_id,
                persIn.known_for_department, persIn.name, persIn.place_of_birth, persIn.popularity,
                persIn.profile_path, new HashSet<>(), new HashSet<>()).withUId(persIn.id);
    }
    public static OurDBCrew tMDBCrew_OurDBCrew(tMDBCrew crewIn) {
        return new OurDBCrew(null, crewIn.job, null, null);
    }
    public static OurDBCast tMDBCast_OurDBCast(tMDBCast castIn) {
        return new OurDBCast(null, castIn.order, castIn.character, null, null);
    }
    public static OurDBStatus tMDBStatus_OurDBStatus(String status) {
        if (status.toLowerCase().equals("released")) return OurDBStatus.Released; return OurDBStatus.Unknown;
    }
    public static OurDBMovie tMDBFullDesc_OurDBMovie(tMDBFullDesc details)
    {
        OurDBMovie m = new OurDBMovie().withUId(details.id);
        m.backdrop_path = details.backdrop_path;
        m.original_title = details.original_title;
        m.overview = details.overview;
        m.popularity = details.popularity;
        m.poster_path = details.poster_path;
        m.release_date = details.release_date;
        m.title = details.title;
        m.vote_average = details.vote_average;
        m.vote_count = details.vote_count;
        m.budget = details.budget;
        m.homepage = details.homepage;
        m.revenue = details.revenue;
        m.runtime = Duration.ofMinutes(details.runtime.longValue());
        m.tagline = details.tagline;
        m.status = tMDBStatus_OurDBStatus(details.status);
        /* relations */
        m.collection = tMDBColl_OurDBColl(details.belongs_to_collection);
        m.original_language_iso_639_1 = details.original_language;
        m.spoken_languages_iso_639_1 = new HashSet<>(Arrays.stream(details.spoken_languages).map(l -> l.iso_639_1).toList());
        m.origin_country_iso_3166_1 = new HashSet<>(Arrays.stream(details.origin_country).toList());
        m.production_countries_iso_3166_1 = new HashSet<>(Arrays.stream(details.production_countries).map(c -> c.iso_3166_1).toList());
        //m.spoken_languages_iso_639_1 = (String[]) Arrays.stream(details.spoken_languages).map(l -> l.iso_639_1).toArray();
        //m.origin_country_iso_3166_1 = details.origin_country;
        //m.production_countries_iso_3166_1 = (String[]) Arrays.stream(details.production_countries).map(c -> c.iso_3166_1).toArray();
        m.genres = new HashSet<>(Arrays.stream(details.genres).map(g -> new OurDBGenre(null, g.name, null)).toList());
        m.keywords = new HashSet<>(Arrays.stream(details.keywords.keywords).map(k -> new OurDBKeyword(null, k.name, null)).toList());
        m.cast = new HashSet<>(Arrays.stream(details.credits.cast).
                map(c -> new OurDBCast(null, c.order, c.character, new OurDBPers().withUId(c.id), null)).toList());
        m.crew = new HashSet<>(Arrays.stream(details.credits.crew).
                map(c -> new OurDBCrew(null, c.job, new OurDBPers().withUId(c.id), null)).toList());
        m.production_companies = new HashSet<>(Arrays.stream(details.production_companies).
                map(c -> new OurDBCmp(null, c.logo_path, c.name, c.origin_country, null).withUId(c.id)).toList());
        return m;
    }
}
















