package dk.obhnothing.persistence.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dk.obhnothing.persistence.dto.MBaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * MService
 */
public class MService
{

    private static String baseurl = "https://api.themoviedb.org/3/discover/movie?";

    public static List<MBaseDTO> fetch(SearchCriteria sc, String apiToken)
    {
        List<MBaseDTO> finalResults = new ArrayList<>();
        ObjectMapper jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        HttpClient client = HttpClient.newHttpClient();

        try {
            for (int i = 0; i < sc.pageTotal; i++) {
                HttpRequest request = HttpRequest.newBuilder().uri(buildURI(sc)).
                    setHeader("Authorization", "Bearer " + apiToken).GET().build();
                System.err.println("fetching: " + request.uri().toString());
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
                String responseStr = response.body();
                //System.err.println("response: " + responseStr);
                MResults results = jsonMapper.readValue(responseStr, MResults.class);
                finalResults.addAll(List.of(results.results));
                sc.pageIndex++;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }

        return finalResults;
    }

    private static URI buildURI(SearchCriteria sc)
    {
        String uriStr = baseurl;
        uriStr += "page=" + ((sc.pageIndex != null) ? sc.pageIndex.toString() : 1);
        uriStr += "include_adult=" + ((sc.includeAdult != null) ? sc.includeAdult.toString() : false);
        uriStr += "&include_video=" + ((sc.includeVideo != null) ? sc.includeVideo.toString() : false);
        uriStr += "&language=" + ((sc.language != null) ? sc.language : "en-US");
        uriStr += "&sort_by=" + sc.sortingCriteria + ((sc.sortAsc != null && sc.sortAsc) ? ".asc" : ".desc");
        uriStr += "&with_origin_country=" + ((sc.originCountry != null) ? sc.originCountry : "US");
        return URI.create(uriStr);
    }

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
class MResults
{
    public Integer page;
    public Integer total_pages;
    public Integer total_results;
    public MBaseDTO[] results;
}





















