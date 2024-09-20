package dk.obhnothing.persistence.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.dao.OurDB;
import dk.obhnothing.persistence.dto.tMDBBase;
import dk.obhnothing.persistence.dto.tMDBBaseLst;
import dk.obhnothing.persistence.dto.tMDBFullDesc;
import dk.obhnothing.persistence.dto.tMDBPers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

/*
 * Web development....
 * -------------------
 * Oskar Bahner Hansen
 * .........2024-09-17
 * -------------------
 */

public class NetScrape
{

    private static int pagesize = 20; // TODO: get this value by dividing totalpages / totalresults in response
    private static String baseurl = "https://api.themoviedb.org/3/";
    private static String apiToken;
    public static void Init(String apitoken) { apiToken = apitoken; }

    public static Integer searchAndStore(SearchCriteria criteria)
    {
        int nCores = 1; // Runtime.getRuntime().availableProcessors();
        ExecutorService jobqueue = Executors.newFixedThreadPool(nCores);
        int nPages = (criteria.maxResults + pagesize - 1) / pagesize;
        int nPagesPerThread = (nPages + nCores - 1) / nCores;
        System.out.printf("ncores: %d, nPages: %d, nPagesPerThread: %d%n%n%n", nCores, nPages, nPagesPerThread);
        System.out.println(criteria);

        List<Future<Integer>> results = new ArrayList<>();
        for (int i = 0; i < nCores; i++) {
            SearchCriteria c = new SearchCriteria(criteria);
            c.pageIndex = i * nPagesPerThread + 1;
            c.maxResults = Math.min(nPagesPerThread * pagesize,
                    c.maxResults - i * pagesize);
            results.add(jobqueue.submit(() -> searchAndStoreN(c)));
        }
        try {
            jobqueue.shutdown();
            jobqueue.awaitTermination(60, TimeUnit.SECONDS);
            return results.stream().map(f -> {
                try {
                    return f.get();
                } catch (InterruptedException | ExecutionException e) {
                    return 0;
                }
            }).reduce(0, (Integer s, Integer i) -> Integer.sum(s, i));
        } catch (Exception e) {
            return 0;
        }
    }

    private static Integer searchAndStoreN(SearchCriteria criteria)
    {
        if (criteria.maxResults < 1)
            return 0;
        System.out.printf("thread %d: page %d, max: %d%n%n",
                Thread.currentThread().getId(), criteria.pageIndex, criteria.maxResults);
        System.out.println(criteria);
        List<tMDBBase> baseResults = search(criteria);
        for (tMDBBase tMDBBase : baseResults)
            OurDB.ourDBMovie_Create(Mapping.tMDBFullDesc_OurDBMovie(fetchDets(tMDBBase.id)));
        return baseResults.size();
    }

    public static List<tMDBBase> search(SearchCriteria sc)
    {
        System.out.println("Search for " + sc.toString());
        List<tMDBBase> finalResults = new ArrayList<>();
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();

        try {
            while (true) {
                HttpRequest request = HttpRequest.newBuilder().uri(buildURI(sc)).
                    setHeader("accept", "application/json").
                    setHeader("Authorization", "Bearer " + apiToken).GET().build();
                System.err.println("fetching: " + request.uri().toString());
                //System.err.println(request.headers());
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                if (response.statusCode() != 200) {
                    //System.out.println(response.statusCode());
                    //System.out.println(response.statusCode());
                    //System.out.println(response.statusCode());
                    return finalResults;
                }

                String responseStr = response.body();
                tMDBBaseLst results = jsonMapper.readValue(responseStr, tMDBBaseLst.class);
                for (tMDBBase tMDBBase : results.results) {
                    finalResults.add(tMDBBase);
                    if (finalResults.size() >= sc.maxResults)
                        return finalResults;
                }
                sc.pageIndex++;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
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
            //System.err.println("fetching: " + request.toString());
            //System.err.println(request.headers());
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
            //System.err.println("fetching: " + request.toString());
            //System.err.println(request.headers());
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
    @NoArgsConstructor @AllArgsConstructor @Builder @ToString
    public static class SearchCriteria
    {
        @Builder.Default public Boolean includeAdult = false;
        @Builder.Default public Boolean includeVideo = false;
        @Builder.Default public String language = "en-US";
        @Builder.Default public String originCountry = "US";
        @Builder.Default public String sortingCriteria = "popularity";
        @Builder.Default public Boolean sortAsc = false;
        @Builder.Default public Integer pageIndex = 1;
        @Builder.Default public Integer maxResults = 1;
        public SearchCriteria(SearchCriteria c) {
            includeAdult = c.includeAdult;
            includeVideo = c.includeVideo;
            language = c.language;
            originCountry = c.originCountry;
            sortingCriteria = c.sortingCriteria;
            sortAsc = c.sortAsc;
            pageIndex = c.pageIndex;
            maxResults = c.maxResults;
        }
    }


}

