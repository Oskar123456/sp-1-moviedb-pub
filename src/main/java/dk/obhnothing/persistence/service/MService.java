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
import dk.obhnothing.persistence.dto.MBaseDTO;
import dk.obhnothing.persistence.dto.MDetailsDTO;
import dk.obhnothing.persistence.dto.MKeywordDTO;
import dk.obhnothing.persistence.dto.PersonDTO;
import dk.obhnothing.persistence.entities.Company;
import dk.obhnothing.persistence.entities.Country;
import dk.obhnothing.persistence.entities.Genre;
import dk.obhnothing.persistence.entities.Language;
import dk.obhnothing.persistence.entities.MCollection;
import dk.obhnothing.persistence.entities.MCreditActor;
import dk.obhnothing.persistence.entities.MCreditCrew;
import dk.obhnothing.persistence.entities.MKeyword;
import dk.obhnothing.persistence.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * .........2024-09-17
 * -------------------
 */

public class MService
{

    private static String baseurl = "https://api.themoviedb.org/3/";

    public static List<MBaseDTO> fetch(SearchCriteria sc, String apiToken)
    {
        List<MBaseDTO> finalResults = new ArrayList<>();
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
                MList results = jsonMapper.readValue(responseStr, MList.class);
                finalResults.addAll(List.of(results.results));
                sc.pageIndex++;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return finalResults;
    }

    public static MDetailsDTO fetchDets(Integer mId, String apiToken)
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
            return jsonMapper.readValue(responseStr, MDetailsDTO.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static List<CreditActorDTO> fetchActorCreds(Integer mId, String apiToken)
    {
        List<CreditActorDTO> finalResults = new ArrayList<>();
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder().
                setHeader("accept", "application/json").
                uri(URI.create(baseurl + "movie/" + mId.toString() + "/credits")).
                setHeader("Authorization", "Bearer " + apiToken).GET().build();
            System.err.println("fetching: " + request.toString());
            System.err.println(request.headers());
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String responseStr = response.body();
            MCreditList results = jsonMapper.readValue(responseStr, MCreditList.class);
            finalResults.addAll(List.of(results.cast));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return finalResults;
    }

    public static PersonDTO fetchPerson(Integer pId, String apiToken)
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
            return jsonMapper.readValue(responseStr, PersonDTO.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static MCreditActor mapCreditActorDTOToEnt(CreditActorDTO c) { return new MCreditActor(c.id, c.order, c.character, null, null); }
    public static MCreditCrew mapCreditCrewDTOToEnt(CreditCrewDTO c) { return new MCreditCrew(c.id, c.job, null, null); }
    public static Genre mapGenreDTOToEnt(GenreDTO g) { return new Genre().withUId(g.id); }
    public static MKeyword mapKeywordDTOToEnt(MKeywordDTO k) { return new MKeyword().withUId(k.id); }
    public static Language mapLanguageDTOToEnt(MDetailsDTO.SpokenLanguage l) { return new Language().withUId(l.iso_639_1); }
    public static Country mapCountryDTOToEnt(MDetailsDTO.ProductionCountry c) { return new Country().withUId(c.iso_3166_1); }
    public static Company mapCompanyDTOToEnt(MDetailsDTO.ProductionCompany c) { return new Company().withUId(c.id); }
    public static MCollection mapCollectionDTOToEnt(MDetailsDTO.Collection c) { return new MCollection().withUId(c.id); }

    public static Movie mapDTOtoEnt(MDetailsDTO details)
    {
        Movie m = new Movie();
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
        m.genres = new HashSet<>(Arrays.stream(details.genres).map(MService::mapGenreDTOToEnt).toList());
        //m.genres = new HashSet<>(Arrays.asList(details.genres));
        //m.keywords = new HashSet<>(Arrays.asList(details.keywords));
        //m.actors = new HashSet<>(Arrays.asList(details.actors));
        //m.crew = new HashSet<>(Arrays.asList(details.crew));
        //m.origin_country = new HashSet<>(Arrays.asList(details.origin_country));
        //m.spoken_languages = new HashSet<>(Arrays.asList(details.spoken_languages));
        //m.production_companies = new HashSet<>(Arrays.asList(details.production_companies));
        //m.production_countries = new HashSet<>(Arrays.asList(details.production_countries));
        //m.collection = new MCollection();
        return m;
    }

    public static void storeMovie()
    {

        /*
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
    public Set<Genre> genres;
    public Set<MKeyword> keywords;
    public Set<MCreditActor> actors;
    public Set<MCreditCrew> crew;
    public Set<Country> origin_country;
    public Set<Language> spoken_languages;
    public Set<Company> production_companies;
    public Set<Country> production_countries;
    public MCollection collection;
         */
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

/**
 * MResults
 */
@EqualsAndHashCode
@NoArgsConstructor
class MList
{
    public Integer page;
    public Integer total_pages;
    public Integer total_results;
    public MBaseDTO[] results;
}

/**
 * MCreditList
 */
@EqualsAndHashCode
@NoArgsConstructor
class MCreditList
{
    public Integer id;
    public CreditActorDTO[] cast;
    public CreditCrewDTO[] crew;
}

