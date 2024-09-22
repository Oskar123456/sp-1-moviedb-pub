package dk.obhnothing.persistence.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.dao.OurDB;
import dk.obhnothing.persistence.dto.tMDBBase;
import dk.obhnothing.persistence.dto.tMDBBaseLst;
import dk.obhnothing.persistence.dto.tMDBFullDesc;
import dk.obhnothing.persistence.dto.tMDBPers;
import dk.obhnothing.persistence.entities.OurDBCast;
import dk.obhnothing.persistence.entities.OurDBCrew;
import dk.obhnothing.persistence.entities.OurDBMovie;
import dk.obhnothing.persistence.entities.OurDBPers;
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

    public static int searchAndStore(SearchCriteria criteria)
    {
        int nCores = Runtime.getRuntime().availableProcessors();
        ExecutorService jobqueue = Executors.newFixedThreadPool(nCores);
        int nPages = (criteria.maxResults + pagesize - 1) / pagesize;
        int nPagesPerThread = (nPages + nCores - 1) / nCores;
        System.out.printf("ncores: %d, nPages: %d, nPagesPerThread: %d%n%n%n", nCores, nPages, nPagesPerThread);
        System.out.println(criteria);

        List<Future<List<OurDBMovie>>> results = new ArrayList<>();
        for (int i = 0; i < nCores; i++) {
            SearchCriteria c = new SearchCriteria(criteria);
            c.pageIndex = i * nPagesPerThread + 1;
            c.maxResults = Math.min(nPagesPerThread * pagesize,
                    c.maxResults - i * pagesize * nPagesPerThread);
            results.add(jobqueue.submit(() -> searchAndFetchDetails(c)));
        }

        int res = 0;
        try {
            jobqueue.shutdown();
            for (Future<List<OurDBMovie>> f : results) {
                System.out.printf("Waiting for results... (%d / %d done)%n", res, criteria.maxResults);
                List<OurDBMovie> ms = f.get();
                for (OurDBMovie movie : ms) {
                    if (OurDB.ourDBMovie_Create(movie) != null)
                        res++;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.printf("Fetched %d / %d results...%n", res, criteria.maxResults);
        return res;
    }

    private static List<OurDBMovie> searchAndFetchDetails(SearchCriteria criteria)
    {
        List<OurDBMovie> finalResults = new ArrayList<>();
        if (criteria.maxResults < 1)
            return finalResults;
        //System.out.printf("thread %d: page %d, max: %d%n%n",
        //      Thread.currentThread().getId(), criteria.pageIndex, criteria.maxResults);
        //System.out.println(criteria);
        List<tMDBBase> baseResults = search(criteria);
        for (tMDBBase tMDBBase : baseResults)
            finalResults.add(Mapping.tMDBFullDesc_OurDBMovie(fetchDets(tMDBBase.id)));
        for (OurDBMovie om : finalResults) {
            for (OurDBCast oc : om.cast)
                oc.person = fetchPerson(oc.person.ext_id);
            /* only include directors; takes too long to fetch full crew */
            List<OurDBCrew> toRemove = new ArrayList<>();
            for (OurDBCrew oc : om.crew) {
                if (oc.job.toLowerCase().equals("director"))
                    oc.person = fetchPerson(oc.person.ext_id);
                else
                    toRemove.add(oc);
            }
            om.crew.removeAll(toRemove);
        }
        return finalResults;
    }

    public static List<tMDBBase> search(SearchCriteria sc)
    {
        //System.out.println("Search for " + sc.toString());
        List<tMDBBase> finalResults = new ArrayList<>();
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();

        try {
            while (true) {
                HttpRequest request = HttpRequest.newBuilder().uri(buildURI(sc)).
                    setHeader("accept", "application/json").
                    setHeader("Authorization", "Bearer " + apiToken).GET().build();
                //System.err.println("fetching: " + request.uri().toString());
                //System.err.println(request.headers());
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    return finalResults;
                }

                String responseStr = response.body();
                tMDBBaseLst results = jsonMapper.readValue(responseStr, tMDBBaseLst.class);

                if (results.results.length < 1) {
                    return finalResults;
                }

                for (tMDBBase tMDBBase : results.results) {
                    if (OurDB.ourDBMovie_FindByExtId(tMDBBase.id) != null)
                        continue;
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

    public static OurDBPers fetchPerson(Integer pId)
    {
        OurDBPers tryFind = null;
        if ((tryFind = OurDB.ourDBPers_FindByExtId(pId)) != null)
            return tryFind;
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();

        try {
            HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(baseurl + "person/" + pId.toString())).
                setHeader("accept", "application/json").
                setHeader("Authorization", "Bearer " + apiToken).GET().build();
            //System.err.println("fetching: " + request.toString());
            //System.ern.println(request.headers());
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
            String responseStr = response.body();
            return Mapping.tMDBPers_OurDBPers(jsonMapper.readValue(responseStr, tMDBPers.class));
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
        DateTimeFormatter ft = DateTimeFormatter.ISO_LOCAL_DATE;
        uriStr += "&primary_release_date.gte=" + ft.format(sc.after);
        uriStr += "&release_date.gte=" + ft.format(sc.after);
        uriStr += "&primary_release_date.lte=" + ft.format(sc.before);
        uriStr += "&release_date.lte=" + ft.format(sc.before);
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
        @Builder.Default public LocalDate before = LocalDate.now();
        @Builder.Default public LocalDate after = LocalDate.now().minusYears(200);
        public SearchCriteria(SearchCriteria c) {
            includeAdult = c.includeAdult;
            includeVideo = c.includeVideo;
            language = c.language;
            originCountry = c.originCountry;
            sortingCriteria = c.sortingCriteria;
            sortAsc = c.sortAsc;
            pageIndex = c.pageIndex;
            maxResults = c.maxResults;
            before = c.before;
            after = c.after;
        }
    }

}















