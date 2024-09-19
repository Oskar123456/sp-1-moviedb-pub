package dk.obhnothing.persistence.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.dto.CreditActorDTO;
import dk.obhnothing.persistence.dto.CreditCrewDTO;
import dk.obhnothing.persistence.dto.GenreDTO;
import dk.obhnothing.persistence.dto.MDetailsDTO;
import dk.obhnothing.persistence.dto.MKeywordDTO;
import dk.obhnothing.persistence.dto.tMDBBase;
import dk.obhnothing.persistence.dto.tMDBBaseLst;
import dk.obhnothing.persistence.dto.tMDBFullDesc;
import dk.obhnothing.persistence.dto.tMDBPers;
import dk.obhnothing.persistence.entities.OurDBCmp;
import dk.obhnothing.persistence.entities.OurDBCountry;
import dk.obhnothing.persistence.entities.OurDBGenre;
import dk.obhnothing.persistence.entities.OurDBLang;
import dk.obhnothing.persistence.entities.OurDBColl;
import dk.obhnothing.persistence.entities.OurDBCast;
import dk.obhnothing.persistence.entities.OurDBCrew;
import dk.obhnothing.persistence.entities.OurDBKeyword;
import dk.obhnothing.persistence.entities.OurDBMovie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * .........2024-09-17
 * -------------------
 */

public class NetScrape
{

    private static String baseurl = "https://api.themoviedb.org/3/";

    public static List<tMDBBase> fetch(SearchCriteria sc, String apiToken)
    {
        List<tMDBBase> finalResults = new ArrayList<>();
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();

        try {
            for (int i = 0; i < sc.pageTotal; i++) {
                HttpRequest request = HttpRequest.newBuilder().uri(buildURI(sc)).
                    setHeader("accept", "application/json").
                    setHeader("Authorization", "Bearer " + apiToken).GET().build();
                System.err.println("fetching: " + request.uri().toString());
                System.err.println(request.headers());
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                String responseStr = response.body();
                tMDBBaseLst results = jsonMapper.readValue(responseStr, tMDBBaseLst.class);
                finalResults.addAll(List.of(results.results));
                sc.pageIndex++;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return finalResults;
    }

    public static tMDBFullDesc fetchDets(Integer mId, String apiToken)
    {
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(baseurl + "movie/" + mId.toString()
                            + "?append_to_response=credits,keywords")).
                setHeader("accept", "application/json").
                setHeader("Authorization", "Bearer " + apiToken).GET().build();
            System.err.println("fetching: " + request.toString());
            System.err.println(request.headers());
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String responseStr = response.body();
            return jsonMapper.readValue(responseStr, tMDBFullDesc.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static tMDBPers fetchPerson(Integer pId, String apiToken)
    {
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(baseurl + "person/" + pId.toString())).
                setHeader("accept", "application/json").
                setHeader("Authorization", "Bearer " + apiToken).GET().build();
            System.err.println("fetching: " + request.toString());
            System.err.println(request.headers());
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String responseStr = response.body();
            return jsonMapper.readValue(responseStr, tMDBPers.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static OurDBCast mapCreditActorDTOToEnt(CreditActorDTO c) { return new OurDBCast(c.id, c.order, c.character, null, null); }
    public static OurDBCrew mapCreditCrewDTOToEnt(CreditCrewDTO c) { return new OurDBCrew(c.id, c.job, null, null); }
    public static OurDBGenre mapGenreDTOToEnt(GenreDTO g) { return new OurDBGenre().withUId(g.id); }
    public static OurDBKeyword mapKeywordDTOToEnt(MKeywordDTO k) { return new OurDBKeyword().withUId(k.id); }
    public static OurDBLang mapLanguageDTOToEnt(MDetailsDTO.SpokenLanguage l) { return new OurDBLang().withUId(l.iso_639_1); }
    public static OurDBCountry mapCountryDTOToEnt(MDetailsDTO.ProductionCountry c) { return new OurDBCountry().withUId(c.iso_3166_1); }
    public static OurDBCmp mapCompanyDTOToEnt(MDetailsDTO.ProductionCompany c) { return new OurDBCmp().withUId(c.id); }
    public static OurDBColl mapCollectionDTOToEnt(MDetailsDTO.Collection c) { return new OurDBColl().withUId(c.id); }

    public static OurDBMovie mapDTOtoEnt(MDetailsDTO details)
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
        m.collection = new OurDBColl().withUId(details.belongs_to_collection.id);
        m.origin_country = new HashSet<>(Arrays.stream(details.origin_country).map(g -> new OurDBCountry().withUId(g)).toList());
        m.genres = new HashSet<>(Arrays.stream(details.genres).map(g -> new OurDBGenre().withUId(g.id)).toList());
        m.keywords = new HashSet<>(Arrays.stream(details.keywords.keywords).map(g -> new OurDBKeyword().withUId(g.id)).toList());
        m.cast = new HashSet<>(Arrays.stream(details.credits.cast).map(NetScrape::mapCreditActorDTOToEnt).toList());
        m.crew = new HashSet<>(Arrays.stream(details.credits.crew).map(NetScrape::mapCreditCrewDTOToEnt).toList());
        m.spoken_languages = new HashSet<>(Arrays.stream(details.spoken_languages).map(g -> new OurDBLang().withUId(g.iso_639_1)).toList());
        m.production_companies = new HashSet<>(Arrays.stream(details.production_companies).map(g -> new OurDBCmp().withUId(g.id)).toList());
        m.production_countries = new HashSet<>(Arrays.stream(details.production_countries).map(g -> new OurDBCountry().withUId(g.iso_3166_1)).toList());
        return m;
    }

    public static void storeMovie()
    {
    }

    private static URI buildURI(SearchCriteria sc)
    {
        String uriStr = baseurl + "discover/movie?";
        uriStr += "page=" + ((sc.pageIndex != null) ? sc.pageIndex.toString() : 1);
        uriStr += "include_adult=" + ((sc.includeAdult != null) ? sc.includeAdult.toString() : false);
        uriStr += "&include_video=" + ((sc.includeVideo != null) ? sc.includeVideo.toString() : false);
        uriStr += "&language=" + ((sc.language != null) ? sc.language : "en-US");
        uriStr += "&sort_by=" + sc.sortingCriteria + ((sc.sortAsc != null && sc.sortAsc) ? ".asc" : ".desc");
        uriStr += "&with_origin_country=" + ((sc.originCountry != null) ? sc.originCountry : "US");
        return URI.create(uriStr);
    }

    /**
     * SearchCriteria used in movie fetches (scrapes)
     */
    @NoArgsConstructor @AllArgsConstructor @Builder
    public static class SearchCriteria
    {
        public Boolean includeAdult;
        public Boolean includeVideo;
        public String language;
        public String originCountry;
        public String sortingCriteria;
        public Boolean sortAsc;
        public Integer pageIndex;
        public Integer pageTotal;
    }


}

