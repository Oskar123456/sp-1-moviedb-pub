package dk.obhnothing.persistence.service;

import dk.obhnothing.persistence.dto.tMDBFullDesc;
import dk.obhnothing.persistence.entities.OurDBMovie;

/**
 * Mapper
 */
public class Mapping
{

    public static OurDBMovie tMDB_OurDB(tMDBFullDesc details)
    {
        OurDBMovie m = new OurDBMovie();
        m.adult = details.adult;
        m.backdrop_path = details.backdrop_path;
        m.original_language = details.original_language;
        m.original_title = details.original_title;
        m.overview = details.overview;
        m.popularity = details.popularity;
        m.poster_path = details.poster_path;
        m.release_date = details.release_date;
        m.title = details.title;
        m.video = details.video;
        m.vote_average = details.vote_average;
        m.vote_count = details.vote_count;
        m.budget = details.budget;
        m.homepage = details.homepage;
        m.imdb_id = details.imdb_id;
        m.revenue = details.revenue;
        m.runtime = details.runtime;
        m.status = details.status;
        m.tagline = details.tagline;
        /* RELATIONS */
        // m.collection = new OurDBColl().withUId(details.belongs_to_collection.id);
        // m.origin_country = new HashSet<>(Arrays.stream(details.origin_country).map(g -> new OurDBCountry().withUId(g)).toList());
        // m.genres = new HashSet<>(Arrays.stream(details.genres).map(g -> new OurDBGenre().withUId(g.id)).toList());
        // m.keywords = new HashSet<>(Arrays.stream(details.keywords.keywords).map(g -> new OurDBKeyword().withUId(g.id)).toList());
        // m.cast = new HashSet<>(Arrays.stream(details.credits.cast).map(NetScrape::mapCreditActorDTOToEnt).toList());
        // m.crew = new HashSet<>(Arrays.stream(details.credits.crew).map(NetScrape::mapCreditCrewDTOToEnt).toList());
        // m.spoken_languages = new HashSet<>(Arrays.stream(details.spoken_languages).map(g -> new OurDBLang().withUId(g.iso_639_1)).toList());
        // m.production_companies = new HashSet<>(Arrays.stream(details.production_companies).map(g -> new OurDBCmp().withUId(g.id)).toList());
        // m.production_countries = new HashSet<>(Arrays.stream(details.production_countries).map(g -> new OurDBCountry().withUId(g.iso_3166_1)).toList());
        return m;
    }

    public static void storeMovie()
    {
    }


}
