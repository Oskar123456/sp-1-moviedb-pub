package dk.obhnothing.persistence.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.dto.tMDBBase;
import dk.obhnothing.persistence.dto.tMDBBaseLst;
import dk.obhnothing.persistence.dto.tMDBFullDesc;
import dk.obhnothing.persistence.dto.tMDBPers;
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
    private static String apiToken;
    public static void Init(String apitoken) { apiToken = apitoken; }

    public static List<tMDBBase> fetch(SearchCriteria sc)
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

    public static tMDBFullDesc fetchDets(Integer mId)
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

    public static tMDBPers fetchPerson(Integer pId)
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

